package application.user;

import common.connectivity.User;

public class UserController {

    private static UserController usercontroller= null;
    private static User currentuser = null;


    public static UserController getUserInstance(){
        if(usercontroller == null)
            return usercontroller = new UserController();
        return usercontroller;
    }

    public static User getCurrentuser() {
        return currentuser;
    }

    public static void setCurrentuser(User currentuser) {
        UserController.currentuser = currentuser;
    }

    public static boolean isLogged(){
        return usercontroller != null;
    }
}
