package gui.UserScreens;
//************************************
//TODO CHECK WHY ENTERING LIOR 1 THAN LIOR 123 NOT WORKING
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
public class LogInScreenController extends ScreenController implements Initializable {
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

    /**
     * @param event
     */
    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, false);
    }

    /**
     * @param event
     *This method opens the forgot password alert
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
    private void logIn(Event event){
        ArrayList<String> credentials = getUsernameAndPassword();
        if(credentials == null)
            return;

        //ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_MACHINE_INVENTORY_REPORT)); // TODO: this should be DELETED
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGIN)); // TODO: this should be uncommented
        if(!UserController.isLogged()){
            errorMessage.setText(MessageHandler.getMessage());
            return;
        }
        Parent root = loadRoot();
        super.switchScreen(event,root);
    }

    private Parent loadRoot(){
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
                    root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));

                    break;

                // TODO: reset UserControler on logout

                default:
                    System.out.println("Unknown!");
                    // TODO: maybe add UnknownScreenException later??
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
        return root;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt1) {
                if (evt1.getCode().equals(KeyCode.ENTER)) {
                    logIn(evt1);
                }
            }
        });
        passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt2) {
                if (evt2.getCode().equals(KeyCode.ENTER)) {
                    logIn(evt2);
                }
            }
        });
    }
}