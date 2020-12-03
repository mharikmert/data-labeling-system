package Project.JsonIO;// import necessary libraries
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Project.Instance;
import Project.Dataset;
import Project.Labeling.Label;
import Project.User;
import org.json.JSONArray;
import org.json.JSONObject;

// com.JsonIO.JsonFileWriter class
public class JsonFileWriter {
	// private data field
	private String path="";
    // class constructor
    public JsonFileWriter(String path){
        this.path=path;
    }
    // export method takes dataset and users as parameters then puts the information to a json object	
    public void Export(Dataset dataset, ArrayList<User> users){
        
	 // dataset part   
	JSONObject details = new JSONObject();
        details.put("dateset id",dataset.getId());
        details.put("dateset name",dataset.getDatasetName());
        details.put("maximum number of labels per instance",dataset.getMaxNumberOfLabelsPerInstance());
        
	 // label part
        JSONArray classLabels = new JSONArray();
        for(Label label: dataset.getLabels()){
            JSONObject classLabel=new JSONObject();
            classLabel.put("label id",label.getId());
            classLabel.put("label text",label.getText());
            classLabels.put(classLabel);
        }
        details.put("class labels",classLabels);


        // instances part
        JSONArray instances = new JSONArray();
        for (Instance instance:dataset.getInstances()) {
            JSONObject instanceObject=new JSONObject();
            instanceObject.put("id",instance.getId());
            instanceObject.put("instance",instance.getInstance());
            instances.put(instanceObject);
        }
        details.put("instances",instances);
        
        
	    
	 // writing the results of assignmets   
        JSONArray assignments = new JSONArray();
        for (Instance instance:dataset.getInstances()) {
            for(User user:users){
                for (Instance usr_inst:user.getInstances()){
                    if (instance.getId()==usr_inst.getId()){
                        JSONObject assignmentObject=new JSONObject();
                        assignmentObject.put("instance id",instance.getId());
                        JSONArray labelIDs = new JSONArray();
                        for(Label label:usr_inst.getLabels()) {
                        	labelIDs.put(label.getId());
                        }
                        assignmentObject.put("class label ids",labelIDs);
                        // date time operations
			LocalDateTime myDateObj = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy , HH:mm:ss");
                        String formattedDate = myDateObj.format(myFormatObj);
                        assignmentObject.put("date time",formattedDate);
                        assignments.put(assignmentObject);
                    }
                }
            }
        }
        details.put("class label assignments",assignments);


        // writing user information 
        JSONArray userList = new JSONArray();
        for(User user:users){
            JSONObject userJSONobject = new JSONObject();
            userJSONobject.put("user id",user.getUserID());
            userJSONobject.put("user name",user.getUserName());
            userJSONobject.put("user type",user.getUserType());
            userList.put(userJSONobject);
        }
        details.put("users",userList);


        // try-catch part
        try (FileWriter file = new FileWriter(path)) {
 
            file.write(details.toString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
	
}
