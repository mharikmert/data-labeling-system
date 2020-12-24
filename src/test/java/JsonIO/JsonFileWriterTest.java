package JsonIO;

import Project.JsonIO.JsonFileWriter;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class JsonFileWriterTest {

    @Test
    public void testNewJSONObject(){

        //Creating necessary fileWriter object
        JsonFileWriter jsonFileWriter = new JsonFileWriter();
        //Creating new jsonObject by using writer class
        JSONObject jsonObject= null;
        jsonObject = jsonFileWriter.newJSONObject();

        //Checking the newJSONObject method
        Assert.assertNotNull(jsonObject);
    }
}
