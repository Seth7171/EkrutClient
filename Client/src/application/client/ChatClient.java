// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package application.client;

import application.user.CustomerController;
import application.user.UserController;
import common.connectivity.ChatIF;
import common.connectivity.Customer;
import common.connectivity.Message;
import common.connectivity.User;
import common.orders.Order;
import common.orders.Product;
import javafx.scene.control.ListView;
import ocsf.client.AbstractClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class overrides some methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
	ChatIF clientUI; 
	  public static User s1 = new User();
	  public static ArrayList<User> subs = new ArrayList<>();
	  public static boolean awaitResponse = false;
	  public static String servermsg = new String();
	  public static ArrayList<Product> productList = new ArrayList<Product>();
	  public static ArrayList<Product> cartList = new ArrayList<Product>();
	  public static ArrayList<Order> orderList = new ArrayList<Order>();
	  public static Order currentOrder = new Order();
	  public static ListView<Object> rememberMyCart = new ListView<Object>();
	  public static String returnMachineID;
	  public static String returnMonthChoose;
	  public static String returnYearChoose;
	  public static String returnLocationChoose;
	  public static boolean isOL;
//  private UserController userController  = UserController.getUserInstance();

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
	 
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    //openConnection();
  }

  //Instance methods ************************************************
  /**
   * This method handles all data that comes in from the server.
   *
   * @param serverMessage The message from the server.
   */
  public void handleMessageFromServer(Object serverMessage) { // TODO: optimize switch case.
	  subs = new ArrayList<>();
	  System.out.println("--> handleMessageFromServer");
	  System.out.println(serverMessage);
	  servermsg = serverMessage.toString();
	  awaitResponse = false;
      Message message = (Message) serverMessage;

      switch (message.getAnswer().name()) {
          case "LOGIN_SUCCESSFUL":
              UserController.setCurrentuser((User)message.getData());
              break;

          case "LOGIN_FAILED_ALREADY_LOGGED_IN":
              MessageHandler.setMessage("already logged in");
              break;

          case "LOG_IN_ERROR_USER_DOES_NOT_EXIST":
              MessageHandler.setMessage("user does not exist");
              break;
          case "CUSTOMER_CREDIT_CARD":
        	  CustomerController.setCreditnumber(((String)message.getData()));
        	  break;

          case "ERROR_UPDATING_DEAL":
          case "DEAL_UPDATED_SUCCESSFULLY":
          case "ERROR_GETTING_DEALS":
          case "USERS_STATUSES_UPDATED_SUCCESSFULLY":
          case "ERROR_UPDATING_USERS_STATUSES":
          case "ERROR_IMPORTING_CUSTOMERS_FROM_USER_TABLE":
          case "CHECK_IF_CUSTOMER_IS_SUB_SUCCESSFUL":
          case "ERROR_CHECKING_IF_CUSTOMER_IS_SUB":
          case "ADD_NEW_ORDER_SUCCESSFUL":
          case "ERROR_ADDING_NEW_ORDER":
          case "ERROR_UPDATING_FIRST_TIME_BUY_AS_SUB":
          case "UPDATE_FIRST_TIME_AS_SUB_SUCCESSFUL":
          case "ERROR_VERIFYING_CREDIT_CARD":
          case "CREDIT_CARD_VERIFIED_SUCCESSFULLY":
          case "ERROR_GETTING_CUSTOMER_DATA":
          case "SUCCESSFULLY_ASSIGNED_EMPLOYEE_TO_REFILL_REQUEST":
          case "ERROR_ASSIGNING_EMPLOYEE_TO_REFILL_REQUEST":
          case "ERROR_UPDATING_MACHINE_AMOUNT":
          case "ERROR_REMOVING_REFILL_ORDER":
          case "SUCCESSFULLY_UPDATED_AMOUNT_IN_MACHINE":
          case "ERROR_IMPORTING_REFILL_ORDERS":
          case "ERROR_GETTING_CLIENT_REPORT":
          case "PRODUCT_ADDED_SUCCESSFULLY":
          case "ERROR_ADDING_PRODUCT":
          case "ERROR_UPDATING_MACHINE_PRODUCT":
          case "MACHINE_PRODUCT_UPDATED_SUCCESSFULLY":
          case "WAREHOUSE_PRODUCTS_UPDATED_SUCCESSFULLY":
          case "ERROR_UPDATING_WAREHOUSE_PRODUCT":
          case "ERROR_IMPORTING_ALL_MACHINES_MONTHLY_REPORT":
          case "ERROR_IMPORTING_INVENTORY_REPORT":
          case "ERROR_IMPORTING_MACHINE_LOCATIONS":
        	  
          case "IMPORT_ORDER_BY_ORDER_ID_AND_CUSTOMER_ID_SUCCESSFUL":
        	  MessageHandler.setData((Order)message.getData());
        	  currentOrder = (Order)message.getData();
        	  break;
        	  
          case "ERROR_ADDING_USER_EXISTS":
              MessageHandler.setMessage((String)message.getData());
              break;

          case "ERROR_ADDING_USER":
              MessageHandler.setMessage("Unknown Error");
              break;

          case "USER_ADDED_SUCCESSFULLY":
              MessageHandler.setMessage("user added successfully!");
              break;

          case "IMPORT_MACHINE_PRODUCTS_SUCCESSFUL":
              MessageHandler.setData((ArrayList<Product>)message.getData());
        	  productList = (ArrayList<Product>)message.getData();
              // TODO: decide if the below block is necessary.
//              //TODO: you should take the following block and put it where you need it or you can even leave it here.
//              //***********************************************************************
//              byte[] inputFile;
//              for (Product o : productList){
//                  inputFile = o.getFile();
//                  try {
//                      FileOutputStream fos = new FileOutputStream("Client/src/gui/ProductImages/" + o.getName() + ".png");
//                      fos.write(inputFile);
//                      fos.close();
//                  } catch (Exception e) {
//                      e.printStackTrace();
//                      throw new RuntimeException(e);
//                  }
//
//              }
//              //**********************************************************************
              break;

          case "DEALS_IMPORTED_SUCCESSFULLY":
          case "IMPORTING_CUSTOMERS_FROM_USER_TABLE_SUCCESSFUL":
          case "CUSTOMER_DATA_IMPORTED_SUCCESSFULLY":
          case "SUCCESSFULLY_IMPORTED_REFILL_ORDERS":
          case "SUCCESSFULLY_IMPORTED_CLIENT_REPORT":
          case "IMPORT_ALL_MACHINES_MONTHLY_REPORT_SUCCESSFUL":
          case "ERROR_IMPORTING_ORDER":
          case "IMPORT_INVENTORY_REPORT_SUCCESSFUL":
          case "IMPORT_MACHINE_ID_SUCCESSFUL":
          case "IMPORT_MACHINE_LOCATIONS_SUCCESSFUL":
          case "ERROR_IMPORTING_MACHINE_PRODUCTS":
          case "ERROR_IMPORTING_ALL_MACHINE_PRODUCTS":
          case "IMPORT_WAREHOUSE_PRODUCTS_SUCCESSFUL":
              MessageHandler.setData(message.getData());
              break;
      }
  }
  
  public void handleMessageFromServer(String msg) 
  {
	  System.out.println("--> handleMessageFromServer");
	  System.out.println(msg);
	  awaitResponse = false;
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  
  public void handleMessageFromClientUI(Object message) {
    try {
    	openConnection();//in order to send more than one message
       	awaitResponse = true;

    	sendToServer(message);
		// wait for response
		while (awaitResponse) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    catch(IOException e)
    {
    	e.printStackTrace();
      quit();
    }
  }
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {
        e.printStackTrace();
    }
    System.exit(0);
  }
}
//End of ChatClient class


