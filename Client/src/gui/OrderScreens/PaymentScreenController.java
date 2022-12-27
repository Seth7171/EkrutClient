package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class PaymentScreenController extends ScreenController implements Initializable{

    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Button paybotton;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	@FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
	
    @FXML
    void goBack(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("CheckoutScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);        
    }
    
	@FXML
    void pay(MouseEvent event) {
		
		
		
		
		
		
		Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("PostPaymentScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);
    }
}
