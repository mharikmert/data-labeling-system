package Project;//import necessary libraries

import Project.JsonIO.JsonFileReader;
import Project.JsonIO.JsonFileWriter;
import Project.Solution.Solution;

import java.util.ArrayList;

/*
    com.Main class
 */
public class Main {
    /*
        com.Main method
    */
    public static void main(String[] args) {
        //create a json file reader
        JsonFileReader jsonFileReader = new JsonFileReader();

        //create dataset object for json files with readDatasetFile method
        Dataset dataset = jsonFileReader.readDatasetFile("input_dataset1.json");

        //create a users arrayList to hold inside users in json file
        ArrayList<User> users = jsonFileReader.readUserFile("input_users.json");

        Solution solution = new Solution();
        solution.solveProblem(dataset, users);

        // writing a JSON output file
        JsonFileWriter jsonfilewriter=new JsonFileWriter();
        jsonfilewriter.export(dataset, users, "output_json");

    }

}
