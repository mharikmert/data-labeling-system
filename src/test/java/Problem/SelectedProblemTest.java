package Problem;

import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import Project.Problem.SelectedProblem;
import Project.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class SelectedProblemTest {

    @Test
    public void testSelectRandomUser(){
        /*
        * After running this method , selectedUser's size must be greater than 0
        *
        * */
        
        //Creating necessary objects for testing of SelectRandomUser
        JsonFileReader jsonFileReader = new JsonFileReader();
        ArrayList<User> users = jsonFileReader.readUserFile("input_users.json");

        ArrayList<User> selectedUsers = new ArrayList<>();

        SelectedProblem selectedProblem = new SelectedProblem();
        selectedProblem.selectRandomUser(users , selectedUsers );

        // size of arraylist is greater than 0 or not
        Assert.assertTrue(selectedUsers.size() > 0 );


    }


}
