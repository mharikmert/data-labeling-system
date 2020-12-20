package Project;//import necessary libraries

import Project.JsonIO.JsonFileReader;
import Project.JsonIO.JsonFileWriter;
import Project.Metrics.UserMetrics;
import Project.Solution.Solution;

import java.io.File;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        //create a json file reader
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Dataset> datasets =new ArrayList<>();
        JsonFileReader jsonFileReader = new JsonFileReader("config.json",datasets,users);

        //create dataset object for json files with readDatasetFile method
        for (Dataset d:datasets) {
            System.out.println(d.toString());
        }
        System.out.println("*********************\n");
        for (User d:users) {
            System.out.println(d.toString());
        }

        //get solution with solve problem method
        Solution.getSolution().solveProblem(datasets.get(1), users,datasets);


    }

}
