package Project.Metrics;

import Project.User;
import Project.Dataset;
import Project.Instance;
import Project.Label;

import java.util.LinkedHashMap;

import javax.xml.stream.events.StartElement;

import org.w3c.dom.UserDataHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class UserMetrics {

    // this class can not be instantiated
    private UserMetrics(){};

    //create an object of UserMetrics
    private static final UserMetrics userMetrics = new UserMetrics();

    //get the only available object
    public static UserMetrics getUserMetrics(){
        return userMetrics;
    }


    public int numberOfDatasetsAssigned(User user){
        return user.getDatasets().size();
    }

    public LinkedHashMap<Object,Object> completenessPercentageOfDatasets(ArrayList<Dataset> datasets,User user){
        LinkedHashMap<Object,Object> complenessPercentage=new LinkedHashMap<>();
        for(Dataset dataset:datasets){
            Dataset userDataset=user.assignedDataset(dataset);
            if(userDataset==null)continue;
            complenessPercentage.put("dataset"+dataset.getId(),((float)getUserMetrics().FrequencyListOfInstances(userDataset,user).keySet().size()/(float)dataset.getInstances().size())*100+"%");}
        return complenessPercentage;
    }

    public int numberOfInstancesLabeled(User user){
        int totalNumber=0;
        for (Dataset dateset:user.getDatasets())
            totalNumber+=dateset.getInstances().size();
        return totalNumber;
    }

    public int numberOfUniqueInstancesLabeled(User user){
        int totalNumber=0;
        for(Dataset dataset:user.getDatasets())
            totalNumber+=FrequencyListOfInstances(dataset,user).keySet().size();
        return totalNumber;
    }

    public float averageTimeSpentInLabeling(User user){
        long totalTimeElapsed=0;
        long totalLabeling=0;
        for(Dataset dataset:user.getDatasets())
            for(Instance instance:dataset.getInstances())
                {totalTimeElapsed+=instance.getTimeElapsed();
                totalLabeling++;}
        float tempReturn=((float)totalTimeElapsed/totalLabeling)/1000;
        if (Float.isFinite(tempReturn)) return tempReturn;
        return 0;
    }

    public float stdDeviationOfTimeSpentInLabeling(User user){
        float averageTime=averageTimeSpentInLabeling(user);
        long totalLabeling=0;
        double total=0;
        for(Dataset dataset:user.getDatasets())
            for(Instance instance:dataset.getInstances()){
                total+=Math.pow(instance.getTimeElapsed()/1000-averageTime,2);
                totalLabeling++;
            }
        float tempReturn=(float)(total/totalLabeling);
        if (Float.isFinite(tempReturn)) return tempReturn;
        return 0;
    }

    public float consistency(Dataset dataset,User user){
        ArrayList<Dataset> datasets=new ArrayList<>();
        datasets.add(dataset);
        return consistency(datasets, user);
    }

    public float consistency(ArrayList<Dataset> datasets,User user){
        int recurrentLabeling=0;
        int consistentCount=0;
        for(Dataset dataset:datasets){
            Dataset userDataset=user.assignedDataset(dataset);
            if(userDataset==null)continue;
            HashMap<Object,Object> tempFrequencyList=FrequencyListOfInstances(userDataset, user);
            for(Object key:tempFrequencyList.keySet()){
                HashMap<Object,Object> tempObject=(HashMap<Object,Object>)tempFrequencyList.get(key);
                if(tempObject.size()>1)recurrentLabeling++;
                else if (!tempObject.isEmpty() && (int)(tempObject.values().toArray())[0]>1){consistentCount++;
                recurrentLabeling++;}
            }
        }
        float tempReturn=(((float)consistentCount/recurrentLabeling)*100);
        if (Float.isFinite(tempReturn)) return tempReturn;
        return 0;
    }

    public /*protected*/ HashMap<Object,Object> FrequencyListOfInstances(Dataset userDataset,User user){
        HashMap<Object,Object> list=new HashMap<>();
        if(!user.getDatasets().contains(userDataset))
            return null;
        for(Instance instance:user.getInstances(userDataset))
        {
            HashMap<Object,Object> subList;
            if(list.containsKey(instance.getId()))subList=(HashMap<Object,Object>)list.get(instance.getId());
            else {subList=new HashMap<>();
                list.put(instance.getId(), subList);}
            for(Label label:instance.getLabels()){
                if(!subList.containsKey(label.getId()))subList.put(label.getId(), 1);
                else {subList.replace(label.getId(),(int)subList.get(label.getId())+1);}
            }
        }
        return list;
    }

}
