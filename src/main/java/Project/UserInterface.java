package Project;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    private String userName ;
    private String password ;

    private UserInterface(){
        setUserName("");
        setPassword("");
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
            setUserName(scanner.nextLine());
            if (getUserName().equals("Exit"))       System.exit(1);                   // if user write exit to name
            System.out.print("Password : ");
            setPassword(scanner.nextLine());
            if (getUserName().equals("") && getPassword().equals(""))       break;          // if it is bot
        }while(userCheck(users)) ;    // user control

    }

    public boolean userCheck(ArrayList<User> users){

        for(User user : users){
            if ( user.getUserName().equals(getUserName()) && user.getPassword().equals(getPassword())){
                users.clear();
                users.add(user);
                return false ;
            }
        }
        System.out.println("Wrong username or password");
        return true ;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
