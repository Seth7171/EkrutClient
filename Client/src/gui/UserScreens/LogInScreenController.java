package gui.UserScreens;

import application.client.ChatClient;
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.CustomerController;
import application.user.UserController;
import common.Deals;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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
    @FXML
    private Button fastLoginButton;
    

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset user on logout
        if (UserController.getCurrentuser() != null)
            UserController.setCurrentuser(null);

    	if (ChatClient.isOL) {
    		fastLoginButton.setVisible(false);
    	}
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
    
    
    @FXML
    private void FastlogIn(Event event){
        ArrayList<String> credentials = new ArrayList<String>();
        credentials.add("ron");
        credentials.add("123");
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGIN));
        if(!UserController.isLogged()){
        	alertHandler(MessageHandler.getMessage(), true);
            //errorMessage.setText(MessageHandler.getMessage());
            MessageHandler.setMessage(null);
            return;
        }
        if(ChatClient.isOL && UserController.getCurrentuser().getDepartment().equals("customer")) {
        	alertHandler("Unauthorized account", true);
			//errorMessage.setText("Unauthorized account");
			 ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGOUT));
			 return;
		}	
		CustomerController.setmachineID(ChatClient.currentMachineID);
        Parent root = loadRoot();
        super.switchScreen(event,root);
    }
    

    /**
     * @param event
     */
    @FXML
    private void logIn(Event event){
        ArrayList<String> credentials = getUsernameAndPassword();
        if(credentials == null)
            return;
        
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGIN)); 
        if(!UserController.isLogged()){
        	alertHandler(MessageHandler.getMessage(), true);
            //errorMessage.setText(MessageHandler.getMessage());
            MessageHandler.setMessage(null);
            return;
        }
		CustomerController.setmachineID(ChatClient.currentMachineID);
        Parent root = loadRoot();
        super.switchScreen(event,root);
    }

    private Parent loadRoot(){
        Parent root = null;
        try {
            switch (UserController.getCurrentuser().getDepartment()) {

            	case "customer":
                case "subscriber":
                	CustomerController.setCurrentCustomer(UserController.getCurrentuser());
                    root = FXMLLoader.load(getClass().getResource("CustomerMainScreen.fxml"));
                    break;

                case "customer_service":
                    root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceEmployeeScreens/CustomerServiceEmployeeScreen.fxml"));
                    break;

                case"ceo":
                    root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen2.fxml"));
                    break;

                case "operations":
                    root = FXMLLoader.load(getClass().getResource("/gui/OperationsEmployeeScreens/operationsEmployeeMainScreen.fxml"));
                    break;
                    
                case "marketing_manager":
                    root = FXMLLoader.load(getClass().getResource("/gui/MarketingManagementScreens/MarketingManagerScreen.fxml"));
                    break;

                case "marketing_employee_south":
                case "marketing_employee_north":
                case "marketing_employee_uae":
                	root = FXMLLoader.load(getClass().getResource("/gui/MarketingManagementScreens/MarketingEmployeeScreen.fxml"));
                    break;

                case "area_manager_uae":
                case "area_manager_north":
                case "area_manager_south":
                	root = FXMLLoader.load(getClass().getResource("/gui/AreaManagersScreens/AreaManagerScreen.fxml"));
                    break;

                    
                case "delivery_north":
                case "delivery_south":
                case "delivery_uae":
                	root = FXMLLoader.load(getClass().getResource("/gui/DeliveryEmployeeScreens/DeliveryEmployeeScreen.fxml"));
                    break;
                default:
                    System.out.println("Unknown!");
            }
        }catch (IOException exception){
        	alertHandler("Unknown login error", true);
            //errorMessage.setText("Unknown login error");
            exception.printStackTrace();
        }
        return root;
    }


    /**
     * @return
     */
    private ArrayList<String> getUsernameAndPassword() {
        if(userNameField.getText().equals("") || passwordField.getText().equals("")){
        	alertHandler("Username AND Password\n\tMUST be filled", true);
            //errorMessage.setText("Username AND Password\nMUST be filled");
            return null;
        }
        if(userNameField.getText().contains(" ") || passwordField.getText().contains(" ")){
        	alertHandler("username AND password fields\nMust not contain spaces", true);
            //errorMessage.setText("username AND password fields\nMust not contain spaces");
            return null;
        }
        ArrayList<String> credentials = new ArrayList<String>();
        credentials.add(userNameField.getText());
        credentials.add(passwordField.getText());
        return credentials;
    }
}