package Solution;

import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import Project.Labeling.RandomMechanism;
import org.junit.Assert;
import org.junit.Test;

public class RandomMechanismTest {

    //Creating necessary objects
    JsonFileReader jsonFileReader = new JsonFileReader();
    Dataset dataset = jsonFileReader.readDatasetFile("input_dataset1.json");

    RandomMechanism randomMechanism = new RandomMechanism();

    @Test
    public void testCreateRandomLabel(){

        /*
         * After running this method , createRandomLabel method should return not null value
         *
         * */

        Assert.assertNotNull(randomMechanism.createRandomLabel(dataset.getLabels()));

    }

}
