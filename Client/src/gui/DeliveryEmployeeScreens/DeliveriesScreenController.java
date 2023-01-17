package gui.DeliveryEmployeeScreens;
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
import gui.ScreenController;
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
 * The DeliveriesScreenController class is a JavaFX controller class that is 
 * responsible for handling the functionality and events of the deliveries screen.
 * It implements the Initializable interface and initializes the screen by setting up 
 * the table columns and populating the table with data.
 * @author Nitsan & Ron
 * @version 1.0
 */
public class DeliveriesScreenController extends ScreenController implements Initializable{
 	@FXML
    private Button backButton;
 
    @FXML
    private Button exitButton;
    
    @FXML
    private Button refresh;
    
    @FXML
    private Button apply;

    @FXML
    private TableColumn<Order, String> orderidColumn;
    
    @FXML
    private TableColumn<Order, Float> overallpriceColumn;

    @FXML
    private TableColumn<String, String> productsColumn;
    
    @FXML
    private TableColumn<Order, String> orderdateColumn;

    @FXML
    private TableColumn<Order, String> addressColumn;
    
    @FXML
    private TableColumn<Order, String> estimatedateColumn;
    
    @FXML
    private TableColumn<Order, String> confirmationdateColumn;
    
    @FXML
    private TableColumn<Order, String> orderstatusColumn;
    
    @FXML
    private TableColumn<Order, String> customeridColumn;

    @FXML
    private TableView<Object> viewAllOrders;
   
    /**
     * An ArrayList of Order objects that is used to temporarily store the data that is to be displayed in the table
     */
    private ArrayList<Order> tempDeliveries;
    /**
     * An ObservableList of objects that is used to display the data in the table
     */
    public static ObservableList<Object> observableDeliveries;
    
    /**
    * The initialize method is called by JavaFX when the screen is loaded. 
    * It sets up the table columns and populates the table with data.
    * @param arg0
    * @param arg1
    */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//initialize tempDeliveries ArrayList
		tempDeliveries = new ArrayList<>();
		//initialize columns
		orderidColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
		overallpriceColumn.setCellValueFactory(new PropertyValueFactory<>("overallPrice"));
		productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
		orderdateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		estimatedateColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedDeliveryTime"));
		confirmationdateColumn.setCellValueFactory(new PropertyValueFactory<>("confirmationDate"));
		orderstatusColumn.setCellValueFactory(new PropertyValueFactory<>("status_co"));
		customeridColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		//populate table
		loadDeliveries();
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
     * Redirects the user to the previous screen, in this case the DeliveryEmployeeScreen.fxml 
     * 
     * @param event the MouseEvent that triggers the goBack method
     */
    @FXML
    void goBack(MouseEvent event) {
    	Parent root = null;
        try {
          	  root = FXMLLoader.load(getClass().getResource("DeliveryEmployeeScreen.fxml"));
      }catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
    
    /**
     * Loads a list of deliveries for a user by querying the server for a list of orders by area.
     * Only orders with a supply method of "delivery" are added to the observableArrayList.
     * A ChoiceBox is created for each order with the options "approved", "not approved", or "awaiting approval"
     * and the value is set to the current status of the order.
     */
    public void loadDeliveries() {
        // Initialize observableDeliveries list
        observableDeliveries = FXCollections.observableArrayList();
        // Clear any existing deliveries in the list
        if (!observableDeliveries.isEmpty())
        	observableDeliveries.clear();
        // Get the area of the current user
        String areaaofuser = UserController.getCurrentuser().getDepartment().split("_")[1];
        // Send a request for orders by area to the server
        ClientUI.chat.accept(new Message(areaaofuser,MessageFromClient.REQUEST_ORDERS_BY_AREA)); 
        // Get the data from the server
        Object data = MessageHandler.getData();
        if (!(data instanceof ArrayList<?>)) {
        	Platform.runLater(new Runnable() {
        	    @Override
        	    public void run() {
                	alertHandler("You Dont Have Orders To Deliver!", true);
        	    }
        	});
            return;
        }
        tempDeliveries = (ArrayList<Order>) data;
        // Iterate through the temporary list of deliveries
        for (Order order : tempDeliveries) {
        	if (order.getSupplyMethod().equals("delivery")) {
    	    	TableOrder torder = new TableOrder(order);
    	    	ArrayList<String> stats = new ArrayList<String>();
    	    	if (order.getOrderStatus().equals("awaiting approval")) {
    	    		stats.add("approved");
    	    		stats.add("not approved");
    	    		stats.add("awaiting approval");
    	    		ChoiceBox<String> status = new ChoiceBox<>();
    	    		status.getItems().addAll(stats);
        	    	// Set the value of the ChoiceBox to the current status of the order
        	    	status.setValue(order.getOrderStatus());
        	    	torder.setStatus_co(status);
    	    	}
    	    	else if (order.getOrderStatus().equals("collected")) {
    	    		stats.add("delivered");
    	    		stats.add("collected");
    	    		ChoiceBox<String> status = new ChoiceBox<>();
    	    		status.getItems().addAll(stats);
    	    		status.setValue(order.getOrderStatus());
    	    		torder.setStatus_co(status);
    	    		// If the order's status is not "awaiting approval", disable the ChoiceBox
    	    	}
    	    	else {
    	    		ChoiceBox<String> status = new ChoiceBox<>();
	    	    	stats.add(order.getOrderStatus());
	    	    	status.getItems().addAll(stats);
    	    		status.setValue(order.getOrderStatus());
    	    		torder.setStatus_co(status);
    	    		// If the order's status is not "awaiting approval", disable the ChoiceBox
    	    		status.setDisable(true);
    	    		status.setPrefWidth(150);
    	    	}
    	    	// Add the TableOrder to the observableDeliveries list
    	    	observableDeliveries.add(torder);
        	}
	    }
        // Set the items of the viewAllOrders table to be the observableDeliveries list
        viewAllOrders.setItems(observableDeliveries);
    }
   
    /**
     * Refreshes the deliveries list by calling the loadDeliveries method.
     * 
     * @param event the MouseEvent that triggers the Refresh method
     */
    @FXML
    void Refresh(MouseEvent event) {
    	loadDeliveries();
    }
    
    /**
     * Applies the changes made to the deliveries list and sends the updated information to the server.
     * Also sends SMS/MAIL notification to the customer if the delivery is approved.
     * 
     * @param event the MouseEvent that triggers the Apply method
     */
    @FXML
    void Apply(MouseEvent event) {
    	tempDeliveries.clear();
    	 // Iterate through the deliveries list
    	for (Object torder : observableDeliveries){
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
	    	    if (!((TableOrder)torder).getOrderStatus().equals("approved")) {
	    	    	alertSMSMAIL(event, (String.format("SMS/MAIL was sent to customer number %s: "
	    					+ "\nDear customer,"
	    					+ "\nOrder number: %s is on its way"
	    					+ "\nEstimate delivery time is: %s", 
	    					((TableOrder)torder).getCustomerID(), ((TableOrder)torder).getOrderID(), estimateddateString.toString())));
	    	    }
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
    		//create an Order from TableOrder
    		((TableOrder)torder).setOrderStatus(((TableOrder)torder).getStatus_co().getValue());
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
    		//add the Order to the tempDeliveries to be send to server
    		tempDeliveries.add(ordertoupdate);
    	}
    	//send the deliveries from the Table-View to server  
        ClientUI.chat.accept(new Message(tempDeliveries,MessageFromClient.REQUEST_UPDATE_MULTIPLE_ORDER_STATUSES));
        super.alertHandler(MessageHandler.getMessage(), MessageHandler.getMessage().contains("Error"));
        Refresh(event);
    }
}
