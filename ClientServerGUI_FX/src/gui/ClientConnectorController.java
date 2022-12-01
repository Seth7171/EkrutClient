package gui;

import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Subscriber;
import client.ChatClient;
import client.ClientController;
import client.ClientUI;

public class ClientConnectorController {
	String temp="";
	
	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnDone = null;
	@FXML
	private Label lbllist;
	@FXML
	private Label warning;
	
	@FXML
	private TextField iptxt;
	
	ObservableList<String> list;
	
	private String getip() {
		return iptxt.getText();			
	}
	
	public void Done(ActionEvent event) throws Exception {
		String ip;
		FXMLLoader loader = new FXMLLoader();
		
		ip=getip();
		if(ip.trim().isEmpty()) {
			System.out.println("You must enter a ip number");
					
		}
		
		/*if (ClientUI.chat.display()) {
			System.out.println("Worng ip or server down cannot connect");
			return;
		}*/
		else
		{
			try {
			ClientUI.chat = new ClientController(ip, 5555); 
			ClientUI.chat.getClient().openConnection();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/gui/SucscriberEdit.fxml").openStream());	
		
			Scene scene = new Scene(root);			
			scene.getStylesheets().add(getClass().getResource("/gui/SucscriberEdit.css").toExternalForm());
			primaryStage.setTitle("Client Editor");

			primaryStage.setScene(scene);		
			primaryStage.show();
			}
			catch (Exception e) {
				iptxt.clear();
				warning.setVisible(true);
			} 
		}
	}

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientConnector.fxml"));
				
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ClientConnector.css").toExternalForm());
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		
		primaryStage.show();		
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit EkrutClient");
		
		System.exit(0);			
	}

}