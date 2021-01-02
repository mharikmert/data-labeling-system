import Project.User;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    private long userID;
    private String userName;
    private String userType;
    private double consistencyCheckProbability;

    @Test //constructor test with check arraylist datasets
    public void constructorTest(){
        User user = new User(userID,userName, userType, consistencyCheckProbability, "");
        Assert.assertNotNull(user.getDatasets());
    }
}
