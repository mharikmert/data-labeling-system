package Project;//import necessary libraries

import Project.JsonIO.JsonFileReader;
import Project.JsonIO.JsonFileWriter;
import Project.Solution.Solution;

import java.io.File;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        //create a json file reader
        JsonFileReader jsonFileReader = new JsonFileReader();

        //create dataset object for json files with readDatasetFile method
        Dataset dataset = jsonFileReader.readDatasetFile("input_dataset2.json");

        //create a users arrayList to hold inside users in json file
        ArrayList<User> users = jsonFileReader.readUserFile("input_users.json");

        Solution.getSolution().solveProblem(dataset, users);

        // writing a JSON output file
        JsonFileWriter jsonfilewriter=new JsonFileWriter();
        jsonfilewriter.export(dataset, users, "output.json");


    }

}
