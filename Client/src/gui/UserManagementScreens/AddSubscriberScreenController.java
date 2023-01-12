package gui.UserManagementScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import common.connectivity.Customer;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddSubscriberScreenController  extends ScreenController implements Initializable {

    @FXML
    private TableView<TableCustomer> customerTable;
    @FXML
    private TableColumn<TableCustomer, String > LnameCol;
    @FXML
    private TableColumn<TableCustomer, String > PhoneCol;
    @FXML
    private TableColumn<TableCustomer, Integer> subNumberCol;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn<TableCustomer, String > creditCol;
    @FXML
    private TableColumn<TableCustomer, ChoiceBox<String>> customerStatusCol;
    @FXML
    private TableColumn<TableCustomer, String > eMailCol;
    @FXML
    private Button exitButton;
    @FXML
    private TableColumn<TableCustomer, String > fnameCol;
    @FXML
    private TableColumn<TableCustomer, String > idCol;
    @FXML
    private Button updateUserButton;
    public static ObservableList<TableCustomer> customerList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCols();
        loadCustomersToTable();

    }

    private void initCols() {
        // id column column
        idCol.setCellValueFactory               (new PropertyValueFactory<>("id"));

        // first name column
        fnameCol.setCellValueFactory            (new PropertyValueFactory<>("firstname"));

        // last name column
        LnameCol.setCellValueFactory            (new PropertyValueFactory<>("lastname"));

        // phone number column
        PhoneCol.setCellValueFactory            (new PropertyValueFactory<>("phonenumber"));

        // last name column
        eMailCol.setCellValueFactory            (new PropertyValueFactory<>("emailaddress"));

        // id column column
        creditCol.setCellValueFactory           (new PropertyValueFactory<>("creditCardNumber"));

        // status column
        customerStatusCol.setCellValueFactory   (new PropertyValueFactory<>("customerStatusBox"));

        // subscriber number column
        subNumberCol.setCellValueFactory   (new PropertyValueFactory<>("subscriberNumber"));
    }

    private void loadCustomersToTable(){
        customerList = FXCollections.observableArrayList();
        if (!customerList.isEmpty())
            customerList.clear();
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_ALL_CUSTOMER_DATA));

        for (Customer customer : (ArrayList<Customer>) MessageHandler.getData()){
            //if (custome)
            TableCustomer tableCustomer = new TableCustomer();
            tableCustomer.setId(customer.getId());
            tableCustomer.setFirstname(customer.getFirstname());
            tableCustomer.setLastname(customer.getLastname());
            tableCustomer.setPhonenumber(customer.getPhonenumber());
            tableCustomer.setEmailaddress(customer.getEmailaddress());
            tableCustomer.setCreditCardNumber(customer.getCreditCardNumber());
            tableCustomer.setSub(customer.isSub());
            tableCustomer.setSubscriberNumber(customer.getSubscriberNumber());
            customerList.add(tableCustomer);
        }

        for (TableCustomer customer : customerList){
            ChoiceBox<String> statusBox = new ChoiceBox<>();
            statusBox.getItems().add("subscriber");
            statusBox.getItems().add("not a subscriber");
            if (customer.isSub()){
                statusBox.setValue("subscriber");
            }
            else
                statusBox.setValue("not a subscriber");
            customer.setCustomerStatusBox(statusBox);
        }
        customerTable.setItems(customerList);
    }

    @FXML
    void updateCustomerInDatabase(MouseEvent event) {
        ArrayList<String> customerToUpdate = new ArrayList<>();
        for (TableCustomer tableCustomer : customerList){
            customerToUpdate.add(tableCustomer.getId());
            customerToUpdate.add(tableCustomer.getCustomerStatusBox().getValue());
            ClientUI.chat.accept(new Message(customerToUpdate, MessageFromClient.REQUEST_UPDATE_CUSTOMER_STATUS));
            customerToUpdate.clear();
        }
        loadCustomersToTable();
    }

    @FXML
    void goBack(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../CustomerServiceEmployeeScreens/CustomerServiceEmployeeScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //switchScreen(event, root);
        super.switchScreen(event, root);
    }
    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }
}
