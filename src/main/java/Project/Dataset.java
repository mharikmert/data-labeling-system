package Project;

import java.util.ArrayList;

public class Dataset {

    //Class attributes definitions
    private long id;
    private String datasetName;
    private long maxNumberOfLabelsPerInstance;
    private ArrayList<Label> labels;
    private ArrayList<Instance> instances;
    private String path;
    private Boolean flag;

    public Dataset(){
        labels = new ArrayList<>() ;
        instances = new ArrayList<>() ;
        flag=false ;
    }

    //Overloaded Constructor
    public Dataset(long id, String datasetName,long maxNumberOfLabelsPerInstance, ArrayList<Label> labels, ArrayList<Instance> instances, String path, Boolean flag) {
        this.id = id;
        this.datasetName = datasetName;
        this.maxNumberOfLabelsPerInstance = maxNumberOfLabelsPerInstance;
        this.labels = labels;
        this.instances = instances;
        this.path=path;
        this.flag=flag;
    }

    //Get method for ID
    public long getId() {
        return id;
    }

    //Set method for ID
    public void setId(long id) {
        this.id = id;
    }

    //Get method for DatasetName
    public String getDatasetName() {
        return datasetName;
    }

    //Set method for DatasetName
    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    //Get method for MaxNumberOfLabelsPerInstance
    public long getMaxNumberOfLabelsPerInstance() {
        return maxNumberOfLabelsPerInstance;
    }

    //Set method for MaxNumberOfLabelsPerInstance
    public void setMaxNumberOfLabelsPerInstance(long maxNumberOfLabelsPerInstance) {
        this.maxNumberOfLabelsPerInstance = maxNumberOfLabelsPerInstance;
    }

    //Get method for labels
    public ArrayList<Label> getLabels() {
        return labels;
    }

    //Set method for labels
    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    //Get method for instances
    public ArrayList<Instance> getInstances() {
        return instances;
    }

    //Set method for instances
    public void setInstances(ArrayList<Instance> instances) {
        this.instances = instances;
    }

    public String getPath(){
        return path;
    }
    
    public void setPath(String path){
        this.path=path;
    }
    
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void addLabel(Label label){
        this.labels.add(label);
    }

    public void addInstance(Instance instance){
        this.instances.add(instance);
    }

}
