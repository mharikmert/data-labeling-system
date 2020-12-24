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

/*
    @Test
    public void testLabelingMechanism(){

        /*
         *   After this method , the parameter instance must be added into user's 'instances' and
         *   one of the label in labels array (in this test we just have 1 label in labels array) ,
         *   must be added into instance's labels array.
         *

        User user = new User(1,"test","test user");
        Instance instance = new Instance(1,"test instance");
        ArrayList<Label> labels = new ArrayList<>();
        labels.add(new Label(1,"test label"));

        randomMechanism.labelingMechanism(user,instance,labels,1);

        Assert.assertNotNull(user.getInstances().get(0).getLabels().get(0));

    }

    @Test
    public void testCreateRandomLabel(){

        /*
         * After running this method , createRandomLabel method should return not null value
         *
         *

        Assert.assertNotNull(randomMechanism.createRandomLabel(dataset.getLabels()));

    }
*/
}
