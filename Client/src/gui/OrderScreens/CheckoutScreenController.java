package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.client.ChatClient;
import application.user.CustomerController;
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

/**
 * This class represents the controller for the CheckoutScreen FXML file.
 * It provides functionality for the CheckoutScreen, including calculating the
 * total price of the products in the shopping cart and allowing the user to 
 * go back to the product catalog or exit the application.
 */
public class CheckoutScreenController extends ScreenController implements Initializable{

    // Variable to store the total price of the products in the shopping cart
    private float totalprice = 0;
    
    // FXML variables for the UI elements in the CheckoutScreen
    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Text totalPrice;
    
    @FXML
    private Text totalPriceDiss;
    
    @FXML
    private ListView<Object> myOrder = new ListView<Object>();
    
    /**
     * Initializes the CheckoutScreen when it is opened.
     * Sets the items in the shopping cart ListView to the items in the remembered cart.
     * Calculates the total price of the items in the shopping cart.
     * Adds a listener to the onMouseClicked event of each Spinner in the ListView items
     * to update the total amount when the spinner is clicked.
     * Adds a listener to the items property of the myOrder ListView to update the 
     * total amount when an item is removed or the quantity is changed.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
      // Set the items in the shopping cart ListView to the items in the remembered cart
      myOrder.setItems(ChatClient.rememberMyCart.getItems());
      // Calculate the total price of the items in the shopping cart
      totalAmount();

      // Add a listener to the onMouseClicked event of each Spinner in the ListView items 
      // to update the total amount when the spinner is clicked
      for (Object item : myOrder.getItems()) {
          Spinner spinner = ((Spinner<Integer>)(((HBox) item).getChildren().get(4)));
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
    
    /**
     * Closes the application when the exit button is clicked.
     * 
     * @param event the mouse event that triggered the method call
     */
    @FXML
    void exit(MouseEvent event) {
        // Call the closeProgram method from the ScreenController class to close the application
		super.closeProgram(event, true);
    }
	
    /**
     * Goes back to the ProductCatalogScreen when the back button is clicked.
     * Updates the remembered cart with the items in the shopping cart.
     * Clears the cart list.
     * 
     * @param event the mouse event that triggered the method call
     */
    @FXML
    void goBack(MouseEvent event) {
        // Update the remembered cart with the items in the shopping cart
    	ChatClient.rememberMyCart.setItems(myOrder.getItems());
    	// Clear the cart list
    	ChatClient.cartList = new ArrayList<Product>();
        Parent root = null;
        try {
            // Load the ProductCatalogScreen FXML file
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ProductCatalogScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Call the switchScreen method from the ScreenController class to switch to the ProductCatalogScreen
        super.switchScreen(event, root);        
    }
    
    /**
     * Calculates the total price of the items in the shopping cart.
     * If the user is a subscriber and this is their first time buying as a subscriber,
     * applies the first time subscriber discount and displays the discounted price.
     * If the user is a subscriber but this is not their first time buying as a subscriber,
     * applies the regular subscriber discount and displays the discounted price.
     * If the user is not a subscriber, no discount is applied.
     */
    void totalAmount() {
        // Reset the total price to 0
    	totalprice=0;
    	// Iterate through each item in the shopping cart
    	for (Object item : myOrder.getItems()) {
    		// Get the total price for the current item as a String
    		 String productTotalPrice = ((Text)(((HBox) item).getChildren().get(3))).getText();
    		 // Remove the shekel symbol from the total price String
    		 productTotalPrice = productTotalPrice.replace('\u20AA', '\0');
    		 // Add the total price for the current item to the overall total price
    		 totalprice += Float.parseFloat(productTotalPrice);
    	}
    	// Set the total price Text to the calculated total price, formatted to 2 decimal places and with the shekel symbol
    	totalPrice.setText(String.format("%.2f",(totalprice)) + "\u20AA");
    	
    	// Check if the user is a subscriber and whether this is their first time buying as a subscriber
    	if(CustomerController.getisSub()) {
    		if(CustomerController.getisFirstTimeBuyasSub()) {
    			// If this is the user's first time buying as a subscriber, apply the first time discount
    			// Strike through the regular price and make the discounted price visible
        		totalPrice.setStrikethrough(true);
        		totalPriceDiss.setVisible(true);
        		totalPriceDiss.setText(String.format("%.2f",(totalprice*0.8)) + "\u20AA");
        		String TotalPriceDiss = totalPriceDiss.getText();
        		TotalPriceDiss = TotalPriceDiss.replace('\u20AA', '\0');
       		    totalprice = Float.parseFloat(TotalPriceDiss);
       		    totalPriceDiss.setText(String.format("%.2f",(totalprice)) + "\u20AA" + " FIRST BUY \nSUBSCRIBER DISCOUNT!");
    		}
    	}
    }
    
    /**
     * Proceeds to the PaymentScreen when the Proceed button is clicked.
     * If the shopping cart is empty, displays an error message and does not proceed.
     * Otherwise, sets the overall price and products for the current order and switches to the PaymentScreen.
     * 
     * @param event the mouse event that triggered this method call
     */
    @FXML
    void proceed(MouseEvent event) {
        // If the shopping cart is empty, display an error message and return
		if (myOrder.getItems().size() == 0) {
			super.alertHandler("Please add some products to your cart before Proceed" , true);
            return;
		}
        // Set the overall price and products for the current order
        ChatClient.currentOrder.setOverallPrice(totalprice);
        ChatClient.currentOrder.setProducts(ChatClient.cartList);
        // Load the PaymentScreen and switch to it
		Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("PaymentScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);   
	}
}
