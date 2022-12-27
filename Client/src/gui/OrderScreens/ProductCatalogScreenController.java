package gui.OrderScreens;
//

import application.client.ChatClient;
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Order;
import common.orders.Product;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Spinner;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductCatalogScreenController extends ScreenController implements Initializable{
    int counter = 0;
    float totalprice = 0;
    
    @FXML
    private Text cartCounter;
    
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	myCart.setFocusTraversable( false );
    	myCart.setItems(ChatClient.rememberMyCart.getItems());
    	totalAmount.setText("0.0\u20AA");
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_WAREHOUSE_PRODUCTS));
        tabPane.getStyleClass().add("tab-pane");
        tabPane.setTabMinWidth(220);
        tabPane.setTabMaxWidth(220);
        for (Product product : (ArrayList<Product>) MessageHandler.getData()) {
        	if(product.getAmount() != 0 ) {
		       if(product.getType().equals("SNACK"))
		    	   snacksPane.getChildren().add(createProductTile(product));
		       else
		       		drinksPane.getChildren().add(createProductTile(product));
        	}
        }
        snacksScroll.setFitToWidth(true);
        drinksScroll.setFitToWidth(true);
    }
    
    @FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
    
    private Node createProductTile(Product product) {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        InputStream inputStream = new ByteArrayInputStream(product.getFile());
        Image image = null;
        image = new Image(inputStream);
        ImageView imageview = new ImageView();
        imageview.setFitHeight(100.0);
        imageview.setFitWidth(100.0);
        Button addBtn = new Button("Add to cart");
        ImageView addtocarticon = new ImageView(getClass().getResource("/gui/OrderScreens/agalaadd.png").toExternalForm());
        addtocarticon.setFitHeight(35.0);
        addtocarticon.setFitWidth(35.0);
        addBtn.setGraphic(addtocarticon);
        addBtn.getStyleClass().add("btn");
        Button detBtn = new Button();
        ImageView deticon = new ImageView(getClass().getResource("/gui/OrderScreens/details.png").toExternalForm());
        deticon.setFitHeight(20.0);
        deticon.setFitWidth(20.0);
        detBtn.setGraphic(deticon);
        Tooltip tooltip = new Tooltip(product.getDescription());
        tooltip.setShowDelay(null);
        detBtn.setTooltip(tooltip);
        detBtn.setStyle("-fx-background-color: transparent;");
        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("name-label");
        nameLabel.setWrapText(true);
        nameLabel.setPrefWidth(150);
        Label idLable = new Label("ID: " + product.getProductId());
        idLable.getStyleClass().add("id-label");
        idLable.setWrapText(true);
        idLable.setPrefWidth(150);
		Spinner<Integer> SpinnerQuantity = new Spinner<>(0,product.getAmount(),0);
        HBox hb = (HBox)(findHBoxOfproductID(product.getProductId()));
        if (hb != null) {
            SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) SpinnerQuantity.getValueFactory();
            int quant = ((Spinner<Integer>)hb.getChildren().get(5)).getValue();
            myCart.getItems().remove(hb);
            SpinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(quant, 
                    valueFactory.getMax(), 0));
            addToCart(product,SpinnerQuantity);
            SpinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 
                    (valueFactory.getMax()-quant), 0));
        
        }
        SpinnerQuantity.getStyleClass().add("combo-color");
        Text newPrice = new Text();
        Text priceLabel = new Text("Price: " + product.getPrice());
        priceLabel.getStyleClass().add("price-label");
        SpinnerQuantity.setMaxWidth(75);
        imageview.setImage(image);
        if(product.getDiscount()!= 0) {
            priceLabel.setStrikethrough(true);
            float dis = product.getPrice()*(1-product.getDiscount());
            String present = String.format("%.0f%%OFF - Discount Price: %.1f",product.getDiscount()*100, dis);
            newPrice.setText(present + "\u20AA");
            newPrice.getStyleClass().add("new-price-label");
        }
        vBox.getChildren().addAll(nameLabel, idLable, detBtn, priceLabel, newPrice, SpinnerQuantity, addBtn);
        hBox.getChildren().addAll(imageview, vBox);
        vBox.setSpacing(15);
        vBox.setId(String.valueOf(product.getProductId()));
        imageview.setTranslateY(50);
        hBox.setPadding(new Insets(0, 0, 0, 0));
        vBox.setPadding(new Insets(0, 0, 20, 5));
        addBtn.setOnAction(event -> {
        if (SpinnerQuantity.getValue() != 0 ) {
        	SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) SpinnerQuantity.getValueFactory();
        	addToCart(product,SpinnerQuantity);
        	SpinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (valueFactory.getMax()-SpinnerQuantity.getValue()), 0));
        }
        });
        
        return hBox;
    }
   
    private void addToCart(Product product, Spinner<Integer> spinnerQuantity) {
    	int quantity = spinnerQuantity.getValue();
    	HBox hboxofcart = new HBox();
    	InputStream inputStream = new ByteArrayInputStream(product.getFile());
        Image image = null;
        image = new Image(inputStream);
        ImageView imageview = new ImageView();
        imageview.setFitHeight(50.0);
        imageview.setFitWidth(50.0);
        imageview.setImage(image);
    	Label namelb = new Label(product.getName());
    	namelb.setPrefWidth(100);
    	Label idlb = new Label(product.getProductId());
    	idlb.setPrefWidth(100);
    	float productPrice = product.getPrice();
    	Text productTotalPrice = new Text();
    	productTotalPrice.setWrappingWidth(100);
    	float priceofproduct = productPrice*spinnerQuantity.getValue();
    	if (product.getDiscount()!=0) {
    		priceofproduct = productPrice*(1-product.getDiscount())*spinnerQuantity.getValue();
    	}
    	productTotalPrice.setText(String.valueOf(priceofproduct) + "\u20AA");
    	Spinner<Integer> spinnerQuantitynew = new Spinner<Integer>(0,product.getAmount(),spinnerQuantity.getValue());
    	Button removeProduct = new Button();
        ImageView addtocarticon = new ImageView(getClass().getResource("/gui/OrderScreens/agalaremove.png").toExternalForm());
        addtocarticon.setFitHeight(15.0);
        addtocarticon.setFitWidth(15.0);
        removeProduct.setGraphic(addtocarticon);
        removeProduct.getStyleClass().add("btn");
    	spinnerQuantitynew.setMaxWidth(75);
    	hboxofcart.getChildren().addAll(imageview, namelb, idlb, productTotalPrice, removeProduct, spinnerQuantitynew);
    	imageview.setTranslateY(0);
    	HBox hb = (HBox)(findHBoxOfproductID(idlb.getText()));
    	if (hb != null) {
    		ChatClient.cartList.add(product);
        	ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).setAmount(
        			ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount()+quantity);
    		((Spinner<Integer>)hb.getChildren().get(5)).increment(quantity);
    		if (product.getDiscount() != 0) { 
    			((Text)hb.getChildren().get(3)).setText(
    					String.valueOf(product.getPrice()*(1-product.getDiscount())*
    							ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount()) + "\u20AA");
	    	}
    		else {
    			((Text)hb.getChildren().get(3)).setText(
    					String.valueOf(product.getPrice()*
    							ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount()) + "\u20AA");
    		}
    		totalAmount();
    	}
    	else {
    		ChatClient.cartList.add(product);
    		ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).setAmount(quantity);
	    	myCart.getItems().addAll(hboxofcart);
	    	counter++;
	    	cartCounter.setText(String.valueOf(counter));
	    	totalAmount();
    	}
    	removeProduct.setOnAction(event -> {
    		HBox hb1 = (HBox)(findHBoxOfproductID(idlb.getText()));
    		ChatClient.cartList.remove(product);
	    	myCart.getItems().remove(hb1);
	    	counter--;
	    	cartCounter.setText(String.valueOf(counter));
	    	spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,product.getAmount(), 0));
	    	totalAmount();
        });
    	spinnerQuantitynew.setOnMouseReleased(event -> {
    		ChatClient.cartList.remove(product);
    		ChatClient.cartList.add(product);
    		ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).setAmount(spinnerQuantitynew.getValue());
			if (product.getDiscount() != 0) {
				((Text) ((HBox) spinnerQuantitynew.getParent()).getChildren().get(3)).setText(
						String.valueOf(product.getPrice()*(1-product.getDiscount())*ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount()) + "\u20AA");
			}
			else {
				((Text) ((HBox) spinnerQuantitynew.getParent()).getChildren().get(3)).setText(
						String.valueOf(product.getPrice()*(ChatClient.cartList.get(ChatClient.cartList.indexOf(product)).getAmount())) + "\u20AA");
			}
			SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerQuantitynew.getValueFactory();
    		spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (valueFactory.getMax()-spinnerQuantitynew.getValue()), 0));
    	    if (spinnerQuantitynew.getValue() == 0) {
        		HBox hb2 = (HBox)(findHBoxOfproductID(idlb.getText()));
        		ChatClient.cartList.remove(product);
    	    	myCart.getItems().remove(hb2);
    	    	counter--;
    	    	cartCounter.setText(String.valueOf(counter));
    	    }
    	    totalAmount();
    	});
	}
    		
    Object findHBoxOfproductID(String nameLabel) {
    	for (Object hb : myCart.getItems()) {
			if (hb instanceof HBox){
	    		Label label = (Label)(((HBox)hb).getChildren().get(2));
	    		if (label.getText().equals(nameLabel)) {
	    			return hb;
	    		}
	    	}
    	}
    	return null; 
    }
    
    void totalAmount() {
    	totalprice = 0;
    	String pricevalue;
		int numItems = myCart.getItems().size();
		for (;numItems>0;numItems--) {
			Object hb = myCart.getItems().get(numItems-1);
			if (hb instanceof HBox){
				Text price = (Text)(((HBox)hb).getChildren().get(3));
				pricevalue = price.getText();
				pricevalue = pricevalue.split("\u20AA")[0];
				totalprice += Float.parseFloat(pricevalue);
	    	}
    	}
		totalAmount.setText(String.valueOf(totalprice) + "\u20AA");
    }
    
	@FXML
    void emptyMyCart(MouseEvent event) {
		int numItems = myCart.getItems().size();
		for (;numItems>0;numItems--) {
			Object hb = myCart.getItems().get(0);
			if (hb instanceof HBox){
				Button removebuttn = new Button();
				removebuttn = (Button)(((HBox)hb).getChildren().get(4));
				removebuttn.fire();
	    	}
    	}
    }

	@FXML
    void goBack(MouseEvent event) {
		emptyMyCart(event);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/UserMainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);        
    }
	
	@FXML
    void checkOut(MouseEvent event) {
		if (myCart.getItems().size() == 0) {
			JOptionPane.showMessageDialog(null, "Please add some products to your cart before CheckOut", "InfoBox: " + "No Cart", JOptionPane.INFORMATION_MESSAGE);
			/*Duration dur = Duration.seconds(3);
	        Timeline timeline = new Timeline(
	                new KeyFrame(
	                		dur,
	                		event -> {
	                            JOptionPane.showMessageDialog(null, "Please add some products to your cart before CheckOut", "InfoBox: " + "No Cart", JOptionPane.INFORMATION_MESSAGE);
	                        }
	                )
	        );
	        timeline.setCycleCount(Animation.INDEFINITE);
	        timeline.play();*/
			return;
		}
		ChatClient.rememberMyCart.setItems(myCart.getItems());
        System.out.println(ChatClient.cartList);
		ChatClient.currentOrder = new Order("OrderID", totalprice, ChatClient.cartList, "machineID", "orderDate", "estimatedDeliveryTime", "confirmationDate", "Pending", UserController.getCurrentuser().getId(), "supplyMethod", "paidWith");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("CheckoutScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);        
    }
}

