package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import common.orders.Order;
import common.orders.Product;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PostPaymentController extends ScreenController implements Initializable{

    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Text orderNum;
    
    @FXML
    private Text machineNum;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		orderNum.setText(ChatClient.currentOrder.getOrderID());
		machineNum.setText(ChatClient.currentOrder.getMachineID());
		
		// reset the currentOrder for the next order
		ChatClient.currentOrder = new Order();
		
	}

	@FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
	
	@FXML
    void goBack(MouseEvent event) {
		ChatClient.cartList = new ArrayList<Product>();
		ChatClient.rememberMyCart = new ListView<Object>();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/CustomerMainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);        
    }
}
