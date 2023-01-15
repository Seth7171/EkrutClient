package application.client;
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import gui.ConnectionScreens.ClientConnectorController;

/**
 * The ClientUI class is the main class for the client side of the application.
 * It extends the javafx Application class and overrides the start method to
 * create a ClientConnectorController and display it on the primary stage.
 */
public class ClientUI extends Application {
    /**
     * The chat variable holds the only instance of the ClientController (Singleton).
     */
	public static ClientController chat;
	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
    /**
     * The start method is called when the application is launched. It creates
     * a new instance of the ClientConnectorController and displays it on the
     * primary stage. The primary stage's decorations are removed.
     * 
     * @param primaryStage The primary stage of the application.
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientConnectorController aFrame = new ClientConnectorController(); // create StudentFrame
		primaryStage.initStyle(StageStyle.UNDECORATED);
		aFrame.start(primaryStage);
	}
	
	
}
