package gui.ProductControlScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.RefillOrder;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private TableView<RefillOrder> requestTable;
    @FXML
    private Button exitButton;
    @FXML
    private Button goBackButton;
    @FXML
    private Button updateButton;

    private ArrayList<RefillOrder> refillOrderList;
    private RefillOrder selectedOrder;

    private ArrayList<RefillOrder> changesToBeMade;
    public static ObservableList<RefillOrder> requestList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changesToBeMade = new ArrayList<>();
        requestTable.setPlaceholder(new Label("no requests to display"));
        initCols();
        loadData();
    }


    private void initCols(){
        // order ID column
        orderidColumn.setCellValueFactory       (new PropertyValueFactory<>("orderID"));

        // product ID column
        productidColumn.setCellValueFactory     (new PropertyValueFactory<>("ProductID"));

        // machine ID column
        machineidColumn.setCellValueFactory     (new PropertyValueFactory<>("MachineID"));

        // creationDate column
        creationDateColumn.setCellValueFactory  (new PropertyValueFactory<>("creationDate"));

        // assigned employee column
        assignedEmployeeColumn.setCellValueFactory  (new PropertyValueFactory<>("assignedEmployee"));
        assignedEmployeeColumn.setCellFactory        (TextFieldTableCell.forTableColumn());
        assignedEmployeeColumn.setOnEditCommit       (event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setAssignedEmployee(event.getNewValue());
            checkAndReplace(event.getTableView().getItems().get(event.getTablePosition().getRow()));
        });

        // amount at request column
        amountAtRequestColumn.setCellValueFactory   (new PropertyValueFactory<>("amountAtRequest"));
        amountAtRequestColumn.setCellFactory        (TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        requestTable.setEditable(true);
    }


    private void loadData(){
        requestList = FXCollections.observableArrayList();
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_REFILL_ORDERS));
        requestList.addAll((ArrayList<RefillOrder>) MessageHandler.getData());
        requestTable.setItems(requestList);
    }

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


    @FXML
    void backToMainScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
        }catch (IOException exception){
            exception.printStackTrace();
        }
        super.switchScreen(event, root);
    }

    @FXML
    void updateEmployeesInDataBase(MouseEvent event) {
        if (changesToBeMade.isEmpty()){
            super.alertHandler("You have no changes to upload", false);
            return;
        }

        for (RefillOrder refOrd : changesToBeMade) {
            MessageHandler.setMessage(null);
            ClientUI.chat.accept(new Message(refOrd, MessageFromClient.REQUEST_ASSIGN_EMPLOYEE_TO_REFILL_ORDER));

            if (MessageHandler.getMessage().contains("Error")) {
                super.alertHandler(MessageHandler.getMessage(), true);
                return;
            }
        }

        super.alertHandler("Data successfully updated!", false);
        changesToBeMade.clear();
    }

    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }
}
