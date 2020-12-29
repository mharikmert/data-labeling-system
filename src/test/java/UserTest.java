import Project.User;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    private long userID;
    private String userName;
    private String userType;
    private double consistenctCheckProbability;

    //constructor test with check arraylist datasets
    @Test
    public void constructorTest(){
        User user = new User(userID,userName, userType, consistenctCheckProbability, " ");
        Assert.assertNotNull(user.getDatasets());
    }
}
