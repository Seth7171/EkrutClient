package gui.CustomerServiceEmployeeScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The controller class for the Customer Service Employee screen, which allows the user to manage users, 
 * view their personal information, and import users from external sources.
 * 
 * @author  Lior Jigalo
 * @version 1.0
 */
public class CustomerServiceEmployeeScreenController extends ScreenController implements Initializable {
    @FXML
    private Button addNewUserButton;
    @FXML
    private Button backButton;
    @FXML
    private Button exitButton;
    @FXML
    private Text welcomeText;
    @FXML
    private AnchorPane anchor1;

    @FXML
    private AnchorPane anchor2;

    @FXML
    private Button importButton;

    @FXML
    private Text depText;

    @FXML
    private Text emailText;
    @FXML
    private Text fullNameText;

    @FXML
    private Text idText;

    @FXML
    private Text phoneText;

    /**
     * Initializes the screen by setting the welcome message, displaying the user's personal information and setting the department name.
     * 
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeText.setText("Welcome back " + capitalLetter(UserController.getCurrentuser().getFirstname()));
    	fullNameText.setText(capitalLetter(UserController.getCurrentuser().getFirstname()) +" " + capitalLetter(UserController.getCurrentuser().getLastname()));
		idText.setText("ID: " + UserController.getCurrentuser().getId());
		depText.setText("Customer Service");
		emailText.setText(UserController.getCurrentuser().getEmailaddress());
		phoneText.setText(UserController.getCurrentuser().getPhonenumber());
    }

    /**
     * Opens the add new user screen when the add new user button is pressed.
     * 
     * @param event The MouseEvent that triggers the openAddUserScreen method.
     */
    @FXML
    protected void openAddUserScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserManagementScreens/AddUserScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    /**
     * Opens the add new subscriber screen when the add new subscriber button is pressed.
     * 
     * @param event The MouseEvent that triggers the openAddSubscriberScreen method.
     */
    @FXML
    void openAddSubscriberScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserManagementScreens/AddSubscriberScreenController.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    /**
     * Closes the program when the exit button is pressed.
     * 
     * @param event The MouseEvent that triggers the exit method.
     */
    @FXML
    protected void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }

    /**
     * Logs the user out and redirects to the login screen when the log out button is pressed.
     * 
     * @param event The MouseEvent that triggers the logOut method.
     */
    @FXML
    protected void logOut(MouseEvent event) {
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
     * Opens the manage users screen when the manage users button is pressed.
     * 
     * @param event The MouseEvent that triggers the openManageUsersScreen method.
     */
    @FXML
    protected void openManageUsersScreen(MouseEvent event) {
    }
    public String capitalLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
    
    @FXML
    void importUsersFromExternalSource(MouseEvent event) {
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_IMPORT_USERS));
        super.alertHandler(MessageHandler.getMessage(), MessageHandler.getMessage().contains("Error"));
    }
}
