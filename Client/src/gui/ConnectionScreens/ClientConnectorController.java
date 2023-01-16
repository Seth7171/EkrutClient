package gui.ConnectionScreens;

import application.client.ChatClient;
import application.client.ClientController;
import application.client.ClientUI;
import application.client.MessageHandler;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The ClientConnectorController class is a JavaFX controller for the client connector screen.
 * It extends the ScreenController class and is responsible for handling user interactions and
 * connecting the client to the server.
 * @author Lior Jigalo, Nitsan Maman, Ron Shahar
 * @version 1.0
 * @since 1.0
 */
public class ClientConnectorController extends ScreenController {
	// fields to keep track of the connection state and user selections
	private boolean choosen;
	private boolean isEK;
	private boolean isConnected;

	// JavaFX GUI elements
	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnDone = null;
	@FXML
	private Button btnEK;
	@FXML
	private Button btnOL;
	@FXML
	private Label lbllist;
	@FXML
	private Label warning;
	@FXML
	private Label warningEKOL;
    @FXML
    private ComboBox<String> machinesID;

	
	@FXML
	private TextField iptxt;
	
	// list to store machine IDs
	ObservableList<String> list;
    double xoffset;
	double yoffset;
    /**
     * Private helper method to get the IP address entered by the user.
     * @return the IP address as a string
     */
	private String getip() {
		return iptxt.getText();			
	}
	
    /**
     * Method called when the user clicks the "Done" button.
     * It checks if the user has selected an EK or OL machine and displays an error message if not.
     * It then attempts to connect to the server using the IP address entered by the user.
     * If the connection is successful, it either requests a list of machine IDs from the server if
     * the user selected EK or switch to LogInScreen.
     * @param event the mouse event that triggered the method call
     * @throws Exception 
     */
	public void Done(MouseEvent event) throws Exception {
		// check if user has selected EK or OL
		if (!choosen) {
			super.alertHandler("Please Chooce One EK / OL", true);
			//warningEKOL.setVisible(true);
			return;
		}
		warningEKOL.setVisible(false);
		String ip;
		ip=getip();
		if(ip.trim().isEmpty()) {
			System.out.println("You Must enter an ip number");
			iptxt.clear();
			super.alertHandler("You Must Enter An IP Number", true);
			//warning.setVisible(true);
			return;
		}

		try {
		ClientUI.chat = new ClientController(ip, 5555);
		ClientUI.chat.getClient().openConnection();
		}

		catch (java.net.ConnectException e) {
			super.alertHandler("Server Refused To Connect", true);
			// e.printStackTrace();
			return;
		}
		catch (java.net.SocketException e) {
			super.alertHandler("IP Is Unreachable", true);
			// e.printStackTrace();
			return;
		}
		catch (java.net.UnknownHostException e) {
			super.alertHandler("Unknown Local Host", true);
			// e.printStackTrace();
			return;
		}
		isConnected = true;
		if (isEK) {
			ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_MACHINE_IDS));
	        ArrayList<String> machineIDs = new ArrayList<>();
	        Object data = MessageHandler.getData();
	        if (!(data instanceof ArrayList<?>)) {
	            alertHandler("There Are No EK machinces", true);
	            return;
	        }
	        machineIDs.addAll((ArrayList<String>) data);
	        machinesID.getItems().clear();
	        machinesID.getItems().addAll(machineIDs);
	        btnOL.setVisible(false);
	        iptxt.setVisible(false);
	        btnDone.setVisible(false);
	        machinesID.setVisible(true);
	        return;
		}
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/LogInScreen.fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		super.switchScreen(event, root);
	}
	
	
    /**
     * Method to initialize the JavaFX application and create the user interface.
     * It loads the FXML file "ClientConnector.fxml" which contains the layout and elements of the user interface,
     * and sets an event handler for when the mouse is pressed on the scene to allow the user to drag and move the window.
     * @param primaryStage the primary stage of the application
     * @throws Exception
     */
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ConnectionScreens/ClientConnector.fxml"));
				
		Scene scene = new Scene(root);

		// event handler for when the mouse is pressed on the scene to trigger the drag and move event
        root.setOnMousePressed(event -> {
            xoffset = event.getSceneX();
            yoffset = event.getSceneY();
        });

        // event handler for when the mouse is pressed AND dragged to move the window
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX()-xoffset);
            primaryStage.setY(event.getScreenY()-yoffset);
        });

		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	@FXML
	public void clickedEK(MouseEvent event) {
		choosen = true;
		isEK = true;
		warningEKOL.setVisible(false);
		// btnEK.setStyle("-fx-background-color: rgba(239,83,246,0.5);");
		btnEK.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 50%, #dd00ff, transparent);");
		btnOL.setStyle(null);
		ChatClient.isOL=false;
	}
	
	@FXML
	public void clickedOL(MouseEvent event) {
		choosen = true;
		isEK = false;
		warningEKOL.setVisible(false);
		// btnOL.setStyle("-fx-background-color: rgba(0,191,255,0.5);");
		btnOL.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 50%, #02f8f8, transparent);");
		btnEK.setStyle(null);
		ChatClient.isOL=true;
	}
	
    @FXML
    void machidWasChoosen(ActionEvent event) {
		ChatClient.currentMachineID = machinesID.getValue();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/LogInScreen.fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		super.switchScreen(event, root);
    }

	@FXML
	public void getExitBtn(MouseEvent event) throws Exception {
		if(isConnected) {
			closeProgram(event,false);
			return;
		}
		Platform.exit();
		System.exit(0);
	}
}