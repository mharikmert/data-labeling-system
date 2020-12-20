package Project;//import necessary libraries

import Project.JsonIO.JsonFileReader;
import Project.JsonIO.JsonFileWriter;
import Project.Metrics.UserMetrics;
import Project.Solution.Solution;

import java.io.File;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Dataset> datasets =new ArrayList<>();
        
        //create a json file reader to load users and datasets
        JsonFileReader jsonFileReader = new JsonFileReader("config.json",datasets,users);
    
        //get solution with solve problem method
        Solution.getSolution().solveProblem(users,datasets);

    }

}
