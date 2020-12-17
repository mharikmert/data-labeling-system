package Project.Metrics;

import Project.User;
import Project.Dataset;
import Project.Instance;
import Project.Label;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.HashMap;

public class InstanceMetrics{
    public static int numberOfLabelAssignments(Dataset dataset,Instance instance,ArrayList<User> users){
        return labelAssignments(dataset, instance, users).size();
    }

    public static int numberOfUniqueLabelAssignments(Dataset dataset,Instance instance,ArrayList<User> users){
        return frequencyListOfLabels(dataset, instance, users).size();
    }

    public static int numberOfUsers(Dataset dataset, Instance instance, ArrayList<User>users){
        int totalNumber=0;
        for(User user:users)
            for(Dataset userDataset:user.getDatasets())
                if(userDataset.getId()==dataset.getId()){
                    for(Instance userInstance:dataset.getInstances())
                        if(instance.getId()==userInstance.getId()){totalNumber++;
                            break;}
                    break;}
        return totalNumber;
    }

    public static HashMap<Object,Object> frequencyListOfLabelsWithPercentages(Dataset dataset,Instance instance,ArrayList<User>users){
        HashMap<Object,Object> list=frequencyListOfLabels(dataset, instance, users);
        int numberOflabelAssignment=numberOfLabelAssignments(dataset,instance,users);
        for(Object labelId:list.keySet())
            list.replace(labelId, (float)((int)list.get(labelId))/(float)numberOflabelAssignment*100);
        return list;
    }

    public static double entropy(Dataset dataset,Instance instance,ArrayList<User>users){
        int numberofLabels=numberOfLabelAssignments(dataset,instance,users);
        int numberOfUniqueLabels=numberOfUniqueLabelAssignments(dataset, instance, users);
        HashMap<Object,Object> list=frequencyListOfLabels(dataset,instance,users);
        double entropy=0;
        for(Object labelId:list.keySet())
                entropy+=(double)((int)list.get(labelId))*-1/numberofLabels*(double)(Math.log(((double)(int)list.get(labelId))/numberofLabels)/Math.log(numberOfUniqueLabels));
        return entropy;
    }

    private static HashMap<Object,Object> frequencyListOfLabels(Dataset dataset, Instance instance, ArrayList<User>users){
        HashMap<Object,Object> list=new HashMap<>();
        for(Object hashMapObject:labelAssignments(dataset, instance, users))
            for(Object LabelId:((HashMap<Object,Object>)hashMapObject).values())
                if(!list.containsKey(LabelId))list.put(LabelId,1);
                else list.replace(LabelId, (int)list.get(LabelId)+1);
        return list;
    }

    private static ArrayList<HashMap<Object,Object>> labelAssignments(Dataset dataset, Instance instance, ArrayList<User> users){
        ArrayList<HashMap<Object,Object>> list=new ArrayList<>();
        for(User user:users)
            for(Dataset userDataset:user.getDatasets())
                if(dataset.getId()==userDataset.getId()){
                    for(Instance userInstance : userDataset.getInstances())
                        if(instance.getId()==userInstance.getId())
                            {
                                for(Label label:userInstance.getLabels())
                                    {
                                        HashMap<Object,Object> subList=new HashMap<>();
                                        subList.put(user.getUserID(), label.getId());
                                        list.add(subList);
                                    }
                                break;
                            }
                    break;
                }
        return list;
    }

}
