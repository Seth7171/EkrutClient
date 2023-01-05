package gui.ProductControlScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Product;
import gui.ScreenController;
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
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductManagementScreenController extends ScreenController implements Initializable {

    @FXML
    private ChoiceBox<String> locationChoiceBox;
    @FXML
    private TextField productImageTextField;
    @FXML
    private Text alertAmountTXT;
    @FXML
    private TextField alertAmountTextField;
    @FXML
    private Text availableAmountTXT;
    @FXML
    private TextField availableAmountTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ChoiceBox<String> discountChoiceBox;
    @FXML
    private Text discountTXT;
    @FXML
    private Text locationToAdd;
    @FXML
    private ChoiceBox<String> machineIDChoiceBox;
    @FXML
    private Text machineIDTXT;
    @FXML
    private Text productDescriptionTXT;
    @FXML
    private Text productIDTXT;
    @FXML
    private TextField productIDTextField;
    @FXML
    private Text productImageNameTXT;
    @FXML
    private Text productPriceTXT;
    @FXML
    private TextField productPriceTextField;
    @FXML
    private Text productType;
    @FXML
    private ChoiceBox<String> productTypeChoiceBox;
    @FXML
    private Button addProductButton;




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
    private TableColumn<Product, String> typeColumn;
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

    private ArrayList<Product> changesToBeMade;

    @FXML
    private Button uploadToDBButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changesToBeMade = new ArrayList<>();
        machineProductTable.setPlaceholder(new Label("You can choose only location or\nLocation and machine id to display the appropriate data"));
        initNewProductElements();
        initCols();
        refreshTable(null);
        setComboBoxes();
        initLocationBox();
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            if (e instanceof NumberFormatException){
                super.alertHandler("This Field must only contain numbers", false);
            }
        });
    }

    private void initNewProductElements(){
        machineIDTXT.setVisible(false);
        machineIDChoiceBox.setVisible(false);
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_ALL_MACHINE_LOCATIONS));
        ArrayList<String> Locations = new ArrayList<>();
        Locations.add("New Product");
        Locations.add("Warehouse");
        Locations.addAll((ArrayList<String>) MessageHandler.getData());
        locationChoiceBox.getItems().addAll(Locations);//add all Locations to Location comboBox

        locationChoiceBox.setOnAction(event -> {
            if (locationChoiceBox.getValue() != null && locationChoiceBox.getValue().equals("New Product")){
                machineIDTXT.setVisible(false);
                machineIDChoiceBox.setVisible(false);


                alertAmountTXT.setVisible(false);
                alertAmountTextField.setVisible(false);

                discountTXT.setVisible(false);
                discountChoiceBox.setVisible(false);


                productDescriptionTXT.setVisible(true);
                descriptionTextArea.setVisible(true);

            }
            else if (locationChoiceBox.getValue() != null && (!locationChoiceBox.getValue().equals("New Product") || locationChoiceBox.getValue().equals("Warehouse"))){
                if (!locationChoiceBox.getValue().equals("Warehouse")){
                    machineIDTXT.setVisible(true);
                    machineIDChoiceBox.setVisible(true);
                }
                else {
                    machineIDTXT.setVisible(false);
                    machineIDChoiceBox.setVisible(false);
                }

                productDescriptionTXT.setVisible(false);
                descriptionTextArea.setVisible(false);

                alertAmountTXT.setVisible(true);
                alertAmountTextField.setVisible(true);

                discountTXT.setVisible(true);
                discountChoiceBox.setVisible(true);

                ClientUI.chat.accept(new Message(locationChoiceBox.getValue(), MessageFromClient.REQUEST_MACHINE_IDS));
                ArrayList<String> machineIDs = new ArrayList<>();
                machineIDs.addAll((ArrayList<String>) MessageHandler.getData());
                machineIDChoiceBox.getItems().clear();
                machineIDChoiceBox.getItems().addAll(machineIDs);
            }
        });
        locationChoiceBox.setValue("New Product");


        ArrayList<String> discounts = new ArrayList<>();
        discounts.add("No discount");
        discounts.add("5%");
        discounts.add("10%");
        discounts.add("15%");
        discounts.add("20%");
        discounts.add("25%");
        discounts.add("30%");
        discounts.add("35%");
        discounts.add("40%");
        discounts.add("45%");
        discounts.add("50%");
        discountChoiceBox.getItems().addAll(discounts);
        discountChoiceBox.setValue("No discount");

        ArrayList<String> types = new ArrayList<>();
        types.add("snack");
        types.add("drink");
        productTypeChoiceBox.getItems().addAll(types);
    }

    @FXML
    void addProductToDatabase(MouseEvent event) {
        Product product = new Product();
        if (locationChoiceBox.getValue().equals("New Product")){
            if (productIDTextField.getText().equals("") || productImageTextField.getText().equals("") || productPriceTextField.getText().equals("") || availableAmountTextField.getText().equals("")){
                super.alertHandler("All fields must be filled", true);
                return;
            }
            // TODO: add other options
        }else if (locationChoiceBox.getValue().equals("Warehouse")){
            if (productIDTextField.getText().equals("") || productImageTextField.getText().equals("") || productPriceTextField.getText().equals("") || availableAmountTextField.getText().equals("") || alertAmountTextField.getText().equals("")){
                super.alertHandler("All fields must be filled", true);
                return;
            }
        }
        else {
            if (productIDTextField.getText().equals("") || productImageTextField.getText().equals("") || productPriceTextField.getText().equals("") || availableAmountTextField.getText().equals("") || alertAmountTextField.getText().equals("") || machineIDChoiceBox.getValue() == null){
                super.alertHandler("All fields must be filled", true);
                return;
            }
        }

        if (productIDTextField.getText().length() > 10){
            super.alertHandler("product ID length must be less than 10", true);
            return;
        }
        product.setProductId(productIDTextField.getText());
        try {
            product.setPrice(Integer.parseInt(productPriceTextField.getText()));
        }catch (NumberFormatException e){
            super.alertHandler("Price field must only contain numbers", true);
            return;
        }
        if (productImageTextField.getText().length() > 40){
            super.alertHandler("file name length must be less than 40", true);
            return;
        }
        product.setName(productImageTextField.getText());
        try {
            product.setAmount(Integer.parseInt(availableAmountTextField.getText()));
        }catch (NumberFormatException e){
            super.alertHandler("Available amount field must only contain numbers", true);
            return;
        }
        try {
            product.setCriticalAmount(Integer.parseInt(alertAmountTextField.getText()));
        }catch (NumberFormatException e){
            super.alertHandler("Alert amount field must only contain numbers", true);
            return;
        }
        if (descriptionTextArea.getText().length() > 100){
            super.alertHandler("description length must be less than 40", true);
            return;
        }
        product.setDescription(descriptionTextArea.getText());
        product.setType(productTypeChoiceBox.getValue().toUpperCase()); //TODO: if i will change it in the database then i will need to delete the: toUppercase function
        if (!discountChoiceBox.getValue().equals("No discount")){
            product.setDiscount(Float.parseFloat(discountChoiceBox.getValue().substring(0, discountChoiceBox.getValue().length() - 1)) / 100);
        }else
            product.setDiscount(0);
        if (locationChoiceBox.getValue() != null && !locationChoiceBox.getValue().equals("New Product")  && !locationChoiceBox.getValue().equals("Warehouse")){
            product.setMachineID(machineIDChoiceBox.getValue());
        }

        switch (locationChoiceBox.getValue()){
            case "New Product":
                ClientUI.chat.accept(new Message(product, MessageFromClient.REQUEST_ADD_NEW_PRODUCT_TO_PRODUCT_TABLE));
                break;
            case "Warehouse":
                ClientUI.chat.accept(new Message(product, MessageFromClient.REQUEST_ADD_NEW_PRODUCT_TO_WAREHOUSE));
                break;
            default:
                ClientUI.chat.accept(new Message(product, MessageFromClient.REQUEST_ADD_NEW_PRODUCT_TO_MACHINE));
                break;
        }

        if (MessageHandler.getMessage().contains("failed")){
            super.alertHandler("Failed to add product!", true);
            return;
        }
        if (MessageHandler.getMessage().contains("exists")){
            super.alertHandler("Product already exists!\nYou can edit it in the appropriate table", true);
            return;
        }
        super.alertHandler("Product added successfully!", false);

    }

    @FXML
    void refreshTable(MouseEvent event) {
        changesToBeMade.clear();
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
        else
            ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_WAREHOUSE_PRODUCTS));

        ArrayList<Product> products = new ArrayList<>();
        if (MessageHandler.getData() instanceof String){
            if (!dataTable.isEmpty())
                dataTable.clear();
            super.alertHandler("No info was found for your choice", false);
            return;
        }
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
        alertAmountColumn.setOnEditCommit       (event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setCriticalAmount(event.getNewValue());
            checkAndReplace(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });


        // price column
        priceColumn.setCellValueFactory         (new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory              (TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        priceColumn.setOnEditCommit             (event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
            checkAndReplace(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });


        // discount column
        discountColumn.setCellValueFactory      (new PropertyValueFactory<>("discount"));
//        discountColumn.setCellFactory           (TextFieldTableCell.forTableColumn(new FloatStringConverter()));
//        discountColumn.setOnEditCommit          (event -> {
//            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDiscount(event.getNewValue());
//            checkAndReplace(event.getTableView().getItems().get(event.getTablePosition().getRow()));
//        });


        // current available amount column
        currentAmountColumn.setCellValueFactory (new PropertyValueFactory<>("amount"));
        currentAmountColumn.setCellFactory      (TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        currentAmountColumn.setOnEditCommit     (event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
            checkAndReplace(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });


        // description column
        descriptionColumn.setCellValueFactory   (new PropertyValueFactory<>("description"));
//        descriptionColumn.setCellFactory        (TextFieldTableCell.forTableColumn());
//        descriptionColumn.setOnEditCommit       (event -> {
//            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDescription(event.getNewValue());
//            checkAndReplace(event.getTableView().getItems().get(event.getTablePosition().getRow()));
//        });


        // type column
        typeColumn.setCellValueFactory (new PropertyValueFactory<>("Type"));
//        typeColumn.setCellFactory      (TextFieldTableCell.forTableColumn());
//        typeColumn.setOnEditCommit     (event -> {
//            event.getTableView().getItems().get(event.getTablePosition().getRow()).setType(event.getNewValue());
//            checkAndReplace(event.getTableView().getItems().get(event.getTablePosition().getRow()));
//        });


        try {
            machineProductTable.setEditable(true);
        }catch (RuntimeException e){
            System.out.println("machine table is null");
        }

        try{
            warehouseProductsTable.setEditable(true);
        }catch (RuntimeException ex) {
            System.out.printf("warehouse table is null");
        }
    }

    private void checkAndReplace(Product product){
        if (changesToBeMade.isEmpty()){
            changesToBeMade.add(product);
            return;
        }

        for (Product prod : changesToBeMade){
            if (warehouseProductsTab.isSelected()){
                if (prod.getProductId().equals(product.getProductId())){
                    changesToBeMade.remove(prod);
                    changesToBeMade.add(product);
                    return;
                }
            }
            else {
                if (prod.getProductId().equals(product.getProductId()) && prod.getMachineID().equals(product.getMachineID())){
                    changesToBeMade.remove(prod);
                    changesToBeMade.add(product);
                    return;
                }
            }

        }
        changesToBeMade.add(product);
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

    @FXML
    void uploadChangesToDataBase(MouseEvent event) {
        if (changesToBeMade.isEmpty()){
            super.alertHandler("You have no changes to upload", false);
            return;
        }

        for (Product product : changesToBeMade){
            if (warehouseProductsTab.isSelected())
                ClientUI.chat.accept(new Message(product, MessageFromClient.REQUEST_UPDATE_WAREHOUSE_PRODUCTS));
            else
                ClientUI.chat.accept(new Message(product, MessageFromClient.REQUEST_UPDATE_MACHINE_PRODUCTS));

            if (!MessageHandler.getMessage().contains("successfully")){
                super.alertHandler(MessageHandler.getMessage(), true);
                return;
            }
        }
        super.alertHandler("Data successfully updated!", false);
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
