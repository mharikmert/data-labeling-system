import java.util.ArrayList;

public class User {

    //Class attributes definitions
    private long userID;
    private String userName;
    private String userType;
    private ArrayList<Instance> instances ;

    //Overloaded Constructor
    public User(long userID, String userName, String userType) {
        this.userID = userID;
        this.userName = userName;
        this.userType = userType;
        this.instances = new ArrayList<>();
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
    public ArrayList<Instance> getInstances() {
        return instances;
    }

    //Set method for Instances
    public void setInstances(ArrayList<Instance> instances) {
        this.instances = instances;
    }

    //This method is for to add instance to the user
    public void addInstanceToUser(Instance instance){
        instances.add(instance);
    }

    //This method to print the instances of current user
    public void writeInstances(){
        for(int i=0 ; i<instances.size() ; i++){
            System.out.println("instance id ->"+instances.get(i).getId());
            instances.get(i).writeLabels();
        }
    }

}
