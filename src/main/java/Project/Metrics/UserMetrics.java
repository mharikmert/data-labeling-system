package Project.Metrics;

import Project.User;
import Project.Dataset;
import Project.Instance;
import Project.Label;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.HashMap;

public class UserMetrics {
    public static int numberOfDatasetsAssigned(User user){
        return user.getDatasets().size();
    }
    public static LinkedHashMap<Object,Object> completenessPercentageOfDatasets(ArrayList<Dataset> datasets,User user){
        LinkedHashMap<Object,Object> complenessPercentage=new LinkedHashMap<>();
        for(Dataset dataset:datasets){
            Dataset userDataset=user.assignedDataset(dataset);
            if(userDataset==null)continue;
            complenessPercentage.put("dataset"+dataset.getId(),((float)FrequencyListOfInstances(userDataset,user).keySet().size()/(float)dataset.getInstances().size())*100+"%");}
        return complenessPercentage;
    }

    public static int numberOfInstancesLabeled(User user){
        int totalNumber=0;
        for (Dataset dateset:user.getDatasets())
            totalNumber+=dateset.getInstances().size();
        return totalNumber;
    }

    public static int numberOfUniqueInstancesLabeled(User user){
        int totalNumber=0;
        for(Dataset dataset:user.getDatasets())
            totalNumber+=FrequencyListOfInstances(dataset,user).keySet().size();
        return totalNumber;
    }

    // public static float consistencyPercentage(User user){
    //     // NEEDS TO BE REVISED AGAIN !!!!
    //     int totalNumberOftheSame=0;
    //     for(Object key:FrequencyListOfInstances(user).values())
    //         try {
    //           for(Object value:((HashMap<Object,Object>)key).values())
    //             totalNumberOftheSame+=(int)value-1;
    //         } catch (ClassCastException e) { }
    //     return ((float)totalNumberOftheSame/(float)numberOfInstancesLabeled(user))*100;
    // }
    public static void test(User user){
        System.out.println(FrequencyListOfInstances(user.getDatasets().get(0),user));
    }

    protected static HashMap<Object,Object> FrequencyListOfInstances(Dataset userDataset,User user){
        HashMap<Object,Object> list=new HashMap<>();
        if(!user.getDatasets().contains(userDataset))
            return null;
        for(Instance instance:user.getInstances(userDataset))
            {
                HashMap<Object,Object> subList=new HashMap<>();
                for(Label label:instance.getLabels()){
                    if(!subList.containsKey(label.getId()))subList.put(label.getId(), 1);
                    else subList.replace(label.getId(),(int)subList.get(label.getId())+1);
                }
                list.put(instance.getId(), subList);
            }
        return list;
    }

}