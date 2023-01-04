package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.ClientUI;
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

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		orderNumField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt1) {
                if (evt1.getCode().equals(KeyCode.ENTER)) {
                	GrabOrder(evt1);
                }
            }
        });
		
	}

	@FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
	
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
                    // TODO: maybe add UnknownScreenException later??
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void GrabOrder(Event event) {
    	String UserID = CustomerController.getCurrentCustomer().getId();
    	String orderNum = orderNumField.getText();  
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(UserID);
    	msg.add(orderNum);
    	if (orderNum.trim().isEmpty()) {
			fieldswarning.setVisible(true);
			return;
		}	
    	 ClientUI.chat.accept(new Message(msg, MessageFromClient.REQUEST_ORDER_BY_ORDER_ID_AND_CUSTOMER_ID));
    	 // we check if the order we received is the order we asked for
    	 if(!ChatClient.currentOrder.getOrderID().equals(orderNum)) {
    		 fieldswarning1.setVisible(true);
    		 ChatClient.currentOrder = new Order();
 			 return;
    	 }
    	 
    	 if(!ChatClient.currentOrder.getOrderStatus().equals("awaiting pickup")){
    		 fieldswarning.setVisible(true);
    		 ChatClient.currentOrder = new Order();
 			 return;
    	 }
    	 // here we check that the order is type DynamicPickUp, else we dont give it to the user.
    	 if(!ChatClient.currentOrder.getSupplyMethod().equals("machine pickup")){
    		 fieldswarning.setVisible(true);
    		 ChatClient.currentOrder = new Order();
 			 return;
    	 }
    	 //check if the user is in the correct machine for pickup.
    	 if(!ChatClient.currentOrder.getMachineID().equals(CustomerController.getmachineID())){ 
    		 fieldswarning.setVisible(true);
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
