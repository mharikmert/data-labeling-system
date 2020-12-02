import java.util.ArrayList;

    public class Solution {
    //Solutions will be in there
    public void solveProblem(Dataset dataset, ArrayList<User> users){
        Problem problem;
        // in this switch case statement , solutions differ for datasets , but for first itearation , we have just random labeling and we
        // can not create different problem types , we just create selectedproblem() object.
        switch(dataset.getDatasetName()){
            case "Sentiment Dataset":
                problem = new SelectedProblem();
                problem.runMechanism(users,dataset);
                break;
            case "Multi-label Topic Classification Dataset":
                problem = new SelectedProblem();
                problem.runMechanism(users,dataset);
                break;
            default:
                problem = null;
                System.out.println("Undetermined problem type !");
        }

    }
}
