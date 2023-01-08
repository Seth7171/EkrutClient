package gui.OperationsEmployeeScreens;

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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OperationsEmployeeMainScreenController extends ScreenController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button viewRefillOrdersButton;

    @FXML
    private Text welcomeBackTXT;
    @FXML
    private AnchorPane anchor1;

    @FXML
    private AnchorPane anchor2;

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

    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeBackTXT.setText("Welcome back " + UserController.getCurrentuser().getFirstname());
        fullNameText.setText(capitalLetter(UserController.getCurrentuser().getFirstname()) +" " + capitalLetter(UserController.getCurrentuser().getLastname()));
		idText.setText("ID: " + UserController.getCurrentuser().getId());
		depText.setText(capitalLetter(UserController.getCurrentuser().getDepartment()) + " Employee");
		emailText.setText(UserController.getCurrentuser().getEmailaddress());
		phoneText.setText(UserController.getCurrentuser().getPhonenumber());
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
        UserController.setCurrentuser(null);
        super.switchScreen(event, root);
    }

    @FXML
    void openRefillOrdersScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ProductControlScreens/RefillOrderScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
    public String capitalLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
}
