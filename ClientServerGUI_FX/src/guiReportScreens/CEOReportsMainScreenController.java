package guiReportScreens;
import java.time.Year;
import java.net.URL;
import java.util.ResourceBundle;

import application.client.ClientUI;
import application.user.UserController;
import gui.ClientConnectorController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class CEOReportsMainScreenController extends Application implements Initializable{

	@FXML
    private ComboBox<String> MachineID;

    @FXML
    private ComboBox<String> Month;

    @FXML
    private ComboBox<String> Type;

    @FXML
    private ComboBox<String> YearComboBox;

    @FXML
    private Button backButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button showReportButton;

    @FXML
    private Label welcomeReportsLabel;
    
    @FXML
    private Label errorLabel;
    
    /**
	 * exit application
	 * @param event
	 * @throws Exception
	 */
    @FXML
    void exitApp(ActionEvent event) throws Exception {
    	System.out.println("exit EkrutClient");
		System.exit(0);	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	//welcomeReportsLabel.setText("Welcome Back, " + UserController.getCurrentuser().getFirstname());
    	
    	// Fill years and months in combo boxes
    	Year y = Year.now();
		for (int i = y.getValue(); i > 2010; i--)
		{
			YearComboBox.getItems().add("" + i);
		}
		
		
		for (int i = 1; i < 13; i++) {
			if (i < 10)
				Month.getItems().add("0" + i);
			else
				Month.getItems().add("" + i);
		}
		
		// Add monthly reports type
		Type.getItems().add("Orders");
		Type.getItems().add("Inventory");
		Type.getItems().add("Activity");
		
		//ClientUI.chat.accept("GetMachineIDs"); // command to server to get all ids
		
//		MachineID.getSelectionModel().clearSelection();
//		MachineID.getItems().clear();
		
		
		
	}
	
	
	// LOAD CEO MAIN SCREEN ON BACK BUTTON
	// WHEN TAREK FINISH CEO SCREEN, COMBINE IT HERE WITH HIDE/SHOW
	@FXML
    void clickBackButton(ActionEvent event) {
	
    }
	
	
	@FXML
    void clickShowReport(ActionEvent event) {
		String reportType;
		
		// Check first for valid inputs
		if (Month.getValue() == null) {
			errorLabel.setText("You didn't choose Month!");
			return;
		}
		if (YearComboBox.getValue() == null) {
			errorLabel.setText("You didn't choose Year!");
			return;
		}
		if (Type.getValue() == null) {
			errorLabel.setText("You didn't choose Report Type!");
			return;
		}
		if (MachineID.getValue() == null) {
			errorLabel.setText("You didn't choose Machine ID!");
			return;
		}
		
		reportType = Type.getValue();
		
    }
	
	 public static void main(String[] args) {
	        launch(args);
	    }

	@Override
	public void start(Stage arg0) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ReportsMainScreen.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);

		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}

}
