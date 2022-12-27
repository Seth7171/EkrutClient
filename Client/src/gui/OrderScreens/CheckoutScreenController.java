package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.client.ChatClient;
import gui.ScreenController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CheckoutScreenController extends ScreenController implements Initializable{

    float totalprice = 0;
    
    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Text totalPrice;
    
    @FXML
    private ListView<Object> myOrder = new ListView<Object>();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		myOrder.setItems(ChatClient.rememberMyCart.getItems());
		totalAmount();
		myOrder.setOnMouseReleased(event -> {
			totalAmount();
		});
		totalAmount();
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
    
    void totalAmount() {
    	totalprice = ChatClient.currentOrder.getOverallPrice();
		totalPrice.setText(String.valueOf(totalprice) + "\u20AA");
    }
    
}
