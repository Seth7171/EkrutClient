package gui.UserScreens;

import application.client.ChatClient;
import application.client.ClientUI;
import application.client.MessageHandler;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
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
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
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
    
    @FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }

    /**
     * @param event
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
     * @param event
     */
    @FXML
    void NewOrder(Event event) {
    	ChatClient.currentOrder.setSupplyMethod("instant pickup");
        //TODO need to change to NOT hard coded
        //ChatClient.currentOrder.setMachineID("NOR1");
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
        switchScreenWithTimer(event, root);
    }
    
    /**
     * @param event
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
     * @param event
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
