//import necessary libraries
import java.util.ArrayList;

/*
    Main class
 */
public class Main {
    /*
        Main method
    */
    public static void main(String[] args) {
        //create a json file reader
        JsonFileReader jsonFileReader = new JsonFileReader();

        //create dataset object for json files with readDatasetFile method
        Dataset dataset1 = jsonFileReader.readDatasetFile("input_dataset1.json");
        Dataset dataset2 = jsonFileReader.readDatasetFile("input_dataset2.json");

        //create a users arrayList to hold inside users in json file
        ArrayList<User> users = jsonFileReader.readUserFile("input_users.json");

        Solution solution = new Solution();
        solution.solveProblem(dataset1, users);

        // writing a JSON output file
        JsonFileWriter jsonfilewriter=new JsonFileWriter("output.json");
        jsonfilewriter.Export(dataset1, users);

    }

}
