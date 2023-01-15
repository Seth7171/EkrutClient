package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.client.ChatClient;
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.CustomerController;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Order;
import gui.ScreenController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


/**
*
* This class is the controller class for the grab order screen,
*  this class is responsible for the grab order screen's functions.
* @author Nitsan & Ron
* @version 1.0
*/
public class GrabOrderScreenController extends ScreenController implements Initializable{

    @FXML
    private TextField orderNumField;
    
    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Button grabNow;
    
    @FXML
    private Label fieldswarning;
    
    @FXML
    private Label fieldswarning1;

    @FXML
    private Label successLabel;

    /**
     * This method is called when the screen is loaded and it is responsible for initializing the screen's components and the screen's logic.
     * The method sets an event listener on the orderNumField component to listen to the user's press of the Enter key.
     * The method uses the EventHandler interface to handle the event of the user pressing the Enter key.
     * The method calls the GrabOrder() method when the Enter key is pressed.
     * @param arg0 URL
     * @param arg1 ResourceBundle
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    // setting a listener on the orderNumField to listen to the user's press of the enter key
		orderNumField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt1) {
            	// if the user pressed the enter key
                if (evt1.getCode().equals(KeyCode.ENTER)) {
                	// call the GrabOrder method
                	GrabOrder(evt1);
                }
            }
        });
		
	}

	/**
	 * This method is called when the user clicks on the exit button or when the user presses the X button on the top of the screen.
	 * The method is responsible for closing the program.
	 * The method calls the closeProgram() method from the parent class (ScreenController) to close the program.
	 * @param event MouseEvent
	 */
	@FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
	
	/**
	 * This method is responsible for handling the back button event.
	 * It navigates the user to the previous screen when the back button is pressed.
	 * 
	 * @param event the event of the mouse clicking on the back button.
	 */
    @FXML
    void goBack(MouseEvent event) {
        Parent root = null;
        try {
            switch (UserController.getCurrentuser().getDepartment()) {
                case "customer":
                    CustomerController.setCurrentCustomer(UserController.getCurrentuser());
                    root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/CustomerMainScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                case "subscriber":
                    root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/CustomerMainScreen.fxml"));;
                    super.switchScreen(event, root);
                    break;

                case "customer_service":
                    root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceEmployeeScreens/CustomerServiceEmployeeScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                case"ceo":
                    root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                case "operations":
                    root = FXMLLoader.load(getClass().getResource("/gui/OperationsEmployeeScreens/operationsEmployeeMainScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                default:
                    System.out.println("Unknown!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * This method is responsible for handling the action of the "Grab Now" button. It retrieves the order number entered by the user in the
    * orderNumField text field, and sends a request to the server to grab the order with that number. If the order number is not valid or
    * the order does not exist, it displays an error message in the fieldswarning label. If the order was successfully grabbed, it
    * displays a success message in the successLabel label.
    * @param event the mouse event that triggers this method when the "Grab Now" button is clicked
    */
    @FXML
    void GrabOrder(Event event) {
    	String UserID = CustomerController.getCurrentCustomer().getId();
    	String orderNum = orderNumField.getText();  
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(UserID);
    	msg.add(orderNum);
    	if (orderNum.trim().isEmpty()) {
    		super.alertHandler("Enter An Order Number!", true);
			return;
		}	
    	 ClientUI.chat.accept(new Message(msg, MessageFromClient.REQUEST_ORDER_BY_ORDER_ID_AND_CUSTOMER_ID));
    	 if (MessageHandler.getData().equals("Error importing your order")) {
    		 super.alertHandler("Worng Order Number!", true);
    		 return;
    	 }
    	 ChatClient.currentOrder = (Order)MessageHandler.getData();
    	 // we check if the order we received is the order we asked for
    	 if(!ChatClient.currentOrder.getOrderID().equals(orderNum)) {
    		 super.alertHandler("Worng Order Has Recived Call Customer Services!", true);
    		 ChatClient.currentOrder = new Order();
 			 return;
    	 }
    	 // here we check that the order is type DynamicPickUp, else we dont give it to the user.
    	 if(!ChatClient.currentOrder.getSupplyMethod().equals("machine pickup")){
    		 super.alertHandler("This order is not a machine pickup order!", true);
    		 ChatClient.currentOrder = new Order();
 			 return;
    	 }
    	 // we check if the order is not a machine pickup
    	 if(!ChatClient.currentOrder.getOrderStatus().equals("awaiting pickup")){
    		 super.alertHandler("Your order was already picked up!", true);
    		 ChatClient.currentOrder = new Order();
 			 return;
    	 }
    	 //check if the user is in the correct machine for pickup.
    	 if(!ChatClient.currentOrder.getMachineID().equals(CustomerController.getmachineID())){ 
    		 super.alertHandler(String.format("Your order is mented to be picked at %s!", ChatClient.currentOrder.getMachineID()), true);
    		 ChatClient.currentOrder = new Order();
 			 return;
    	 }
    	 
    	 // send the order to be exe by ek op
    	 PostPaymentController.executeOrder(ChatClient.currentOrder);
    	 successLabel.setVisible(true);
    	 System.out.println(ChatClient.currentOrder); 
    	 msg = new ArrayList<String>();
     	 msg.add(orderNum);
     	 msg.add("picked up");
    	 ClientUI.chat.accept(new Message(msg, MessageFromClient.REQUEST_UPDATE_ORDER_STATUS));
    	 //change currentOrder back to empty.
    	 ChatClient.currentOrder = new Order();
    }
    
    
}
