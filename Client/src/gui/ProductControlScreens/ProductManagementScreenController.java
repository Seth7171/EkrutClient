package gui.ProductControlScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Product;
import gui.ScreenController;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductManagementScreenController extends ScreenController implements Initializable {
    @FXML
    private Text errorMessage;
    @FXML
    private Button backButton;
    @FXML
    private Button exitButton;
    @FXML
    private TableView<Product> machineProductTable;
    @FXML
    private TableView<Product> warehouseProductsTable;
    public static ObservableList<Product> dataTable;
    @FXML
    private TableColumn<Product, String> productIDColumn;
    @FXML
    private TableColumn<Product, String> machineIDColumn;
    @FXML
    private TableColumn<Product, Integer> alertAmountColumn;
    @FXML
    private TableColumn<Product, Float> priceColumn;
    @FXML
    private TableColumn<Product, Float> discountColumn;
    @FXML
    private TableColumn<Product, Integer> currentAmountColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private ChoiceBox<String> areaComboBox;
    @FXML
    private ChoiceBox<String> machineIDComboBox;
    @FXML
    private AnchorPane warehouseProducts;

    @FXML
    private Tab warehouseProductsTab;
    @FXML
    private Tab machineProductsTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        machineProductTable.setPlaceholder(new Label("You can choose only location or\nLocation and machine id to display the appropriate data"));
        initCols();
        refreshTable(null);
        setComboBoxes();
        initLocationBox();
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            if (e instanceof NumberFormatException){
                errorMessage.setText("This Field must only contain numbers");
                PauseTransition delay = new PauseTransition(Duration.seconds(5));
                delay.setOnFinished( event2 -> {
                    errorMessage.setText("");
                });
                delay.play();
            }
        });
    }

    @FXML
    void refreshTable(MouseEvent event) {
        dataTable = FXCollections.observableArrayList();
        if (machineProductsTab.isSelected()){
            ArrayList<String > data = new ArrayList<>();
            if (areaComboBox.getValue() != null && (machineIDComboBox.getValue() == null  || machineIDComboBox.getValue().equals("All"))){
                data.add(areaComboBox.getValue());
                data.add("1");
                ClientUI.chat.accept(new Message(data, MessageFromClient.REQUEST_LOCATION_PRODUCTS));
                data.clear();
            }else if (areaComboBox.getValue() != null && machineIDComboBox.getValue() != null){
                data.add(machineIDComboBox.getValue());
                data.add("0");
                ClientUI.chat.accept(new Message(data, MessageFromClient.REQUEST_LOCATION_PRODUCTS));
                data.clear();
            }
            else if (areaComboBox.getValue() == null && machineIDComboBox.getValue() == null){
                ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_LOCATION_PRODUCTS));
            }
        }
        else {
            ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_WAREHOUSE_PRODUCTS));
        }

        ArrayList<Product> products = new ArrayList<>();
        if (MessageHandler.getData() instanceof String){
            if (!dataTable.isEmpty())
                dataTable.clear();
            errorMessage.setText("No info was found for your choice");
            return;
        }
        errorMessage.setText("");
        products = (ArrayList<Product>) MessageHandler.getData(); // check if I won't have reference errors when communicating with the server.
        dataTable.addAll(products);

        if (machineProductsTab.isSelected()){
            machineProductTable.setItems(dataTable);
            return;
        }
        warehouseProductsTable.setItems(dataTable);

    }
    @FXML
    private void initCols(){ // TODO: ADD TYPE COLUMN!!!!
        // user cannot change productID value
        productIDColumn.setCellValueFactory     (new PropertyValueFactory<>("productId"));
        //productIDColumn.setCellFactory        (TextFieldTableCell.forTableColumn());
        //productIDColumn.setOnEditCommit       (event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setProductId(event.getNewValue()));

        try {
            if (machineProductsTab.isSelected()){
                // user cannot change machine ID value
                machineIDColumn.setCellValueFactory     (new PropertyValueFactory<>("machineID"));
                //machineIDColumn.setCellFactory        (TextFieldTableCell.forTableColumn());
                //machineIDColumn.setOnEditCommit       (event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setMachineID(event.getNewValue()));
            }
        }catch (RuntimeException ignored){

        }


        // alert amount column
        alertAmountColumn.setCellValueFactory   (new PropertyValueFactory<>("criticalAmount"));
        alertAmountColumn.setCellFactory        (TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        alertAmountColumn.setOnEditCommit       (event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setCriticalAmount(event.getNewValue())); // check for exceptions when entering characters


        // price column
        priceColumn.setCellValueFactory         (new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory              (TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        priceColumn.setOnEditCommit             (event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue())); // check for exceptions when entering characters


        // discount column
        discountColumn.setCellValueFactory      (new PropertyValueFactory<>("discount"));
        discountColumn.setCellFactory           (TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        discountColumn.setOnEditCommit          (event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setDiscount(event.getNewValue())); // check for exceptions when entering characters


        // current available amount column
        currentAmountColumn.setCellValueFactory (new PropertyValueFactory<>("amount"));
        currentAmountColumn.setCellFactory      (TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        currentAmountColumn.setOnEditCommit     (event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setCriticalAmount(event.getNewValue())); // check for exceptions when entering characters


        // description column
        descriptionColumn.setCellValueFactory   (new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory        (TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit       (event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setDescription(event.getNewValue()));


        machineProductTable.setEditable(true);
        warehouseProductsTable.setEditable(true);
    }

    private void loadData(){
        dataTable = FXCollections.observableArrayList();
        //ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_ALL_MACHINE_PRODUCTS)); // TODO: here is the data to be displayed

        ArrayList<Product> products = new ArrayList<>();
        products = (ArrayList<Product>) MessageHandler.getData(); // check if I won't have reference errors when communicating with the server.
        dataTable.addAll(products);
        if (machineProductsTab.isSelected())
            machineProductTable.setItems(dataTable);
        warehouseProductsTable.setItems(dataTable);
    }

    private void setComboBoxes(){
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_ALL_MACHINE_LOCATIONS));
        ArrayList<String> Locations = (ArrayList<String>) MessageHandler.getData();
        areaComboBox.getItems().addAll(Locations);//add all Locations to Location comboBox
    }


    void initLocationBox(){
        areaComboBox.setOnAction(event -> {
            if (areaComboBox.getValue() != null){
                ClientUI.chat.accept(new Message(areaComboBox.getValue(), MessageFromClient.REQUEST_MACHINE_IDS));
                ArrayList<String> machineIDs = new ArrayList<>();
                machineIDs.add("All");
                machineIDs.addAll((ArrayList<String>) MessageHandler.getData());
                machineIDComboBox.getItems().clear();
                machineIDComboBox.getItems().addAll(machineIDs);
            }
        });
    }

    void initMachineIdBox(){
        machineIDComboBox.setOnAction(event -> {
            if (machineIDComboBox.getValue() != null){
                ClientUI.chat.accept(new Message(machineIDComboBox.getValue(), MessageFromClient.REQUEST_MACHINE_IDS));
                ArrayList<String> machineIDs = (ArrayList<String>) MessageHandler.getData();
                machineIDComboBox.getItems().clear();
                machineIDComboBox.getItems().addAll(machineIDs);
            }
        });
    }

    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }

    @FXML
    void goBackToCEOMainScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
}



// init backup:
//@Override
//public void initialize(URL location, ResourceBundle resources) {
//    ScrollPane scrollPane = new ScrollPane();
//    GridPane gridPane = new GridPane();
//    gridPane.setPrefSize(500, 421);
//    //gridPane.setLayoutX(140);
//    gridPane.setLayoutX(0);
//    gridPane.setLayoutY(0);
//    //gridPane.setGridLinesVisible(true);
//    Text productID = new Text("Product ID");
//    Text alertAmount = new Text("Alert amount");
//    Text price = new Text("Price");
//    Text discount = new Text("Discount");
//    Text currentAmount = new Text("Current amount");
//    Text description = new Text("description");
//
//    gridPane.setHgap(10);
//    gridPane.setVgap(7);
//
//    // generate the header row:
//    gridPane.add(productID, 0, 0);
//    gridPane.add(alertAmount, 1, 0);
//    gridPane.add(price, 2, 0);
//    gridPane.add(discount, 3, 0);
//    gridPane.add(currentAmount, 4, 0);
//    gridPane.add(description, 5, 0);
//
//
//    // generate all rows using data from products:
//    ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_WAREHOUSE_PRODUCTS));
//    ArrayList<Product> products = (ArrayList<Product>)MessageHandler.getData();
//    int index = 1;
//
//    TextArea textField = new TextArea();
//    for (Product product : products){
//        gridPane.add(createNewArea(product.getProductId(), true, 50), 0, index);
//        gridPane.add(createNewArea(product.getCriticalAmount() + "", true, 60), 1, index);
//        gridPane.add(createNewArea(product.getPrice() + "", true, 70), 2, index);
//        gridPane.add(createNewArea(product.getDiscount() * 100 + "%", true, 100), 3, index);
//        gridPane.add(createNewArea(product.getAmount() + "", true, 50), 4, index);
//        gridPane.add(createNewArea(product.getDescription(), false, 150), 5, index);
//
//        index += 1;
//    }
//
//
//    scrollPane.setPrefSize(520, 421);
//    scrollPane.setLayoutX(141);
//    scrollPane.setLayoutY(0);
//
//    scrollPane.setContent(gridPane);
//    warehouseProducts.getChildren().add(scrollPane);
//
//}
