package Project;

import java.util.ArrayList;

public class User {

    //Class attributes definitions
    private long userID;
    private String userName;
    private String password;
    private String userType;
    private double consistencyCheckProbability;
    private ArrayList<Dataset> datasets ;

    //Overloaded Constructor
    public User(long userID, String userName, String userType, double consistencyCheckProbability, String password) {
        this.userID = userID;
        this.userName = userName;
        this.userType = userType;
        this.consistencyCheckProbability = consistencyCheckProbability;
        this.datasets = new ArrayList<>();
        this.password = password ;
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

    //Set method for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Get method for UserType
    public String getUserType() {
        return userType;
    }

    //Set method for UserType
    public void setUserType(String userType) {
        this.userType = userType;
    }

    //Get method for consistency check probability
    public double getConsistencyCheckProbability() {
        return consistencyCheckProbability;
    }

    //Set method for consistency check probability
    public void setConsistencyCheckProbability(double consistencyCheckProbability) {
        this.consistencyCheckProbability = consistencyCheckProbability;
    }

    //Get method for Dataset
    public ArrayList<Dataset> getDatasets() {
        return datasets;
    }

    //This method is for to add instance to the user
    public void addInstanceToUser(Dataset dataset , Instance instance){
        for(Dataset d:datasets)
            if(dataset.getId()==d.getId())
                d.getInstances().add(instance);
    }

    //This method for assignin dataset
    public void assignDataset(Dataset dataset){
        Dataset newDataset;
        newDataset=new Dataset(dataset.getId(),null,0,null, new ArrayList<Instance>(),null,false);
        datasets.add(newDataset);
    }

    //This method returns assigned dataset of users
    public Dataset assignedDataset(Dataset dataset){
        for(Dataset d:datasets)
            if(d.getId()==dataset.getId()){
                return d;
            }
        return null;
    }

    //Get all Instances from dataset parameter
    public ArrayList<Instance> getInstances(Dataset dataset) {
        for (Dataset d:datasets)
            if (dataset.getId()==d.getId())
                return d.getInstances();
        return null;
    }


}
