package gui.MarketingManagementScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.MessageHandler;
import application.user.UserController;
import common.Deals;
import common.orders.Product;
import gui.ScreenController;
import gui.UserScreens.LogInScreenController;
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

public class EmployeeDealsScreenController extends ScreenController implements Initializable {

    @FXML
    private TableColumn<Deals, String> typeColumn;

    @FXML
    private Button apply;
    @FXML
    
    private Button refresh;
    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Deals, String> dealNameColumn;

    @FXML
    private TableColumn<Deals, String> descriptionColumn;

    @FXML
    private TableColumn<Deals, Float> discountColumn;
    
    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Deals, ChoiceBox> statusColumn;

    @FXML
    private TableView<Deals> viewAllDeals;
    
    
    private ArrayList<Deals> tempDeal;
    
    public static  ObservableList<Deals> observablesubs;
    
    @FXML
    void exit(MouseEvent event) {
    	super.closeProgram(event, true);
    }

    @FXML
    void goBackToMarketingEmployeeScreen(MouseEvent event) {
    	Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MarketingEmployeeScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    public void loadDeals() {//load data into table
    	observablesubs=FXCollections.observableArrayList();
    	if (!observablesubs.isEmpty())
    		observablesubs.clear();
    	String user=UserController.getCurrentuser().getFirstname();
    	tempDeal = (ArrayList<Deals>) MessageHandler.getData();//getting data from server
		// insert the data for the table.
    for(Deals d : tempDeal){	
    	Deals dealsData = new Deals();
    		ChoiceBox<String> area = new ChoiceBox<>(FXCollections.observableArrayList());
    		area.setValue(d.getAreaS());//get the area 
    		//show data on the specific area
    		if(area.getValue().contains(user) || area.getValue().contains("all")) { 
    			dealsData.setDealName(d.getDealName());
    			dealsData.setDiscount((int)(d.getDiscount()*100));
    			dealsData.setDescription(d.getDescription());
    			ChoiceBox<String> type = new ChoiceBox<>(FXCollections.observableArrayList());//remove the choiceBox
    			type.setValue(d.getTypeStr());
    			dealsData.setType(type);
	    		ChoiceBox<String> status = new ChoiceBox<>(FXCollections.observableArrayList( "approved","not approved"));
	    		status.setMinWidth(95);
	    		status.setValue(d.getStatusString());
	    		dealsData.setStatus(status);

	    		observablesubs.add(dealsData);
    	}
    }
    viewAllDeals.setItems(observablesubs);
}
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//get the data from DB !!!
		tempDeal = new ArrayList<>();	
		//init columns
		dealNameColumn.setCellValueFactory(new PropertyValueFactory<>("DealName"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<>("Discount"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
		//load deals
	    loadDeals();
			
	}
	
	  @FXML
	    void Apply(MouseEvent event) {//send the new data to DB

	    }

	    @FXML
	    void Refresh(MouseEvent event) {
	    	loadDeals();
	    }
	

}
