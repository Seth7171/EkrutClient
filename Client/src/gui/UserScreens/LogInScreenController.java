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
 * This class is responsible for the log in screen of the application. It contains methods for initializing the view,
 * handling events related to logging in, and exiting the program.
 * @author Lior Jigalo
 */
public class LogInScreenController extends ScreenController implements Initializable {
	private boolean didntLogIn = false;
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


    /**
     *
     * Initializes the log in screen by setting text fields and buttons, and adding event handlers for the "Enter" key
     * to submit the log in form.
     * @param location The location of the FXML file associated with this controller.
     * @param resources The resources used to localize the root object.
    */

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
    * 
    * Handles the event of the user clicking the "Exit" button. Closes the program.
    * @param event The event of the mouse clicking the "Exit" button.
    */
    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, false);
    }

    /**
    * UNUSED IN FINAL FORM
    * Handles the event of the user clicking the "Forgot Password" button. Shows an alert message that this functionality
    * is not yet supported.
    * @param event The event of the mouse clicking the "Forgot Password" button.
    */
    @FXML
    void openForgotPasswordScreen(MouseEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "This functionality is not supported yet");
        a.setTitle("Functionality Error");
        a.show();
    }
    
    /**
    * TEMPORARY IMPLEMENTION will work later with EKT app.
    * Handles the event of the user clicking the "Fast Login" button. Sends a message to the server to log in with predefined
    * credentials "ron" and "123" and switches to the appropriate screen depending on the user's department and if EK or OL
    * @param event The event of the user clicking the "Fast Login" button.
    */
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
     *
     * Handles the event of the user clicking the "Log In" button or pressing the "Enter" key in the username or password field.
     * Sends a message to the server with the entered username and password, and switches to the appropriate screen depending
     * on the user's department and if EK or OL.
     * @param event The event of the user clicking the "Log In" button or pressing the "Enter" key in the username or password field.
     */
    @FXML
    public String logIn(Event event){
    	didntLogIn = false;
        ArrayList<String> credentials = null;
        credentials = getUsernameAndPassword();
        if(credentials == null)
            return "FillAllUserCredentials";
        requestLogIn(credentials);
        if(didntLogIn) {
        	return "WorngUserCredentials";
        }
        if (!ChatClient.isOL){
            switch (UserController.getCurrentuser().getDepartment()){
                case "subscriber":
                case "customer":
                    break;

                default:
                	requestLogOut(credentials);
                    return "Unauthorized account";

            }

        }
        loadUserHomeScreen(event);
        return "LoggedIn";
    }
    public void loadUserHomeScreen(Event event) {
		CustomerController.setmachineID(ChatClient.currentMachineID);
        Parent root = loadRoot();
        super.switchScreen(event,root);
    }
    
    public void requestLogIn( ArrayList<String> credentials) {
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGIN)); 
        if(!UserController.isLogged()){
        	alertHandler(MessageHandler.getMessage(), true);
            //errorMessage.setText(MessageHandler.getMessage());
            MessageHandler.setMessage(null);
            didntLogIn=true;
        }
    }
    
    public void requestLogOut( ArrayList<String> credentials) {
    	alertHandler("Unauthorized account", true);
        //errorMessage.setText("Unauthorized account");
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGOUT));
    }

    /**
     *
     * Loads the appropriate screen for the user based on their department, and sets the current customer if applicable.
     * @return The Parent root of the appropriate screen.
     */
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
     *
     * Retrieves the username and password entered by the user and returns it in an ArrayList.
     * If one of the fields is empty or contains spaces, an error message is displayed.
     * @return ArrayList containing the username and password entered by the user, or null if the fields are empty or contain spaces.
     */
    public ArrayList<String> getUsernameAndPassword() {
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
    
    public void setUsernameAndPassword(String username,String password) {
        System.out.println("Aaaaaaa");
    	userNameField.setText(username);
        passwordField.setText(password);
    }
}