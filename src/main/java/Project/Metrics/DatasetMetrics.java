package Project.Metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Project.Dataset;
import Project.Instance;
import Project.User;

public class DatasetMetrics {
    public static float complenessPercentage(Dataset dataset,ArrayList<User> users){
        return ((float)LabeledInstanceList(dataset, users).size()/dataset.getInstances().size())*100;
    }

    public static HashMap<Object,Object> listOfAssignedUsersWithComplenessPercentage(Dataset dataset,ArrayList<User> users){
        /*
         Maybe we can find a way to generalize this method with UserMetrics.completenessPercentageOfDatasets
        */
        HashMap<Object,Object> list=new HashMap<>();
        for(User user:assignedUsers(dataset,users))
            for(Dataset userDataset:user.getDatasets())
                if(userDataset.getId()==dataset.getId()){
                    list.put("user"+user.getUserID(),((float)UserMetrics.FrequencyListOfInstances(userDataset,user).keySet().size()/(float)dataset.getInstances().size())*100+"%");
                    break;}
        return list;
    }
    public static int numberOfUsersAssigned(Dataset dataset,ArrayList<User>users){
        return assignedUsers(dataset,users).size();
    }

    protected static ArrayList<User> assignedUsers(Dataset dataset,ArrayList<User> users){
        ArrayList<User> userList=new ArrayList<>();
        for(User user:users){
            Dataset userDataset=user.assignedDataset(dataset);
            if(userDataset!=null)userList.add(user);
        }
        return userList;
    }

    protected static HashSet<Object> LabeledInstanceList(Dataset dataset,ArrayList<User>users){
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
