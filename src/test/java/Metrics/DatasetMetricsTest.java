package Metrics;

import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import Project.Metrics.DatasetMetrics;
import Project.Solution.Solution;
import Project.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class DatasetMetricsTest {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Dataset> datasets =new ArrayList<>();

    @BeforeEach
    public void init(){
        new JsonFileReader("config.json",datasets,users);
        Solution.getSolution().solveProblem(users,datasets);
    }
   @Test
    public void testListofAssignedUsersWithCompeletenessPercentage(){
       for(Dataset dataset: datasets){
           Assert.assertTrue(DatasetMetrics.getDatasetMetrics().listOfAssignedUsersWithCompletenessPercentage(dataset, users).size()> 0);
       }
   }

   @Test
    public void testNumberOfUsersAssigned(){
       for(Dataset dataset: datasets){
           MatcherAssert.assertThat(DatasetMetrics.getDatasetMetrics().numberOfUsersAssigned(dataset, users), Matchers.not(0));
       }
   }

   @Test
    public void testListOfAssignedAndConsistencyPercentage(){
       for(Dataset dataset: datasets){
           Assert.assertTrue(DatasetMetrics.getDatasetMetrics().listOfUsersAssignedAndConsistencyPercentage(dataset, users).size()> 0);
       }
   }

   @Test
    public void testClassDistributionBasedOnFinal(){
       for(Dataset dataset: datasets){
           MatcherAssert.assertThat(DatasetMetrics.getDatasetMetrics().classDistribuionBasedOnFinal(dataset).size(),Matchers.equalTo(0));
       }
   }

   @Test // test for protected assignUsers method
    public void testAssignedUsers() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //reach the method with java Method Library and set it accessible to invoke
       Method method = DatasetMetrics.class.getDeclaredMethod("assignedUsers", Dataset.class, ArrayList.class);
        method.setAccessible(true);
       DatasetMetrics datasetMetrics = DatasetMetrics.getDatasetMetrics();
       for(Dataset dataset: datasets)
           Assert.assertNotNull(method.invoke(datasetMetrics,dataset,users));
    }

   @Test //test for protected labeledInstanceList method
    public void testLabeledInstanceList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       Method method = DatasetMetrics.class.getDeclaredMethod("labeledInstanceList", Dataset.class, ArrayList.class);
       method.setAccessible(true);
       DatasetMetrics datasetMetrics = DatasetMetrics.getDatasetMetrics();
       for(Dataset dataset: datasets)
            Assert.assertNotNull(method.invoke(datasetMetrics,dataset,users));
    }
}
