package gui.ProductControlScreens;

import application.user.UserController;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RefillOrderScreenController extends ScreenController implements Initializable {

    @FXML
    private Text chooseProductIDTXT;

    @FXML
    private ChoiceBox<?> chooseProductIDtoRefillChoiceBox;

    @FXML
    private Button exitButton;

    @FXML
    private Button goBackButton;

    @FXML
    private ChoiceBox<?> machineToRefillChoiceBox;

    @FXML
    private Text newAmountTXT;

    @FXML
    private TextField newAmountTextField;

    @FXML
    private Button updateButton;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void backToMainScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
        }catch (IOException exception){
            exception.printStackTrace();
        }
        super.switchScreen(event, root);
    }


    @FXML
    void updateAmountInDataBase(MouseEvent event) {

    }


    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }
}
