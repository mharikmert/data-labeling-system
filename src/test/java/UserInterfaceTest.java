import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import Project.User;
import Project.UserInterface;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class UserInterfaceTest {
     ArrayList<User> users = new ArrayList<>();
     ArrayList<Dataset> datasets = new ArrayList<>();
     @BeforeEach
     public void init(){
          //read the config and create users and dataset before the tests
          new JsonFileReader("config.json", datasets, users);
     }

     @Test
     public void testUserInterface(){
          Assert.assertNotNull(UserInterface.getUserInterface());
     }

     @Test
     public void testRun(){
          //when run invoked, there should not be any authenticated user
          Assert.assertNull(UserInterface.getUserInterface().getAuthenticatedUser());
     }

}
