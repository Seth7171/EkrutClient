package gui.ReportScreens;
import java.time.Year;
import javafx.event.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.User;
import gui.ScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class CEOReportsMainScreenController extends ScreenController implements Initializable{
	int MachineIDFlag=0; // MachineID ComboBox:  1=open|0-close
	@FXML
    private ComboBox<String> MachineID;

    @FXML
    private ComboBox<String> Month;

    @FXML
    private ComboBox<String> Type;

    @FXML
    private ComboBox<String> YearComboBox;

    @FXML
    private Button backButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button showReportButton;

    @FXML
    private Label welcomeReportsLabel;
    
    @FXML
    private Label errorLabel;
    
    private double xOffset;
    private double yOffset;
    
    /**
	 * exit application
	 * @param event
	 * @throws Exception
	 */
    @FXML
    void exitApp(ActionEvent event) throws Exception {
    	 ArrayList<String> cred = new ArrayList<String>();
         cred.add(UserController.getCurrentuser().getUsername());
         ClientUI.chat.accept("disconnect");
         ClientUI.chat.accept(new Message(cred, MessageFromClient.REQUEST_LOGOUT));
         Platform.exit();
    	System.out.println("exit EkrutClient");
		System.exit(0);	
    }
    
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//- TEMP- until CEO main screen will be done
		 User user = new User();
	        user.setUsername("ravid");
	        user.setPassword("123");
	        user.setFirstname("ravid");
	        user.setLastname("goldin");
	        user.setId("315679654");
	        user.setPhonenumber("0524459292");
	        user.setDepartment("ceo");
	        
	        UserController.setCurrentuser(user);
	        
	        ClientUI.chat.accept(new Message(user, MessageFromClient.REQUEST_MACHINE_IDS));
			ArrayList<String> ids = (ArrayList<String>) MessageHandler.getData();
			MachineID.getItems().addAll(ids);//add all id's to MachineID comboBox
		
	        	
		
    	welcomeReportsLabel.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
    	
		MachineID.setVisible(false);//set machineID combobox unvisible
		
    	// Fill years and months in combo boxes
    	Year y = Year.now();
		for (int i = y.getValue(); i > 2010; i--)
		{
			YearComboBox.getItems().add("" + i);
		}
		
		
		for (int i = 1; i < 13; i++) {
			if (i < 10)
				Month.getItems().add("0" + i);
			else
				Month.getItems().add("" + i);
		}
		
		// Add monthly reports type
		Type.getItems().add("Orders");
		Type.getItems().add("Inventory");
		Type.getItems().add("Activity");
		
		
		
//		MachineID.getSelectionModel().clearSelection();
//		MachineID.getItems().clear();
		

	}
	
	
	// LOAD CEO MAIN SCREEN ON BACK BUTTON
	// WHEN TAREK FINISH CEO SCREEN, COMBINE IT HERE WITH HIDE/SHOW
	@FXML
    void clickBackButton(ActionEvent event) {
	
    }
	
	@FXML
	void clickOnType(ActionEvent event) {
			if(Type.getValue().equals("Inventory"))
			{
				MachineID.setVisible(true);
				MachineIDFlag=1;//MachineID comboBox is open
			}
			else MachineID.setVisible(false);
	    }

	@FXML
    void clickShowReport(MouseEvent event) {
		String reportType;
		
		// Check first for valid inputs
		if (Month.getValue() == null) {
			errorLabel.setText("You didn't choose Month!");
			return;
		}
		if (YearComboBox.getValue() == null) {
			errorLabel.setText("You didn't choose Year!");
			return;
		}
		if (Type.getValue() == null) {
			errorLabel.setText("You didn't choose Report Type!");
			return;
		}
		if (MachineID.getValue() == null && MachineIDFlag==1) {
			errorLabel.setText("You didn't choose Machine ID!");
			return;
		}
		
	
 
		 //switch screens//
		reportType = Type.getValue();
		 Parent root = null;
		 try {
			 	switch (reportType) {
			 	
		            case "Inventory":
		                 root = FXMLLoader.load(getClass().getResource("InventoryReportScreen.fxml"));
		                  break;

		            case "Orders":
		                  root = FXMLLoader.load(getClass().getResource("OrdersReportScreen.fxml"));
		                   break;
		                    
//		            case "Activity":
//		                  root = FXMLLoader.load(getClass().getResource(".fxml"));
//		                   break;

		                default://TODO: change default
		                    System.out.println("Unknown!");
		                   
		            }
		       }
		 
		  		catch (IOException exception){exception.printStackTrace();}
		 
		        super.switchScreen(event, root);
		        
		    }

}
