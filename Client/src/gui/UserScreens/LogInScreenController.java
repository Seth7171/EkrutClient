package gui.UserScreens;
import application.client.ChatClient;
//************************************
//TODO CHECK WHY ENTERING LIOR 1 THAN LIOR 123 NOT WORKING
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.CustomerController;
import application.user.UserController;
import common.Deals;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.MessageFromServer;
import common.orders.Order;
import common.orders.Product;
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
import javafx.scene.layout.Pane;
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
    @FXML
    private Button fastLoginButton;
    
    @FXML
    private ComboBox<String> machinesID;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset user on logout
        if (UserController.getCurrentuser() != null)
            UserController.setCurrentuser(null);

    	if (!ChatClient.isOL) {
			ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_MACHINE_IDS));
	        ArrayList<String> machineIDs = new ArrayList<>();
	        machineIDs.addAll((ArrayList<String>) MessageHandler.getData());
	        machinesID.getItems().clear();
	        machinesID.getItems().addAll(machineIDs);
    	}
    	else {
    		machinesID.setVisible(false);
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
        if(machinesID.getValue()==null && !ChatClient.isOL) {
			errorMessage.setText("Please choose a machine");
			 return;
        }
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGIN)); // TODO: this should be uncommented
        if(!UserController.isLogged()){
            errorMessage.setText(MessageHandler.getMessage());
            MessageHandler.setMessage(null);
            return;
        }
        if(ChatClient.isOL && UserController.getCurrentuser().getDepartment().equals("customer")) {
			errorMessage.setText("Unauthorized account");
			 ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGOUT));
			 return;
		}	
		CustomerController.setmachineID(machinesID.getValue());
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

        if(machinesID.getValue()==null && !ChatClient.isOL) {
			errorMessage.setText("Please choose a machine");
			 return;
        }
        Deals deal = new Deals();
        deal.setDealID("001");
        deal.setDealName("Night time sale");
        deal.setDiscount(0.1F);
        deal.setDescription("Special offer for late night students from 20pm to 5am");
        deal.setType("ALL");
        deal.setArea("north");
        deal.setStatusString("approved");
        deal.setActive("active");

        ArrayList<String> abc = new ArrayList<>();
        abc.add("false");
        abc.add("717717717");
        //

        ClientUI.chat.accept(new Message(abc, MessageFromClient.REQUEST_SET_FIRST_TIME_BUY_AS_SUB )); // TODO: this should be DELETED
        //ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGIN)); // TODO: this should be uncommented
        if(!UserController.isLogged()){
            errorMessage.setText(MessageHandler.getMessage());
            MessageHandler.setMessage(null);
            return;
        }
		CustomerController.setmachineID(machinesID.getValue());
        Parent root = loadRoot();
        super.switchScreen(event,root);
    }

    private Parent loadRoot(){
        Parent root = null;
        try {
            // TODO: expand next screen switch case
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
                    
                case "marketing_manager"://TODO: the path is not working..!
                    root = FXMLLoader.load(getClass().getResource("/gui/MarketingManagementScreens/MarketingManagerScreen.fxml"));
                    break;

                case "marketing_employee_south":
                case "marketing_employee_north":
                case "marketing_employee_uae":
                	root = FXMLLoader.load(getClass().getResource("/gui/MarketingManagementScreens/MarketingEmployeeScreen.fxml"));
                    break;
                /*case "area_manager_uae":
                	root = FXMLLoader.load(getClass().getResource("/gui/AreaManagersScreens/AreaManagerScreen.fxml"));
                    break;
                case "area_manager_north":
                	root = FXMLLoader.load(getClass().getResource("/gui/AreaManagersScreens/AreaManagerScreen.fxml"));
                    break;*/	//NOOBS

                case "area_manager_uae":
                case "area_manager_north":
                case "area_manager_south":
                	root = FXMLLoader.load(getClass().getResource("/gui/AreaManagersScreens/AreaManagerScreen.fxml"));
                    break;

                //TODO: delivery per location
                // @Lior all delivery employees have the same screen, just the data displayed is being changed accordingly
                    // @Nitsan ma ata omerrrrrrrrrr liorrrrrrrrrrr leh la server///!!!!!!
                        // @Lior I will stop this before there will be a huge ktata here. ):<

                case "delivery_north":
                case "delivery_south":
                case "delivery_uae":
                	root = FXMLLoader.load(getClass().getResource("/gui/DeliveryEmployeeScreens/DeliveryEmployeeScreen.fxml"));
                    break;
                default:
                    System.out.println("Unknown!");
                    // TODO: maybe add UnknownScreenException later??
            }
        }catch (IOException exception){
            errorMessage.setText("Unknown login error");
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
}