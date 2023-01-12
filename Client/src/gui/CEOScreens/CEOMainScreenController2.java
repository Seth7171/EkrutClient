package gui.CEOScreens;

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

public class CEOMainScreenController2 extends ScreenController implements Initializable {

	 @FXML
	    private Text depText;

	    @FXML
	    private Text emailText;

	    @FXML
	    private Button exitButton;

	    @FXML
	    private Text fullNameText;

	    @FXML
	    private Text idText;

	    @FXML
	    private Button logOutButton;

	    @FXML
	    private Button viewReportsButton;

	    @FXML
	    private Text welcomeBackText;
	    @FXML
	    private Text phoneText;
	    @FXML
	    private AnchorPane anchor1;

	    @FXML
	    private AnchorPane anchor2;



    @FXML
    void exit(MouseEvent event) {
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


    @FXML
    void openViewReportsScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ReportScreens/ReportsMainScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	//anchor1.setStyle("-fx-background-color: white");//change color if we need
        welcomeBackText.setText("Welcome back " + UserController.getCurrentuser().getFirstname());
        fullNameText.setText(UserController.getCurrentuser().getFirstname() +" " + UserController.getCurrentuser().getLastname());
        idText.setText("ID: " + UserController.getCurrentuser().getId());
        depText.setText(UserController.getCurrentuser().getDepartment());
        emailText.setText(UserController.getCurrentuser().getEmailaddress());
        phoneText.setText(UserController.getCurrentuser().getPhonenumber());
    }
}
