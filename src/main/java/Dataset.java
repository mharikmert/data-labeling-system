import java.util.ArrayList;

public class Dataset {

    private long id;
    private String datasetName;
    private long maxNumberOfLabelsPerInstance;
    private ArrayList<Label> labels;
    private ArrayList<Instance> instances;

    public Dataset(long id, String datasetName, String instanceType, long maxNumberOfLabelsPerInstance, ArrayList<Label> labels, ArrayList<Instance> instances) {
        this.id = id;
        this.datasetName = datasetName;
        this.maxNumberOfLabelsPerInstance = maxNumberOfLabelsPerInstance;
        this.labels = labels;
        this.instances = instances;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public long getMaxNumberOfLabelsPerInstance() {
        return maxNumberOfLabelsPerInstance;
    }

    public void setMaxNumberOfLabelsPerInstance(long maxNumberOfLabelsPerInstance) {
        this.maxNumberOfLabelsPerInstance = maxNumberOfLabelsPerInstance;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public ArrayList<Instance> getInstances() {
        return instances;
    }

    public void setInstances(ArrayList<Instance> instances) {
        this.instances = instances;
    }
}
