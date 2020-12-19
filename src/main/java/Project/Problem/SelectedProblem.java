package Project.Problem;

import Project.Dataset;
import Project.Instance;
import Project.Labeling.RandomMechanism;
import Project.User;

import java.time.Instant;
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
        selectedUsers = selectUsers(users,selectedUsers,dataset.getAssignedUserIDs());    // select users from dataset
        selectedUsers.sort(new Comparator<User>() {             // sort the list
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o1.getUserID(), o2.getUserID());
            }
        });
        dataset.setAssignedUsers(selectedUsers);                // write users to dataset

        for(User currentUser : selectedUsers){
            if(currentUser.assignedDataset(dataset)==null)continue;

            for(Instance currentInstance : dataset.getInstances()){

               // yüzde 10 ihtimalle öncekilerden alacak
                double consistencyCheckRandom = (int)(Math.random()*100);
                double consistencyCheckProbability = currentUser.getConsistenctCheckProbability()*100;

                if (consistencyCheckRandom < consistencyCheckProbability && currentUser.getInstances(dataset).size()!=0){
                    int previousSelectRandom = (int)(Math.random()*(currentUser.getInstances(dataset).size()));
                    Instance copyInstance = new Instance(currentUser.getInstances(dataset).get(previousSelectRandom).getId(),
                                                         currentUser.getInstances(dataset).get(previousSelectRandom).getInstance());
                    super.labelingMechanism.labelingMechanism(currentUser,copyInstance,dataset.getLabels(),dataset,users);
                }

                // yüzde 60 sıradakini ekleyecek
                int nextCheckRandom = (int)(Math.random()*100);
                if (nextCheckRandom < 60){
                    Instance copyInstance = new Instance(currentInstance.getId(),currentInstance.getInstance());
                    super.labelingMechanism = new RandomMechanism();
                    super.labelingMechanism.labelingMechanism(currentUser,copyInstance,dataset.getLabels(),dataset,users);
                }

            }

        }

    }

    // in this method , we create com.User array which we select the number of user and select the user or users randomly.
    public ArrayList<User> selectUsers(ArrayList<User> users, ArrayList<User> selectedUsers,ArrayList<Integer> userIDs){

        for (Integer id: userIDs) {
            for (User user: users) {
                if(user.getUserID() == id && userControl(user)){
                    selectedUsers.add(user);
                }
            }

        }

        return selectedUsers ;

    }

    // in this method , we check if one user added before the selectedUsers array , if user added before , return false and while
    // we find new user it works again and again
    public boolean userControl(User user){

        return user.getUserType().equals("RandomBot") ;

    }

}
