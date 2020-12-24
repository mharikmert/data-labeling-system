package Project;//import necessary libraries

import Project.JsonIO.JsonFileReader;
import Project.Solution.Solution;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Dataset> datasets =new ArrayList<>();
         //create a json file reader
        JsonFileReader jsonFileReader = new JsonFileReader("config.json",datasets,users);

        //get solution with solve problem method
        Solution.getSolution().solveProblem(users,datasets);
    }

}
