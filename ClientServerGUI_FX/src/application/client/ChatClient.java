// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package application.client;

import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Product;
import ocsf.client.*;
import common.connectivity.ChatIF;
import common.connectivity.User;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

import application.client.*;

/**
 * This class overrides some of the methods defined in the abstract
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
  public static User  s1 = new User();
  public static ArrayList<User> subs = new ArrayList<>();
  public static boolean awaitResponse = false;
  public static String servermsg = new String();
  public static ArrayList<Product> productList = new ArrayList<Product>();
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
  public void handleMessageFromServer(Object serverMessage) {
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

          case "ERROR_ADDING_USER_EXISTS":
              String reply = (String)message.getData();
              MessageHandler.setMessage(reply);
              break;
          case "ERROR_ADDING_USER":
              MessageHandler.setMessage("Unknown Error");
              break;
          case "USER_ADDED_SUCCESSFULLY":
              MessageHandler.setMessage("user user added successfully!");
              break;
//          case"IMPORT_MACHINE_ID_SUCCESSFUL":
//              MessageHandler.setData(message.getData());
          case "IMPORT_MACHINE_PRODUCTS_SUCCESSFUL":
        	  productList = (ArrayList<Product>)message.getData();
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
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class


