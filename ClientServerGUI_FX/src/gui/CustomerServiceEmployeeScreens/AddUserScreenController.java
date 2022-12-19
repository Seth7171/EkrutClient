package gui.CustomerServiceEmployeeScreens;

import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddUserScreenController implements Initializable {

    @FXML
    private ChoiceBox<String> departmentField;

    @FXML
    private TextField emailAddressField;

    @FXML
    private Text errorMessage;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField userNameField;

    private double xoffset;
    private double yoffset;
    @FXML
    void addUser(MouseEvent event) {
        if (userNameField.getText().equals("") || passwordField.getText().equals("") || firstNameField.getText().equals("")
         || lastNameField.getText().equals("") || idField.getText().equals("") || phoneNumberField.getText().equals("")
         || emailAddressField.getText().equals("") || departmentField.getValue().equals("")){
            this.errorMessage.setText("All fields MUST be filled.");
            return;
        }

    }

    @FXML
    protected void exit(MouseEvent event) {
        ArrayList<String> cred = new ArrayList<String>();
        cred.add(UserController.getCurrentuser().getUsername());
        ClientUI.chat.accept("disconnect");
        ClientUI.chat.accept(new Message(cred, MessageFromClient.REQUEST_LOGOUT));
        Platform.exit();
        System.exit(0);
    }

    @FXML
    protected void goBack(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("CustomerServiceEmployeeScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        switchScreen(event, root);
    }


    private void switchScreen(MouseEvent event, Parent root){
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // departmentField
    }
}
