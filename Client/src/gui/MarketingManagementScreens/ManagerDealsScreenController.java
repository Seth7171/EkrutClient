package gui.MarketingManagementScreens;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ClientUI;
import application.client.MessageHandler;
import common.Deals;
import common.Reports.InventoryReport;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Product;
import gui.ScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ManagerDealsScreenController extends ScreenController implements Initializable{

	 @FXML
	    private Button backButton;

	    @FXML
	    private TableColumn<Deals, String> dealNameColumn;
	    
	    @FXML
	    private TableColumn<Deals, String> areaColumn;

	    @FXML
	    private TableColumn<Deals, String> descriptionColumn;

	    @FXML
	    private TableColumn<Deals, Float> discountColumn;

	    @FXML
	    private Button exitButton;
	    
	    @FXML
	    private TableColumn<Deals, String> typeColumn;
	    

	    @FXML
	    private TableColumn<Deals, String> statusColumn;

	    @FXML
	    private TableView<Deals> viewAllDeals;
	    
	    @FXML
	    private Button apply;
	    
	    private ArrayList<Deals> tempProd = new ArrayList<>();
	    
    
    @FXML
    void exit(MouseEvent event) {
    	super.closeProgram(event, true);
    }

  
    @FXML
    void goBackToMarketingManagerScreen(MouseEvent event) {
    	Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MarketingManagerScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
    
    
    public void loadProducts() {
    	viewAllDeals.getItems().clear();
		// here we insert the data for the table.
    for(Deals d : tempProd){	
    	Deals dealsData = new Deals();
    	dealsData.setDealName(d.getDealName());
    	dealsData.setDiscount(d.getDiscount());
    	dealsData.setDescription(d.getDescription());
    	System.out.println("d.getTypeStr(): " + d.getTypeStr()); 
    	dealsData.setType(d.getTypeStr());
    	dealsData.setArea(d.getAreaS());
    	dealsData.setStatusString(d.getStatusString());
    	
    	viewAllDeals.getItems().add(dealsData);
	}
}
    
//   ObservableList<Deals> observablesubs= FXCollections.observableArrayList(
//   
//			 new Deals("Night time sale",10,"Special offer for late night students from 20pm to 5am ",FXCollections.observableArrayList("Drinks","ALL","Snacks"),FXCollections.observableArrayList("North","South","UAE","ALL"),FXCollections.observableArrayList("Approved","Not Aproved")),
//	    	 new Deals("Holiday sale",25,"Going on a holiday trip ?, get a 25% discount on all products",FXCollections.observableArrayList("Drinks","ALL","Snacks"),FXCollections.observableArrayList("North","South","UAE","ALL"),FXCollections.observableArrayList("Approved","Not Aproved")),
//	    	 new Deals("Summer sale",15,"Summer has already arrived - buy something cold to drink",FXCollections.observableArrayList("Drinks","ALL","Snacks"),FXCollections.observableArrayList("North","South","UAE","ALL"),FXCollections.observableArrayList("Approved","Not Aproved")),
//	    	 new Deals("World Cup sale",10,"watch France vs Argentina finals and get 10% off on SNACKS",FXCollections.observableArrayList("Drinks","ALL","Snacks"),FXCollections.observableArrayList("North","South","UAE","ALL"),FXCollections.observableArrayList("Approved","Not Aproved")),
//	    	 new Deals("Subscribe sale ",20,"Congratulation for register as Subscriber get 20% off on the first order ",FXCollections.observableArrayList("Drinks","ALL","Snacks"),FXCollections.observableArrayList("North","South","UAE","ALL"),FXCollections.observableArrayList("Approved","Not Aproved")));
//	    	
    
    ;
    @FXML
    void Apply(MouseEvent event) {//send the data from the Table-View to DB 
    	//TODO:///
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		tempProd = (ArrayList<Deals>) MessageHandler.getData();//getting data from server
		
		
		ObservableList<Deals> observablesubs= FXCollections.observableArrayList();
		
				
	
		
		
		dealNameColumn.setCellValueFactory(new PropertyValueFactory<Deals,String>("DealName"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<Deals,Float>("Discount"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Deals,String>("Description"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Deals,String>("Type"));
		areaColumn.setCellValueFactory(new PropertyValueFactory<Deals,String>("Area"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Deals,String>("Status"));
		
		viewAllDeals.setItems(observablesubs);
		
		loadProducts();
	



	}
}
