package gui.ReportScreens;
import java.time.Year;
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
import gui.ScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/** Description of CEO Reports Main Screen Controller.
 * @author Ravid Goldin
 * @author Ben Ben Baruch
 */

public class ReportsMainScreenController extends ScreenController implements Initializable{
	/** Flags for machine id and location combo boxes. 1 means open, 0 means close.*/
	int MachineIDFlag=0; // MachineID ComboBox:  1=open|0=close
	int LocationFlag=0; // Location ComboBox:  1=open|0=close
	Parent root = null;
	ArrayList<String> monthYearMachine = new ArrayList<>();
	public int getLocationFlag() {
		return LocationFlag;
	}
	public int getMachineIDFlag() {
		return MachineIDFlag;
	}
	@FXML
    private ComboBox<String> Location;
	public String getLocation() {
		return Location.getValue();
	}
	
	@FXML
    private ComboBox<String> MachineID;
	public String getMachineID() {
		return MachineID.getValue();
	}

    @FXML
    private ComboBox<String> Month;
	public String getMonth() {
		return Month.getValue();
	}
	
    @FXML
    private ComboBox<String> Type;
	public String getType() {
		return Type.getValue();
	}
	
    @FXML
    private ComboBox<String> YearComboBox;
	public String getYearComboBox() {
		return YearComboBox.getValue();
	}
	
    @FXML
    private Button backButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button showReportButton;

    @FXML
    private Label welcomeReportsLabel;
    
    
  
    /**
	 * exit application
	 * @param event
	 * @throws Exception
	 */
    @FXML
    void exitApp(MouseEvent event) throws Exception {
    	super.closeProgram(event, true);
    }

    /**
     * Initializes the screen. Receiving all machines locations from server.
     * The method fill all elements on the screen: set texts and combo boxes ready to use.
     * @param arg0 the location of the root object
     * @param arg1 the resources used to localize the root object
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	
		//request all machines locations
		ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_ALL_MACHINE_LOCATIONS));
		ArrayList<String> Locations = (ArrayList<String>) MessageHandler.getData();
		Location.getItems().addAll(Locations);//add all Locations to Location comboBox

    	welcomeReportsLabel.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
    	
		MachineID.setVisible(false);//set machineID comboBox  invisible
		Location.setVisible(false);//set Location comboBox  invisible
		
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
		Type.getItems().add("Clients");
		
		
	}

	/**
     * Set the right window when clicking back button.
     * It refers to previous screen depends on role.
     * @param event the mouse event that triggered the method call
     */ 
	@FXML
    void clickBackButton(MouseEvent  event) {
		Parent root = null;
    	try {
    		 switch (UserController.getCurrentuser().getDepartment()) {
             case "marketing_manager":
                 root = FXMLLoader.load(getClass().getResource("/gui/MarketingManagementScreens/MarketingManagerScreen.fxml"));
                 break;
            	 
             case"ceo":
                 root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen2.fxml"));
                 break;

			 case"area_manager_north":
			 case"area_manager_south":
			 case"area_manager_uae":
				 root = FXMLLoader.load(getClass().getResource("/gui/AreaManagersScreens/AreaManagerScreen.fxml"));
				 break;
    		
    		 }
		}
    	catch (IOException exception){exception.printStackTrace();}
		super.switchScreen(event, root);
	}
	
	/**
     * Event handler method for the Type combo box.
     * It opens more options for reports depends on report type. (Options are location and machineID)
     * @param event the action event that triggered the method call
     */
	@FXML
	void clickOnType(ActionEvent event) {
			if(Type.getValue().equals("Inventory")) {

				 switch (UserController.getCurrentuser().getDepartment()) {
		            case "area_manager_north":
		            	Location.setValue("north");
		            	Location.setDisable(true);
		           	     break;
					 case"area_manager_south":
			             Location.setValue("south");
			             Location.setDisable(true);
			             break;
					 case"area_manager_uae":
						 Location.setValue("uae");
						 Location.setDisable(true);
						 break;
		   		
		   		 }
				
				Location.setVisible(true);
				LocationFlag=1;//Location comboBox is open
				MachineID.setVisible(true);
				MachineIDFlag=1;//MachindID comboBox is close
				
			}
			else 
				{
				Location.setVisible(false);
				LocationFlag=0;//Location comboBox is close
				MachineID.setVisible(false);
				MachineIDFlag=0;//MachindID comboBox is close
				}
	    }
	
	/**
     * Event handler method for the Location combo box.
     * It opens all the machines on specific location. Based on the Location that has been chosen.
     * @param event the action event that triggered the method call
     */
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
    }
	
	/**
     * Event handler method for the Show Report button.
     * Checking for wrong input when Month, Year, Type, Machine ID and Location are null.
     * Set all necessary stuff for the report screen. Sends request to server to obtain the data it needs.
     * Move to the next window depends on Report type: Inventory, Clients or Orders.
     * @param event the mouse event that triggered the method call
     */

	@FXML
    String clickShowReport(MouseEvent event) {
		
		// Check first for valid inputs
		if (getMonth() == null) {
        	alertHandler("You didn't choose Month!" , true);
			return "You didn't choose Month!";
		}
		if (getYearComboBox() == null) {
			alertHandler("You didn't choose Year!" , true);
			return "You didn't choose Year!";
		}
		if (getType() == null) {
			alertHandler("You didn't choose Report Type!" , true);
			return "You didn't choose Report Type!";
		}
		if (getLocation() == null && getLocationFlag()==1) {
			alertHandler("You didn't choose Location !" , true);
			return "You didn't choose Location !";
		}
		if (getMachineID() == null && getMachineIDFlag()==1) {
			alertHandler("You didn't choose Machine ID!" , true);
			return "You didn't choose Machine ID!";
		}
		
		
		
		//Save data for next window
		saveChatClientData();
		 
		
		 
		 //switch screens//
		 monthYearMachine.add(getMonth());
		 monthYearMachine.add(getYearComboBox());
		
		 try {
			 	switch (getType())
			 		{
			            case "Inventory":
			            	  if (inventoryReportCase(monthYearMachine,root).equals("Report not found")) {
			            		  return "Report not found";
			            	  }
			                  break;
	
			            case "Orders":
			            	//Fix month 
			            	
				                if(monthYearMachine.get(0).startsWith("0"))
			            		monthYearMachine.set(0, monthYearMachine.get(0).substring(1));
			            	
			            	// request report from server
			            	ClientUI.chat.accept(new Message(monthYearMachine, MessageFromClient.REQUEST_ALL_MACHINES_ORDERS_MONTHLY_REPORT));
							//if no such report
			            	if (MessageHandler.getMessage() != null && MessageHandler.getMessage().contains("Error")) {
								MessageHandler.setMessage(null);
								Alert a = new Alert(Alert.AlertType.INFORMATION, "Sorry,No monthly report were found.");
								a.setTitle("Report not found");
								a.show();
								return "Report not found";
			            	}
							root = FXMLLoader.load(getClass().getResource("OrdersReportScreen.fxml"));
								break;
			                    
			            case "Clients":
			            			// request report from server
			            		ClientUI.chat.accept(new Message(monthYearMachine, MessageFromClient.REQUEST_CLIENT_REPORT));
									//if no such report
				            	if (MessageHandler.getMessage() != null && MessageHandler.getMessage().contains("error")) {
								MessageHandler.setMessage(null);
								Alert a = new Alert(Alert.AlertType.INFORMATION, "Sorry,No monthly report were found.");
								a.setTitle("Report not found");
								a.show();
								return "Report not found";
			            	}
			                  root = FXMLLoader.load(getClass().getResource("ClientsReportScreen.fxml"));
			                   break;
	
			                default:
			                    System.out.println("Unknown!");
			                    break;
		            }
		 	}
		 catch (IOException exception){
			 exception.printStackTrace();
		 }
		switchToReport(event,root);
		return "done";
	}

	public void switchToReport(MouseEvent event, Parent root){
		super.switchScreen(event, root);
	}
	
	public String inventoryReportCase(ArrayList<String> monthYearMachine,Parent root) {
   	 monthYearMachine.add(getMachineID());
	 
		// request report from server
		ClientUI.chat.accept(new Message(monthYearMachine, MessageFromClient.REQUEST_MACHINE_MONTHLY_INVENTORY_REPORT));
		//if no such report
		if (MessageHandler.getMessage() != null && MessageHandler.getMessage().contains("Error")) {
			MessageHandler.setMessage(null);
			Alert a = new Alert(Alert.AlertType.INFORMATION, "No monthly reports were found for this machine.");
			a.setTitle("Report not found");
			a.show();
			return "Report not found";
		}
        try {
			root = FXMLLoader.load(getClass().getResource("InventoryReportScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "Report was found";
	}
	
	public void saveChatClientData() {
		//Save data for next window
		 ChatClient.returnMachineID=getMachineID(); // save the machine id that has been choosing
		 ChatClient.returnMonthChoose=getMonth(); // save the month that has been choosing
		 ChatClient.returnYearChoose=getYearComboBox(); // save the year that has been choosing
		 ChatClient.returnLocationChoose=getLocation(); // save the machine location that has been choosing
	}

}
