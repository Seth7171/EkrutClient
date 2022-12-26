package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.client.ChatClient;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class CheckoutScreenController extends ScreenController implements Initializable{

    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private ListView<Object> myOrder = new ListView<Object>();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		myOrder.setItems(ChatClient.rememberMyCart.getItems());
	}

	@FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
	
    @FXML
    void goBack(MouseEvent event) {
    	ChatClient.rememberMyCart.setItems(myOrder.getItems());
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ProductCatalogScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);        
    }
    
}
