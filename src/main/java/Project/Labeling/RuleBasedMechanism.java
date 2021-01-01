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

public class RuleBasedMechanism extends LabelingMechanism {

    @Override
    public void labelingMechanism(User user, Instance instance, ArrayList<Label> labels, Dataset dataset, ArrayList<User> users, ArrayList<Dataset> datasets) {

        ArrayList<Label> tempLabels = new ArrayList<>(labels);
        long start = System.currentTimeMillis();
        final Logger logger = Logger.getLogger("InstanceTagger");

        int numberOfLabels = (int)(Math.random()*(dataset.getMaxNumberOfLabelsPerInstance()))+1;

        // in this for loop , we select the labels
        for (int k=0 ; k<numberOfLabels ; k++) {

            int instanceLength = instance.getInstance().length();

            int userCount = 3;

            if (InstanceMetrics.getInstanceMetrics().labelAssignments(dataset, instance, users).size() > 0) {

                int addingCount = 0;
                for (int i = 0; i < InstanceMetrics.getInstanceMetrics().labelAssignments(dataset, instance, users).size(); i++) {
                    for (Object o : InstanceMetrics.getInstanceMetrics().labelAssignments(dataset, instance, users).get(i).keySet()) {
                        addingCount = 0;
                        userCount++;
                        for (User currentUser : users) {
                            if (currentUser.getUserID() == (long) o) {
                                if (currentUser.getUserType().equals("Human")) {
                                    addingCount = 3;
                                    break;
                                } else if (currentUser.getUserType().equals("RuleBasedBot")) {
                                    addingCount = 2;
                                    break;
                                }
                            }
                        }
                        for (Label currentLabel : labels) {
                            if (currentLabel.getId() == (long) InstanceMetrics.getInstanceMetrics().labelAssignments(dataset, instance, users).get(i).get(o)) {
                                for (int j = 0; j < addingCount; j++)
                                    tempLabels.add(currentLabel);
                                break;
                            }
                        }
                    }
                }

            }

                // multilabel için kullanılmış labelları listeden çıkarıyor.
                for (Label usedLabels : instance.getLabels()){
                    tempLabels.removeIf(removeUsedLabels -> removeUsedLabels.getId() == usedLabels.getId());
                }

                while (instanceLength < tempLabels.size() || instanceLength < userCount)
                    instanceLength *= instanceLength;
                while (tempLabels.size() < instanceLength)
                    instanceLength /= userCount;

                instanceLength--;               // size 7 olacak , bölümün sonucu hiç bir zaman 0 çıkmaz ama instancelength 7 olabilir
                                                // size.get(7) error döner, çıkan sonucu 1 eksiltmeliyiz.

                instance.addLabelToInstance(tempLabels.get(instanceLength));

        }

        user.addInstanceToUser(dataset, instance);
        instance.setTimeStamp(LocalDateTime.now());
        JsonFileWriter jsonfilewriter = new JsonFileWriter();
        jsonfilewriter.export(datasets, users, dataset);
        long finish = System.currentTimeMillis();
        instance.setTimeElapsed(finish - start);

    }

}


