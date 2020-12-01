import java.util.ArrayList;

public abstract class Problem {
    /*
        This class will be the most parent class of all kind of problems
     */

    LabelingMechanism labelingMechanism ;
    public abstract void runMechanism(ArrayList<User> users, Dataset dataset);

}
