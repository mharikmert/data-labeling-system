package Project.Labeling;

import java.time.LocalDateTime;
import java.util.ArrayList;

import Project.Dataset;
import Project.Instance;
import Project.JsonIO.JsonFileWriter;
import Project.Label;
import Project.User;
import org.apache.log4j.Logger;

public class RandomMechanism extends LabelingMechanism {

    @Override
    public void labelingMechanism(User user, Instance instance, Dataset dataset, ArrayList<User> users,ArrayList<Dataset>datasets) {
        final Logger logger = Logger.getLogger("InstanceTagger");
        // we create random number for number of labels for an instance
        long start = System.currentTimeMillis();
        int numberOfLabels = (int)(Math.random()*(dataset.getMaxNumberOfLabelsPerInstance()))+1;
        // in this for loop , we select the labels
        for (int i=0 ; i<numberOfLabels ; i++){
            // select random label with createRandomLabel method

            Label randomLabel = createRandomLabel(dataset.getLabels());
            // if the instance has no label , add the label directly , we don't need to control.
            if (instance.getLabels().size() == 0){
                instance.addLabelToInstance(randomLabel);
            }
            else{
                // in this for loop , we look all labels inside the instance ,
                for (int j=0 ; j<instance.getLabels().size() ; j++){
                    // if a label is added before , create new label for
                    // this instance , don't add same label again
                    while (instance.getLabels().get(j).getId() == randomLabel.getId()){
                        // create new label
                        randomLabel = createRandomLabel(dataset.getLabels());
                        j=0 ;
                    }
                }
                // add the label to instance
                instance.addLabelToInstance(randomLabel);
            }

            instance.setTimeStamp(LocalDateTime.now());
            if(i==0)user.addInstanceToUser(dataset,instance);
            JsonFileWriter jsonfilewriter=new JsonFileWriter();
            jsonfilewriter.export(datasets, users, dataset);

            logger.info("user id:"+user.getUserID()+" "+user.getUserName()+" tagged instance id:"+instance.getId()+" with class label "+randomLabel.getId()+":"+randomLabel.getText()+" instance:\""+instance.getInstance()+"\"");

            // writing a JSON output file
        }

        long finish = System.currentTimeMillis();
        instance.setTimeElapsed(finish-start);

    }

    public Label createRandomLabel(ArrayList<Label> labels){
        // randomly select one label from labels array
        return labels.get((int)(Math.random()*labels.size()));
    }

}
