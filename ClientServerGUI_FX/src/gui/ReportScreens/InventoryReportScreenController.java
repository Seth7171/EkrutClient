package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InventoryReportScreenController extends ScreenController implements Initializable{
	
    @FXML
    private Button backButton;

    @FXML
    private Button exitApp;
    
    @FXML
    private Label MachineIDLabel;
    
    @FXML
    private Label DateChooseLabel;

    // all stuff needed for Inventory report screen.
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	MachineIDLabel.setText("Machine ID: " + ChatClient.returnMachineID);//get the Machine Id that has been choose
    	DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);
	}
    
    
    // Go back to main reports screen.
    @FXML
    void clickBackButton(MouseEvent event) {
    	Parent root = null;
    	try {
		root = FXMLLoader.load(getClass().getResource("ReportsMainScreen.fxml"));
		}
    	catch (IOException exception){exception.printStackTrace();}
    	super.switchScreen(event, root);
	}
    
    // exit from application
    @FXML
    void exitApplication(MouseEvent event) {
    	super.closeProgram(event, true);	
    }

}