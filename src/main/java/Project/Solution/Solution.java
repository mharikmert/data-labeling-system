package Project.Solution;

import Project.Dataset;
import Project.User;
import Project.Problem.Problem;
import Project.Problem.SelectedProblem;

import java.util.ArrayList;

    /*
    * Implementing Solution class with singleton design pattern
    * */

    public class Solution {

        // this class can not be instantiated
        private Solution(){}

        //create an object of Solution
        private static final Solution solution = new Solution();

        //get the only available object
        public static Solution getSolution(){
            return solution;
        }
        //Solutions will be in there
        public void solveProblem(Dataset dataset, ArrayList<User> users){
            Problem problem;
            // in this switch case statement , solutions differ for datasets , but for first iteration , we have just random labeling and we
            // can not create different problem types , we just create selectedProblem() object.
            switch(dataset.getDatasetName()){
                case "Sentiment Dataset":
                case "Multi-label Topic Classification Dataset":
                    problem = new SelectedProblem();
                    problem.runMechanism(users,dataset);
                    break;
                default:
                    System.out.println("Undetermined problem type !");
            }
        }
}
