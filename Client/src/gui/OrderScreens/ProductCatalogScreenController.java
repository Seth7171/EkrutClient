package gui.OrderScreens;
//

import application.client.ChatClient;
import application.client.ClientUI;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Order;
import common.orders.Product;
import gui.ScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductCatalogScreenController extends ScreenController implements Initializable{
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
    private TilePane snacksPane;

    @FXML
    private TilePane drinksPane;
    
    @FXML
    private ScrollPane snacksScroll;
    
    @FXML
    private ScrollPane drinksScroll;
    
    Order order = new Order();
    
    ObservableList<String> quantityPicker =
            FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientUI.chat.accept(new Message("HA01", MessageFromClient.REQUEST_ALL_MACHINE_PRODUCTS));
        tabPane.getStyleClass().add("tab-pane");
        for (Product product : ChatClient.productList) {
           if(product.getType().equals("SNACK"))
        	   snacksPane.getChildren().add(createProductTile(product));
           else
           		drinksPane.getChildren().add(createProductTile(product));
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
        addBtn.getStyleClass().add("btn");
        Button viewDetails = new Button("View details");
        viewDetails.getStyleClass().add("btn-secondary");
        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("name-label");
        nameLabel.setWrapText(true);
        nameLabel.setPrefWidth(150);
        Spinner<String> SpinnerQuantity = new Spinner<>(0,product.getAmount(),0);
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
            //present += dis;
            newPrice.setText(present + "\u20AA");
            newPrice.getStyleClass().add("new-price-label");
        }
        vBox.getChildren().addAll(nameLabel, viewDetails, priceLabel, newPrice, SpinnerQuantity, addBtn);
        hBox.getChildren().addAll(imageview, vBox);
        vBox.setSpacing(15);
        vBox.setId(String.valueOf(product.getProductId()));
        imageview.setTranslateY(50);
        hBox.setPadding(new Insets(0, 0, 0, 0));
        vBox.setPadding(new Insets(0, 0, 20, 20));
        addBtn.setOnAction(event -> {
            String valueOfQuantity = SpinnerQuantity.getValue();
            addToCart(product, Integer.valueOf(valueOfQuantity));
            System.out.println("");
        });
        addBtn.setCursor(Cursor.HAND);
        viewDetails.setOnAction(event -> {
            JOptionPane.showMessageDialog(null, product.getDescription(), "InfoBox: " + product.getName(), JOptionPane.INFORMATION_MESSAGE);
        });
        viewDetails.setBorder(null);
        return hBox;
    }
    
    @FXML
    void goBack(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/UserMainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);        
    }
    

    void addToCart(Product product, int quantity) {
    	
    }
}




