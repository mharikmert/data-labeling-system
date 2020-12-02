import java.util.ArrayList;

public class RandomMechanism extends LabelingMechanism{

    /*
        For the first iteration , instance of this class will be used for random solution

     */

    @Override
    public void labelingMechanism(User user, Instance instance, ArrayList<Label> labels , long maxNumberOfLabels) {

        // we create random number for number of labels for an instance
        int numberOfLabels = (int)(Math.random()*(maxNumberOfLabels-1))+1;

        // in this for loop , we select the labels
        for (int i=0 ; i<numberOfLabels ; i++){
            // select random label with createRandomLabel method
            Label randomLabel = createRandomLabel(labels);
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
                        randomLabel = createRandomLabel(labels) ;
                    }
                }
                // add the label to instance
                instance.addLabelToInstance(randomLabel);
            }

        }
        user.addInstanceToUser(instance);
    }

    Label createRandomLabel(ArrayList<Label> labels){
        // randomly select one label from labels array
        return labels.get((int)(Math.random()*labels.size()));

    }

}
