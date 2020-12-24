package Problem;

import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import Project.Problem.SelectedProblem;
import Project.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class SelectedProblemTest {

    //Creating necessary objects for testing of SelectRandomUser
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Dataset> datasets =new ArrayList<>();
    //create a json file reader
    JsonFileReader jsonFileReader = new JsonFileReader("config.json",datasets,users);

    @Test
    public void testSelectUsers(){

        ArrayList<User> selectedUsers = new ArrayList<>();

        SelectedProblem selectedProblem = new SelectedProblem();

        for (Dataset dataset:
                datasets) {

            selectedProblem.selectUsers(this.users,selectedUsers,dataset);

            Assert.assertTrue(selectedUsers.size() > 0);
        }

    }
}

