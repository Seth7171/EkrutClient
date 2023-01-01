package gui.MarketingManagementScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import common.Deals;
import common.orders.Product;
import gui.ScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class EmployeeDealsScreenController extends ScreenController implements Initializable {

    @FXML
    private TableColumn<Deals, String> typeColumn;

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
    private TableColumn<Deals, String> statusColumn;

    @FXML
    private TableView<String> viewAllDeals;

    
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//get the data from DB !!!
	    ObservableList<Deals> observablesubs= FXCollections.observableArrayList(
				 //get the DATA from the db
				 new Deals("Night time sale",10,"Special offer for late night students from 20pm to 5am ","Snacks",FXCollections.observableArrayList("Approved","Not Aproved")),
		    	 new Deals("Holiday sale",25,"Going on a holiday trip ?, get a 25% discount on all products","ALL",FXCollections.observableArrayList("Approved","Not Aproved")),
		    	 new Deals("Summer sale",15,"Summer has already arrived - buy something cold to drink","Drinks",FXCollections.observableArrayList("Approved","Not Aproved")),
		    	 new Deals("World Cup sale",10,"watch France vs Argentina finals and get 10% off on SNACKS","Snacks",FXCollections.observableArrayList("Approved","Not Aproved")),
		    	 new Deals("Subscribe sale ",20,"Congratulation for register as Subscriber get 20% off on the first order ","ALL",FXCollections.observableArrayList("Approved","Not Aproved")));
		    	
		
		dealNameColumn.setCellValueFactory(new PropertyValueFactory<Deals,String>("DealName"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<Deals,Float>("Discount"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Deals,String>("Description"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Deals,String>("Type"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Deals,String>("Status"));
		
		
	//	viewAllDeals.setItems(observablesubs);
	
	
		
	}
	

}
