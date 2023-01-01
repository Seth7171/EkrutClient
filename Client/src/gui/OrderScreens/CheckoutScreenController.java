package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.client.ChatClient;
import application.user.UserController;
import common.orders.Order;
import common.orders.Product;
import gui.ScreenController;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CheckoutScreenController extends ScreenController implements Initializable{

    private float totalprice = 0;
    
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

      // Add a listener to the onMouseClicked event of each Spinner in the ListView items to update the total amount when the spinner is clicked
      for (Object item : myOrder.getItems()) {
          Spinner spinner = ((Spinner<Integer>)(((HBox) item).getChildren().get(5)));
          spinner.setOnMouseClicked(event -> {
            // update the total amount when the spinner is clicked
            totalAmount();
          });
        }

      // Add a listener to the items property of the myOrder ListView to update the total amount when an item is removed or the quantity is changed
      myOrder.getItems().addListener((ListChangeListener<Object>) change -> {
         while (change.next()) {
            if (change.wasRemoved()) {
               // An item was removed from the list, update the total amount
               totalAmount();
            }
         }
      });
    }

	@FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
	
    @FXML
    void goBack(MouseEvent event) {
    	ChatClient.rememberMyCart.setItems(myOrder.getItems());
    	ChatClient.cartList = new ArrayList<Product>();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ProductCatalogScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);        
    }
    
    void totalAmount() {
    	totalprice=0;
    	for (Object item : myOrder.getItems()) {
    		 String productTotalPrice = ((Text)(((HBox) item).getChildren().get(3))).getText();
    		 productTotalPrice = productTotalPrice.replace('\u20AA', '\0');
    		 totalprice += Float.parseFloat(productTotalPrice);
    	}
		totalPrice.setText(String.valueOf(totalprice) + "\u20AA");
    }
    
	@FXML
    void proceed(MouseEvent event) {
		if (myOrder.getItems().size() == 0) {
			super.alertHandler("Please add some products to your cart before Proceed" , true);
            return;
		}
        ChatClient.currentOrder.setOverallPrice(totalprice);
        ChatClient.currentOrder.setProducts(ChatClient.cartList);
		Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("PaymentScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);   
	}
}
