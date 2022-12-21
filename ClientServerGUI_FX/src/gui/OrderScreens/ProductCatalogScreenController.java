package gui.OrderScreens;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import application.client.ChatClient;
import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Order;
import common.orders.Product;
import gui.ScreenController;
import gui.UserScreens.UserMainScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProductCatalogScreenController extends ScreenController implements Initializable{
    @FXML
    private Button logOutButton;

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
    
    double xoffset;
    double yoffset;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientUI.chat.accept(new Message("HA01", MessageFromClient.REQUEST_ALL_MACHINE_PRODUCTS));
        tabPane.getStyleClass().add("tab-pane");
        for (Product product : ChatClient.productList) {
           //if(product.gettype().equals("snack"))
        	   snacksPane.getChildren().add(createProductTile(product));
           //else
           		//drinksPane.getChildren().add(createProductTile(product));
        }

        snacksScroll.setFitToWidth(true);
        drinksScroll.setFitToWidth(true);
    }
    
    @FXML
    void logOut(MouseEvent event) {
		super.closeProgram(event, true);
    }
    
    private Node createProductTile(Product product) {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        
        //Image image =new Image("@../logo.png");
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

        //Client.productController.createProductImage(product);
        //iv.setImage(image);

        // Check which price to display to the customer
        if(product.getDiscount()!=0) {
            priceLabel.setStrikethrough(true);
            newPrice.setText("Discount Price: " + (product.getDiscount() - product.getDiscount()) + " \u20AA");
            newPrice.getStyleClass().add("new-price-label");
        }

        /*if(Client.userController.getLoggedInUser() != null)
            if(Client.userController.getLoggedInUser().getUserType() == UserType.CUSTOMER && ((Customer) Client.userController.getLoggedInUser()).isBlocked())
                vBox.getChildren().addAll(nameLabel, priceLabel, newPrice, new Label("Color: " + product.getDominantColor()), viewDetails);
            else
                vBox.getChildren().addAll(nameLabel, priceLabel, newPrice, new Label("Color: " + product.getDominantColor()), comboBoxQuantity, addBtn, viewDetails);
        else*/
        vBox.getChildren().addAll(nameLabel, priceLabel, newPrice, new Label("",viewDetails));
        hBox.getChildren().addAll(iv, vBox);
        vBox.setSpacing(15);
        vBox.setId(String.valueOf(product.getProductId()));
        iv.setTranslateY(50);
        hBox.setPadding(new Insets(0, 0, 0, 0));
        vBox.setPadding(new Insets(50, 30, 20, 25));

        addBtn.setOnAction(event -> {
            String valueOfQuantity = comboBoxQuantity.getValue();
            addToCart(product, Integer.valueOf(valueOfQuantity));
            System.out.println("");
            //MainDashboardController.refreshCartCounter();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.switchScreen(event, root);        
    }
    

    void addToCart(Product product, int quantity) {
    	
    }
}




