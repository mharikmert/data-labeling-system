package Labeling;

import Project.Dataset;
import Project.Instance;
import Project.JsonIO.JsonFileReader;
import Project.Label;
import Project.Labeling.RandomMechanism;
import Project.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class RandomMechanismTest {

    //Creating necessary objects for testing of SelectRandomUser
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Dataset> datasets =new ArrayList<>();
    //create a json file reader
    JsonFileReader jsonFileReader = new JsonFileReader("config.json",datasets,users);
    RandomMechanism randomMechanism = new RandomMechanism();

    @Test
    public void testCreateRandomLabel(){

        /*
         * After running this method , createRandomLabel method should return not null value
         *
         **/
        for (Dataset dataset:
             datasets) {
            Assert.assertNotNull(randomMechanism.createRandomLabel(dataset.getLabels()));
        }
    }
}
