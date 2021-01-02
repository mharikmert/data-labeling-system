package Solution;

import Project.Solution.Solution;
import org.junit.Assert;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void testGetSolution(){
        Assert.assertNotNull(Solution.getSolution());
    }
}
