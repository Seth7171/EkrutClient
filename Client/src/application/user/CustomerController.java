package application.user;

import application.client.ClientUI;
import common.connectivity.Customer;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.User;

public class CustomerController {

    private static Customer currentCustomer = null;
    private static String creditnumber = null;

    public static void setCurrentCustomer(User currentUser) {
    	if (currentUser==null) {
    		currentCustomer = null;
    		return;
    	}
    	//ClientUI.chat.accept(new Message(currentUser, MessageFromClient.REQUEST_CREDITCARD)); // TODO: this should be uncommented
    	currentCustomer = new Customer(currentUser.getUsername(), currentUser.getPassword(),currentUser.getFirstname(),
    			currentUser.getLastname(), currentUser.getId(),
    			currentUser.getPhonenumber(), currentUser.getEmailaddress(), 
    			currentUser.getIsLoggedIn(), currentUser.getDepartment(), 
    			currentUser.getStatus(), creditnumber);	
    }
    public static boolean isLogged(){
        return currentCustomer != null;
    }
    public static String getCreditnumber() {
		return creditnumber;
	}
	public static void setCreditnumber(String creditnumber) {
		CustomerController.creditnumber = creditnumber;
	}
	public static Customer getCurrentCustomer() {
        return currentCustomer;
    }
}
