package Metrics;

import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import Project.Metrics.DatasetMetrics;
import Project.Metrics.UserMetrics;
import Project.Solution.Solution;
import Project.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class UserMetricsTest {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Dataset> datasets = new ArrayList<>();
    JsonFileReader jsonFileReader = new JsonFileReader("config.json", datasets,users);

    @BeforeEach
    public void init(){
        Solution.getSolution().solveProblem(users, datasets);
    }

    @Test
    public void testNumberOfUsersAssigned(){
        for(User user: users){
            Assert.assertNotNull(UserMetrics.getUserMetrics().numberOfDatasetsAssigned(user));
        }
    }

    // @Test
    // public void testFrequencyListOfInstances(){
    //     for(User user: users){
    //         for(Dataset dataset: datasets)
    //             Assert.assertNotNull(UserMetrics.getUserMetrics().frequencyListOfInstances(dataset,user));
    //     }
    // }

    @Test
    public void testNumberOfInstancesLabeled(){
        for(User user: users){
            Assert.assertNotNull(UserMetrics.getUserMetrics().numberOfInstancesLabeled(user));
        }
    }

    @Test
    public void testCompletenessPercentageOfDatasets() {
        for (User user : users) {
            Assert.assertNotNull(UserMetrics.getUserMetrics().completenessPercentageOfDatasets(datasets, user));
        }
    }
    @Test
    public void testNumberOfUniqueInstancesLabeled(){
        for(User user: users){
            Assert.assertNotNull(UserMetrics.getUserMetrics().numberOfUniqueInstancesLabeled(user));
        }
    }

    @Test
    public void testAverageTimeSpentInLabeling(){
        for(User user: users){
            Assert.assertTrue(UserMetrics.getUserMetrics().averageTimeSpentInLabeling(user) != 0);
        }
    }

    @Test
    public void testStdDeviationOfTimeSpentInLabeling(){
        for(User user: users){
            Assert.assertNotNull(UserMetrics.getUserMetrics().stdDeviationOfTimeSpentInLabeling(user));
        }
    }

    @Test
    public void testConsistency(){
        for(Dataset dataset: datasets)
            for(User user: users)
                Assert.assertNotNull(UserMetrics.getUserMetrics().consistency(dataset, user));
    }

    @Test
    public void testConsistency2(){
        for(User user: users){
            Assert.assertNotNull(UserMetrics.getUserMetrics().consistency(datasets, user));
        }
    }

}
