import java.util.ArrayList;

public abstract class Problem {

    LabelingMechanism labelingMechanism ;
    public abstract void runMechanism(ArrayList<User> users, Dataset dataset);

}
