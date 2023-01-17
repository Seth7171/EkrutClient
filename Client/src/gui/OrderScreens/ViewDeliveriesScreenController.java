package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Order;
import common.orders.Product;
import gui.ScreenController;
import gui.DeliveryEmployeeScreens.TableOrder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * The ViewDeliveriesScreenController class is a controller class for the View Deliveries screen in the GUI.
 * It handles all the actions and events that occur in the View Deliveries screen.
 *
 * @author  Ron
 * @version 1.0
 * @since   2.01.2023
 */
public class ViewDeliveriesScreenController extends ScreenController implements Initializable{
	
    @FXML
    private Button backButton; // button that allows user to go back to the previous screen
 
    @FXML
    private Button exitButton; // button that allows user to exit the program
    
    @FXML
    private Button refresh; // button that allows user to refresh the deliveries
    
    @FXML
    private Button apply; // button that allows user to filter the deliveries based on the selected status
    
    @FXML
    private TableColumn<Order, String> orderidColumn; // column for displaying the order id
    
    @FXML
    private TableColumn<Order, Float> overallpriceColumn; // column for displaying the overall price of the order
    
    @FXML
    private TableColumn<Order, ArrayList<Product>> productsColumn; // column for displaying the products in the order
    
    @FXML
    private TableColumn<Order, String> orderdateColumn; // column for displaying the date the order was placed
    
    @FXML
    private TableColumn<Order, String> addressColumn; // column for displaying the delivery address
    
    @FXML
    private TableColumn<Order, String> estimatedateColumn; // column for displaying the estimated delivery time
    
    @FXML
    private TableColumn<Order, String> confirmationdateColumn; // column for displaying the date the order was confirmed
    
    @FXML
    private TableColumn<Order, String> orderstatusColumn; // column for displaying the status of the order
    
    @FXML
    private TableColumn<Order, String> customeridColumn; // column for displaying the customer id

    @FXML
    private TableView<Object> viewAllOrders; // table view for displaying all the orders
   
    
    private ArrayList<Order> tempDeliveries; // temporary array list for storing the deliveries
    public static ObservableList<Object> observableDeliveries; // observable list for holding the deliveries to display in the table view
    
    
    
    /**
     * Initializes the View Deliveries screen by setting up the table columns and loading the deliveries.
     *
     * @param arg0 the URL of the FXML file that was loaded to create this controller
     * @param arg1 the resource bundle for localization
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tempDeliveries = new ArrayList<Order>();
		//init columns
		customeridColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		orderidColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
		overallpriceColumn.setCellValueFactory(new PropertyValueFactory<>("overallPrice"));
		productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
		orderdateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		estimatedateColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedDeliveryTime"));
		confirmationdateColumn.setCellValueFactory(new PropertyValueFactory<>("confirmationDate"));
		orderstatusColumn.setCellValueFactory(new PropertyValueFactory<>("status_co"));
		//load user orders for the first time
		loadDeliveries();
	}
	
	
	/**
	 * Handles the event where the user clicks the "Exit" button by closing the program.
	 *
	 * @param event the mouse event that triggered this method call
	 */
    @FXML
    void exit(MouseEvent event) {
    	super.closeProgram(event, true);
    }

    /**
     * Handles the event where the user clicks the "Back" button by navigating back to the previous screen.
     *
     * @param event the mouse event that triggered this method call
     */
    @FXML
    void goBack(MouseEvent event) {
    	Parent root = null;
        try {
          	  root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/CustomerMainScreen.fxml"));
      }catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
    
    
    /**
     * Loads the deliveries from the server and displays them in the table view.
     */
    public void loadDeliveries() {
    	// Create an observable list for holding the deliveries
	    observableDeliveries = FXCollections.observableArrayList();
	    // Clear the observableDeliveries list if it is not empty
    	if (!observableDeliveries.isEmpty())
    		observableDeliveries.clear();
    	// Get the current user's ID
    	String userID = UserController.getCurrentuser().getId();
    	// Send a message to the server requesting the orders by customer ID
    	ClientUI.chat.accept(new Message(userID,MessageFromClient.REQUEST_ORDERS_BY_CUSTOMER_ID)); 
    	// Get the data from the server and store it in the tempDeliveries list
        Object data = MessageHandler.getData();
        if (!(data instanceof ArrayList<?>)) {
        	Platform.runLater(new Runnable() {
        	    @Override
        	    public void run() {
                	alertHandler("You Dont Have Orders On Their Way!", true);
        	    }
        	});
            return;
        }
    	tempDeliveries = (ArrayList<Order>) data;//getting data from server
    	// Add orders from tempDeliveries to observableDeliveries
	    for (Order order : tempDeliveries) {
	    	if (order.getSupplyMethod().equals("delivery")) {
		    	TableOrder torder = new TableOrder(order);
		    	ChoiceBox<String> status = new ChoiceBox<>(FXCollections.observableArrayList("collected","delivery"));
	    		status.setValue(order.getOrderStatus());
	    		torder.setStatus_co(status);
	    		// If the order status is not "approved", disable the status choice box
		    	if (!order.getOrderStatus().equals("approved")) {
		    		status.setDisable(true);
		    		status.setValue(order.getOrderStatus());
		    	}
		    	observableDeliveries.add(torder);
	    	}
	    }
	    // Set the items of the viewAllOrders table to be the observableDeliveries list
	    viewAllOrders.setItems(observableDeliveries);
	    if (observableDeliveries.isEmpty()) {
	    	Platform.runLater(new Runnable() {
        	    @Override
        	    public void run() {
                	alertHandler("You Dont Have Orders On Their Way!", true);
        	    }
        	});
	    }
    }
   
    /**
     * Handles the event where the user clicks the "Refresh" button by reloading the deliveries from the server.
     *
     * @param event the mouse event that triggered this method call
     */
    @FXML
    void Refresh(MouseEvent event) {
    	loadDeliveries();
    }
    
    /**
     * Handles the event where the user clicks the "Apply" button by filtering the orders based on the selected status.
     *
     * @param event the mouse event that triggered this method call
     */
    @FXML
    void Apply(MouseEvent event) {//send the data from the Table-View to DB  
    	tempDeliveries.clear();
    	for (Object torder : observableDeliveries){
    		((TableOrder)torder).setOrderStatus(((TableOrder)torder).getStatus_co().getValue());
    		
    		//GETTING THE DATE to set it in order.
    		// Get the current time
    	    Date currentDate = new Date();
    	    // Create a SimpleDateFormat object to format the date as a string
    	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
    	    // Format the current date as a string
    	    String dateString = formatter.format(currentDate);
    	    
    	    //CREATING THE Estimated DATE to set it in order.
    	    // Create a Calendar object
    	    Calendar calendar = Calendar.getInstance();
    	    // Set the Calendar object to the current date
    	    calendar.setTime(currentDate);
    	    // Add 7 days to the Calendar object
    	    calendar.add(Calendar.DAY_OF_YEAR, 7);
    	    // Format the new date as a string
    	    String estimateddateString = formatter.format(calendar.getTime());
    	    
    	    //if delivery approved
    		if (((TableOrder)torder).getStatus_co().getValue().equals("approved")){
    			((TableOrder)torder).setConfirmationDate(dateString.toString());
    			((TableOrder)torder).setEstimatedDeliveryTime(estimateddateString.toString());
    		}
    		//if delivery NOT approved
    		if (((TableOrder)torder).getStatus_co().getValue().equals("not approved")){
    			((TableOrder)torder).setConfirmationDate(dateString.toString());
    			((TableOrder)torder).setEstimatedDeliveryTime(null);
    		}
    		//if delivery awaiting approval
    		if (((TableOrder)torder).getStatus_co().getValue().equals("awaiting approval")){
    			((TableOrder)torder).setConfirmationDate(null);
    			((TableOrder)torder).setEstimatedDeliveryTime(null);
    		}
    		Order ordertoupdate = new Order(((TableOrder)torder).getOrderID(), 
    				((TableOrder)torder).getOverallPrice(), 
    				((TableOrder)torder).getProducts(), 
    				((TableOrder)torder).getMachineID(), 
    				((TableOrder)torder).getOrderDate(), 
    				((TableOrder)torder).getEstimatedDeliveryTime(), 
    				((TableOrder)torder).getConfirmationDate(), 
    				((TableOrder)torder).getOrderStatus(), 
    				((TableOrder)torder).getCustomerID(), 
    				((TableOrder)torder).getSupplyMethod(), 
    				((TableOrder)torder).getPaidWith(), 
    				((TableOrder)torder).getAddress());
    		tempDeliveries.add(ordertoupdate);
    	}
        ClientUI.chat.accept(new Message(tempDeliveries,MessageFromClient.REQUEST_UPDATE_MULTIPLE_ORDER_STATUSES));//send new DB
        super.alertHandler(MessageHandler.getMessage(), MessageHandler.getMessage().contains("Error"));
        Refresh(event);
    }
}