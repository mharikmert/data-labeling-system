package Project;

import java.util.ArrayList;

public class User {

    //Class attributes definitions
    private long userID;
    private String userName;
    private String userType;
    private double consistenctCheckProbability;
    private ArrayList<Dataset> datasets ;

    //Overloaded Constructor
    public User(long userID, String userName, String userType, double consistenctCheckProbability) {
        this.userID = userID;
        this.userName = userName;
        this.userType = userType;
        this.consistenctCheckProbability = consistenctCheckProbability ;
        this.datasets = new ArrayList<>();
    }

    //Get method for ID
    public long getUserID() {
        return userID;
    }

    //Set method for UserID
    public void setUserID(long userID) {
        this.userID = userID;
    }

    //Get method for UserName
    public String getUserName() {
        return userName;
    }

    //Set method for UserName
    public void setUserName(String userName) {
        this.userName = userName;
    }

    //Get method for UserType
    public String getUserType() {
        return userType;
    }

    //Set method for UserType
    public void setUserType(String userType) {
        this.userType = userType;
    }

    //Get method for Instances
    public ArrayList<Instance> getInstances(Dataset dataset) {
        for (Dataset d:datasets)
            if (dataset.getId()==d.getId())
                return d.getInstances();
        return null;
    }

    //Set method for Instances
    public void setInstances(Dataset dataset,ArrayList<Instance> instances) {
        for(Dataset d:datasets)
            if(dataset.getId()==d.getId())
                d.setInstances(instances);
    }

    //This method is for to add instance to the user
    public void addInstanceToUser(Dataset dataset , Instance instance){
        for(Dataset d:datasets)
            if(dataset.getId()==d.getId())
                d.getInstances().add(instance);
    }

    //This method to print the instances of current user
    public void writeInstances(){
        for(Dataset d:datasets)
            for(Instance instance:d.getInstances()){
                System.out.println("instance id ->"+instance.getId());
                instance.writeLabels();
        }
    }

    public ArrayList<Dataset> getDatasets() {
        return datasets;
    }

    public void assignDataset(Dataset dataset){
        Dataset newDataset;
        newDataset=new Dataset(dataset.getId(),null,0,null, new ArrayList<Instance>(), dataset.getAssignedUserIDs());
        datasets.add(newDataset);
    }
    public Dataset assignedDataset(Dataset dataset){
        for(Dataset d:datasets)
            if(d.getId()==dataset.getId()){
                return d;
            }
        return null;
    }

    public double getConsistenctCheckProbability() {
        return consistenctCheckProbability;
    }

    public void setConsistenctCheckProbability(double consistenctCheckProbability) {
        this.consistenctCheckProbability = consistenctCheckProbability;
    }


}
