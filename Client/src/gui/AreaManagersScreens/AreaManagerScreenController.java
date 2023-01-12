package gui.AreaManagersScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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
    void openRefilOrdersScreen(MouseEvent event) {
    	Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ProductControlScreens/RefillOrderScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

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

    @FXML
    void exit(MouseEvent event) {
    	super.closeProgram(event, true);
    }

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
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeText.setText("Welcome back " + capitalLetter(UserController.getCurrentuser().getFirstname()));
		fullNameText.setText(capitalLetter(UserController.getCurrentuser().getFirstname()) +" " + capitalLetter(UserController.getCurrentuser().getLastname()));
		idText.setText("ID: " + UserController.getCurrentuser().getId());
		depText.setText(capitalLetter(extractDepartment())+ " Area Manager");
		emailText.setText(UserController.getCurrentuser().getEmailaddress());
		phoneText.setText(UserController.getCurrentuser().getPhonenumber());
	}
	
	public String capitalLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	public String extractDepartment() {
		String userDepartment = UserController.getCurrentuser().getDepartment();
		return userDepartment.substring(userDepartment.lastIndexOf("_")+1);
	}
}
