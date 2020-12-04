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
    JsonFileReader jsonFileReader = new JsonFileReader();
    ArrayList<User> users = jsonFileReader.readUserFile("input_users.json");
    Dataset dataset = jsonFileReader.readDatasetFile("input_dataset1.json");
    ArrayList<User> selectedUsers = new ArrayList<>();

    @Test
    public void testSelectRandomUser(){
        /*
        * After running this method , selectedUser's size must be greater than 0
        *
        */
        SelectedProblem selectedProblem = new SelectedProblem();
        selectedProblem.selectRandomUser(users , selectedUsers );

        // size of arraylist is greater than 0 or not
        Assert.assertTrue(selectedUsers.size() > 0 );
    }


    @Test
    public void testUserControl(){

        //lets made first user in user list like
        users.clear();
        users.add(new User(1,"test","test"));
        //made the selected user same as users first user
        selectedUsers.add(new User(1,"test","test"));
        int testRandom = 0 ;
        SelectedProblem selectedProblem = new SelectedProblem();
        /*
        *  in userControl method , if users.get(testRandom) equals any of the user in selectedUsers array
        *  method should return false .
        */
        Assert.assertFalse(selectedProblem.userControl(selectedUsers, users, testRandom));

    }



}
