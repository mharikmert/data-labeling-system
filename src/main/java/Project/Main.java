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

        String getPath = jsonFileReader.getDatasetPath("config.json");
        if(getPath.equals("Path not found")){
            System.err.println("Path not found");
        }

        Dataset dataset = jsonFileReader.readDatasetFile(getPath);

        //create a users arrayList to hold inside users in json file
        ArrayList<User> users = jsonFileReader.readUserFile("config.json");

        //get solution with solve problem method
        Solution.getSolution().solveProblem(dataset, users);

        // writing a JSON output file
        JsonFileWriter jsonfilewriter=new JsonFileWriter();
        jsonfilewriter.export(dataset, users, "output.json");

    }

}
