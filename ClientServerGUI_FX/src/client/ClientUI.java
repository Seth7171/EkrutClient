package client;
import javafx.application.Application;

import javafx.stage.Stage;
import logic.Subscriber;

import java.util.Vector;
import gui.SubscriberFinderController;
import gui.ClientConnectorController;
import gui.SucscriberEditController;
import client.ClientController;

public class ClientUI extends Application {
	public static ClientController chat; //only one instance
	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientConnectorController aFrame = new ClientConnectorController(); // create StudentFrame
		 
		aFrame.start(primaryStage);
	}
	
	
}
