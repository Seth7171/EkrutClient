package gui.ConnectionScreens;

import application.client.ClientController;
import application.client.ClientUI;
import gui.ScreenController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientConnectorController extends ScreenController {
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
    double xoffset;
	double yoffset;
	private String getip() {
		return iptxt.getText();			
	}
	
	public void Done(MouseEvent event) throws Exception {
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
		catch (Exception e) {
			System.out.printf("Exception 1");
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
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit EkrutClient");
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