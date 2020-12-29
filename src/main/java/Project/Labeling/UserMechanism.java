package Project.Labeling;

import Project.Dataset;
import Project.Instance;
import Project.JsonIO.JsonFileWriter;
import Project.Label;
import Project.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserMechanism extends LabelingMechanism {

    private Scanner scanner = new Scanner(System.in) ;

    @Override
    public void labelingMechanism(User user, Instance instance, Dataset dataset, ArrayList<User> users, ArrayList<Dataset> datasets) {

        String select = null ;
        boolean control = false ;

        System.out.println(instance.getId()+ "-->"+instance.getInstance());
        for (Label label : dataset.getLabels()){
            System.out.println(label.getId() + "->" + label.getText());
        }

        do {
            System.out.print("Select label : ");
            select = scanner.nextLine();
            for (Label label : dataset.getLabels()){
                if (String.valueOf(label.getId()).equals(select)){
                    instance.addLabelToInstance(label);
                    instance.setTimeStamp(LocalDateTime.now());
                    control=true;
                }
            }
            if (!control)
                System.out.println("Select one of the labels from the list : ");
        }while (!control);

        instance.setTimeStamp(LocalDateTime.now());
        user.addInstanceToUser(dataset,instance);
        JsonFileWriter jsonfilewriter=new JsonFileWriter();
        jsonfilewriter.export(datasets, users, dataset);

    }
}
