package JsonIO;

import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import Project.User;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class JsonFileReaderTest {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Dataset> datasets =new ArrayList<>();
    //create a json file reader

    @Test
    public void testJsonFileReaderConstructor(){
        JsonFileReader jsonFileReader = new JsonFileReader("config.json",datasets,users);

        Assert.assertTrue(users.size() > 0 && datasets.size() > 0);

    }
}
