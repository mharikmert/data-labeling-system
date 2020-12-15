package Project.JsonIO;//import necessary libraries
import Project.Dataset;
import Project.Exception.InputValidationException;
import Project.Instance;
import Project.Label;
import Project.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

//JsonIO.JsonFileReader class
public class JsonFileReader {

    //private data fields
    private final ArrayList<Label> labels;
    private final ArrayList<Instance> instances;
    private final ArrayList<User> users;
    private long numberofuser ;

    //class constructor
    public JsonFileReader() {
        //initialize arraylists in constructor
        labels = new ArrayList<>();
        instances = new ArrayList<>();
        users = new ArrayList<>();
    }


    public String getDatasetPath(String path){

        String datasetPath = "";

        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(path)) {

            //create an object from jasonParser parse method
            Object obj = jsonParser.parse(fileReader);
            //create a jsonObject from obj
            JSONObject jsonObject = (JSONObject) obj;
            //create a json array from json object to hold user info in it

            long currentDatasetId = (long) jsonObject.get("currentDatasetID");

            JSONArray datasetsJSON = (JSONArray)jsonObject.get("datasets");

            for (Object o : datasetsJSON) {
                //create a current user in the same manner
                numberofuser = (long) ((JSONObject) o).get("number of user");
                if((long) ((JSONObject) o).get("dataset id") == currentDatasetId){
                    return (String) ((JSONObject) o).get("path");
                }
            }

        }catch (IOException | ParseException e){
            e.printStackTrace();
        }

        return "Path not found" ;

    }

    //readDataSet method to read dataset type json files with String path parameter
    public Dataset readDatasetFile(String path) {
        //creating a json parser to parse read json file
        JSONParser jsonParser = new JSONParser();
        //create a fileReader first and put the path as parameter in try catch to avoid exceptions
        try (FileReader fileReader = new FileReader(path)) {
            //create an Object obj to parse file
            Object obj = jsonParser.parse(fileReader);
            //create a json object from obj ready to create objects from jsonObject
            JSONObject jsonObject = (JSONObject) obj;
            //read dataset id form file as long casting
            long id = (long) jsonObject.get("dataset id");
            //next attribute dataset name with string casting
            String datasetName = (String) jsonObject.get("dataset name");
            //and maxNumberOfLabelsPerInstance with long casting
            long maxNumberOfLabelsPerInstance = (long) jsonObject.get("maximum number of labels per instance");

            //Arrays in json file
            //create a json array object to read classLabels in file
            JSONArray classLabels = (JSONArray) jsonObject.get("class labels");

            //iterate through in classLabels json array
            for (Object classLabel : classLabels) {
                //create a com.labeling.Label object current label and give the each label attributes for each object
                Label currentLabel = new Label((long) ((JSONObject) classLabel).get("label id"), (String) ((JSONObject) classLabel).get("label text"));
                //add to current label to the labels arraylist
                labels.add(currentLabel);
            }

            if (labels.size()<maxNumberOfLabelsPerInstance)
                throw new InputValidationException() ;

            //create a json array to hold instances in it
            JSONArray classInstances = (JSONArray) jsonObject.get("instances");
            //iterate through in instances
            for (Object classInstance : classInstances) {
                //like the class labels create a current instance
                Instance currentInstance = new Instance((long) ((JSONObject) classInstance).get("id"), (String) ((JSONObject) classInstance).get("instance"));
                //add current instances to the instances arraylist
                instances.add(currentInstance);
            }
            //return a dataset object with attributes in json file
            return new Dataset(id, datasetName,maxNumberOfLabelsPerInstance, labels, instances, numberofuser);

        //catch io or parse exception
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (InputValidationException exception) {
            exception.InputValidationExceptionMessage();
        }
        //return null if any exception occurred in method body
        return null;
    }


    //readUserFile method to read users type json file with a path parameter
    public ArrayList<User> readUserFile(String path){
        final Logger logger = Logger.getLogger("UserManager");
        //create a json parser to parse objects
        JSONParser jsonParser = new JSONParser();
        //create a file reader first in try catch
        try (FileReader fileReader = new FileReader(path)){

            //create an object from jasonParser parse method
            Object obj = jsonParser.parse(fileReader);
            //create a jsonObject from obj
            JSONObject jsonObject = (JSONObject) obj;
            //create a json array from json object to hold user info in it
            JSONArray usersJSON = (JSONArray)jsonObject.get("users");

            //iterate through usersJson
            for (Object o : usersJSON) {
                //create a current user in the same manner
                User currentUser = new User((long) ((JSONObject) o).get("userID"),
                        (String) ((JSONObject) o).get("userName"), (String) ((JSONObject) o).get("userType"),
                        (double) ((JSONObject) o).get("ConsistencyCheckProbability"));
                //adding users to the users
                users.add(currentUser);
                logger.info("userManager: created " + currentUser.getUserName() + " as " + currentUser.getUserType());
            }
            //return users arraylist
            return this.users;
        //catch exceptions
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        //return null if any exception occurred in method body
        return null;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public ArrayList<Instance> getInstances() {
        return instances;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

}
