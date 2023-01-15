package application.user;

import common.connectivity.Customer;
import common.connectivity.User;


/**
 * The CustomerController class holds and manages the customer information for the client side of the application.
 * It includes methods for setting and getting the current customer, credit number, machine id, subscription status, 
 * subscriber number,and whether the customer is logged in.
 * @author Ron & Nitsan
 */
public class CustomerController {

    /**
     * The currentCustomer variable holds the current customer that is logged in.
     */
    private static Customer currentCustomer = null;
    /**
     * The creditnumber variable holds the credit card number of the customer
     */
    private static String creditnumber = null;
    /**
     * The machineID variable holds the identification number of the machine that the customer is using.
     */
    private static String machineID = null;
    /**
     * The isFirstTimeBuyasSub variable holds the status whether customer is buying subscription for the first time or not.
     */
    private static boolean isFirstTimeBuyasSub = false;
    /**
     * The isSub variable holds the status whether customer is subscribed or not.
     */
    private static boolean isSub = false;
    /**
     * The subscribernNumber variable holds the subscriber number of the customer.
     */
    private static int subscribernNumber;

    /**
     * Sets the current customer to the provided user.
     *
     * @param currentUser The user to set as the current customer.
     */
    public static void setCurrentCustomer(User currentUser) {
    	if (currentUser==null) {
    		currentCustomer = null;
    		return;
    	}
    	currentCustomer = new Customer(currentUser.getUsername(), currentUser.getPassword(),currentUser.getFirstname(),
    			currentUser.getLastname(), currentUser.getId(),
    			currentUser.getPhonenumber(), currentUser.getEmailaddress(), 
    			currentUser.getIsLoggedIn(), currentUser.getDepartment(), 
    			currentUser.getStatus(), creditnumber);	
    }
    
    /**
     * Returns the login status of the customer.
     *
     * @return true if a customer is logged in, false otherwise.
     */
    public static boolean isLogged(){
        return currentCustomer != null;
    }
    
    /**
     * Returns the credit card number of the customer.
     * 
     * @return The credit card number of the customer.
     */
    public static String getCreditnumber() {
		return creditnumber;
	}
    
    /**
     * Sets the credit card number of the customer to a new value.
     * 
     * @param creditnumber The new credit card number for the customer.
     */
	public static void setCreditnumber(String creditnumber) {
		CustomerController.creditnumber = creditnumber;
	}
	
    /**
     * Returns the current customer.
     * 
     * @return The current customer.
     */
	public static Customer getCurrentCustomer() {
        return currentCustomer;
    }
	
    /**
     * Sets the machine ID
     * to a new value.
     *
     * @param machineID The new machine ID for the customer.
     */
    public static void setmachineID(String machineID) {
    	CustomerController.machineID = machineID;
	}
    
    /*
    * Returns the machine ID of the customer.
    *
    * @return The machine ID of the customer.
    */
    public static String getmachineID() {
		return machineID;
	}
    
    /*
    * Sets the isFirstTimeBuyasSub variable.
    *
    * @param isFirstTimeBuyasSub True if the customer is buying subscription for the first time, false otherwise.
    */
    public static void setisFirstTimeBuyasSub(Boolean isFirstTimeBuyasSub) {
    	CustomerController.isFirstTimeBuyasSub = isFirstTimeBuyasSub;
	}
    
    /*
    * Returns the isFirstTimeBuyasSub variable.
    *
    * @return The isFirstTimeBuyasSub variable.
    */
    public static Boolean getisFirstTimeBuyasSub() {
		return isFirstTimeBuyasSub;
	}
    
    /*
    * Returns the isSub variable.
    *
    * @return The isSub variable.
    */
    public static Boolean getisSub() {
		return isSub;
	}
    
    /*
    * Sets the isSub variable.
    *
    * @param isSub True if the customer is subscribed, false otherwise.
    */
    public static void setisSub(boolean isSub) {
    	CustomerController.isSub = isSub;
	}
    
    /*
    * Sets the subscriber number of the customer to a new value.
    *
    * @param subscribernNumber The new subscriber number for the customer.
    */
    public static void setsubscribernNumber(int subscribernNumber) {
    	CustomerController.subscribernNumber = subscribernNumber;
	}
    
    /*
    * Returns the subscriber number of the customer.
    *
    * @return The subscriber number of the customer.
    */
    public static int getsubscribernNumber() {
		return subscribernNumber;
	}
}
