package gui.DeliveryEmployeeScreens;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private TableColumn<Order, String> priceColumn;

    @FXML
    private TableColumn<Order, Float> productsColumn;
    
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
    private TableView<Order> viewAllOrders;
   
    
    private ArrayList<Order> tempDeliveries;
    public static  ObservableList<Order> observableDeliveries;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tempDeliveries = new ArrayList<>();
		//init columns
		orderidColumn.setCellValueFactory(new PropertyValueFactory<>("Order ID"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
		productsColumn.setCellValueFactory(new PropertyValueFactory<>("Products"));
		orderdateColumn.setCellValueFactory(new PropertyValueFactory<>("Order Date"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
		estimatedateColumn.setCellValueFactory(new PropertyValueFactory<>("Estimated Delivery Date"));
		confirmationdateColumn.setCellValueFactory(new PropertyValueFactory<>("Confirmation Date"));
		orderstatusColumn.setCellValueFactory(new PropertyValueFactory<>("Order Status"));
		customeridColumn.setCellValueFactory(new PropertyValueFactory<>("Customer ID"));
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
        	switch (UserController.getCurrentuser().getDepartment()) {
            case "marketing_manager":
          	  root = FXMLLoader.load(getClass().getResource("MarketingManagerScreen.fxml"));
          	  break;
           case "ceo":
          	 root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
          	 break;
          	 
           default:
               System.out.println("Unknown!");
               // TODO: maybe add UnknownScreenException later??
        	} 	
      }catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
    
    
    public void loadDeliveries() {
    	observableDeliveries=FXCollections.observableArrayList();
    	if (!observableDeliveries.isEmpty())
    		observableDeliveries.clear();
    	ClientUI.chat.accept(new Message(null,MessageFromClient.REQUEST_DISCOUNT_LIST )); 
    	tempDeliveries = (ArrayList<Order>) MessageHandler.getData();//getting data from server
		// here we insert the data for the table.
    for(Order o : tempDeliveries){	
    	Order orderData = new Order();
    	orderData.setOrderID(o.getOrderID());
    	orderData.setOverallPrice((o.getOverallPrice()));
    	orderData.setDescription(d.getDescription());
    	orderData.setType(d.getType());
    	ChoiceBox<String> area = new ChoiceBox<>(FXCollections.observableArrayList("ALL", "NORTH","SOUTH","UAE"));
    	area.setMinWidth(60);
    	area.setValue(d.getAreaS().toUpperCase());
    	orderData.setArea(area);
    	ChoiceBox<String> status = new ChoiceBox<>(FXCollections.observableArrayList( "approved","not approved"));
    	status.setMinWidth(95);
    	status.setValue(d.getStatusString());
    	
    	orderData.setStatus(status);
    	orderData.setDealID(d.getDealID());
    	orderData.setActive(d.getActive());
    	observableDeliveries.add(orderData);
	}
    viewAllDeals.setItems(observableDeliveries);
}
   
    @FXML
    void Refresh(MouseEvent event) {
    	loadDeliveries();
    }
    @FXML
    void Apply(MouseEvent event) {//send the data from the Table-View to DB  
   
         for (Order deal : observableDeliveries){
        	 Order dealToList = new Order();
             dealToList.setDealName(deal.getDealName());
             dealToList.setDiscount((float)deal.getDiscount()/100);
             dealToList.setDescription(deal.getDescription());
             dealToList.setType(deal.getType());
             dealToList.setArea(deal.getArea().getValue().toString());
             dealToList.setStatusString(deal.getStatus().getValue().toString());
             if(deal.getStatus().getValue().toString().equals("not approved"))//if not approved -change the active status to "not active"
                  dealToList.setActive("not active");
             else 
            	 dealToList.setActive(deal.getActive());
             dealToList.setDealID(deal.getDealID());
             
          
            ClientUI.chat.accept(new Message(dealToList,MessageFromClient.REQUEST_UPDATE_DEALS ));//send new DB
            super.alertHandler(MessageHandler.getMessage(), MessageHandler.getMessage().contains("Error"));
         }
       
    }
}
