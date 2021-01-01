package Project.Metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Project.Dataset;
import Project.Instance;
import Project.Label;
import Project.User;

public class DatasetMetrics {

    // this class can not be instantiated
    private DatasetMetrics(){}

    //create an object of DatasetMetrics
    private static final DatasetMetrics datasetMetrics = new DatasetMetrics();

    //get the only available object
    public static DatasetMetrics getDatasetMetrics(){
        return datasetMetrics;
    }

    public float completenessPercentage(Dataset dataset, ArrayList<User> users){
        return ((float)getDatasetMetrics().labeledInstanceList(dataset, users).size()/dataset.getInstances().size())*100;
    }

    public HashMap<Object,Object> listOfAssignedUsersWithCompletenessPercentage(Dataset dataset, ArrayList<User> users){
        HashMap<Object,Object> list=new HashMap<>();
        for(User user:assignedUsers(dataset,users))
            for(Dataset userDataset:user.getDatasets())
                if(userDataset.getId()==dataset.getId()){
                    list.put("user"+user.getUserID(),((float)UserMetrics.getUserMetrics().frequencyListOfInstances(userDataset,user).keySet().size()/(float)dataset.getInstances().size())*100+"%");
                    break;}
        return list;
    }

    public int numberOfUsersAssigned(Dataset dataset,ArrayList<User>users){
        return assignedUsers(dataset,users).size();
    }

    public HashMap<Object,Object> listOfUsersAssignedAndConsistencyPercentage(Dataset dataset,ArrayList<User>users){
        HashMap<Object,Object> list=new HashMap<>();
        for(User user:users)
        {
            if (user.assignedDataset(dataset)==null)continue;
            list.put("user"+user.getUserID(), UserMetrics.getUserMetrics().consistency(dataset,user)+"%");
        }
        return list;
    }

    public HashMap<Object,Object> classDistribuionBasedOnFinal(Dataset dataset){
        HashMap<Object,Object> list=new HashMap<>();
        for(Instance instance:dataset.getInstances()){
            if(instance.getLabels().size()==0)continue;
            if(!list.containsKey(instance.getLabels().get(0).getId()))list.put(instance.getLabels().get(0).getId(), 1);
            else list.put(instance.getLabels().get(0).getId(), (int)list.get(instance.getLabels().get(0).getId())+1);
        }
        int totalNumber=0;
        for(Object o:list.values())
             totalNumber+=(int)o;
        for(Object o:list.keySet()){
            list.replace(o,(float)((int)list.get(o))/totalNumber*100);
        }
        return list;
    }

    public HashMap<Object,Object> numberOfUniqueInstancesForEachLabels(Dataset dataset,ArrayList<User>users){
        HashMap<Object,Object> list=new HashMap<>();
        for(Instance instance:dataset.getInstances())
        {
            HashMap<Object,Object> instanceFrequencyList=InstanceMetrics.getInstanceMetrics().frequencyListOfLabels(dataset, instance, users);
            for(Label label:dataset.getLabels())
                if(instanceFrequencyList.keySet().contains(label.getId()))
                {
                    if(list.keySet().contains(label.getId()))list.replace(label.getId(), (int)list.get(label.getId())+1);
                    else list.put(label.getId(), 1);
                }
        }
        return list;   
    }

    protected ArrayList<User> assignedUsers(Dataset dataset,ArrayList<User> users){
        ArrayList<User> userList=new ArrayList<>();
        for(User user:users){
            Dataset userDataset=user.assignedDataset(dataset);
            if(userDataset!=null)userList.add(user);
        }
        return userList;
    }

    protected HashSet<Object> labeledInstanceList(Dataset dataset, ArrayList<User>users){
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
