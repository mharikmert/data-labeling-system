package Project.Problem;

import Project.Dataset;
import Project.Instance;
import Project.JsonIO.JsonFileWriter;
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
    public void runMechanism(ArrayList<User> users, Dataset dataset,ArrayList<Dataset>datasets) {
        // defining problem type , for now we just use randomLabeling
        // with this method , we randomly select the number of users which labels the instance , for example
        // for 1. instance , program selects random number of users , it can select 1 user or 3 user for 1 instance ,
        // for each instance , users labels the instances different.

        ArrayList<User> selectedUsers = new ArrayList<>();
        selectedUsers = selectUsers(users,selectedUsers,dataset);    // select users from dataset
        selectedUsers.sort(new Comparator<User>() {             // sort the list
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o1.getUserID(), o2.getUserID());
            }
        });
   //     dataset.setAssignedUsers(selectedUsers);                // write users to dataset

        for(User currentUser : selectedUsers){
            if(currentUser.assignedDataset(dataset)==null)continue;

            ArrayList<Instance> instancesToLabel = new ArrayList<>() ;
            ArrayList<Instance> labeledInstances = new ArrayList<>() ;
            boolean control = false ;
            for(Instance currentInstance : dataset.getInstances()){
                for (Instance userInstances : currentUser.getInstances(dataset)){
                    if (userInstances.getId()==currentInstance.getId() &&
                            userInstances.getInstance().equals(currentInstance.getInstance())){
                        labeledInstances.add(currentInstance);
                        control = true ;
                        break;
                    }
                }
                if (!control)
                    instancesToLabel.add(currentInstance);
                control=false ;
            }

            if (instancesToLabel.size()==0){
                JsonFileWriter jsonfilewriter=new JsonFileWriter();
                jsonfilewriter.export(datasets, users, dataset);
            }

/*          System.out.println("\n\nuser : " + currentUser.getUserID());
            System.out.println("labellanacakların listesi");
            for(Instance currentInstance : instancesToLabel)
                System.out.println(currentInstance.getId());
            System.out.println("labellanmışların listesi");
            for (Instance currentInstance : labeledInstances)
                System.out.println(currentInstance.getId());
            System.out.println("\n\n");
*/

            for(Instance currentInstance : instancesToLabel){

               // yüzde 10 ihtimalle öncekilerden alacak
                double consistencyCheckRandom = (int)(Math.random()*100);
                double consistencyCheckProbability = currentUser.getConsistenctCheckProbability()*100;

                if (consistencyCheckRandom < consistencyCheckProbability && labeledInstances.size()!=0){
                    int previousSelectRandom = (int)(Math.random()*(labeledInstances.size()));
                    Instance copyInstance = new Instance(labeledInstances.get(previousSelectRandom).getId(),
                                                         labeledInstances.get(previousSelectRandom).getInstance());
                    super.labelingMechanism = new RandomMechanism();
                    super.labelingMechanism.labelingMechanism(currentUser,copyInstance,dataset.getLabels(),dataset,users,datasets);
                }

                // yüzde 60 sıradakini ekleyecek
                int nextCheckRandom = (int)(Math.random()*100);
                if (nextCheckRandom < 60){
                    Instance copyInstance = new Instance(currentInstance.getId(),currentInstance.getInstance());
                    super.labelingMechanism = new RandomMechanism();
                    super.labelingMechanism.labelingMechanism(currentUser,copyInstance,dataset.getLabels(),dataset,users,datasets);
                }

            }

            labeledInstances.clear();
            instancesToLabel.clear();

        }

    }

    // in this method , we create com.User array which we select the number of user and select the user or users randomly.
    public ArrayList<User> selectUsers(ArrayList<User> users, ArrayList<User> selectedUsers,Dataset dataset){
            for (User user: users) {
                if(user.assignedDataset(dataset)!=null && userControl(user)){
                    selectedUsers.add(user);
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
