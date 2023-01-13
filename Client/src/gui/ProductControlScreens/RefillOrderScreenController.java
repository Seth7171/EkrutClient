package gui.ProductControlScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.RefillOrder;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.User;
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
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lior Jigalo
 * Refill order table screen for both operations employee and area manager
 */
public class RefillOrderScreenController extends ScreenController implements Initializable {

    @FXML
    private TableColumn<RefillOrder, Integer> amountAtRequestColumn;

    @FXML
    private TableColumn<RefillOrder, String> assignedEmployeeColumn;

    @FXML
    private TableColumn<RefillOrder, String> creationDateColumn;

    @FXML
    private TableColumn<RefillOrder, Integer> newAmountColumn;

    @FXML
    private TableColumn<RefillOrder, String> machineidColumn;

    @FXML
    private TableColumn<RefillOrder, String> orderidColumn;

    @FXML
    private TableColumn<RefillOrder, String> productidColumn;

    @FXML
    private TableColumn<RefillOrder, String> productNameColumn;

    @FXML
    private Text refilOrderTXT;

    @FXML
    private TableView<RefillOrder> requestTable;

    @FXML
    private Button exitButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Button updateButton;


    private ArrayList<RefillOrder> changesToBeMade;

    public static ObservableList<RefillOrder> requestList;



    /**
     * Method to initialize the layout and data for the request table.
     * @param location The location of the FXML file.
     * @param resources The resource bundle for localization.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changesToBeMade = new ArrayList<>();
        requestTable.setPlaceholder(new Label("no requests to display"));
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            if (e instanceof NumberFormatException){
                super.alertHandler("This Field must only contain numbers", false);
            }
        });
        initCols();
        switch (UserController.getCurrentuser().getDepartment()){
            case"area_manager_north":
            case"area_manager_south":
            case"area_manager_uae":
            case "ceo":
                initManagerCol();
                break;

            case "operations":
                initOperationsCol();
                break;
            default:
                break;

        }
        loadData();
    }


    /**
     * Method to initialize the columns for the request table.
     */
    private void initCols(){
        // order ID column
        orderidColumn.setCellValueFactory           (new PropertyValueFactory<>("orderID"));

        // product ID column
        productidColumn.setCellValueFactory         (new PropertyValueFactory<>("ProductID"));

        // product name column
        productNameColumn.setCellValueFactory       (new PropertyValueFactory<>("ProductName"));

        // machine ID column
        machineidColumn.setCellValueFactory         (new PropertyValueFactory<>("MachineID"));

        // creationDate column
        creationDateColumn.setCellValueFactory      (new PropertyValueFactory<>("creationDate"));

        // hide column
        assignedEmployeeColumn.setVisible(false);

        // new amount column
        newAmountColumn.setVisible(false);

        // amount at request column
        amountAtRequestColumn.setCellValueFactory   (new PropertyValueFactory<>("amountAtRequest"));
        //amountAtRequestColumn.setCellFactory        (TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        requestTable.setEditable(true);
    }


    /**
     * Method to initialize the columns specific to the operations department for the request table.
     */
    private void initOperationsCol(){
        // assigned employee column
        newAmountColumn.setCellValueFactory  (new PropertyValueFactory<>("newAmount"));
        newAmountColumn.setCellFactory        (TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        newAmountColumn.setOnEditCommit       (event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setNewAmount(event.getNewValue());
            checkAndReplace(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });
        newAmountColumn.setVisible(true);
    }


    /**
     * Method to initialize the columns specific to the manager for the request table.
     */
    private void initManagerCol(){
        // assigned employee column
        assignedEmployeeColumn.setCellValueFactory  (new PropertyValueFactory<>("assignedEmployeeBox"));
        assignedEmployeeColumn.setVisible(true);
    }


    /**
     * Method to load the data for the request table.
     */
    private void loadData(){
        requestList = FXCollections.observableArrayList();

        // add refill orders to list
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_REFILL_ORDERS));
        ArrayList<RefillOrder> list = new ArrayList<>();
        list = (ArrayList<RefillOrder>) MessageHandler.getData();

        // get employees list
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_OPERATIONS_EMPLOYEE_DATA));
        ArrayList<User> operationsList = new ArrayList<>();
        operationsList = (ArrayList<User>) MessageHandler.getData();

        // insert employees to choice boxes
        for (RefillOrder refOrder : list){
            ChoiceBox<String> choiceB = new ChoiceBox<>();
            choiceB.getItems().add("not assigned");
            choiceB.setValue(refOrder.getAssignedEmployee());
            refOrder.setAssignedEmployeeBox(choiceB);
            for (User user :operationsList){
               refOrder.getAssignedEmployeeBox().getItems().add(user.getFirstname() + " " + user.getLastname());
            }
        }

        switch (UserController.getCurrentuser().getDepartment()){
            case "operations":
                refilOrderTXT.setText("My refill orders");
                for (RefillOrder refillOrder : list){
                    if (refillOrder.getAssignedEmployee().equals(UserController.getCurrentuser().getFirstname() + " " + UserController.getCurrentuser().getLastname())){
                        requestList.add(refillOrder);
                    }
                }
                requestTable.setItems(requestList);
                return;

            case "area_manager_south":
                for (RefillOrder refillOrder : list){
                    if (refillOrder.getMachineID().startsWith("SOU")){
                        requestList.add(refillOrder);
                    }
                }
                requestTable.setItems(requestList);
                return;


            case "area_manager_north":
                for (RefillOrder refillOrder : list){
                    if (refillOrder.getMachineID().startsWith("NOR")){
                        requestList.add(refillOrder);
                    }
                }
                requestTable.setItems(requestList);
                return;

            case "area_manager_uae":
                for (RefillOrder refillOrder : list){
                    if (refillOrder.getMachineID().startsWith("UAE")){
                        requestList.add(refillOrder);
                    }
                }
                requestTable.setItems(requestList);
                return;
        }
        requestList.addAll(list);
        requestTable.setItems(requestList);
    }

    /**
     * Method to check and replace a RefillOrder in the list of changes to be made
     * @param refillOrder the RefillOrder to be checked and replaced
     */
    private void checkAndReplace(RefillOrder refillOrder){
        if (changesToBeMade.isEmpty()){
            changesToBeMade.add(refillOrder);
            System.out.println("");
            return;
        }

        for (RefillOrder refOrd : changesToBeMade){
                if (refOrd.getOrderID().equals(refillOrder.getOrderID())){
                    changesToBeMade.remove(refOrd);
                    changesToBeMade.add(refillOrder);
                    System.out.println("");
                    return;
                }
            }
        changesToBeMade.add(refillOrder);
        System.out.println("");
    }


    /**
     * Method to handle the event of clicking the back button and navigate to the appropriate main screen based on the current user's department.
     * @param event The event of clicking the back button.
     */
    @FXML
    void backToMainScreen(MouseEvent event) {
        Parent root = null;
        try {
            switch (UserController.getCurrentuser().getDepartment()){
                case "ceo":
                    root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
                    break;

                case "operations":
                    root = FXMLLoader.load(getClass().getResource("/gui/OperationsEmployeeScreens/operationsEmployeeMainScreen.fxml"));
                    break;

                case"area_manager_north":
                case"area_manager_south":
                case"area_manager_uae":
                    root = FXMLLoader.load(getClass().getResource("/gui/AreaManagersScreens/AreaManagerScreen.fxml"));
                    super.switchScreen(event, root);
                    break;

                default:
                    break;
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
        super.switchScreen(event, root);
    }

    /**
     * Method to handle the event of clicking the update button and upload the changes made to the database.
     * It also checks the current user's department and sends the appropriate message to the server.
     * @param event The event of clicking the update button.
     */
    @FXML
    void updateDataBase(MouseEvent event) {
        if (changesToBeMade.isEmpty() && UserController.getCurrentuser().getDepartment().equals("operations")){
            super.alertHandler("You have no changes to upload", false);
            return;
        }

        switch (UserController.getCurrentuser().getDepartment()){
            case"area_manager_north":
            case"area_manager_south":
            case"area_manager_uae":
            case "ceo":
                ArrayList<RefillOrder> tempList = new ArrayList<>();
                for (RefillOrder temp :requestList){
                    RefillOrder copy = temp;
                    copy.setAssignedEmployee(copy.getAssignedEmployeeBox().getValue());
                    tempList.add(copy);
                }
                for (RefillOrder refOrd : tempList) {
                    MessageHandler.setMessage(null);
                    ClientUI.chat.accept(new Message(refOrd, MessageFromClient.REQUEST_ASSIGN_EMPLOYEE_TO_REFILL_ORDER));

                    if (MessageHandler.getMessage().contains("Error")) {
                        super.alertHandler(MessageHandler.getMessage(), true);
                        return;
                    }
                }
                break;

            case "operations":
                for (RefillOrder refOrd : changesToBeMade) {
                    MessageHandler.setMessage(null);
                    ArrayList<String> orderData = new ArrayList<>();
                    orderData.add(refOrd.getMachineID());
                    orderData.add(refOrd.getProductID());
                    orderData.add(String.valueOf(refOrd.getNewAmount()));
                    ClientUI.chat.accept(new Message(orderData, MessageFromClient.REQUEST_UPDATE_MACHINE_PRODUCT_AMOUNT));

                    if (MessageHandler.getMessage().contains("Error")) {
                        super.alertHandler(MessageHandler.getMessage(), true);
                        return;
                    }
                }
                break;

        }

        super.alertHandler("Data successfully updated!", false);
        changesToBeMade.clear();
    }

    /**
     * Method to handle the event of clicking the exit button and close the program.
     * @param event The event of clicking the exit button.
     */
    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }
}
