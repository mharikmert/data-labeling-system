package Project.Labeling;

import Project.Dataset;
import Project.Instance;
import Project.JsonIO.JsonFileWriter;
import Project.Label;
import Project.Metrics.InstanceMetrics;
import Project.User;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RuleBasedMechanism extends LabelingMechanism {

    @Override
    public void labelingMechanism(User user, Instance instance, ArrayList<Label> labels, Dataset dataset, ArrayList<User> users, ArrayList<Dataset> datasets) {

        ArrayList<Label> tempLabels = new ArrayList<>(labels);
        long start = System.currentTimeMillis();
        final Logger logger = Logger.getLogger("InstanceTagger");

        int numberOfLabels = (int)(Math.random()*(dataset.getMaxNumberOfLabelsPerInstance()))+1;
        HashSet<Object> usersSet=new HashSet<>();
        user.addInstanceToUser(dataset, instance);

        // in this for loop , we select the labels
        for (int k=0 ; k<numberOfLabels ; k++) {
            ArrayList<HashMap<Object,Object>> tempList=InstanceMetrics.getInstanceMetrics().labelAssignments(dataset, instance, users);
       
            int instanceLength = instance.getInstance().length();

            if (tempList.size() > 0) {

                int addingCount = 0;
                for (int i = 0; i < tempList.size(); i++) {
                    for (Object o : tempList.get(i).keySet()) {
                        addingCount = 0;
                        for (User currentUser : users) {
                            if (currentUser.getUserID() == (long) o) {
                                if (currentUser.getUserType().equals("Human")) {
                                    addingCount = 3;if(!usersSet.contains(currentUser.getUserID()))usersSet.add(currentUser.getUserID());
                                    break;
                                } else if (currentUser.getUserType().equals("RuleBasedBot")) {
                                    addingCount = 2;if(!usersSet.contains(currentUser.getUserID()))usersSet.add(currentUser.getUserID());
                                    break;
                                }
                            }
                        }
                        for (Label currentLabel : labels) {
                            if (currentLabel.getId() == (long) tempList.get(i).get(o)) {
                                for (int j = 0; j < addingCount; j++)
                                    tempLabels.add(currentLabel);
                                break;
                            }
                        }
                    }
                }

            }
            int usersCount=3;
            if(usersSet.size()>3)usersCount=usersSet.size();
                // multilabel için kullanılmış labelları listeden çıkarıyor.
                for (Label usedLabels : instance.getLabels()){
                    tempLabels.removeIf(removeUsedLabels -> removeUsedLabels.getId() == usedLabels.getId());
                }

                instanceLength+=tempLabels.size();
                instanceLength*=instanceLength;
                while (tempLabels.size() < instanceLength)
                    instanceLength /= usersCount;

                instanceLength--;               // size 7 olacak , bölümün sonucu hiç bir zaman 0 çıkmaz ama instancelength 7 olabilir
                                                // size.get(7) error döner, çıkan sonucu 1 eksiltmeliyiz.
                // System.out.println(instance.getId()+" "+instanceLength+" "+tempLabels.get(instanceLength));
                if((int)(k&1)==1)instanceLength=tempLabels.size()-instanceLength;
                if(instanceLength>=0 && instanceLength<tempLabels.size()){instance.addLabelToInstance(tempLabels.get(instanceLength));
                    logger.info("user id:"+user.getUserID()+" "+user.getUserName()+" tagged instance id:"+instance.getId()+" with class label "+tempLabels.get(instanceLength).getId()+":"+tempLabels.get(instanceLength).getText()+" instance:\""+instance.getInstance()+"\"");
                }

        }
        if(instance.getLabels().size()==0)user.getInstances(dataset).remove(instance);
        instance.setTimeStamp(LocalDateTime.now());
        if(!usersSet.contains(user.getUserID()))usersSet.add(user.getUserID());
        JsonFileWriter jsonfilewriter = new JsonFileWriter();
        jsonfilewriter.export(datasets, users, dataset);
        long finish = System.currentTimeMillis();
        instance.setTimeElapsed(finish - start);

    }

}
