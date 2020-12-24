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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.apache.log4j.Logger;

//JsonIO.JsonFileReader class
public class JsonFileReader {
    final DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

    //class constructor
    public JsonFileReader(String path,ArrayList<Dataset> datasets,ArrayList<User> users) {
        //initialize arraylists in constructor
        readUserFile(users,path);
        getDatasetPath(datasets,users,path);
        readClassLabelAssignments(datasets,users);

    }

    public void getDatasetPath(ArrayList<Dataset> datasets,ArrayList<User> users,String path){

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

                    datasets.add(readDatasetFile((String)((JSONObject)o).get("path")));
                    datasets.get(datasets.size()-1).setFlag(datasets.get(datasets.size() - 1).getId() == currentDatasetId);

                    JSONArray usersJson = (JSONArray)((JSONObject) o).get("users");
                    for (Object a: usersJson) {
                        for (User user : users) {
                            if (user.getUserID() == (Long) a && datasets.size() != 0) {
                                user.assignDataset(datasets.get(datasets.size() - 1));
                            }
                        }
                    }
            }

        }catch (IOException | ParseException e){
            e.printStackTrace();
        }

    }

    //readDataSet method to read dataset type json files with String path parameter
    public Dataset readDatasetFile(String path) {
        //creating a json parser to parse read json file
        JSONParser jsonParser = new JSONParser();
        Dataset temp = new Dataset() ;
        //create a fileReader first and put the path as parameter in try catch to avoid exceptions
        try (FileReader fileReader = new FileReader(path)) {
            //create an Object obj to parse file
            Object obj = jsonParser.parse(fileReader);
            //create a json object from obj ready to create objects from jsonObject
            JSONObject jsonObject = (JSONObject) obj;
            //read dataset id form file as long casting
            temp.setId((long) jsonObject.get("dataset id"));
            //next attribute dataset name with string casting
            temp.setDatasetName((String) jsonObject.get("dataset name"));
            //and maxNumberOfLabelsPerInstance with long casting
            temp.setMaxNumberOfLabelsPerInstance((long) jsonObject.get("maximum number of labels per instance"));
            temp.setPath(path);
            //Arrays in json file
            //create a json array object to read classLabels in file
            JSONArray classLabels = (JSONArray) jsonObject.get("class labels");

            //iterate through in classLabels json array
            for (Object classLabel : classLabels) {

                temp.addLabel(new Label((long) ((JSONObject) classLabel).get("label id"), (String) ((JSONObject) classLabel).get("label text")));

            }

            //create a json array to hold instances in it
            JSONArray classInstances = (JSONArray) jsonObject.get("instances");
            //iterate through in instances
            for (Object classInstance : classInstances) {
                //like the class labels create a current instance
                int a=0;
                for (Instance instance : temp.getInstances()) {
                    if (instance.getId()==((long) ((JSONObject) classInstance).get("id")))
                        a=1;
                }
                if(a==0){
                    //add current instances to the instances arraylist
                    temp.addInstance(new Instance((long) ((JSONObject) classInstance).get("id"), (String) ((JSONObject) classInstance).get("instance")));
                }

            }
            //return a dataset object with attributes in json file

            return temp ;

        //catch io or parse exception
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null ;

    }

    //readUserFile method to read users type json file with a path parameter
    public void readUserFile(ArrayList<User> users,String path){
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

        //catch exceptions
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public void readClassLabelAssignments(ArrayList<Dataset> datasets , ArrayList<User> users){

        //creating a json parser to parse read json file
        JSONParser jsonParser = new JSONParser();

        for (Dataset dataset : datasets){

            try (FileReader fileReader = new FileReader(dataset.getPath())) {
                //create an Object obj to parse file
                Object obj = jsonParser.parse(fileReader);
                JSONObject jsonObject = (JSONObject) obj;

                JSONArray classLabelsAssignments = (JSONArray) jsonObject.get("class label assignments");

                //iterate through in classLabels json array
                for (Object assignments : classLabelsAssignments) {

                    for (User user : users){

                        if (user.getUserID()==((long) ((JSONObject) assignments).get("user id"))){

                            for (Instance instance : dataset.getInstances()){

                                if (instance.getId()==((long) ((JSONObject) assignments).get("instance id"))){

                                    Instance tempInstance = new Instance(((long) ((JSONObject) assignments).get("instance id")),
                                            instance.getInstance());

                                    JSONArray classLabel = (JSONArray) ((JSONObject) assignments).get("class label ids") ;

                                    for (Object labels : classLabel) {

                                        for(Label label : dataset.getLabels()){

                                            if (label.getId() == (long)labels )
                                                tempInstance.addLabelToInstance(label);

                                        }

                                    }
                                    try {
                                        tempInstance.setTimeStamp(LocalDateTime.parse(((JSONObject) assignments).get("date time").toString(),datetimeFormat)); 
                                    } catch (Exception e) {}
                                    try {
                                        tempInstance.setTimeElapsed((long)(1000*Float.parseFloat(((JSONObject) assignments).get("time elapsed").toString())));                                      
                                    } catch (Exception e) {}
                                   user.addInstanceToUser(dataset,tempInstance);
                                    break;
                                }

                            }

                            break;

                        }

                    }


                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



    }

}
