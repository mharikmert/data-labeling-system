package Metrics;

import Project.Dataset;
import Project.JsonIO.JsonFileReader;
import Project.Metrics.InstanceMetrics;
import Project.Solution.Solution;
import Project.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class InstanceMetricsTest {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Dataset> datasets = new ArrayList<>();

    @BeforeEach
    public void init(){
        new JsonFileReader("config.json", datasets,users);
        Solution.getSolution().solveProblem(users,datasets);
    }

    @Test
    public void testNumberOfLabelAssignments(){
        int i = 0;
        for(Dataset dataset: datasets){
            MatcherAssert.assertThat(InstanceMetrics.getInstanceMetrics().numberOfLabelAssignments(dataset, dataset.getInstances().get(i),users), Matchers.is(0));
            i++;
        }
    }

    @Test
    public void testNumberOfUniqueLabelAssigments(){
        int i= 0;
        for(Dataset dataset: datasets){
            MatcherAssert.assertThat(InstanceMetrics.getInstanceMetrics().numberOfUniqueLabelAssignments(dataset, dataset.getInstances().get(i),users), Matchers.is(0));
            i++;
        }
    }

    @Test
    public void testNumberOfUsers(){
        for(Dataset dataset: datasets){
            int i = 0;
            Assert.assertTrue(InstanceMetrics.getInstanceMetrics().numberOfUsers(dataset,dataset.getInstances().get(i),users) != 0);
            i++;
        }
    }

    @Test
    public void testFrequencyListOfLabelsWithPercentages(){
        for(Dataset dataset: datasets){
            int i = 0;
            Assert.assertNotNull(InstanceMetrics.getInstanceMetrics().frequencyListOfLabelsWithPercentages(dataset,dataset.getInstances().get(i),users));
            i++;
        }
    }

    @Test
    public void testEntropy(){
        for(Dataset dataset: datasets){
            int i = 0;
            Assert.assertFalse(InstanceMetrics.getInstanceMetrics().entropy(dataset,dataset.getInstances().get(i),users) < 0);
            i++;
        }
    }

    @Test
    public void testMostFrequentClassWithPercentage(){
        for(Dataset dataset: datasets){
            int i = 0;
            Assert.assertNotNull(InstanceMetrics.getInstanceMetrics().mostFrequentClassLabelwithPercentage(dataset,dataset.getInstances().get(i),users));
            i++;
        }
    }

    @Test
    public void testFrequencyListOfLabels(){
        for(Dataset dataset: datasets){
            int i = 0;
            MatcherAssert.assertThat(InstanceMetrics.getInstanceMetrics().frequencyListOfLabels(dataset,dataset.getInstances().get(i),users).size(),
                    Matchers.equalTo(0));
            i++;
        }
    }

    @Test
    public void testLabelAssignments(){
        for(Dataset dataset: datasets){
            int i = 0;
            MatcherAssert.assertThat(InstanceMetrics.getInstanceMetrics().labelAssignments(dataset,dataset.getInstances().get(i),users).size(),
                    Matchers.equalTo(0));
            i++;
        }

    }

}
