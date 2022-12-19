package application.client;
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import gui.ConnectionScreens.ClientConnectorController;

public class ClientUI extends Application {
	public static ClientController chat; //only one instance
	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientConnectorController aFrame = new ClientConnectorController(); // create StudentFrame
		primaryStage.initStyle(StageStyle.UNDECORATED);
		aFrame.start(primaryStage);
	}
	
	
}
