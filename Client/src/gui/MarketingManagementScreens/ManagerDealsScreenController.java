package gui.MarketingManagementScreens;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class ManagerDealsScreenController extends ScreenController implements Initializable{

	 @FXML
	    private Button backButton;

	    @FXML
	    private TableColumn<String, String> dealNameColumn;
	    
	    @FXML
	    private TableColumn<String, String> areaColumn;

	    @FXML
	    private TableColumn<String, String> descriptionColumn;

	    @FXML
	    private TableColumn<String, String> discountColumn;

	    @FXML
	    private Button exitButton;
	    
	    @FXML
	    private TableColumn<String, String> typeColumn;
	    

	    @FXML
	    private TableColumn<String, String> statusColumn;

	    @FXML
	    private TableView<String> viewAllDeals;

//	    ObservableList<String> list= FXCollections.observableArrayList(
//	    		new String("Night time sale","10%","Special offer for late night students from 20pm to 5am ","ALL","Activated"),
//	    		new String("Holiday sale","25%","Going on a holiday trip ?, get a 25% discount on all products","ALL","NOT Activated"),
//	    		new String("Summer sale","15%","Summer has already arrived - buy something cold to drink","DRINKS"," NOT Activated"),
//	    		new String("World Cup sale","10%","watch France vs Argentina finals and get 10% off on SNACKS", "SNACKS" , "NOT Activated"),
//	    		new String("Subscribe sale ","20%","Congratulation for register as Subscriber get 20% off on the first order ","ALL","Activated")
//	    		);
	    
//  private ObservableList<String> observablesubs = FXCollections.observableArrayList();
    
    //TODO: in t 
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
    
    public void loadProducts() {//add deals manually!! -->> Deal Name  | Discount | Description | Type |Area | Status
    	viewAllDeals.getItems().clear();
   
    	//examples: 
    	//1) -->  "Night time sale","10%","Special offer for late night students from 20pm to 5am ","ALL", "ALL", "Activated"
    	//2) -->  "Holiday sale","25%","Going on a holiday trip ?, get a 25% discount on all products","ALL","North","NOT Activated"
    	//3) -->  "Summer sale","15%","Summer has already arrived - buy something cold to drink","DRINKS","South"," NOT Activated"
    	//4) -->  "World Cup sale","10%","watch France vs Argentina finals and get 10% off on SNACKS", "SNACKS" ,"UAE", "NOT Activated" 
    	//5) -->  "Subscribe sale ","20%","Congratulation for register as Subscriber get 20% off on the first order ","ALL","ALL","Activated"
          
          
		
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		dealNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("Deal name"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<String,String>("Discount"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<String,String>("Description"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<String,String>("Type"));
		areaColumn.setCellValueFactory(new PropertyValueFactory<String,String>("Area"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<String,String>("Status"));
	}

}
