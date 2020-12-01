import java.util.ArrayList;

public class Instance {

    private long id;
    private String instance;
    private ArrayList<Label> labels ;

    public Instance(long id, String instance) {
        this.id = id;
        this.instance = instance;
        labels = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public ArrayList<Label> getLabels() {
        return labels ;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    void addLabelToInstance(Label label){
        labels.add(label);
    }

    void writeLabels(){
        for (Label label : labels) {
            System.out.println("label id ->" +label.getId());
        }
    }

}
