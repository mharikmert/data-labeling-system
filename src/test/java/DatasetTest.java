import org.junit.Assert;
import org.junit.Test;
import Project.Dataset;
public class DatasetTest {
    @Test
    public void testConstructor(){
        Dataset dataset = new Dataset();
        // flag is false
        Assert.assertFalse(dataset.isFlag());
        Assert.assertNotNull(dataset.getLabels());
        Assert.assertNotNull(dataset.getInstances());
    }
}
