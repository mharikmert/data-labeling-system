package Project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    private User authenticatedUser;

    private UserInterface(){
        
    }

    private static final UserInterface userInterface = new UserInterface();

    public static UserInterface getUserInterface(){
        return userInterface;
    }

    public void run(ArrayList<User> users){
        authenticatedUser=null;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("If you want to exit write exit to User Name..");
            System.out.print("User Name : ");
            String username=scanner.nextLine();
            if (username.toLowerCase().equals("exit"))
                System.exit(1);                   // if user write exit to name
            System.out.print("Password : ");
            String password=scanner.nextLine();
            User usergotten=getUser(users, username, password);
            if(username.equals("") && password.equals("")) {break;}
            else if (usergotten!=null){authenticatedUser=usergotten;break;}
            else System.out.println("Wrong username or password");
        }while(true) ;    // user control
    }

    public void runLabel(Dataset dataset , Instance instance){

        Scanner scanner = new Scanner(System.in);
        String select = null ;
        boolean control = false ;
        System.out.println(instance.getId()+ "-->"+instance.getInstance());
        for (Label label : dataset.getLabels()){
            System.out.println(label.getId() + "->" + label.getText());
        }

        do {
            System.out.println("Select labels from the list (between " + 1 + "-" + dataset.getMaxNumberOfLabelsPerInstance() + "): ");
            select = scanner.nextLine();
            String[] selects = select.split(",");
            for (int i=0 ; i<selects.length ; i++){
                selects[i]=selects[i].trim();
                for (Label label : dataset.getLabels()){
                    if (String.valueOf(label.getId()).equals(selects[i])){
                        instance.addLabelToInstance(label);
                        control=true;
                        break;
                    }
                }
            }
            instance.setTimeStamp(LocalDateTime.now());
            if (!control)
                System.out.println("Select labels from the list : ");
        }while (!control);

    }

    private User getUser(ArrayList<User> users,Object username,Object password){
        for(User user : users){
            if (user.getUserName().equals(username) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public User getAuthenticatedUser(){
        return authenticatedUser;
    }
}
