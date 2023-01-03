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

public class MarketingManagerScreenController extends ScreenController implements Initializable{

    @FXML
    private Button existingDeals;

    @FXML
    private Button exitButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Text welcomeText;
    @FXML
    private Button viewReport;


    @FXML
    void existingDeals(MouseEvent event) {// switch to ManagerDealsScreen.fxml
    	 Parent root = null;
         try {
        	  switch (UserController.getCurrentuser().getDepartment()) {
              case "marketing_manager":
            	  root = FXMLLoader.load(getClass().getResource("ManagerDealsScreen.fxml"));
            	  break;
             case "marketing_employee_uae":
            	 root = FXMLLoader.load(getClass().getResource("EmployeeDealsScreen.fxml"));
            	 break;
             default:
                 System.out.println("Unknown!");
                 // TODO: maybe add UnknownScreenException later??
        	 	}
        	 } 
        	  catch (IOException e) {
             throw new RuntimeException(e);
         }
         super.switchScreen(event, root);
    }

    @FXML
    void exitApp(MouseEvent event) {
    	super.closeProgram(event, true);
    }


    @FXML
    void logOut(MouseEvent event) {//logOut
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
    
    @FXML
    void viewReports(MouseEvent event) {//view Report
    	Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ReportScreens/ReportsMainScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		welcomeText.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
	}

}
