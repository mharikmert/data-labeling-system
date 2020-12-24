import Project.Instance;
import org.junit.Assert;
import org.junit.Test;
// test for instance class
public class InstanceTest {
    private long id;
    private String strInstance = null;

    @Test // testing constructor and labels arraylist
    public void testContructor(){
        Instance instance = new Instance(id, strInstance);
        Assert.assertNotNull(instance.getLabels());
    }
}
