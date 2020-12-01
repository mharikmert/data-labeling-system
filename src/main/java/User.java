import java.util.ArrayList;

public class User {

    private long userID;
    private String userName;
    private String userType;
    private ArrayList<Instance> instances ;

    public User(long userID, String userName, String userType) {
        this.userID = userID;
        this.userName = userName;
        this.userType = userType;
        this.instances = new ArrayList<>();
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public ArrayList<Instance> getInstances() {
        return instances;
    }

    public void setInstances(ArrayList<Instance> instances) {
        this.instances = instances;
    }

    public void addInstanceToUser(Instance instance){
        instances.add(instance);
    }

    public void writeInstances(){
        for(int i=0 ; i<instances.size() ; i++){
            System.out.println("instance id ->"+instances.get(i).getId());
            instances.get(i).writeLabels();
        }
    }

}
