package application.user;

import common.connectivity.User;

/**
 * The UserController class holds and manages the current user for the client side of the application.
 * It includes methods for setting and getting the current user and checking if a user is logged in.
 * @author Lior
 */
public class UserController {

    /**
     * The currentuser variable holds the current user that is logged in.
     */
    private static User currentuser = null;
    
    /**
     * Returns the current user.
     * 
     * @return The current user.
     */
    public static User getCurrentuser() {
        return currentuser;
    }
    
    /**
     * Sets the current user to the provided user.
     * 
     * @param currentuser The user to set as the current user.
     */
    public static void setCurrentuser(User currentuser) {
        UserController.currentuser = currentuser;
    }
    
    /**
     * Returns the login status of the user.
     * 
     * @return true if a user is logged in, false otherwise.
     */
    public static boolean isLogged(){
        return currentuser != null;
    }
}
