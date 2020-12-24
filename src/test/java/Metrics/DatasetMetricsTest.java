package Metrics;

import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import Project.Main;
import Project.Metrics.DatasetMetrics;
import Project.Solution.Solution;
import Project.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class DatasetMetricsTest {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Dataset> datasets =new ArrayList<>();
    //create a json file reader
    JsonFileReader jsonFileReader = new JsonFileReader("config.json",datasets,users);

    @BeforeEach
    public void init(){
        Solution.getSolution().solveProblem(users,datasets);
    }
//    @Test
//     public void testLabeldInstancesList(){
//        for(Dataset dataset: datasets){
//            Assert.assertNotNull(DatasetMetrics.getDatasetMetrics().labeledInstanceList(dataset, users));
//        }
//    }

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

   @Test
    public void testAssignedUsers(){
       for(Dataset dataset:datasets){
           MatcherAssert.assertThat(DatasetMetrics.getDatasetMetrics().classDistribuionBasedOnFinal(dataset).size(),Matchers.equalTo(0));
       }
   }
}
