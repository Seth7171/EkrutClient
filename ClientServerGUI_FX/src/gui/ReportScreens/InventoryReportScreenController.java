package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InventoryReportScreenController implements Initializable{

    @FXML
    private Button backButton;

    @FXML
    private Button exitApp;

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
    
    // Go back to main reports screen.
    @FXML
    void clickBackButton(ActionEvent event) {
    	Parent root = null;
    	try {
		root = FXMLLoader.load(getClass().getResource("ReportsMainScreen.fxml"));
		}
    	catch (IOException exception){exception.printStackTrace();}

		Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
    
    // exit from application
    @FXML
    void exitApplication(ActionEvent event) {
    	ArrayList<String> cred = new ArrayList<String>();
        cred.add(UserController.getCurrentuser().getUsername());
        ClientUI.chat.accept("disconnect");
        ClientUI.chat.accept(new Message(cred, MessageFromClient.REQUEST_LOGOUT));
        Platform.exit();
        System.out.println("exit EkrutClient");
		System.exit(0);	
    }

}