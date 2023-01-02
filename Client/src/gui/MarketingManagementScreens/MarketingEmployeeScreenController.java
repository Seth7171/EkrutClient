package gui.MarketingManagementScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.text.Text;

public class MarketingEmployeeScreenController extends ScreenController implements Initializable { 

    @FXML
    private Button existingDeals;

    @FXML
    private Button exitButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Text welcomeText;

    @FXML
    void existingDeals(MouseEvent event) {
    	 Parent root = null;
         try {
             root = FXMLLoader.load(getClass().getResource("EmployeeDealsScreen.fxml"));
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
         super.switchScreen(event, root);
    }

    @FXML
    void exitApp(MouseEvent event) {
    	super.closeProgram(event, true);
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
         super.switchScreen(event, root);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		welcomeText.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
		ClientUI.chat.accept(new Message(null,MessageFromClient.REQUEST_DISCOUNT_LIST )); 
		
	}

}
