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
import common.connectivity.User;
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
import javafx.scene.control.ChoiceBox;
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
	    private Button refresh;
	    @FXML
	    private Button apply;
	    
	    private ArrayList<Deals> tempDeal;
	    public static  ObservableList<Deals> observablesubs;
    
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
    
    
    public void loadDeals() {
    	observablesubs=FXCollections.observableArrayList();
    	if (!observablesubs.isEmpty())
    		observablesubs.clear();
    	
    	tempDeal = (ArrayList<Deals>) MessageHandler.getData();//getting data from server
		// here we insert the data for the table.
    for(Deals d : tempDeal){	
    	Deals dealsData = new Deals();
    	dealsData.setDealName(d.getDealName());
    	dealsData.setDiscount(d.getDiscount());
    	dealsData.setDescription(d.getDescription());
    	ChoiceBox<String> type = new ChoiceBox<>(FXCollections.observableArrayList("ALL", "DRINK","SNACK"));
    	type.setMinWidth(70);
    	type.setValue(d.getTypeStr());
    	dealsData.setType(type);
    	ChoiceBox<String> area = new ChoiceBox<>(FXCollections.observableArrayList("ALL", "NORTH","SOUTH","UAE"));
    	area.setMinWidth(60);
    	area.setValue(d.getAreaS().toUpperCase());
    	dealsData.setArea(area);
    	ChoiceBox<String> status = new ChoiceBox<>(FXCollections.observableArrayList( "approved","not approved"));
    	status.setMinWidth(95);
    	status.setValue(d.getStatusString());
    	dealsData.setStatus(status);

    	observablesubs.add(dealsData);
	}
    viewAllDeals.setItems(observablesubs);
}
   
    @FXML
    void Refresh(MouseEvent event) {
    	loadDeals();
    }
    @FXML
    void Apply(MouseEvent event) {//send the data from the Table-View to DB  
    	//userManagementScreenController
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tempDeal = new ArrayList<>();
		//init columns
		dealNameColumn.setCellValueFactory(new PropertyValueFactory<>("DealName"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<>("Discount"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
		areaColumn.setCellValueFactory(new PropertyValueFactory<>("Area"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
		//load deals
		loadDeals();
		
		

	}
}
