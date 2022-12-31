package gui.OrderScreens;

import java.awt.TextField;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class DeliveryOprtionsScreenController extends ScreenController implements Initializable{

    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Button proceedToShop;
    
    @FXML
    private TextField StreetAd;
    
    @FXML
    private TextField City;
    
    @FXML
    private TextField State;
    
    @FXML
    private TextField Zip;
    
    @FXML
    private ComboBox<String> location;
    
    @FXML
    private ComboBox<String> machineID;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showMachineLocationAndMachineID();

		
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
                    root = FXMLLoader.load(getClass().getResource("CustomerMainScreen.fxml"));
                    super.switchScreenWithTimerCustomersOnly(event, root);
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
		
		
		// Dynamic Pick Up
		ChatClient.currentOrder.setMachineID(null);
		ChatClient.currentOrder.setAddress(null);
		ChatClient.currentOrder.setSupplyMethod("machine pickup");
		
		// Delivery
		StringBuilder sb = new StringBuilder();
		   // FILL THE
		   // ADRESS IN THE SB
		ChatClient.currentOrder.setAddress(sb.toString());
		ChatClient.currentOrder.setSupplyMethod("delivery");
		
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ProductCatalogScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root); 
    }
    
}
