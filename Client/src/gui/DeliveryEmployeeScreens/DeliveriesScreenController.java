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
import common.Deals;
import common.Reports.InventoryReport;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.User;
import common.orders.Order;
import common.orders.Product;
import gui.ScreenController;
import gui.UserManagementScreens.UserRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<Order, ArrayList<Product>> productsColumn;
    
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
   
    
    private ArrayList<Order> tempDeliveries;
    public static ObservableList<Object> observableDeliveries;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tempDeliveries = new ArrayList<>();
		//init columns
		orderidColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
		overallpriceColumn.setCellValueFactory(new PropertyValueFactory<>("overallPrice"));
		productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
		orderdateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		estimatedateColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedDeliveryTime"));
		confirmationdateColumn.setCellValueFactory(new PropertyValueFactory<>("confirmationDate"));
		orderstatusColumn.setCellValueFactory(new PropertyValueFactory<>("status_co"));
		customeridColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		//load deals
		loadDeliveries();
	}
	
    @FXML
    void exit(MouseEvent event) {
    	super.closeProgram(event, true);
    }

  
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
    
    
    public void loadDeliveries() {
	    observableDeliveries = FXCollections.observableArrayList();
    	if (!observableDeliveries.isEmpty())
    		observableDeliveries.clear();
    	String areaaofuser = UserController.getCurrentuser().getDepartment().split("_")[1];
    	ClientUI.chat.accept(new Message(areaaofuser,MessageFromClient.REQUEST_ORDERS_BY_AREA)); 
    	tempDeliveries = (ArrayList<Order>) MessageHandler.getData();//getting data from server
	    // Add orders from tempDeliveries to observableDeliveries
	    for (Order order : tempDeliveries) {
	    	if (order.getSupplyMethod().equals("delivery")) {
		    	TableOrder torder = new TableOrder(order);
		    	ChoiceBox<String> status = new ChoiceBox<>(FXCollections.observableArrayList("approved","not approved", "awaiting approval"));
	    		status.setValue(order.getOrderStatus());
	    		torder.setStatus_co(status);
		    	observableDeliveries.add(torder);
	    	}
	    }
	    // Set the items of the viewAllOrders table to be the observableDeliveries list
	    viewAllOrders.setItems(observableDeliveries);
    }
   
    @FXML
    void Refresh(MouseEvent event) {
    	loadDeliveries();
    }
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
