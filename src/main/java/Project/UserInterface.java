package Project;

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

        do {
            System.out.println("If you want to exit write exit to User Name..");
            Scanner scanner = new Scanner(System.in);
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
            scanner.close();
        }while(true) ;    // user control
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
