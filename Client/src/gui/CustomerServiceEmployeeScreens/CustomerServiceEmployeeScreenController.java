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
 * @author Lior Jigalo
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeText.setText("Welcome back " + capitalLetter(UserController.getCurrentuser().getFirstname()));
    	fullNameText.setText(capitalLetter(UserController.getCurrentuser().getFirstname()) +" " + capitalLetter(UserController.getCurrentuser().getLastname()));
		idText.setText("ID: " + UserController.getCurrentuser().getId());
		depText.setText("Customer Service");
		emailText.setText(UserController.getCurrentuser().getEmailaddress());
		phoneText.setText(UserController.getCurrentuser().getPhonenumber());
    }

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

    @FXML
    protected void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }

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

    // TODO: here tarek should add his customer management screen.
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
