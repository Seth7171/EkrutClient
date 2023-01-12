package gui.OrderScreens;

// Import necessary classes for the program
import application.client.ChatClient;
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.CustomerController;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Order;
import common.orders.Product;
import gui.ScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

// Class to control the product catalog screen
public class ProductCatalogScreenController extends ScreenController implements Initializable{
    // Declare variables to keep track of the number of items in the cart and the total price
    private int counter = 0;
    private float totalprice = 0;
    
    // Declare FXML variables
    @FXML
    private Pane productsPane;

    private Text cartCounter;
//    @FXML
//    private Text cartCounter;
    
    @FXML
    private Text totalAmount;
    
    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;

    @FXML
    private Button emptyMyCartButton;

    @FXML
    private Button checkOutButton;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private ListView<Object> myCart = new ListView<Object>();
    
    @FXML
    private TilePane snacksPane;

    @FXML
    private TilePane drinksPane;
    
    @FXML
    private ScrollPane snacksScroll;
    
    @FXML
    private ScrollPane drinksScroll;

    @FXML
    private Tab drinkTab;

    @FXML
    private Tab snackTab;


    @FXML
    private Pane backmycart;

    /**
     * Initializes the screen by setting the focus traversable property of the list view to false, 
     * setting the items in the list view to the items in the user's cart, 
     * setting the total amount text to 0, 
     * requesting the list of products from the warehouse, 
     * and adding the products to the UI.
     * @param location the location of the root object
     * @param resources the resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the focus traversable property of the list view to false
        myCart.setFocusTraversable( false );
        
        // Set the items in the list view to the items in the user's cart
        myCart.setItems(ChatClient.rememberMyCart.getItems());
        
        // Set the total amount text to 0
        totalAmount.setText("0.0\u20AA");
        
        // Lower product gradient cheats:
        cartCounter = new Text("0");
        cartCounter.setLayoutX(184);
        cartCounter.setLayoutY(636);
        cartCounter.setFill(Color.WHITE);
        cartCounter.setStyle("-fx-font-size: 12");
        
    	if (CustomerController.isLogged()) {
    		ArrayList<String> machine = new ArrayList<String>();
    		if (ChatClient.currentOrder.getSupplyMethod().equals("delivery")){
    			machine.add(null);
        		machine.add("0");
        		ClientUI.chat.accept(new Message(machine, MessageFromClient.REQUEST_WAREHOUSE_PRODUCTS));
    		}
    		else {
    			machine.add(CustomerController.getmachineID());
        		machine.add("0");
        		ClientUI.chat.accept(new Message(machine, MessageFromClient.REQUEST_MACHINE_PRODUCTS));
    		}
    	}
        // Request the list of products from the warehouse
    	else
    		ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_WAREHOUSE_PRODUCTS));
        tabPane.getStyleClass().add("tab-pane");
        tabPane.setTabMinWidth(220);
        tabPane.setTabMaxWidth(220);
        // Iterate through the list of products received from the warehouse
        for (Product product : (ArrayList<Product>)MessageHandler.getData()) {
            // Add products of type "SNACK" to the snacks pane
            if(product.getType().equals("SNACK"))
                snacksPane.getChildren().add(createProductTile(product));
            // Add products of other types to the drinks pane
            else
                drinksPane.getChildren().add(createProductTile(product));
        }
        snacksPane.setHgap(25);
        snacksPane.setVgap(15);
        drinksPane.setHgap(25);
        drinksPane.setVgap(15);

        // Enable horizontal scrolling for the snacks and drinks panes
        snacksScroll.setFitToWidth(true);
        drinksScroll.setFitToWidth(true);

        ImageView viewportGradient = new ImageView("/gui/OrderScreens/viewport-gradient.png");
        viewportGradient.setLayoutX(0);
        viewportGradient.setLayoutY(616);

        ImageView cartOverlay = new ImageView("/gui/OrderScreens/cart-overlay.png");
        cartOverlay.setLayoutX(47);
        cartOverlay.setLayoutY(617);

        productsPane.getChildren().add(viewportGradient);
        productsPane.getChildren().add(cartOverlay);
        productsPane.getChildren().add(cartCounter);
    }

    @FXML
    void exit(MouseEvent event) {
        // Close the program
        super.closeProgram(event, true);
    }

    /**
     * Creates a tile for a product that displays the product image, name, ID, price,
     * and options to add the product to the cart or view its details.
     *
     * @param product the product to create a tile for
     * @return a Node representing the product tile
     */
    private Node createProductTile(Product product) {
        // Create a horizontal box and a vertical box
        HBox hBox = new HBox();
        VBox vBox = new VBox();     

        // Create an input stream from the product image data
        InputStream inputStream = new ByteArrayInputStream(product.getFile());
        Image image = null;
        image = new Image(inputStream);

        // Create an image view to display the product image
        ImageView imageview = new ImageView();
        imageview.setFitHeight(100.0);
        imageview.setFitWidth(100.0);

        // Create a button to add the product to the cart
        Button addBtn = new Button("Add to cart");
        ImageView addtocarticon = new ImageView(getClass().getResource("/gui/OrderScreens/agalaadd.png").toExternalForm());
        addtocarticon.setFitHeight(35.0);
        addtocarticon.setFitWidth(35.0);
        addBtn.setGraphic(addtocarticon);
        addBtn.getStyleClass().add("btn");

        // Create a button to display the product details
        Button detBtn = new Button();
        ImageView deticon = new ImageView(getClass().getResource("/gui/OrderScreens/details.png").toExternalForm());
        deticon.setFitHeight(20.0);
        deticon.setFitWidth(20.0);
        detBtn.setGraphic(deticon);

        // Create a tooltip to display the product description
        Tooltip tooltip = new Tooltip(product.getDescription());
        tooltip.setShowDelay(null);
        detBtn.setTooltip(tooltip);
        detBtn.setStyle("-fx-background-color: transparent;");

        // Create a label to display the product name
        Label nameLabel = new Label(product.getProductName()); //CHANGED FROM getName TO getProductName
        nameLabel.getStyleClass().add("name-label");
        nameLabel.setWrapText(true);
        nameLabel.setPrefWidth(150);
        nameLabel.setAlignment(Pos.CENTER_RIGHT);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        nameLabel.setUnderline(true);

        // Create a label to display the product ID
        Label idLable = new Label("ID: " + product.getProductId());
        idLable.getStyleClass().add("id-label");
        idLable.setWrapText(true);
        idLable.setPrefWidth(50);

        // Create a spinner to select the quantity of the product to add to the cart
        Spinner<Integer> SpinnerQuantity = new Spinner<>(0,product.getAmount(),0);
        

        // Check if the product is already in the cart
        HBox hb = (HBox)(findHBoxOfproductID(product.getProductId()));
        if (hb != null) {
            // If the product is in the cart, set the spinner value to the current quantity
            SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) SpinnerQuantity.getValueFactory();
            int quant = ((Spinner<Integer>)hb.getChildren().get(4)).getValue();
            myCart.getItems().remove(hb);
            SpinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(quant, 
                    valueFactory.getMax(), 0));
            addToCart(product,SpinnerQuantity);
            // Reset the spinner value to 0
            SpinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (valueFactory.getMax()-quant), 0));
        }
        SpinnerQuantity.getStyleClass().add("combo-color");
        Label newPrice = new Label();
        Text priceLabel = new Text("Price: " + product.getPrice());
        priceLabel.getStyleClass().add("price-label");
        SpinnerQuantity.setMaxWidth(75);
        imageview.setImage(image);
        if(product.getDiscount()!= 0 && CustomerController.getisSub()) {
            priceLabel.setStrikethrough(true);
            // Calculate the discounted price of the product
            float dis = product.getPrice()*(1-product.getDiscount());
            String present = String.format("%.0f%%OFF - %.2f",product.getDiscount()*100, dis);
            newPrice.setText(present + "\u20AA");
            newPrice.getStyleClass().add("new-price-label");
            newPrice.setTextFill(Color.RED);
            newPrice.setPrefWidth(150);
            newPrice.setWrapText(true);
            newPrice.setAlignment(Pos.CENTER_RIGHT);
        }
        StackPane imagecontainer = new StackPane();
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(detBtn, idLable);
        hBox1.setAlignment(Pos.CENTER_RIGHT);
        vBox.getChildren().addAll(nameLabel, hBox1, priceLabel, newPrice, SpinnerQuantity, addBtn);
        hBox.getChildren().addAll(imagecontainer, vBox);
        vBox.setSpacing(15);
        // Set the ID of the vertical box to the product ID
        vBox.setId(String.valueOf(product.getProductId()));
        imageview.setTranslateY(50);
        
        
        if(product.getAmount() == 0 ) {
        	addBtn.setDisable(true);
        	SpinnerQuantity.setDisable(true);
            ImageView imagesoldout = new ImageView(getClass().getResource("/gui/OrderScreens/soldout.png").toExternalForm());
            imagesoldout.setFitHeight(100.0);
            imagesoldout.setFitWidth(100.0);
            imagesoldout.setTranslateY(50);
            imagecontainer.getChildren().addAll(imageview, imagesoldout);
        }
        else {
        	imagecontainer.getChildren().add(imageview);
        }
        imagecontainer.setAlignment(Pos.TOP_CENTER);


        hBox.setPadding(new Insets(0, 5, 0, 5));
        vBox.setPadding(new Insets(0, 5, 30, 5));
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER_RIGHT);
        addBtn.setOnAction(event -> {
            // If a non-zero quantity of the product is selected, add it to the cart
            if (SpinnerQuantity.getValue() != 0 ) {
                SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) SpinnerQuantity.getValueFactory();
                addToCart(product,SpinnerQuantity);
                // Reset the spinner value to 0
                SpinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (valueFactory.getMax()-SpinnerQuantity.getValue()), 0));
            	super.alertHandler("Product was added to the cart" , false);
                return;
            }
         // If a zero quantity of the product is selected alert the user
            else {
            	super.alertHandler("Please choose quantity of the product" , true);
                return;
            }
        });
        hBox.setStyle("-fx-border-color: rgba(65,65,65,0.63); -fx-border-width: 2 2 2 2; -fx-border-style: solid; -fx-border-radius: 13; -fx-background-color: rgba(255,255,255,0.84); -fx-background-radius: 13");
        return hBox;
    }
   
    /**
     * Method to add a product to the cart and update the total price of the cart.
     *
     * @param product the product to add to the cart
     * @param spinnerQuantity the quantity spinner for the product
     */
    private void addToCart(Product product, Spinner<Integer> spinnerQuantity) {
        // Get the selected quantity of the product
        int quantity = spinnerQuantity.getValue();
        //represent the product in the cart
    	//Product product = new Product(product.getPrice(), product.getDiscount(), product.getName(), product.getAmount(), 
        		//product.getDescription(), product.getType(), product.getProductId(), product.getFile(), product.getCriticalAmount());
        // Create a new horizontal box to hold the cart items
        HBox hboxofcart = new HBox();
        // Create an input stream from the product's file
        InputStream inputStream = new ByteArrayInputStream(product.getFile());
        // Create a new image from the input stream
        Image image = null;
        image = new Image(inputStream);
        // Create an image view to display the image
        ImageView imageview = new ImageView();
        // Set the image view's dimensions
        imageview.setFitHeight(50.0);
        imageview.setFitWidth(50.0);
        // Set the image for the image view
        imageview.setImage(image);
        // Create a label to display the product's name
        Label namelb = new Label(product.getProductName()); // CHANGED getName to getProductName
        namelb.setWrapText(true);
        namelb.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        namelb.setUnderline(true);
        // Set the width of the name label
        namelb.setPrefWidth(100);
        // Create a label to display the product's ID
        Label idlb = new Label(product.getProductId());
        // Set the width of the ID label
        idlb.setPrefWidth(100);
        idlb.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        // Get the product's price
        float productPrice = product.getPrice();
        // Create a text object to display the total price of the product
        Text productTotalPrice = new Text();
        // Set the width of the total price text
        productTotalPrice.setWrappingWidth(100);
        productTotalPrice.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        // Calculate the total price of the product based on the quantity selected
        float priceofproduct = productPrice*spinnerQuantity.getValue();
        // If the product has a discount, apply the discount to the total price
        if (product.getDiscount()!= 0 && CustomerController.getisSub()) {
    		priceofproduct = productPrice*(1-product.getDiscount())*spinnerQuantity.getValue();
    	}
    	// Set the text of the total price text to the calculated total price
    	productTotalPrice.setText(String.format("%.2f", priceofproduct) + "\u20AA");
    	// Create a new spinner for the quantity of the product
    	Spinner<Integer> spinnerQuantitynew = new Spinner<Integer>(0,product.getAmount(),spinnerQuantity.getValue());
    	// Set the maximum width of the quantity spinner
    	spinnerQuantitynew.setMaxWidth(75);
    	// Create a button to remove the product from the cart
    	Button removeProduct = new Button();
        // Create an image view to display an icon for the remove product button
        ImageView addtocarticon = new ImageView(getClass().getResource("/gui/OrderScreens/agalaremove.png").toExternalForm());
        // Set the dimensions of the remove icon
        addtocarticon.setFitHeight(15.0);
        addtocarticon.setFitWidth(15.0);
        // Set the icon as the graphic for the remove product button
        removeProduct.setGraphic(addtocarticon);
        // Add a style class to the remove product button
        removeProduct.getStyleClass().add("btn");
    	spinnerQuantitynew.setMaxWidth(75);
    	HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(removeProduct, spinnerQuantitynew);
        // Add the image view, labels, total price text, remove button, and quantity spinner to the horizontal box
    	hboxofcart.getChildren().addAll(imageview, namelb, idlb, productTotalPrice, spinnerQuantitynew, removeProduct);
    	hboxofcart.setSpacing(77);
    	// Set the vertical position of the image view
    	imageview.setTranslateY(0);
    	// Find the horizontal box that corresponds to the product ID
    	HBox hb = (HBox)(findHBoxOfproductID(idlb.getText()));
    	// If the horizontal box is found, update the quantity and total price of the product in the cart
    	if (hb != null) {
        	// Update the quantity of the product in the cart
        	ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).setAmount(
        			ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount()+quantity);
    		// Increment the quantity spinner by the selected quantity
    		((Spinner<Integer>)hb.getChildren().get(4)).increment(quantity);
    		// If the product has a discount, update the total price text to reflect the discounted price
    		if (product.getDiscount()!= 0 && CustomerController.getisSub()) { 
    			((Text)hb.getChildren().get(3)).setText(
    					String.format("%.2f",(product.getPrice()*(1-product.getDiscount())*
    							ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount())) + "\u20AA");
	    	}
    		// If the product does not have a discount, update the total price text to the regular price
    		else {
    			((Text)hb.getChildren().get(3)).setText(
    					String.format("%.2f",(product.getPrice()*
    							ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount())) + "\u20AA");
    		}
    		// Call the totalAmount method to update the total price of all items in the cart
    		totalAmount();
    	}
    	// If the horizontal box is not found, add the product to the cart
    	else {
    		// Add the product to the list of products in the cart
    		ChatClient.cartList.add(product);
    		// Set the quantity of the product in the cart
    		ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).setAmount(quantity);
	    	// Add the horizontal box to the list view of the cart
	    	myCart.getItems().addAll(hboxofcart);
	    	// Increment the counter for the number of items in the cart
	    	counter++;
	    	// Set the text of the cart counter label to the new value of the counter
	    	cartCounter.setText(String.valueOf(counter));
	    	// Call the totalAmount method to update the total price of all items in the cart
	    	totalAmount();
    	}
    	/**
    	 * An event handler that removes a product from the shopping cart and updates the cart counter.
    	 * The event is triggered when the "Remove" button is clicked.
    	 * 
    	 * @param event the action event that occurred
    	 */
    	removeProduct.setOnAction(event -> {
    	    // Find the HBox that contains the product details
    	    HBox hb1 = (HBox)(findHBoxOfproductID(idlb.getText()));
    	    // Remove the product from the cartList
    	    ChatClient.cartList.remove(product);
    	    // Remove the HBox from the ListView
    	    myCart.getItems().remove(hb1);
    	    // Decrement the counter
    	    counter--;
    	    // Update the cart counter label
    	    cartCounter.setText(String.valueOf(counter));
    	    // Reset the quantity spinner to 0
    	    SpinnerValueFactory<Integer> valueFactory = spinnerQuantity.getValueFactory();
    	    ((IntegerSpinnerValueFactory) valueFactory).setMax(((IntegerSpinnerValueFactory) valueFactory).getMax()+ product.getAmount());
    	    product.setAmount(((IntegerSpinnerValueFactory) valueFactory).getMax());
    	    // Update the total amount
    	    totalAmount();
    	});

    	/**
    	 * An event handler that updates the quantity of a product in the shopping cart and updates the total amount.
    	 * The event is triggered when the quantity spinner is released.
    	 * 
    	 * @param event the mouse event that occurred
    	 */
    	spinnerQuantitynew.setOnMouseReleased(event -> {
    	    // Remove the product from the cartList
    	    ChatClient.cartList.remove(product);
    	    // Add the updated product to the cartList
    	    ChatClient.cartList.add(product);
    	    // Update the quantity of the product in the cartList
    	    ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).setAmount(spinnerQuantitynew.getValue());
    	    // Update the price label with the new total price
    	    if (product.getDiscount()!= 0 && CustomerController.getisSub()) {
    	        // If the product has a discount, apply it to the total price
    	        ((Text) ((HBox) spinnerQuantitynew.getParent()).getChildren().get(3)).setText(
    	        		String.format("%.2f",(product.getPrice()*(1-product.getDiscount())
    	        				*ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount())) + "\u20AA");
    	    } else {
    	        // If the product doesn't have a discount, just multiply the price by the quantity
    	        ((Text) ((HBox) spinnerQuantitynew.getParent()).getChildren().get(3)).setText(
    	        		String.format("%.2f",(product.getPrice()*
    	        				(ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount()))) + "\u20AA");
    	    }
    	    // Get the maximum value of the quantity spinner
    	    SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerQuantitynew.getValueFactory();
    	    // Update the quantity spinner for the other product
    	    spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (valueFactory.getMax()-spinnerQuantitynew.getValue()), 0));
    	    // If the quantity of the product is set to 0, remove it from the cart
    	    if (spinnerQuantitynew.getValue() == 0) {
    	        // Find the HBox that contains the product details
    	        HBox hb2 = (HBox)(findHBoxOfproductID(idlb.getText()));
    	     // Get the remove button from the horizontal box
				Button removebuttn = new Button();
				removebuttn = (Button)(((HBox)hb2).getChildren().get(5));
				// Click the remove button to remove the item from the cart
				removebuttn.fire();											//@NITMA changed 11/01
    	        /*// Remove the product from the cartList
    	        ChatClient.cartList.remove(product);
    	        // Remove the HBox from the ListView
    	        myCart.getItems().remove(hb2);
    	        // Decrement the counter
    	        counter--;
    	        // Update the cart counter label
    	        cartCounter.setText(String.valueOf(counter));*/
    	    }
    	    // Update the total amount
    	    totalAmount();
    	});
	    System.out.println(ChatClient.cartList);
    }
    		
    /**
     * Method to find the horizontal box in the cart that corresponds to a given product ID.
     *
     * @param nameLabel the product ID to search for
     * @return the horizontal box that corresponds to the given product ID, or null if no matching horizontal box is found
     */
    private Object findHBoxOfproductID(String nameLabel) {
    	// Iterate through the items in the cart
    	for (Object hb : myCart.getItems()) {
    		// If the item is a horizontal box
			if (hb instanceof HBox){
	    		// Get the product ID label from the horizontal box
	    		Label label = (Label)(((HBox)hb).getChildren().get(2));
	    		// If the product ID label matches the given product ID
	    		if (label.getText().equals(nameLabel)) {
	    			// Return the horizontal box
	    			return hb;
	    		}
	    	}
    	}
    	// Return null if no matching horizontal box is found
    	return null; 
    }
    
    /**
     * Method to calculate and update the total price of all items in the cart.
     */
    void totalAmount() {
    	// Initialize the total price to 0
    	totalprice = 0;
    	// String to store the price value of an item
    	String pricevalue;
    	// Get the number of items in the cart
		int numItems = myCart.getItems().size();
		// Iterate through the items in the cart
		for (;numItems>0;numItems--) {
			// Get the current item
			Object hb = myCart.getItems().get(numItems-1);
			// If the item is a horizontal box
			if (hb instanceof HBox){
				// Get the total price text from the horizontal box
				Text price = (Text)(((HBox)hb).getChildren().get(3));
				// Get the price value from the total price text
				pricevalue = price.getText();
				// Split the price value and currency symbol and get the price value
				pricevalue = pricevalue.split("\u20AA")[0];
				// Add the price value to the total price
				totalprice += Float.parseFloat(pricevalue);
	    	}
    	}
    	// Set the text of the total amount label to the calculated total price
		totalAmount.setText(String.format("%.2f",(totalprice)) + "\u20AA");
    }
    
    /**
     * Method to empty the cart by clicking the remove button of each item in the cart.
     *
     * @param event the mouse event that triggered the method call
     */
    @FXML
    void emptyMyCart(MouseEvent event) {
    	// Get the number of items in the cart
		int numItems = myCart.getItems().size();
		// Iterate through the items in the cart
		for (;numItems>0;numItems--) {
			// Get the current item
			Object hb = myCart.getItems().get(0);
			// If the item is a horizontal box
			if (hb instanceof HBox){
				// Get the remove button from the horizontal box
				Button removebuttn = new Button();
				removebuttn = (Button)(((HBox)hb).getChildren().get(5));
				// Click the remove button to remove the item from the cart
				removebuttn.fire();
	    	}
    	}
    }


    /**
     * Event handler method for the Go Back button.
     *
     * @param event the mouse event that triggered the method call
     */
    @FXML
    void goBack(MouseEvent event) {
    	ChatClient.delay.stop();
    	// Empty the cart
		emptyMyCart(event);
		// Initialize the root node of the new scene to null
        Parent root = null;
        // Try to load the user main screen scene
        try {
            switch (UserController.getCurrentuser().getDepartment()) {
                case "customer":
                    CustomerController.setCurrentCustomer(UserController.getCurrentuser());
                    root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/CustomerMainScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                case "subscriber":
                	if (ChatClient.isOL) {
                		 root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/DeliveryOprtionsScreen.fxml"));;
                		}
                		else {
                			root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/CustomerMainScreen.fxml"));
                		}
                    super.switchScreen(event, root);
                    break;

                case "customer_service":
                    root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceEmployeeScreens/CustomerServiceEmployeeScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                case"ceo":
                    root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                case "operations":
                    root = FXMLLoader.load(getClass().getResource("/gui/OperationsEmployeeScreens/operationsEmployeeMainScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                default:
                    System.out.println("Unknown!");
                    // TODO: maybe add UnknownScreenException later??
            }
        } 
        // Catch any exceptions that may occur
        catch (IOException e) {
            e.printStackTrace();
        }
    }

	
    /**
     * Event handler method for the Check Out button.
     *
     * @param event the mouse event that triggered the method call
     */
    @FXML
    void checkOut(MouseEvent event) {
        // Check if the cart is empty
        if (myCart.getItems().size() == 0) {
        	super.alertHandler("Please add some products to your cart before CheckOut" , true);
            return;
        }
        // Set the items in the rememberMyCart to the items in the current cart
        ChatClient.rememberMyCart.setItems(myCart.getItems());
        // Print the current cart list
        System.out.println(ChatClient.cartList);
        // Create a new order with the specified parameters
        ChatClient.currentOrder.setCustomerID(UserController.getCurrentuser().getId());
        ChatClient.currentOrder.setOverallPrice(totalprice);
        ChatClient.currentOrder.setProducts(ChatClient.cartList);
        Parent root = null;
        try {
            // Load the CheckoutScreen FXML file
            root = FXMLLoader.load(getClass().getResource("CheckoutScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Switch to the CheckoutScreen
        super.switchScreen(event, root);        
    }

}