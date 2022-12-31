package gui.MarketingManagementScreens;

import java.net.URL;
import java.util.ResourceBundle;

import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MarketingEmployeeScreenController extends ScreenController implements Initializable { 

    @FXML
    private Button existingDeals;

    @FXML
    private Button exitButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Text welcomeText;

    @FXML
    void existingDeals(MouseEvent event) {

    }

    @FXML
    void exitApp(MouseEvent event) {
    	super.closeProgram(event, true);
    }

    @FXML
    void logOut(MouseEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
