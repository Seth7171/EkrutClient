package gui.UserScreens;

import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
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
public class UserMainScreenController extends ScreenController implements Initializable {

    @FXML
    private Button LogOutButton;

    @FXML
    private Button grabOrderButton;

    @FXML
    private Button newOrderButton;

    @FXML
    private Text userStatusText;

    @FXML
    private Text welcomeBackText;

    double xoffset;
    double yoffset;

    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeBackText.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
        userStatusText.setText("User Status: " + UserController.getCurrentuser().getStatus());
    }

    /**
     * @param event
     */
    @FXML
    void logOut(MouseEvent event) {
        ArrayList<String> credentials = new ArrayList<String>();
        credentials.add(UserController.getCurrentuser().getUsername());
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGOUT));
        UserController.setCurrentuser(null);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.switchScreen(event, root);
    }

    /**
     * @param event
     */
    @FXML
    void NewOrder(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ProductCatalogScreen.fxml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.switchScreen(event, root);
    }

}
