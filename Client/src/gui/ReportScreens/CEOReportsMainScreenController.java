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
	int LocationFlag=0; // Location ComboBox:  1=open|0-close
	
	@FXML
    private ComboBox<String> Location;
	
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
    
  
    /**
	 * exit application
	 * @param event
	 * @throws Exception
	 */
    @FXML
    void exitApp(MouseEvent event) throws Exception {
    	super.closeProgram(event, true);
    }

	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//request all machines locations
		ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_ALL_MACHINE_LOCATIONS));
		ArrayList<String> Locations = (ArrayList<String>) MessageHandler.getData();
		Location.getItems().addAll(Locations);//add all Locations to Location comboBox

    	welcomeReportsLabel.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
    	
		MachineID.setVisible(false);//set machineID combobox unvisible
		Location.setVisible(false);//set Location combobox unvisible
		
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
		
		
	}
	
	
	// LOAD CEO MAIN SCREEN ON BACK BUTTON
	// WHEN TAREK FINISH CEO SCREEN, COMBINE IT HERE WITH HIDE/SHOW
	
	@FXML
    void clickBackButton(ActionEvent event) {
	
    }
	
	//click on Type combo BOX
	@FXML
	void clickOnType(ActionEvent event) {
			if(Type.getValue().equals("Inventory"))
			{
				Location.setVisible(true);
				LocationFlag=1;//Location comboBox is open
				MachineID.setVisible(false);
				MachineIDFlag=0;//MachindID comboBox is close
				
			}
			else 
				{
				Location.setVisible(false);
				LocationFlag=0;//Location comboBox is close
				MachineID.setVisible(false);
				MachineIDFlag=0;//MachindID comboBox is close
				}
	    }
	
	//click on Location combo BOX
	@FXML
    void clickOnLocation(ActionEvent event) {
		if(Location.getValue()!=null)
		{
			MachineID.setVisible(true);
			MachineIDFlag=1;//MachindID comboBox is open
			//request all machines IDS in specific Location
			ClientUI.chat.accept(new Message(Location.getValue(), MessageFromClient.REQUEST_MACHINE_IDS));
			ArrayList<String> ids = (ArrayList<String>) MessageHandler.getData();
			MachineID.getItems().clear();
			MachineID.getItems().addAll(ids);//add all MachinesID in specific Location to MachineID comboBox
			
		}
		else 
			{
			MachineID.setVisible(false);
			MachineIDFlag=0;//Machind id comboBox is close
			}
		System.out.println("Location choose CEO:" + Location.getValue());
		
    }
	
	@FXML
    void clickShowReport(MouseEvent event) {
		String reportType = null;
		String LocationChoose= null;// location
		String MonthChoose=null;//month
		String YearChoose=null;//year
		
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
		if (Location.getValue() == null && LocationFlag==1) {
			errorLabel.setText("You didn't choose Location !");
			return;
		}
		if (MachineID.getValue() == null && MachineIDFlag==1) {
			errorLabel.setText("You didn't choose Machine ID!");
			return;
		}
		
		System.out.println("MachineID choose CEO:" + MachineID.getValue());
		
		 ChatClient.returnMachineID=MachineID.getValue(); //save the machine id that has been choosing
		 ChatClient.returnMonthChoose=Month.getValue();
		 ChatClient.returnYearChoose=YearComboBox.getValue();
		// ChatClient.returnLocationChoose=Location.getValue();// need to add returnLocationChoose in ChatClient 
		 
		
		 
		 //switch screens//
		reportType = Type.getValue();
		 Parent root = null;
		 try {
			 	switch (reportType) 
			 		{
			 	
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