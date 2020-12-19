package Project.Metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Project.Dataset;
import Project.Instance;

import Project.User;

public class DatasetMetrics {

    // this class can not be instantiated
    private DatasetMetrics(){};

    //create an object of DatasetMetrics
    private static final DatasetMetrics datasetMetrics = new DatasetMetrics();

    //get the only available object
    public static DatasetMetrics getDatasetMetrics(){
        return datasetMetrics;
    }

    public float complenessPercentage(Dataset dataset,ArrayList<User> users){
        return ((float)getDatasetMetrics().LabeledInstanceList(dataset, users).size()/dataset.getInstances().size())*100;
    }

    public HashMap<Object,Object> listOfAssignedUsersWithComplenessPercentage(Dataset dataset,ArrayList<User> users){
        /*
         Maybe we can find a way to generalize this method with UserMetrics.completenessPercentageOfDatasets
        */
        HashMap<Object,Object> list=new HashMap<>();
        for(User user:assignedUsers(dataset,users))
            for(Dataset userDataset:user.getDatasets())
                if(userDataset.getId()==dataset.getId()){
                    list.put("user"+user.getUserID(),((float)UserMetrics.getUserMetrics().FrequencyListOfInstances(userDataset,user).keySet().size()/(float)dataset.getInstances().size())*100+"%");
                    break;}
        return list;
    }
    public int numberOfUsersAssigned(Dataset dataset,ArrayList<User>users){
        return assignedUsers(dataset,users).size();
    }

    protected ArrayList<User> assignedUsers(Dataset dataset,ArrayList<User> users){
        ArrayList<User> userList=new ArrayList<>();
        for(User user:users){
            Dataset userDataset=user.assignedDataset(dataset);
            if(userDataset!=null)userList.add(user);
        }
        return userList;
    }

    protected HashSet<Object> LabeledInstanceList(Dataset dataset,ArrayList<User>users){
        HashSet<Object> list = new HashSet<>();
        for(User user:users)
            for(Dataset userDataset:user.getDatasets())
                if(userDataset.getId()==dataset.getId()){
                    for(Instance instance:userDataset.getInstances())
                        list.add(instance.getId());
                    break;
                }
        return list;
    }
}
