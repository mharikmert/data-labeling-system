package Project.Metrics;

import Project.User;
import Project.Dataset;
import Project.Instance;
import Project.Label;

import java.util.ArrayList;
import java.util.HashMap;

public class InstanceMetrics{

    // this class can not be instantiated
    private InstanceMetrics(){}

    //create an object of InstanceMetrics
    private static final InstanceMetrics instanceMetrics = new InstanceMetrics();

    //get the only available object
    public static InstanceMetrics getInstanceMetrics(){
        return instanceMetrics;
    }


    public int numberOfLabelAssignments(Dataset dataset,Instance instance,ArrayList<User> users){
        return labelAssignments(dataset, instance, users).size();
    }

    public int numberOfUniqueLabelAssignments(Dataset dataset,Instance instance,ArrayList<User> users){
        return frequencyListOfLabels(dataset, instance, users).size();
    }

    public int numberOfUsers(Dataset dataset, Instance instance, ArrayList<User>users){
        int totalNumber=0;
        for(User user:users)
        {
            Dataset userDataset=user.assignedDataset(dataset);
            if(userDataset==null)continue;
            for(Instance userInstance:userDataset.getInstances())
                if(instance.getId()==userInstance.getId()){totalNumber++;
                    break;}
            break;}
        return totalNumber;
    }

    public HashMap<Object,Object> frequencyListOfLabelsWithPercentages(Dataset dataset,Instance instance,ArrayList<User>users){
        HashMap<Object,Object> list=frequencyListOfLabels(dataset, instance, users);
        int numberOflabelAssignment=numberOfLabelAssignments(dataset,instance,users);
        for(Object labelId:list.keySet())
            list.replace(labelId, (float)((int)list.get(labelId))/(float)numberOflabelAssignment*100);
        return list;
    }

    public double entropy(Dataset dataset,Instance instance,ArrayList<User>users){
        int numberofLabels=numberOfLabelAssignments(dataset,instance,users);
        int numberOfUniqueLabels=numberOfUniqueLabelAssignments(dataset, instance, users);
        HashMap<Object,Object> list=frequencyListOfLabels(dataset,instance,users);
        double entropy=0;
        for(Object labelId:list.keySet())
            entropy+=(double)((int)list.get(labelId))*-1/numberofLabels*(Math.log(((double)(int)list.get(labelId))/numberofLabels)/Math.log(numberOfUniqueLabels));
        if (Double.isFinite(entropy)) return entropy;
        return 0;
    }

    public HashMap<Object,Object> mostFrequentClassLabelwithPercentage(Dataset dataset,Instance instance,ArrayList<User>users){
        ArrayList<Object> list=new ArrayList<>();
        int listFrequency=0;
        HashMap<Object,Object> tempFrequencyList=frequencyListOfLabels(dataset,instance,users);
        for(Object tempObject:tempFrequencyList.keySet())
            if((int)tempFrequencyList.get(tempObject)>=listFrequency){
                if((int)tempFrequencyList.get(tempObject)!=listFrequency)list.clear();
                list.add(tempObject);
                listFrequency=(int)tempFrequencyList.get(tempObject);
            }
       Object theMostFrequentOne;
        if(list.size()==0)return null;
        theMostFrequentOne=list.get((int)(Math.random()*list.size()));
        for(Label label:dataset.getLabels())
            {
                if(label.getId()==(long)theMostFrequentOne){
                    theMostFrequentOne=label;
                    break;
                }
            }
        if(instance.getLabels().size()>0)instance.setLabels(new ArrayList<Label>());
        instance.addLabelToInstance((Label)theMostFrequentOne);
        int numberOflabelAssignment=numberOfLabelAssignments(dataset,instance,users);
        HashMap<Object,Object> theMost=new HashMap<>();
        theMost.put(((Label)theMostFrequentOne).getId(), ((float)listFrequency/numberOflabelAssignment)*100);      
      return theMost;
    }


    public /*private*/ HashMap<Object,Object> frequencyListOfLabels(Dataset dataset, Instance instance, ArrayList<User>users){
        HashMap<Object,Object> list=new HashMap<>();
        for(Object hashMapObject:labelAssignments(dataset, instance, users))
            for(Object LabelId:((HashMap<Object,Object>)hashMapObject).values())
                if(!list.containsKey(LabelId))list.put(LabelId,1);
                else list.replace(LabelId, (int)list.get(LabelId)+1);
        return list;
    }

    public /*private*/ ArrayList<HashMap<Object,Object>> labelAssignments(Dataset dataset, Instance instance, ArrayList<User> users){
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
                        }
                    break;
                }
        return list;
    }

}
