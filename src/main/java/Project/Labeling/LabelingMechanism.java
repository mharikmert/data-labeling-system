package Project.Labeling;

import Project.Instance;
import Project.Label;
import Project.User;

import java.util.ArrayList;

public abstract class LabelingMechanism {

    public abstract void labelingMechanism(User user , Instance instance , ArrayList<Label> labels , long maxNumberOfLabels);

}
