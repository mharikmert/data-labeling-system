package Project.Problem;

import Project.Dataset;
import Project.Instance;
import Project.JsonIO.JsonFileWriter;
import Project.Labeling.RandomMechanism;
import Project.Labeling.UserMechanism;
import Project.User;
import Project.UserInterface;

import java.util.ArrayList;
import java.util.Comparator;

public class SelectedProblem extends Problem {
    /*
        For the first iteration , instance of this class will be used
    */

    @Override
    public void run(ArrayList<User> users, Dataset dataset, ArrayList<Dataset> datasets){

        findInstancesToLabel(users,dataset,datasets);
        ArrayList<User> tempUsers = new ArrayList<>(users);
        UserInterface.getUserInterface().run(tempUsers);
        if (tempUsers.size()==1){
            if (tempUsers.get(0).assignedDataset(dataset).getInstancesToLabel().size()==0)
                System.out.println("There is no instance left for you");
            runUser(tempUsers.get(0),users,dataset,datasets);
        }
        else
            runBot(users,dataset,datasets);

    }

    public void findInstancesToLabel(ArrayList<User> users, Dataset dataset, ArrayList<Dataset> datasets){

        for(User user : users){
            if(user.assignedDataset(dataset)!=null) {
                boolean control = false;
                for (Instance currentInstance : dataset.getInstances()) {
                    for (Instance userInstances : user.getInstances(dataset)) {
                        if (userInstances.getId() == currentInstance.getId() &&
                                userInstances.getInstance().equals(currentInstance.getInstance())) {
                            control = true;
                            break;
                        }
                    }
                    if (!control)
                        user.assignedDataset(dataset).addInstanceToLabel(currentInstance);
                    control = false;
                }
            }
        }

    }

    @Override
    public void runUser(User user, ArrayList<User> users, Dataset dataset, ArrayList<Dataset> datasets) {

        if(user.assignedDataset(dataset)!=null){

            if (user.assignedDataset(dataset).getInstancesToLabel().size()==0){
                JsonFileWriter jsonfilewriter=new JsonFileWriter();
                jsonfilewriter.export(datasets, users, dataset);
            }

            for (Instance currentInstance : user.assignedDataset(dataset).getInstancesToLabel()){
                // at the consistencyChechParameter user can label previous labeled instances again
                double consistencyCheckRandom = (int)(Math.random()*100);
                double consistencyCheckProbability = user.getConsistencyCheckProbability()*800;
                if (consistencyCheckRandom < consistencyCheckProbability && user.assignedDataset(dataset).getInstances().size()!=0){
                    int previousSelectRandom = (int)(Math.random()*(user.assignedDataset(dataset).getInstances().size()));
                    Instance copyInstance = new Instance(user.assignedDataset(dataset).getInstances().get(previousSelectRandom).getId(),
                            user.assignedDataset(dataset).getInstances().get(previousSelectRandom).getInstance());
                    super.labelingMechanism = new UserMechanism() ;
                    super.labelingMechanism.labelingMechanism(user,copyInstance,dataset,users,datasets);
                }
                super.labelingMechanism = new UserMechanism() ;
                super.labelingMechanism.labelingMechanism(user,currentInstance,dataset,users,datasets);
            }

        }else
            System.out.println("This user not allowed for current dataset");

    }

    //Solution mechanism for this class
    @Override
    public void runBot(ArrayList<User> users, Dataset dataset,ArrayList<Dataset>datasets) {
        // defining problem type , for now we just use randomLabeling
        // with this method , we randomly select the number of users which labels the instance , for example
        // for 1. instance , program selects random number of users , it can select 1 user or 3 user for 1 instance ,
        // for each instance , users labels the instances different.

        ArrayList<User> selectedUsers = new ArrayList<>();
        selectedUsers = selectUsers(users,selectedUsers,dataset);           // select users from dataset
        selectedUsers.sort(Comparator.comparingLong(User::getUserID));      // sort the list

        for(User currentUser : selectedUsers){
            if(currentUser.assignedDataset(dataset)==null)continue;

            if (currentUser.assignedDataset(dataset).getInstancesToLabel().size()==0){
                JsonFileWriter jsonfilewriter=new JsonFileWriter();
                jsonfilewriter.export(datasets, users, dataset);
            }

            for(Instance currentInstance : currentUser.assignedDataset(dataset).getInstancesToLabel()){

               // yüzde 10 ihtimalle öncekilerden alacak
                double consistencyCheckRandom = (int)(Math.random()*100);
                double consistencyCheckProbability = currentUser.getConsistencyCheckProbability()*100;

                if (consistencyCheckRandom < consistencyCheckProbability && currentUser.assignedDataset(dataset).getInstances().size()!=0){
                    int previousSelectRandom = (int)(Math.random()*(currentUser.assignedDataset(dataset).getInstances().size()));
                    Instance copyInstance = new Instance(currentUser.assignedDataset(dataset).getInstances().get(previousSelectRandom).getId(),
                            currentUser.assignedDataset(dataset).getInstances().get(previousSelectRandom).getInstance());
                    super.labelingMechanism = new RandomMechanism();
                    super.labelingMechanism.labelingMechanism(currentUser,copyInstance,dataset,users,datasets);
                }

                // yüzde 60 sıradakini ekleyecek
                int nextCheckRandom = (int)(Math.random()*100);
                if (nextCheckRandom < 60){
                    Instance copyInstance = new Instance(currentInstance.getId(),currentInstance.getInstance());
                    super.labelingMechanism = new RandomMechanism();
                    super.labelingMechanism.labelingMechanism(currentUser,copyInstance,dataset,users,datasets);
                }

            }

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
