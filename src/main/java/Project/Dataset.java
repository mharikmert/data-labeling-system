package Project;

import java.util.ArrayList;


public class Dataset {

    //Class attributes definitions
    private long id;
    private String datasetName;
    private long maxNumberOfLabelsPerInstance;
    private ArrayList<Label> labels;
    private ArrayList<Instance> instances;

    //Overloaded Constructor
    public Dataset(long id, String datasetName,long maxNumberOfLabelsPerInstance, ArrayList<Label> labels, ArrayList<Instance> instances) {
        this.id = id;
        this.datasetName = datasetName;
        this.maxNumberOfLabelsPerInstance = maxNumberOfLabelsPerInstance;
        this.labels = labels;
        this.instances = instances;
    }
    //Overridden toString Method for DataSet class
    @Override
    public String toString() {
        return "Dataset{" +
                "id=" + id +
                ", datasetName='" + datasetName + '\'' +
                ", maxNumberOfLabelsPerInstance=" + maxNumberOfLabelsPerInstance +
                ", labels=" + labels +
                ", instances=" + instances +
                '}';
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

}
