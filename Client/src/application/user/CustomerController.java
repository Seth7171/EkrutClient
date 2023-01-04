package application.user;

import common.connectivity.Customer;
import common.connectivity.User;

public class CustomerController {

    private static Customer currentCustomer = null;
    private static String creditnumber = null;
    private static String machineID = null;
    private static boolean isFirstTimeBuyasSub = false;
    private static String subscribernNumber = null;

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
    public static void setmachineID(String machineID) {
    	CustomerController.machineID = machineID;
	}
    public static String getmachineID() {
		return machineID;
	}
    public static void setisFirstTimeBuyasSub(Boolean isFirstTimeBuyasSub) {
    	CustomerController.isFirstTimeBuyasSub = isFirstTimeBuyasSub;
	}
    public static Boolean getisFirstTimeBuyasSub() {
		return isFirstTimeBuyasSub;
	}
    public static void setsubscribernNumber(String subscribernNumber) {
    	CustomerController.subscribernNumber = subscribernNumber;
	}
    public static String getsubscribernNumber() {
		return subscribernNumber;
	}
}
