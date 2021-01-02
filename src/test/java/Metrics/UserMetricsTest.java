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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class UserMetricsTest {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Dataset> datasets = new ArrayList<>();

    @BeforeEach
    public void init(){
        new JsonFileReader("config.json", datasets,users);
        Solution.getSolution().solveProblem(users, datasets);
    }

    @Test
    public void testNumberOfUsersAssigned(){
        for(User user: users){
            Assert.assertTrue(UserMetrics.getUserMetrics().numberOfDatasetsAssigned(user) >=0);
        }
    }

    @Test
    public void testNumberOfInstancesLabeled(){
        for(User user: users){
            Assert.assertTrue(UserMetrics.getUserMetrics().numberOfInstancesLabeled(user)>=0);
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
            Assert.assertFalse(UserMetrics.getUserMetrics().numberOfUniqueInstancesLabeled(user)<0);
        }
    }

    @Test
    public void testAverageTimeSpentInLabeling(){
        for(User user: users){
            Assert.assertFalse(UserMetrics.getUserMetrics().averageTimeSpentInLabeling(user)<0);
        }
    }

    @Test
    public void testStdDeviationOfTimeSpentInLabeling(){
        for(User user: users){
            Assert.assertFalse(UserMetrics.getUserMetrics().stdDeviationOfTimeSpentInLabeling(user)<0);
        }
    }

    @Test
    public void testConsistency(){
        for(Dataset dataset: datasets)
            for(User user: users)
                Assert.assertFalse( UserMetrics.getUserMetrics().consistency(dataset, user)<0);
    }

    @Test
    public void testConsistency2(){
        for(User user: users){
            Assert.assertEquals(0, UserMetrics.getUserMetrics().consistency(datasets, user), 0);
        }
    }

    @Test //test for protected frequencyListOfInstances method
    public void testFrequencyListOfInstances() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = UserMetrics.class.getDeclaredMethod("frequencyListOfInstances", Dataset.class, User.class);
        method.setAccessible(true);
        UserMetrics userMetrics = UserMetrics.getUserMetrics();
        for(Dataset dataset: datasets){
            for(User user:users){
                Assert.assertNotNull(method.invoke(userMetrics,dataset,user));
            }
        }
    }
}
