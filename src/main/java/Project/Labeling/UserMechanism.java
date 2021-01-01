package Project.Labeling;

import Project.*;
import Project.JsonIO.JsonFileWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserMechanism extends LabelingMechanism {
    private Scanner scanner = new Scanner(System.in) ;
    public void labelingMechanism(User user , Instance instance , ArrayList<Label> labels , Dataset dataset, ArrayList<User> users, ArrayList<Dataset> datasets){
        long start = System.currentTimeMillis();
        UserInterface.getUserInterface().runLabel(dataset,instance);

        instance.setTimeStamp(LocalDateTime.now());
        user.addInstanceToUser(dataset,instance);
        JsonFileWriter jsonfilewriter=new JsonFileWriter();
        jsonfilewriter.export(datasets, users, dataset);
   
        long finish = System.currentTimeMillis();
        instance.setTimeElapsed(finish-start);
    }    
}
