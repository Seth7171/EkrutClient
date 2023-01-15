package gui.DeliveryEmployeeScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * DeliveryEmployeeScreenController class is responsible for controlling the GUI for delivery employees.
 * It allows the delivery employee to log out, view their orders, and exit the program.
 * It also allows to displays the user's orders.
 */
public class DeliveryEmployeeScreenController extends ScreenController implements Initializable{
    
    @FXML
    private Button LogOutButton;

    @FXML
    private Button grabOrderButton;

    @FXML
    private Button viewOrdersButton;
    
    @FXML
    private Button exitButton;

    @FXML
    private Text userStatusText;

    @FXML
    private Text welcomeBackText;
    
    @FXML
    private Text fullNameText;

    @FXML
    private Text idText;
    
    @FXML
    private Text depText;

    @FXML
    private Text emailText;
    
    @FXML
    private Text phoneText;


    /**
     * initialize method is responsible for initializing the GUI elements when the program starts.
     * It sets the welcome message, user status, user's full name, id, department, email, and phone number.
     * 
     * @param arg0 url
     * @param arg1 ResourceBundle
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 welcomeBackText.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
	     userStatusText.setText("User Status: " + UserController.getCurrentuser().getStatus());
	     fullNameText.setText(UserController.getCurrentuser().getFirstname() +" " + UserController.getCurrentuser().getLastname());
	     idText.setText("ID: " + UserController.getCurrentuser().getId());
	     depText.setText(UserController.getCurrentuser().getDepartment());
	     emailText.setText(UserController.getCurrentuser().getEmailaddress());
	     phoneText.setText(UserController.getCurrentuser().getPhonenumber());
	}
	
	/**
	 * Closes the program when the exit button is pressed.
	 * 
	 * @param event the MouseEvent that triggers the exit method
	 */
	@FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
	 
	/**
	 * Logs the current user out, sends a logout request to the server and redirects to the login screen.
	 * 
	 * @param event the Event that triggers the logOut method
	 */
	@FXML
    void logOut(Event event) {
        ArrayList<String> credentials = new ArrayList<String>();
        credentials.add(UserController.getCurrentuser().getUsername());
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGOUT));
        UserController.setCurrentuser(null);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/LogInScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);
    }
	

	/**
	 * Redirects to the DeliveriesScreen.fxml where the current user can view their orders.
	 * 
	 * @param event the MouseEvent that triggers the viewOrders method
	 */
	@FXML
    void viewOrders(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/DeliveryEmployeeScreens/DeliveriesScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);
    }

}
