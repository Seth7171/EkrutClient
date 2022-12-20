package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Product;
import gui.UserScreens.UserMainScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProductCatalogScreenController implements Initializable {
    @FXML
    private Button logOutButton;

    @FXML
    private Button backButton;

    @FXML
    private Button emptyMyCartButton;

    @FXML
    private Button checkOutButton;
    
    @FXML
    private TilePane snacksPane;

    @FXML
    private TilePane drinksPane;
    
    @FXML
    private ScrollPane scrollSnacksPane;

    @FXML
    private ScrollPane scrollDrinksPane;
    
    @FXML
    private TabPane tabPane;
    
    ObservableList<String> quantityPicker =
            FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    
    double xoffset;
    double yoffset;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tabPane.getStyleClass().add("tab-pane");
        ArrayList<String> credentials = new ArrayList<String>();
        credentials.add(UserController.getCurrentuser().getUsername());
    	ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_ALL_MACHINE_PRODUCTS));
        System.out.println(ChatClient.productList);
        for (Product product : ChatClient.productList) {
           // if(!product.isCustomMade())
            	snacksPane.getChildren().add(createProductTile(product));
           // else
            //	drinksPane.getChildren().add(createProductTile(product));
        }

        scrollSnacksPane.setFitToWidth(true);
        scrollDrinksPane.setFitToWidth(true);
    }
    
    @FXML
    void logOut(MouseEvent event) {
        UserMainScreenController userMainScreenController = new UserMainScreenController();
		userMainScreenController.logOut(event);
    }
    
    private Node createProductTile(Product product) {


        HBox hBox = new HBox();
        VBox vBox = new VBox();

        ImageView iv = new ImageView();
        iv.setFitHeight(250.0);
        iv.setFitWidth(150.0);

        Button addBtn = new Button("Add to cart");
        addBtn.getStyleClass().add("btn");
        Button viewDetails = new Button("View details");
        viewDetails.getStyleClass().add("btn-secondary");

        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("name-label");
        nameLabel.setWrapText(true);
        nameLabel.setPrefWidth(170);

        ComboBox<String> comboBoxQuantity = new ComboBox<>(quantityPicker);
        comboBoxQuantity.getStyleClass().add("combo-color");

        Text newPrice = new Text();
        Text priceLabel = new Text("Price: " + product.getPrice());
        priceLabel.getStyleClass().add("price-label");

        comboBoxQuantity.getSelectionModel().selectFirst();

       // ClientUI.productController.createProductImage(product);
      //  iv.setImage(ClientUI.productController.getProductImages().get(product.getProductId()));

        // Check which price to display to the customer
        if(product.getPrice() != product.getPrice()-product.getDiscount()) {
            priceLabel.setStrikethrough(true);
            newPrice.setText("Discount Price: " + product.getDiscount() + " \u20AA");
            newPrice.getStyleClass().add("new-price-label");
        }

        //if(Client.userController.getLoggedInUser() != null)
        //    if(Client.userController.getLoggedInUser().getUserType() == UserType.CUSTOMER && ((Customer) Client.userController.getLoggedInUser()).isBlocked())
         //       vBox.getChildren().addAll(nameLabel, priceLabel, newPrice, new Label("Color: " + product.getDominantColor()), viewDetails);
        //    else
             //   vBox.getChildren().addAll(nameLabel, priceLabel, newPrice, new Label("Color: " + product.()), comboBoxQuantity, addBtn, viewDetails);
        //else
          //  vBox.getChildren().addAll(nameLabel, priceLabel, newPrice, new Label("Color: " + product.getDominantColor()), viewDetails);
        hBox.getChildren().addAll(iv, vBox);
        vBox.setSpacing(15);
       // vBox.setId(String.valueOf(product.get));
        iv.setTranslateY(50);
        hBox.setPadding(new Insets(5, 30, 20, 70));
        vBox.setPadding(new Insets(50, 30, 20, 25));

        /*addBtn.setOnAction(event -> {
            String valueOfQuantity = comboBoxQuantity.getValue();

            Client.orderController.addToCart(new OrderProduct(product, Integer.valueOf(valueOfQuantity)));
            MainDashboardController.createAlert(product.getName() + " was added to cart", Alert.SUCCESS, Duration.seconds(2), 135, 67);
            MainDashboardController.refreshCartCounter();
        });
        addBtn.setCursor(Cursor.HAND);
        viewDetails.setOnAction(event -> {
            currentProduct = product;
            MainDashboardController.setContentFromFXML("ViewProductDetailsPage.fxml");
        });
        viewDetails.setCursor(Cursor.HAND);*/
        return hBox;
    }
}
