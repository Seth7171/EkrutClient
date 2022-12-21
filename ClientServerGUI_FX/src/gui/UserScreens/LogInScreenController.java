package gui.UserScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Lior Jigalo
 */
public class LogInScreenController extends ScreenController {
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text errorMessage;
    @FXML
    private Text forgotPassword;
    @FXML
    private Button loginButton;
    double xoffset;
    double yoffset;


    /**
     * @param event
     */
    @FXML
    void exit(MouseEvent event) {
        ClientUI.chat.accept("disconnect");
        Platform.exit();
        System.exit(0);
    }

    /**
     * @param event
     */
    @FXML
    void openForgotPasswordScreen(MouseEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "This functionality is not supported yet");
        a.setTitle("Functionality Error");
        a.show();
    }

    /**
     * @param event
     */
    @FXML
    private void logIn(MouseEvent event){
        ArrayList<String> credentials = getUsernameAndPassword();
        if(credentials == null)
            return;
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGIN));
        if(!UserController.isLogged()){
            errorMessage.setText(MessageHandler.getMessage());
            return;
        }

        Parent root = null;
        try {
            // TODO: expand next screen switch case
            switch (UserController.getCurrentuser().getDepartment()) {
                case "member":
                    root = FXMLLoader.load(getClass().getResource("UserMainScreen.fxml"));
                    break;

                case "customer_service":
                    root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceEmployeeScreens/CustomerServiceEmployeeScreen.fxml"));
                    break;
                case"ceo":
                        root = FXMLLoader.load(getClass().getResource("/gui/ReportScreens/ReportsMainScreen.fxml"));
                    break;

                default:
                    System.out.println("Unknown!");
                    // TODO: maybe add UnknownScreenException later??
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
        super.switchScreen(event,root);
    }

    /**
     * @return
     */
    private ArrayList<String> getUsernameAndPassword() {
        if(userNameField.getText().equals("") || passwordField.getText().equals("")){
            errorMessage.setText("Username AND Password\nMUST be filled");
            return null;
        }
        if(userNameField.getText().contains(" ") || passwordField.getText().contains(" ")){
            errorMessage.setText("username AND password fields\nMust not contain spaces");
            return null;
        }
        ArrayList<String> credentials = new ArrayList<String>();
        credentials.add(userNameField.getText());
        credentials.add(passwordField.getText());
        return credentials;
    }
}