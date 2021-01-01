package Project;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Instance {

    //Class attributes definitions
    private long id;
    private String instance;
    private ArrayList<Label> labels ;
    private long timeElapsed ;
    private LocalDateTime timestamp;  

    //Overloaded constructor
    public Instance(long id, String instance) {
        this.id = id;
        this.instance = instance;
        labels = new ArrayList<>();
    }

    //Get method for ID
    public long getId() {
        return id;
    }

    //Set method for ID
    public void setId(long id) {
        this.id = id;
    }

    //Get method for com.Instance
    public String getInstance() {
        return instance;
    }

    //Set method for com.Instance
    public void setInstance(String instance) {
        this.instance = instance;
    }

    //Get method for Labels
    public ArrayList<Label> getLabels() {
        return labels ;
    }

    //Set method for Labels
    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    //Get method for timeElapsed
    public long getTimeElapsed() {
        return timeElapsed;
    }

    //Set method for timeElapsed
    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    //Get method for timeStamp
    public LocalDateTime getTimeStamp(){
        return this.timestamp;
    }

    //Set method for timeStamp
    public void setTimeStamp(LocalDateTime timestamp){
        this.timestamp=timestamp;
    }

    //This method is for add label to the instance
    public void addLabelToInstance(Label label){
        labels.add(label);
    }

}
