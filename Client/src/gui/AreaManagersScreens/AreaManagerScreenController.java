package gui.AreaManagersScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.RefillOrder;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.User;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/** Description of Area Manager Screen Controller.
 * @author Ravid Goldin
 * @author Ben Ben Baruch
 */

public class AreaManagerScreenController extends ScreenController implements Initializable {

	@FXML
    private AnchorPane anchor1;

    @FXML
    private AnchorPane anchor2;

    @FXML
    private Text depText;

    @FXML
    private Text emailText;

    @FXML
    private Button exitButton;

    @FXML
    private Text fullNameText;

    @FXML
    private Text idText;

    @FXML
    private Button logOutButton;

    @FXML
    private Button manageProductsButton;

    @FXML
    private Button manageUsersButton;

    @FXML
    private Text phoneText;

    @FXML
    private Button viewRefilOrdersButton;

    @FXML
    private Button viewReportsButton;
    @FXML
    private Text welcomeText;

    /**
  	 * Open Manage Prdocuts Screen.
  	 * @param event the mouse event that triggered the method call
  	 */
    @FXML
    void openManageProductsScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ProductControlScreens/productManagementScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }


    @FXML
    private void showAlertData(){
        int refillRequests = 0;

        int unapprovedUsers = 0;

        String unapprovedStr = "";
        String requestStr = "";


        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_CUSTOMERS_FROM_USER_TABLE));

        if (MessageHandler.getData() == null)
            return;

        for (User user :(ArrayList<User>)MessageHandler.getData()){
            if (user.getStatus().equals("not approved"))
                unapprovedUsers += 1;
        }

        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_REFILL_ORDERS));
        if (MessageHandler.getData() == null)
            return;

        for (RefillOrder order :(ArrayList<RefillOrder>)MessageHandler.getData()){
            if (order.getMachineID().contains("NOR") && UserController.getCurrentuser().getDepartment().split("_")[2].equals("north") && order.getAssignedEmployee().equals("not assigned")){
                refillRequests += 1;
            }

            if (order.getMachineID().contains("SOU") && UserController.getCurrentuser().getDepartment().split("_")[2].equals("south")&& order.getAssignedEmployee().equals("not assigned")){
                refillRequests += 1;
            }

            if (order.getMachineID().contains("UAE") && UserController.getCurrentuser().getDepartment().split("_")[2].equals("uae")&& order.getAssignedEmployee().equals("not assigned")){
                refillRequests += 1;
            }
        }

        if (unapprovedUsers > 0)
            unapprovedStr = unapprovedUsers + "";
        else
            unapprovedStr = "No";

        if (refillRequests > 0)
            requestStr = refillRequests + "";
        else
            requestStr = "No";

        super.alertHandler("There are:\n" + unapprovedStr + " unapproved users.\n" + requestStr + " unassigned refill requests", false);
    }



    /**
  	 * Open Refill Orders Screen.
  	 * @param event the mouse event that triggered the method call
  	 */
    @FXML
    void openRefilOrdersScreen(MouseEvent event) {
    	Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ProductControlScreens/RefillOrderScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
    
    /**
  	 * Open User Management Screen.
  	 * @param event the mouse event that triggered the method call
  	 */
    @FXML
    void openUserManagementScreen(MouseEvent event) {
    	 Parent root = null;
         try {
             root = FXMLLoader.load(getClass().getResource("/gui/UserManagementScreens/userManagementScreen.fxml"));
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
         super.switchScreen(event, root);
    }
    
    /**
  	 * Open view reports screen.
  	 * @param event the mouse event that triggered the method call
  	 */
    @FXML
    void openViewReportsScreen(MouseEvent event) {
    	 Parent root = null;
         try {
             root = FXMLLoader.load(getClass().getResource("/gui/ReportScreens/ReportsMainScreen.fxml"));
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
         super.switchScreen(event, root);
    }
    
    /**
  	 * exit application
  	 * @param event the mouse event that triggered the method call
  	 */
    @FXML
    void exit(MouseEvent event) {
    	super.closeProgram(event, true);
    }
    
    /**
  	 * Log out from app and goes back to login screen.
  	 * @param event the mouse event that triggered the method call
  	 */
    @FXML
    void logOut(MouseEvent event) {
    	 ArrayList<String> cred = new ArrayList<String>();
         cred.add(UserController.getCurrentuser().getUsername());
         ClientUI.chat.accept(new Message(cred, MessageFromClient.REQUEST_LOGOUT));

         Parent root = null;
         try {
             root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/LogInScreen.fxml"));
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
         super.switchScreen(event, root);
    }
    
    /**
     * Initializes the screen.
     * Set all relevant texts to area manager screen.
     * @param arg0 the location of the root object
     * @param arg1 the resources used to localize the root object
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeText.setText("Welcome back " + capitalLetter(UserController.getCurrentuser().getFirstname()));
		fullNameText.setText(capitalLetter(UserController.getCurrentuser().getFirstname()) +" " + capitalLetter(UserController.getCurrentuser().getLastname()));
		idText.setText("ID: " + UserController.getCurrentuser().getId());
		depText.setText(capitalLetter(extractDepartment())+ " Area Manager");
		emailText.setText(UserController.getCurrentuser().getEmailaddress());
		phoneText.setText(UserController.getCurrentuser().getPhonenumber());
	}
	
	/**
	 * Make the string with capital letter first and all rest are lower-case letters. (yossi -> Yossi)
	 * @param str String input
	 * @return String with capital letter.
	 */
	public String capitalLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	/**
     * Helper method to extract the department of current logged in user.
     * Example: marketing_employee_uae -> uae
     * @return String department of current logged in user.
     */
	public String extractDepartment() {
		String userDepartment = UserController.getCurrentuser().getDepartment();
		return userDepartment.substring(userDepartment.lastIndexOf("_")+1);
	}
}
