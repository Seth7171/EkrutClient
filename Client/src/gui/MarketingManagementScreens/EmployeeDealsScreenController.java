package gui.MarketingManagementScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class EmployeeDealsScreenController extends ScreenController implements Initializable {

    @FXML
    private TableColumn<String, String> TypeColumn;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<String, String> dealNameColumn;

    @FXML
    private TableColumn<String, String> descriptionColumn;

    @FXML
    private TableColumn<String, String> discountColumn;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<String, String> statusColumn;

    @FXML
    private TableView<String> viewAllDeals;

    @FXML
    void exit(MouseEvent event) {
    	super.closeProgram(event, true);
    }

    @FXML
    void goBackToMarketingEmployeeScreen(MouseEvent event) {
    	Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MarketingEmployeeScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
