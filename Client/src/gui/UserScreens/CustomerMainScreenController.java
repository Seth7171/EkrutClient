package gui.UserScreens;

import application.client.ChatClient;
import application.client.ClientUI;
import application.user.CustomerController;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Product;
import gui.ScreenController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class is responsible for the main menu of the Customer. It contains methods for initializing the view,
 * handling events related to logging out, creating new orders, grabbing existing orders, and viewing deliveries.
 * @author Lior Jigalo
 */
public class CustomerMainScreenController extends ScreenController implements Initializable {

    @FXML
    private Button LogOutButton;

    @FXML
    private Button grabOrderButton;

    @FXML
    private Button newOrderButton;
    
    @FXML
    private Button ViewDeliveriesButton;
    
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

    double xoffset;
    double yoffset;

    /**
     * 
     * Initializes the main menu view by setting the visibility of certain buttons, setting text fields to display information
     * about the current user, and sending a message to the server to request customer data.
     * @param location The location of the FXML file associated with this controller.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	ViewDeliveriesButton.setVisible(false);
		 welcomeBackText.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
	     userStatusText.setText("User Status: " + UserController.getCurrentuser().getStatus());
	     fullNameText.setText(UserController.getCurrentuser().getFirstname() +" " + UserController.getCurrentuser().getLastname());
	     idText.setText("ID: " + UserController.getCurrentuser().getId());
	     depText.setText(UserController.getCurrentuser().getDepartment());
	     emailText.setText(UserController.getCurrentuser().getEmailaddress());
	     phoneText.setText(UserController.getCurrentuser().getPhonenumber());
        ClientUI.chat.accept(new Message(CustomerController.getCurrentCustomer().getId(), MessageFromClient.REQUEST_CUSTOMER_DATA));
        if (ChatClient.isOL) {
        	grabOrderButton.setVisible(false);
        	ViewDeliveriesButton.setVisible(true);
        }
    }
    
    /**
     * Handles the event of the user clicking the "Exit" button. Closes the program.
     * @param event The event of the mouse clicking the "Exit" button.
     */
    @FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }

    /**
     * Handles the event of the user clicking the "Log Out" button. Sends a message to the server to request log out,
     * sets the current user and customer to null, and switches to the log in screen.
     * @param event The event of the user clicking the "Log Out" button.
     */
    @FXML
    void logOut(Event event) {
        ArrayList<String> credentials = new ArrayList<String>();
        credentials.add(UserController.getCurrentuser().getUsername());
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGOUT));
        UserController.setCurrentuser(null);
        CustomerController.setCurrentCustomer(null);
        ChatClient.rememberMyCart = new ListView<Object>();
        ChatClient.cartList = new ArrayList<Product>();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);
    }

    /**
     * Handles the event of the user clicking the "New Order" button. Sets the supply method of the current order to
     * "instant pickup" and switches to the appropriate screen depending on whether the user is EK or OL.
     * @param event The event of the user clicking the "New Order" button.
     */
    @FXML
    void NewOrder(Event event) {
    	ChatClient.currentOrder.setSupplyMethod("instant pickup");
        Parent root = null;
        try {
    		if (ChatClient.isOL) {
            	root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/DeliveryOprtionsScreen.fxml"));
        		}
        	else {
        		root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ProductCatalogScreen.fxml"));
        		}
        } catch (IOException e) {
            e.printStackTrace();
        }
        //super.switchScreen(event, root);
        switchScreen(event, root);
        // switchScreenWithTimer(event, root);
    }
    
    /**
     * Handles the event of the user clicking the "Grab Order" button. Switches to the "Grab Order" screen.
     * @param event The event of the user clicking the "Grab Order" button.
     */
    @FXML
    void GrabOrder(Event event) {
                Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/GrabOrderScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);
    }
    
    /**
     * Handles the event of the user clicking the "View Deliveries" button. Switches to the "View Deliveries" screen.
     * @param event The event of the user clicking the "View Deliveries" button.
     */
    @FXML
    void ViewDeliveries(Event event) {
                Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ViewDeliveriesScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);
    }

}
