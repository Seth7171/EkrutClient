package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import application.client.ChatClient;
import application.client.ClientUI;
import application.user.CustomerController;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


/**

The PaymentScreenController class is a controller class for the PaymentScreen.fxml file.
It handles all the events that happens in the PaymentScreen and communicates with the server
to make sure the payment is successful and the order is placed.
It implements Initializable interface to initialize the PaymentScreen.
@author Ron & Nitsan
@version 1.0
@since 2020-11-11
*/
public class PaymentScreenController extends ScreenController implements Initializable{
	private boolean remember = true;
    @FXML
    private Text Subtotal;
    
    @FXML
    private Label fieldswarning;
    
    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Button delay;
    
    @FXML
    private Button paybotton;
    
    @FXML
    private Button rememberMyCard;
    
    @FXML
    private Button infoCvv;
    
    @FXML
    private TextField cardNumberTextField;
    
    @FXML
    private TextField cardNameTextField;
    
    @FXML
    private TextField cardCVVTextField;
    
    @FXML
    private ComboBox<String> monthCombobox;
    
    @FXML
    private ComboBox<String> yearCombobox;
    
    /**
    *
    * Initializes the elements of the PaymentScreen.
    * This method is called by JavaFX when the FXML file is loaded.
    * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
    * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
    */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (UserController.getCurrentuser().getDepartment().equals("customer")) {
			delay.setVisible(false);
		}
		Subtotal.setText(String.valueOf(ChatClient.currentOrder.getOverallPrice()) + "\u20AA");
		
		monthCombobox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
		yearCombobox.getItems().addAll("2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033");
		
        // Create a tooltip to display the cvv helper
        Tooltip tooltip = new Tooltip();
        ImageView cvvimage = new ImageView("/gui/OrderScreens/cvvHelper.png");
        tooltip.setGraphic(cvvimage);
        tooltip.setShowDelay(null);
        infoCvv.setTooltip(tooltip);
        infoCvv.setStyle("-fx-background-color: transparent;");
        
        //make only credit card text field
        cardNumberTextField.setTextFormatter(new TextFormatter<>(c ->
        c.getControlNewText().matches("\\d{0,16}") ? c : null));
        
      //make only cvv text field
        cardCVVTextField.setTextFormatter(new TextFormatter<>(c ->
        c.getControlNewText().matches("\\d{0,3}") ? c : null));
	}

	private boolean callCreditCardCompany(String cardNumber, String cardName, String cardYear, String cardMonth, String cardCVV, float totalPrice) {
		return true;
	}
	
	/**
    *
	* This method allows the user to remember the credit card information for future use.
	* @param event The event that triggered the execution of this method, in this case, a mouse click.
	*/
	@FXML
    void rememberMyCard(MouseEvent event) {
		if (remember) {
			rememberMyCard.setText("Pay with another card");
			cardNumberTextField.setText(CustomerController.getCurrentCustomer().getCreditCardNumber());
			cardNameTextField.setText(CustomerController.getCurrentCustomer().getFirstname()
					+" "+ CustomerController.getCurrentCustomer().getLastname());
			remember = false;
			return;
		}
		else {
			rememberMyCard.setText("Pay with your saved Credit Card");
			cardNumberTextField.clear();;
			cardNameTextField.clear();
			remember = true;
		}
    }
	
	/**
	* This method is responsible for handling the exit button event.
	* It closes the program when the exit button is pressed.
	*
	* @param event the event of the mouse clicking on the exit button.
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
            root = FXMLLoader.load(getClass().getResource("CheckoutScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);        
    }
    
    /**
     * This method is responsible for handling the delay button event.
     * It allows the user to delay the delivery of the order when the delay button is pressed.
     * 
     * @param event the event of the mouse clicking on the delay button.
     */
	@FXML
    void delayPay(MouseEvent event) {
        ChatClient.currentOrder.setPaidWith("delayed payment");
		generateInvoice(event, true);
	}
    
	/**
    *
	* The method that handle the payment process, it get the credit card details, check if the details are valid, if so
	* it will create a new invoice, and send a request to update the status of the order to the server.
	* @param event: a mouse event that happen when the user click the pay button
	*/
	@FXML
    void pay(MouseEvent event) {
		ChatClient.currentOrder.setPaidWith("credit card");
		String cardNumber = cardNumberTextField.getText();
		String cardName = cardNameTextField.getText();
		String cardYear = yearCombobox.getValue();
		String cardMonth = monthCombobox.getValue();
		String cardCVV = cardCVVTextField.getText();
		
		if (cardNumber.trim().isEmpty() || cardNumber.length()<16 || cardName.trim().isEmpty() || 
				cardYear.trim().isEmpty() || cardMonth.trim().isEmpty() ||
				cardCVV.trim().isEmpty()) {
			fieldswarning.setVisible(true);
			return;
		}
			
		
		if(callCreditCardCompany(cardNumber, cardName ,cardYear, cardMonth, cardCVV, ChatClient.currentOrder.getOverallPrice())) {
			generateInvoice(event, false);
		}
	}
	
	/**
    *
	* This method is responsible for generating the invoice for the current order and updating the order status,
	* order ID and confirmation date. It also sets the estimated delivery time and machine ID if the supply method is machine pickup or instant pickup.
	* @param event the event that triggers this method, usually a mouse click on a button
	* @param isdelayed a boolean value that indicates whether the order is delayed or not
	*/
	void generateInvoice(MouseEvent event, boolean isdelayed) {
		//GETTING THE DATE and setting it in order.
		// Get the current time
	    Date currentDate = new Date();
	    // Create a SimpleDateFormat object to format the date as a string
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
	    // Format the current date as a string
	    String dateString = formatter.format(currentDate);
	    ChatClient.currentOrder.setOrderDate(dateString);
		
		// generate order number and paid with
        String  uuid = UUID.randomUUID().toString().substring(0, 8);
        ChatClient.currentOrder.setOrderID(uuid);
        
        // if the subscriber bought for the first time, here we are saying goodbye to the discount.
        if(CustomerController.getisSub()) {
        	if(CustomerController.getisFirstTimeBuyasSub()) {
        		CustomerController.setisFirstTimeBuyasSub(false);
        		ArrayList<String> msg = new ArrayList<String>();
        		msg.add("false");
        		msg.add(CustomerController.getCurrentCustomer().getId());
        		ClientUI.chat.accept(new Message(msg, MessageFromClient.REQUEST_SET_FIRST_TIME_BUY_AS_SUB));
        	}
        }
        // IF DELIVERY :
        if (ChatClient.currentOrder.getSupplyMethod().equals("delivery")) {
	        ChatClient.currentOrder.setOrderStatus("awaiting approval");
        }
        // IF dynamic pickup :
        if(ChatClient.currentOrder.getSupplyMethod().equals("machine pickup")) {
        	ChatClient.currentOrder.setOrderStatus("awaiting pickup");
    		ChatClient.currentOrder.setEstimatedDeliveryTime(null);
    		ChatClient.currentOrder.setConfirmationDate(dateString);
        }
        // ELSE :
        if(ChatClient.currentOrder.getSupplyMethod().equals("instant pickup")) {
        	ChatClient.currentOrder.setMachineID(CustomerController.getmachineID());
	        ChatClient.currentOrder.setOrderStatus("processing");
	        ChatClient.currentOrder.setEstimatedDeliveryTime(dateString);
	        ChatClient.currentOrder.setConfirmationDate(dateString);
        }
        System.out.println(ChatClient.currentOrder);
        
        ClientUI.chat.accept(new Message(ChatClient.currentOrder, MessageFromClient.REQUEST_ADD_NEW_ORDER));
		
		Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("PostPaymentScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);
	}

	
}
