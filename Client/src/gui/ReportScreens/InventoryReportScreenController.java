package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.Reports.InventoryReport;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.MessageFromServer;
import common.orders.Product;
import gui.ScreenController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InventoryReportScreenController extends ScreenController implements Initializable{
	
    @FXML
    private Button backButton;

    @FXML
    private Button exitApp;
    
    @FXML
    private Label MachineIDLabel;
    
    @FXML
    private Label DateChooseLabel;
    
    @FXML
    private Label locationMachineLabel;
    
    @FXML
    private Label totalWorthLabel;
    
    @FXML
    private Label notInStockLabel;
    
    @FXML
    private Label inStockItemsLabel;
    
    // all stuff needed for Inventory report screen.
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	float totalWorthItems = 0;
        int notInStockItems = 0;
        int inStockItems = 0;
    	MachineIDLabel.setText("Machine ID: " + ChatClient.returnMachineID);//get the Machine Id that has been choose
    	DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);//get the Date that has been choose
    	String locationMachine = ChatClient.returnLocationChoose;
    	locationMachine = locationMachine.substring(0,1).toUpperCase() + locationMachine.substring(1).toLowerCase();// make the location with capital letter
    	locationMachineLabel.setText("Location: " + locationMachine);//get the Location  that has been choose
    	
    	//get data
    	InventoryReport currentReportData = null;
    	ArrayList<String> productsArr= new ArrayList<>();
    	productsArr.add(ChatClient.returnMonthChoose);//adding Month  that has been choose to arraylist
    	productsArr.add(ChatClient.returnYearChoose);//adding Year  that has been choose to arraylist
    	productsArr.add(ChatClient.returnMachineID);//adding Machine id  that has been choose to arraylist
    	
    	ClientUI.chat.accept(new Message(productsArr, MessageFromClient.REQUEST_MACHINE_MONTHLY_INVENTORY_REPORT));
    	System.out.println("Products in machine: " +  currentReportData.getProducts() );
//   	 		for (Product product : currentReportData.getProducts()) {//{System.out.println("Products: " + products.getName());}
//   	 			totalWorthItems += product.getPrice();
//   	 			if(product.getAmount() == 0)
//   	 				notInStockItems++;
//   	 			else
//   	 				inStockItems++;
//   	 			product.getProductId();
//   	 			product.getName();
//   	 			product.getAmount();
//   	 			product.getPrice();
//   	 		}
//   	 	
   	 	totalWorthLabel.setText("Total worth stock: " +  totalWorthItems + "¤");
   	 	notInStockLabel.setText("Total items not in stock: " + notInStockItems);
   	 	inStockItemsLabel.setText("Total items in stock: " + inStockItems);
   	 	
	}
    
    
    // Go back to main reports screen.
    @FXML
    void clickBackButton(MouseEvent event) {
    	Parent root = null;
    	try {
		root = FXMLLoader.load(getClass().getResource("ReportsMainScreen.fxml"));
		}
    	catch (IOException exception){exception.printStackTrace();}
    	super.switchScreen(event, root);
	}
    
    // exit from application
    @FXML
    void exitApplication(MouseEvent event) {
    	super.closeProgram(event, true);	
    }

}