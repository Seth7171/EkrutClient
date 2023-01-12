package gui.UserManagementScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import common.connectivity.Customer;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lior Jigalo
 */
public class AddUserScreenController extends ScreenController{
    @FXML
    private TextField creditCardField;

    @FXML
    private TextField emailAddressField;

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

    @FXML
    void addUser(MouseEvent event) {
        // check for empty fields
        if (userNameField.getText().equals("") || passwordField.getText().equals("") || firstNameField.getText().equals("")
         || lastNameField.getText().equals("") || idField.getText().equals("") || phoneNumberField.getText().equals("")
         || emailAddressField.getText().equals("") || creditCardField.getText().equals("")){
            super.alertHandler("All fields MUST be filled.", true);
            return;
        }

        // check for valid email address
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matcher = pattern.matcher(emailAddressField.getText());
        if (!matcher.matches()){
            super.alertHandler("invalid email address format", true);
            return;
        }
        if (emailAddressField.getText().contains(" ")){
            super.alertHandler("email address MUST NOT contain spaces", true);
            return;
        }

        // check valid id number
        pattern = Pattern.compile("^[0-9]+$");
        matcher = pattern.matcher(idField.getText());
        if (!matcher.matches()){
            super.alertHandler("ID MUST NOT contain letters", true);
            return;
        }
        if (idField.getText().length() < 9){
            super.alertHandler("ID too short", true);
            return;
        }
        if (idField.getText().length() > 9){
            super.alertHandler("ID too long", true);
            return;
        }

        matcher = pattern.matcher(creditCardField.getText());
        if (!matcher.matches()){
            super.alertHandler("Credit card MUST NOT contain letters", true);
            return;
        }
        if (creditCardField.getText().length() < 16){
            super.alertHandler("Credit card too short", true);
            return;
        }
        if (creditCardField.getText().length() > 16){
            super.alertHandler("Credit card too long", true);
            return;
        }

        // check phone number
        matcher = pattern.matcher(phoneNumberField.getText());
        if (!matcher.matches()){
            super.alertHandler("phone number MUST only contain numbers", true);
            return;
        }

        // create user to add
        Customer user = new Customer();
        user.setUsername(userNameField.getText());
        user.setPassword(passwordField.getText());
        user.setFirstname(firstNameField.getText());
        user.setLastname(lastNameField.getText());
        user.setId(idField.getText());
        user.setPhonenumber(phoneNumberField.getText());
        user.setEmailaddress(emailAddressField.getText());
        user.setCreditCardNumber(creditCardField.getText());

        ClientUI.chat.accept(new Message(user, MessageFromClient.REQUEST_ADD_USER));
        super.alertHandler(MessageHandler.getMessage(), MessageHandler.getMessage().contains("exists"));
    }


    @FXML
    protected void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }

    @FXML
    protected void goBack(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceEmployeeScreens/CustomerServiceEmployeeScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //switchScreen(event, root);
        super.switchScreen(event, root);
    }
}
