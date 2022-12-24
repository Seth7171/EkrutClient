package gui.CustomerServiceEmployeeScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.Departments;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.User;
import gui.ScreenController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lior Jigalo
 */
public class AddUserScreenController extends ScreenController implements Initializable {

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
        errorMessage.setFill(Color.RED);
        // check for empty fields
        if (userNameField.getText().equals("") || passwordField.getText().equals("") || firstNameField.getText().equals("")
         || lastNameField.getText().equals("") || idField.getText().equals("") || phoneNumberField.getText().equals("")
         || emailAddressField.getText().equals("") || departmentField.getValue() == null){
            this.errorMessage.setText("All fields MUST be filled.");
            return;
        }

        // check for valid email address
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matcher = pattern.matcher(emailAddressField.getText());
        if (!matcher.matches()){
            this.errorMessage.setText("invalid email address format");
            return;
        }
        if (emailAddressField.getText().contains(" ")){
            this.errorMessage.setText("email address MUST NOT contain spaces");
            return;
        }

        // check valid id number
        pattern = Pattern.compile("^[0-9]+$");
        matcher = pattern.matcher(idField.getText());
        if (!matcher.matches()){
            this.errorMessage.setText("ID MUST NOT contain letters");
            return;
        }
        if (idField.getText().length() < 9){
            this.errorMessage.setText("ID too short");
            return;
        }
        if (idField.getText().length() > 9){
            this.errorMessage.setText("ID too long");
            return;
        }

        // check phone number
        matcher = pattern.matcher(phoneNumberField.getText());
        if (!matcher.matches()){
            this.errorMessage.setText("phone number MUST only contain numbers");
            return;
        }

        // create user
        User user = new User();
        user.setUsername(userNameField.getText());
        user.setPassword(passwordField.getText());
        user.setFirstname(firstNameField.getText());
        user.setLastname(lastNameField.getText());
        user.setId(idField.getText());
        user.setPhonenumber(phoneNumberField.getText());
        user.setDepartment(departmentField.getValue().replace(" ", "_"));

        ClientUI.chat.accept(new Message(user, MessageFromClient.REQUEST_ADD_USER));

        errorMessage.setText(MessageHandler.getMessage());
        if (MessageHandler.getMessage().contains("successfully")){
            MessageHandler.setMessage(null);
            errorMessage.setFill(Color.GREEN);
        }
    }

    @FXML
    protected void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }

    @FXML
    protected void goBack(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("CustomerServiceEmployeeScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //switchScreen(event, root);
        super.switchScreen(event, root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> departments = new ArrayList<String>();
        for (Departments dep : Departments.values()){
            if (dep.name().contains("_")){
                departments.add(dep.name().replace("_", " "));
                continue;
            }
            departments.add(dep.name());
        }
        departmentField.getItems().setAll(departments);
    }
}
