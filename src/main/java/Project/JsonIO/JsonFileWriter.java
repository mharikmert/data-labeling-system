package Project.JsonIO;// import necessary libraries
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import Project.Instance;
import Project.Dataset;
import Project.Label;
import Project.User;
import Project.Metrics.DatasetMetrics;
import Project.Metrics.InstanceMetrics;
import Project.Metrics.UserMetrics;

import org.json.JSONArray;
import org.json.JSONObject;

//JsonFileWriter class
public class JsonFileWriter {
    final DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

    public void export(ArrayList<Dataset> datasets,ArrayList<User> users,Dataset dataset){
        filewriter(datasetJson(dataset,users).toString(2), dataset.getPath());
        filewriter(UsersMetric(datasets,users).toString(2),"UserMetrics.json");
   }

   private void filewriter(String written,String path){
        try(FileWriter file=new FileWriter(path+".tmp")) {
            file.write(written);
            file.close();
            while(true){
                File f1 = new File(path+".tmp");
                if(f1.length()!=0){
                    File f2=new File(path);
                    f2.delete();
                    f2=new File(path);
                    if(f1.renameTo(f2))break;
                }
            }
        } catch (Exception e) {}
   }

    // dataset method takes dataset and users as parameters then puts the information to a json object
    private JSONObject datasetJson(Dataset dataset, ArrayList<User> users){
	 // dataset part   
	JSONObject details = newJSONObject();
        details.put("dataset id",dataset.getId());
        details.put("dataset name",dataset.getDatasetName());
        details.put("maximum number of labels per instance",dataset.getMaxNumberOfLabelsPerInstance());

	 // label part
        JSONArray classLabels = new JSONArray();
        for(Label label: dataset.getLabels()){
            JSONObject classLabel=newJSONObject();
            classLabel.put("label id",label.getId());
            classLabel.put("label text",label.getText());
            classLabels.put(classLabel);
        }
        details.put("class labels",classLabels);

        // instances part
        JSONArray instances = new JSONArray();
        for (Instance instance:dataset.getInstances()) {
            JSONObject instanceObject=newJSONObject();
            instanceObject.put("id",instance.getId());
            instanceObject.put("instance",instance.getInstance());
            JSONObject MetricObject=newJSONObject();
            MetricObject.put("Total number of label assignments",InstanceMetrics.getInstanceMetrics().numberOfLabelAssignments(dataset, instance, users));
            MetricObject.put("Number of unique label assignments",InstanceMetrics.getInstanceMetrics().numberOfUniqueLabelAssignments(dataset, instance, users));
            MetricObject.put("Number of unique users",InstanceMetrics.getInstanceMetrics().numberOfUniqueLabelAssignments(dataset, instance, users));
            MetricObject.put("Most frequent class label and percentage","");
            MetricObject.put("Class labels and percentages",InstanceMetrics.getInstanceMetrics().frequencyListOfLabelsWithPercentages(dataset, instance, users));
            try {
                MetricObject.put("Entropy",InstanceMetrics.getInstanceMetrics().entropy(dataset, instance, users)); 
            } catch (Exception e) {
                MetricObject.put("Entropy","NaN");                
            }
            instanceObject.put("Metrics",MetricObject);
            instances.put(instanceObject);
        }
        details.put("instances",instances);

        // writing user information
        JSONArray userList = new JSONArray();
        for(User user:users){
            if(user.assignedDataset(dataset)==null)continue;
            JSONObject userJSONobject = newJSONObject();
            userJSONobject.put("user id",user.getUserID());
            userJSONobject.put("user name",user.getUserName());
            userJSONobject.put("user type",user.getUserType());
            userList.put(userJSONobject);
        }
        details.put("users",userList);

        JSONObject DatasetMetricObject=newJSONObject();
        DatasetMetricObject.put("Completeness percentage",DatasetMetrics.getDatasetMetrics().complenessPercentage(dataset, users));
        DatasetMetricObject.put("Class distribution based on final instance labels","");
        DatasetMetricObject.put("List number of unique instances for each class label","");
        DatasetMetricObject.put("Number of users assigned to this dataset",DatasetMetrics.getDatasetMetrics().numberOfUsersAssigned(dataset, users));
        DatasetMetricObject.put("List of users assigned and their completeness percentage",DatasetMetrics.getDatasetMetrics().listOfAssignedUsersWithComplenessPercentage(dataset, users));
        DatasetMetricObject.put("List of users assigned and their consistency percentag","");
        details.put("Metrics",DatasetMetricObject);
        // writing the results of assignments
        JSONArray assignments = new JSONArray();
        for(User user:users){
            if(user.assignedDataset(dataset)==null)continue;
                for (Instance instance:user.getInstances(dataset)){
                        JSONObject assignmentObject=newJSONObject();
                        assignmentObject.put("instance id",instance.getId());
                        JSONArray labelIDs = new JSONArray();
                        for(Label label:instance.getLabels()) {
                            labelIDs.put(label.getId());
                        }
                        assignmentObject.put("class label ids",labelIDs);
                        assignmentObject.put("user id",user.getUserID());
                        // date time operations
                        LocalDateTime myDateObj = instance.getTimeStamp();
                        assignmentObject.put("date time",myDateObj.format(datetimeFormat));
                        assignments.put(assignmentObject);
                    }
        }
        details.put("class label assignments",SortingForAssignments(assignments,"date time"));
        return details;
    }

    private JSONObject newJSONObject(){
        JSONObject newjsonobject = new JSONObject();
        //This part enable us to print everything in order
        try{
            Field changeMap = newjsonobject.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(newjsonobject,new LinkedHashMap<>());
            changeMap.setAccessible(false);
        }catch(IllegalAccessException | NoSuchFieldException e){
            e.printStackTrace();
            }
            return newjsonobject;
    }
    
    private JSONArray SortingForAssignments(JSONArray list,String key){
        for(int i=list.length();i>0;i--){
            int index=0;
            for(int j=1;j<i;j++) 
            {
                LocalDateTime tempTime=LocalDateTime.parse(((JSONObject)list.get(index)).getString(key),datetimeFormat);
                if(tempTime.isAfter(LocalDateTime.parse(((JSONObject)list.get(j)).getString(key),datetimeFormat)))
                   index=j;
            }
            Object tempObject=list.get(index);
            list.remove(index);
            list.put(tempObject);
        }
        return list;
    }

    private JSONArray UsersMetric(ArrayList<Dataset> datasets,ArrayList<User>users){
        JSONArray userJSONArray=new JSONArray();
        for(User user:users){
            JSONObject userJSONobject = newJSONObject();
            userJSONobject.put("user id",user.getUserID());
            userJSONobject.put("user name",user.getUserName());
            userJSONobject.put("user type",user.getUserType());
            JSONObject MetricObject=newJSONObject();
            MetricObject.put("Number of datasets assigned",UserMetrics.getUserMetrics().numberOfDatasetsAssigned(user));
            MetricObject.put("all datasets with their completeness percentage",UserMetrics.getUserMetrics().completenessPercentageOfDatasets(datasets,user));
            MetricObject.put("Total number of instances labeled ",UserMetrics.getUserMetrics().numberOfInstancesLabeled(user));
            MetricObject.put("Total number of unique instances labeled ",UserMetrics.getUserMetrics().numberOfUniqueInstancesLabeled(user));
            MetricObject.put("Consistency Percentage","");
            MetricObject.put("Average time spent in labeling an instance in seconds","");
            MetricObject.put("Std. dev. of  time spent in labeling an instance in seconds","");
            userJSONobject.put("Metrics",MetricObject);
            userJSONArray.put(userJSONobject);
        }
        return userJSONArray;
    }

}
