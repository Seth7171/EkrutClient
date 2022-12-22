package application.user;

import common.connectivity.User;

public class UserController {

    private static User currentuser = null;
    public static User getCurrentuser() {
        return currentuser;
    }
    public static void setCurrentuser(User currentuser) {
        UserController.currentuser = currentuser;
    }
    public static boolean isLogged(){
        return currentuser != null;
    }
}
