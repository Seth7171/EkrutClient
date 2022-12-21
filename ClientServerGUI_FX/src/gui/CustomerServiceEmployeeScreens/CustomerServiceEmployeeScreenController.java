package gui.CustomerServiceEmployeeScreens;

import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private double xoffset;
    private double yoffset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeText.setText("Welcome back " + UserController.getCurrentuser().getFirstname());
    }

    @FXML
    protected void openAddUserScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceEmployeeScreens/AddUserScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    @FXML
    protected void exit(MouseEvent event) {
        ArrayList<String> cred = new ArrayList<String>();
        cred.add(UserController.getCurrentuser().getUsername());
        ClientUI.chat.accept(new Message(cred, MessageFromClient.REQUEST_LOGOUT));
        ClientUI.chat.accept("disconnect");
        Platform.exit();
        System.exit(0);
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
}