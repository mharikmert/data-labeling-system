package JsonIO;

import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class JsonFileReaderTest {

    @Test
    public void testReadDataset(){
            JsonFileReader jsonFileReader = new JsonFileReader();

            Dataset temp = jsonFileReader.readDatasetFile("input_dataset1.json");

            MatcherAssert.assertThat(temp.getDatasetName(), Matchers.equalTo("Sentiment Dataset"));

    }
}
