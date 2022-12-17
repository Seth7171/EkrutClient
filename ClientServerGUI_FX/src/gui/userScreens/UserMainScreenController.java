package gui.userScreens;

import application.user.UserController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeBackText.setText("Welcome Back " + UserController.getCurrentuser().getFirstname());
        userStatusText.setText("User Status: " + UserController.getCurrentuser().getStatus());
    }
}
