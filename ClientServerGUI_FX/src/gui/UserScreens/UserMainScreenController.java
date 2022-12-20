package gui.UserScreens;

import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserMainScreenController implements Initializable {

    @FXML
    private Button LogOutButton;

    @FXML
    private Button grabOrderButton;

    @FXML
    private Button newOrderButton;

    @FXML
    private Text userStatusText;

    @FXML
    private Text welcomeBackText;

    double xoffset;
    double yoffset;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeBackText.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
        userStatusText.setText("User Status: " + UserController.getCurrentuser().getStatus());
    }

    @FXML
    public void logOut(MouseEvent event) {
        ArrayList<String> credentials = new ArrayList<String>();
        credentials.add(UserController.getCurrentuser().getUsername());
        ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGOUT));
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        root.setOnMousePressed(event1 -> {
            xoffset = event1.getSceneX();
            yoffset = event1.getSceneY();
        });

        // event handler for when the mouse is pressed AND dragged to move the window
        root.setOnMouseDragged(event1 -> {
            primaryStage.setX(event1.getScreenX()-xoffset);
            primaryStage.setY(event1.getScreenY()-yoffset);
        });
        primaryStage.setTitle("Client Editor");

        primaryStage.setScene(scene);

        primaryStage.show();
    }
    
    @FXML
    void NewOrder(MouseEvent event) {

        Parent root = null;
        try {
        	
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ProductCatalogScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        root.setOnMousePressed(event1 -> {
            xoffset = event1.getSceneX();
            yoffset = event1.getSceneY();
        });

        // event handler for when the mouse is pressed AND dragged to move the window
        root.setOnMouseDragged(event1 -> {
            primaryStage.setX(event1.getScreenX()-xoffset);
            primaryStage.setY(event1.getScreenY()-yoffset);
        });
        primaryStage.setTitle("New Order");

        primaryStage.setScene(scene);
        
        primaryStage.setResizable(false);

        primaryStage.show();
    }

}
