package Project.Problem;

import Project.Dataset;
import Project.Instance;
import Project.Solution.RandomMechanism;
import Project.User;

import java.util.ArrayList;

public class SelectedProblem extends Problem {
    /*
        For the first iteration , instance of this class will be used
    */

    //com.solution.Solution mechanism for this class
    @Override
    public void runMechanism(ArrayList<User> users, Dataset dataset) {
        // defining problem type , for now we just use randomLabeling
        // with this method , we randomly select the number of users which labels the instance , for example
        // for 1. instance , program selects random number of users , it can select 1 user or 3 user for 1 instance ,
        // for each instance , users labels the instances different.

        ArrayList<User> selectedUsers = new ArrayList<>();

        // this for loop traverse the instances one by one .
        for(int i=0 ; i<dataset.getInstances().size() ; i++){

            // selectRandomUser() method -> select # of users randomly and add these users to selectedUsers array.
            selectedUsers = selectRandomUser(users,selectedUsers);

            // after select the number of users , for each user , create the copy of instance which we looking now and add this instance
            // users seperately , because when we add label for user1's instance1 , it is update the user2's instance1 , because of this
            // we create copy of instances for each user
            for (User selectedUser : selectedUsers) {
                // create copy instance
                Instance tempInstance = new Instance(dataset.getInstances().get(i).getId(),dataset.getInstances().get(i).getInstance());
                // in this switch case statement , we look the userType and run its labelingMechanism , for 1st iteration we only
                // have randomLabeling
                switch (selectedUser.getUserType()) {
                    case "RandomBot":
                        // create a new randomMechanism object and awake its labeling method with the parameters which we selected
                        // the user , the instance and all labels with the numberOfLabels
                        super.labelingMechanism = new RandomMechanism();
                        super.labelingMechanism.labelingMechanism(selectedUser, tempInstance,
                                dataset.getLabels(), dataset.getMaxNumberOfLabelsPerInstance());
                        break;
                    case "MachineLearningBot":
                        // System.out.println("We dont have this");
                        break;
                    default:
                        break;
                }

            }
            selectedUsers.clear();

        }
    }

    // in this method , we create com.User array which we select the number of user and select the user or users randomly.
    public ArrayList<User> selectRandomUser(ArrayList<User> users, ArrayList<User> selectedUsers){

        int userCount = (int)(1+Math.random()*(users.size()));          // randomly select how many user will be label the instance
        int randomUserIndex ;                                           // this variable keeps the selected random user index
        // in this for loop , users will be selected
        for(int u=0 ; u<userCount ; u++){
            randomUserIndex = (int)(Math.random()*(users.size()));      // select the user index randomly between 0 and #of user
            if(!users.get(randomUserIndex).getUserType().equals("RandomBot")){
                randomUserIndex = (int)(Math.random()*(users.size()));
            }
            // in this while statement , if there is no user in selectedUsers array , we don't need to control the user
            // if it is added before or not , add directly , after adding first user .
            while (!userControl(selectedUsers,users,randomUserIndex) && selectedUsers.size()!=0){
                randomUserIndex = (int)(Math.random()*(users.size()));
            }
            selectedUsers.add(users.get(randomUserIndex));
        }
        return selectedUsers ;
    }

    // in this method , we check if one user added before the selectedUsers array , if user added before , return false and while
    // we find new user it works again and again
    boolean userControl(ArrayList<User> selectedUsers , ArrayList<User> users , int random){

        for (int i=0 ; i<selectedUsers.size() ; i++){
            if (selectedUsers.get(i).getUserID() == users.get(random).getUserID()){
                return false ;
            }
        }
        return true ;
    }

}
