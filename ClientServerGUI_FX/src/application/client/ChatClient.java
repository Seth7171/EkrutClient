// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package application.client;

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
  public static User  s1 = new User(null,null,null,null,null,null,null);
  public static ArrayList<User> subs = new ArrayList<>();
  public static boolean awaitResponse = false;
  public static String servermsg = new String();

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
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	  subs = new ArrayList<>();
	  System.out.println("--> handleMessageFromServer");
	  System.out.println(msg);
	  servermsg = msg.toString();
     
	  awaitResponse = false;
	  String st;
	  st=msg.toString();
	  BufferedReader bufReader = new BufferedReader(new StringReader(st));
	  //String[] lines = st.split(System.getProperty("line.separator"));
	  //System.out.println("bbbbbbbb " + lines);
	  String line=null;
	  try {
		while( (line=bufReader.readLine()) != null )
		  {
			s1 = new User(null,null,null,null,null,null,null);
			  String[] result = line.split("\\s");
			  s1.setFirstname(result[0]);
			  s1.setLastname(result[1]);
			  s1.setId(result[2]);
			  s1.setPhonenumber(result[3]);
			  s1.setEmailaddress(result[4]);
			  s1.setCreditcardnumber(result[5]);
			  s1.setSubscribernumber(result[6]);
			  subs.add(s1);
		  }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 // for (String line : lines) {

	 // }
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
  
  public void handleMessageFromClientUI(String message)  
  {
    try
    {
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


