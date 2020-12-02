import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonFileWriter {
	
	private String path="";

    public JsonFileWriter(String path){
        this.path=path;
    }
    public void Export(Dataset dataset,ArrayList<User> users){
        JSONObject details = new JSONObject();
        details.put("dateset id",dataset.getId());
        details.put("dateset name",dataset.getDatasetName());
        details.put("maximum number of labels per instance",dataset.getMaxNumberOfLabelsPerInstance());
        
        JSONArray classLabels = new JSONArray();
        for(Label label: dataset.getLabels()){
            JSONObject classLabel=new JSONObject();
            classLabel.put("label id",label.getId());
            classLabel.put("label text",label.getText());
            classLabels.put(classLabel);
        }
        details.put("class labels",classLabels);

        JSONArray instances = new JSONArray();
        for (Instance instance:dataset.getInstances()) {
            JSONObject instanceObject=new JSONObject();
            instanceObject.put("id",instance.getId());
            instanceObject.put("instance",instance.getInstance());
            instances.put(instanceObject);
        }
        details.put("instances",instances);
        
        
	    
	    
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

        JSONArray userList = new JSONArray();
        for(User user:users){
            JSONObject userJSONobject = new JSONObject();
            userJSONobject.put("user id",user.getUserID());
            userJSONobject.put("user name",user.getUserName());
            userJSONobject.put("user type",user.getUserType());
            userList.put(userJSONobject);
        }
        details.put("users",userList);

        try (FileWriter file = new FileWriter(path)) {
 
            file.write(details.toString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
	
}
