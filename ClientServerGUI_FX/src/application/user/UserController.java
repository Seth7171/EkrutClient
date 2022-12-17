package application.user;

import common.connectivity.User;

public class UserController {

    private static User currentuser = null;
    private static String message = null;
    public static User getCurrentuser() {
        return currentuser;
    }
    public static void setCurrentuser(User currentuser) {
        UserController.currentuser = currentuser;
    }
    public static String getMessage() {
        return message;
    }
    public static void setMessage(String message) {
        UserController.message = message;
    }
    public static boolean isLogged(){
        return currentuser != null;
    }
}
