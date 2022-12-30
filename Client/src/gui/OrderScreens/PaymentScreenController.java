package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import application.client.ChatClient;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PaymentScreenController extends ScreenController implements Initializable{
	
    @FXML
    private Text Subtotal;
	
    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Button paybotton;
    
    @FXML
    private Button infoCvv;
    
    @FXML
    private ComboBox<String> monthCombobox;
    
    @FXML
    private ComboBox<String> yearCombobox;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Subtotal.setText(String.valueOf(ChatClient.currentOrder.getOverallPrice()) + "\u20AA");
		
		monthCombobox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
		yearCombobox.getItems().addAll("2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033");
		
        // Create a tooltip to display the cvv helper
        Tooltip tooltip = new Tooltip();
        ImageView cvvimage = new ImageView("/gui/OrderScreens/cvvHelper.png");
        tooltip.setGraphic(cvvimage);
        tooltip.setShowDelay(null);
        infoCvv.setTooltip(tooltip);
        infoCvv.setStyle("-fx-background-color: transparent;");
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
		
		//String cardYear = yearCombobox.getValue();
		//String cardMonth = monthCombobox.getValue();
		
		
        String  uuid = UUID.randomUUID().toString().substring(0, 8);
        ChatClient.currentOrder.setOrderID(uuid);
		
		Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("PostPaymentScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);
    }
	
}
