package gui.ConnectionScreens;

import application.client.ChatClient;
import application.client.ClientController;
import application.client.ClientUI;
import application.client.MessageHandler;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientConnectorController extends ScreenController {
	String temp="";
	boolean choosen;

	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnDone = null;
	@FXML
	private Button btnEK;
	@FXML
	private Button btnOL;
	@FXML
	private Label lbllist;
	@FXML
	private Label warning;
	@FXML
	private Label warningEKOL;

	
	@FXML
	private TextField iptxt;
	
	ObservableList<String> list;
    double xoffset;
	double yoffset;
	private String getip() {
		return iptxt.getText();			
	}
	
	public void Done(MouseEvent event) throws Exception {
		if (!choosen) {
			warningEKOL.setVisible(true);
			return;
		}
		warningEKOL.setVisible(false);
		String ip;
		FXMLLoader loader = new FXMLLoader();
		
		ip=getip();
		if(ip.trim().isEmpty()) {
			System.out.println("You Must enter an ip number");
			iptxt.clear();
			warning.setVisible(true);
			return;
		}

		try {
		ClientUI.chat = new ClientController(ip, 5555);
		ClientUI.chat.getClient().openConnection();
		}
		catch (java.net.ConnectException e) {
			super.alertHandler("server refused to connect", true);
			e.printStackTrace();
			return;
		}
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/LogInScreen.fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		super.switchScreen(event, root);
	}

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ConnectionScreens/ClientConnector.fxml"));
				
		Scene scene = new Scene(root);

		// event handler for when the mouse is pressed on the scene to trigger the drag and move event
        root.setOnMousePressed(event -> {
            xoffset = event.getSceneX();
            yoffset = event.getSceneY();
        });

        // event handler for when the mouse is pressed AND dragged to move the window
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX()-xoffset);
            primaryStage.setY(event.getScreenY()-yoffset);
        });

		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	@FXML
	public void clickedEK(MouseEvent event) {
		choosen = true;
		warningEKOL.setVisible(false);
		btnEK.setStyle("-fx-background-color: #00bfff;");
		btnOL.setStyle(null);
		ChatClient.isOL=false;
	}
	
	@FXML
	public void clickedOL(MouseEvent event) {
		choosen = true;
		warningEKOL.setVisible(false);
		btnOL.setStyle("-fx-background-color: #00bfff;");
		btnEK.setStyle(null);
		ChatClient.isOL=true;
	}

	@FXML
	public void getExitBtn(MouseEvent event) throws Exception {
		Platform.exit();
		System.exit(0);
	}
}


//	public void Done(ActionEvent event) throws Exception {
//		String ip;
//		FXMLLoader loader = new FXMLLoader();
//
//		ip=getip();
//		if(ip.trim().isEmpty()) {
//			System.out.println("You must enter a ip number");
//		}
//		else
//		{
//			try {
//				ClientUI.chat = new ClientController(ip, 5555);
//				ClientUI.chat.getClient().openConnection();
//				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
//				Stage primaryStage = new Stage();
//				Pane root = loader.load(getClass().getResource("/gui/SubscriberEdit.fxml").openStream());
//
//				Scene scene = new Scene(root);
//				scene.getStylesheets().add(getClass().getResource("/gui/SubscriberEdit.css").toExternalForm());
//				// event handler for when the mouse is pressed on the scene to trigger the drag and move event
//				root.setOnMousePressed(event1 -> {
//					xoffset = event1.getSceneX();
//					yoffset = event1.getSceneY();
//				});
//
//				// event handler for when the mouse is pressed AND dragged to move the window
//				root.setOnMouseDragged(event1 -> {
//					primaryStage.setX(event1.getScreenX()-xoffset);
//					primaryStage.setY(event1.getScreenY()-yoffset);
//				});
//				primaryStage.initStyle(StageStyle.UNDECORATED);
//				primaryStage.setTitle("Client Editor");
//
//				primaryStage.setScene(scene);
//
//				primaryStage.show();
//			}
//			catch (Exception e) {
//				iptxt.clear();
//				warning.setVisible(true);
//			}
//		}
//	}