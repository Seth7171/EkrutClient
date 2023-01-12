package gui.OrderScreens;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.CustomerController;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DeliveryOprtionsScreenController extends ScreenController implements Initializable{

	@FXML
    private Label dynamicError;
	
	@FXML
    private Label deliveryError;
	
	@FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Button proceedToShop;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private Pane DynamicPane;
    
    @FXML
    private Pane DeliveryPane;
    
    @FXML
    private TextField StreetAd;
    
    @FXML
    private TextField City;
    
    @FXML
    private ComboBox<String> State;
    
    @FXML
    private TextField Zip;
    
    @FXML
    private ComboBox<String> location;
    
    @FXML
    private ComboBox<String> machineID;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showMachineLocationAndMachineID();
		State.getItems().addAll("ISR/north","ISR/south", "UAE/uae");
	}

	private void showMachineLocationAndMachineID(){
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_ALL_MACHINE_LOCATIONS));
        ArrayList<String> Locations = new ArrayList<>();
        Locations.addAll((ArrayList<String>) MessageHandler.getData());
        location.getItems().addAll(Locations);//add all Locations to Location comboBox
        location.setOnAction(event -> {
            ClientUI.chat.accept(new Message(location.getValue(), MessageFromClient.REQUEST_MACHINE_IDS));
            ArrayList<String> machineIDs = new ArrayList<>();
            machineIDs.addAll((ArrayList<String>) MessageHandler.getData());
            machineID.getItems().clear();
            machineID.getItems().addAll(machineIDs);
            machineID.setDisable(false);
            dynamicError.setVisible(false);
        });
        machineID.setOnAction(event -> {
        	dynamicError.setVisible(false);
        });
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
                case "subscriber":
                case "customer":
                    CustomerController.setCurrentCustomer(UserController.getCurrentuser());
                    root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/CustomerMainScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                case "customer_service":
                    root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceEmployeeScreens/CustomerServiceEmployeeScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                case"ceo":
                    root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                case "operations":
                    root = FXMLLoader.load(getClass().getResource("/gui/OperationsEmployeeScreens/operationsEmployeeMainScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                default:
                    System.out.println("Unknown!");
                    // TODO: maybe add UnknownScreenException later??
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	@FXML
    void proceedToShop(MouseEvent event) {
		Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
		Node content = selectedTab.getContent();
        if (content instanceof Pane) {
        	Pane pane = (Pane) content;
        	String id = pane.getId();
        	if (id.equals("DynamicPane")) { // Dynamic Pick Up
        		ComboBox<String> comboBox = (ComboBox<String>) pane.lookup("#machineID");
        		Object selectedItem = comboBox.getValue();
        		String machineID = (String) selectedItem;
        		if (selectedItem==null || machineID==null) {
        			alertHandler("Please Select A Location And Than A Machine", true);
        			//dynamicError.setVisible(true);
        			return;
        		}
        		System.out.println(machineID);
        		ChatClient.currentOrder.setMachineID(machineID);
        		CustomerController.setmachineID(machineID);
        		ChatClient.currentOrder.setAddress(null); // in dynamic pickup Address is always null
        		ChatClient.currentOrder.setSupplyMethod("machine pickup");
        		ChatClient.currentOrder.setOrderStatus("awaiting pickup");
        	}
        	else { // Delivery
            	TextField TextField = (TextField) pane.lookup("#StreetAd");
            	String Address = TextField.getText();
            	TextField = (TextField) pane.lookup("#City");
            	String City = TextField.getText();
            	TextField = (TextField) pane.lookup("#Zip");
            	String Zip = TextField.getText();
            	if (Address.trim().isEmpty() || City.trim().isEmpty() || State.getSelectionModel().isEmpty()|| Zip.trim().isEmpty()) {
            		alertHandler("Please Fill All The Fields Below", true);
            		//deliveryError.setVisible(true);
        			return;
            	}
        		StringBuilder sb = new StringBuilder();
     		    sb.append(Address);
     		    sb.append(" ");
     		    sb.append(City);
     		    sb.append(" ");
     		    sb.append(State.getValue());
     		    sb.append(" ");
     		    sb.append(Zip);
     		   String state = State.getValue().split("/")[1];
     		    System.out.println(sb.toString());
     			ChatClient.currentOrder.setAddress(sb.toString());
     			ChatClient.currentOrder.setSupplyMethod("delivery");
     	        ChatClient.currentOrder.setMachineID(null);
     	       ChatClient.currentOrder.setArea(state);
     	        CustomerController.setmachineID(null);
        	}
        }
		
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ProductCatalogScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //super.switchScreen(event, root); 
        switchScreenWithTimer(event, root);
    }
    
}
