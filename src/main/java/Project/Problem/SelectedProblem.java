package Project.Problem;

import Project.Dataset;
import Project.Instance;
import Project.Labeling.RandomMechanism;
import Project.User;

import java.util.ArrayList;
import java.util.Comparator;

public class SelectedProblem extends Problem {
    /*
        For the first iteration , instance of this class will be used
    */

    //Solution mechanism for this class
    @Override
    public void runMechanism(ArrayList<User> users, Dataset dataset) {
        // defining problem type , for now we just use randomLabeling
        // with this method , we randomly select the number of users which labels the instance , for example
        // for 1. instance , program selects random number of users , it can select 1 user or 3 user for 1 instance ,
        // for each instance , users labels the instances different.

        ArrayList<User> selectedUsers = new ArrayList<>();
        selectedUsers = selectRandomUser(users,selectedUsers,dataset.getNumberOfUser());    // select users randomly
        selectedUsers.sort(new Comparator<User>() {             // sort the list
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o1.getUserID(), o2.getUserID());
            }
        });
        dataset.setAssignedUsers(selectedUsers);                // write users to dataset

        RandomMechanism randomMechanism = new RandomMechanism();
        for(User currentUser : selectedUsers){

            for(Instance currentInstance : dataset.getInstances()){

                // yüzde 10 ihtimalle öncekilerden alacak
                double consistencyCheckRandom = (int)(Math.random()*100);
                double consistencyCheckProbability = currentUser.getConsistenctCheckProbability()*100;
                if (consistencyCheckRandom < consistencyCheckProbability && currentUser.getInstances().size()!=0){
                    int previousSelectRandom = (int)(Math.random()*(currentUser.getInstances().size()));
                    Instance copyInstance = new Instance(currentUser.getInstances().get(previousSelectRandom).getId(),
                                                         currentUser.getInstances().get(previousSelectRandom).getInstance());
                    randomMechanism.labelingMechanism(currentUser,copyInstance,dataset.getLabels(),dataset.getMaxNumberOfLabelsPerInstance());
                }
                // yüzde 60 sıradakini ekleyecek
                int nextCheckRandom = (int)(Math.random()*100);
                if (nextCheckRandom < 60){
                    Instance copyInstance = new Instance(currentInstance.getId(),currentInstance.getInstance());
                    randomMechanism.labelingMechanism(currentUser,copyInstance,dataset.getLabels(),dataset.getMaxNumberOfLabelsPerInstance());
                }

            }
        }

    }

    // in this method , we create com.User array which we select the number of user and select the user or users randomly.
    public ArrayList<User> selectRandomUser(ArrayList<User> users, ArrayList<User> selectedUsers,long numberofuser){

        for(int i=0 ; i<9 ; i++){

            int randomUserIndex = (int)(Math.random()*users.size());
            for (int j=0 ; j<selectedUsers.size() ; j++){
                while (!userControl(selectedUsers,users,randomUserIndex) || !users.get(randomUserIndex).getUserType().equals("RandomBot")){
                    randomUserIndex = (int)(Math.random()*users.size());
                    j=0;
                }
            }
            selectedUsers.add(users.get(randomUserIndex));
        }

        return selectedUsers ;

    }

    // in this method , we check if one user added before the selectedUsers array , if user added before , return false and while
    // we find new user it works again and again
    public boolean userControl(ArrayList<User> selectedUsers , ArrayList<User> users , int random){

        for (User selectedUser : selectedUsers) {
            if (selectedUser.getUserID() == users.get(random).getUserID()) {
                return false;
            }
        }
        return true ;
    }

}
