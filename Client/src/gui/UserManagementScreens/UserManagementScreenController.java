package gui.UserManagementScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
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

public class UserManagementScreenController extends ScreenController implements Initializable {
    @FXML
    private TableView<UserRow> UserManagementTable;
    @FXML
    private TableColumn<UserRow, String> firstNameColumn;
    @FXML
    private TableColumn<UserRow, String> LastNameColumn;
    @FXML
    private TableColumn<UserRow, String> idColumn;
    @FXML
    private TableColumn<UserRow, ChoiceBox> statusColumn;
    @FXML
    private Button backButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button revertChangesButton;
    public static ObservableList<UserRow> userList;
    ArrayList<UserRow> usersToUpdate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersToUpdate = new ArrayList<>();
        initCols();
        loadUsersToTable();
    }

    private void loadUsersToTable() {
        userList = FXCollections.observableArrayList();
        if (!userList.isEmpty())
            userList.clear();

        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_CUSTOMERS_FROM_USER_TABLE));
        for (User user : (ArrayList<User>) MessageHandler.getData()){
            UserRow userRow = new UserRow();
            userRow.setFirstname(user.getFirstname());
            userRow.setLastname(user.getLastname());
            userRow.setId(user.getId());

            ChoiceBox<String> status = new ChoiceBox<>(FXCollections.observableArrayList("approved", "not approved", "frozen"));
            status.setMinWidth(95);
            status.setValue(user.getStatus());
            userRow.setStatus(status);
            userList.add(userRow);
        }
        UserManagementTable.setItems(userList);
    }

    private void initCols() {
        // first name column
        firstNameColumn.setCellValueFactory (new PropertyValueFactory<>("firstname"));

        // last name column
        LastNameColumn.setCellValueFactory  (new PropertyValueFactory<>("lastname"));

        // id column column
        idColumn.setCellValueFactory        (new PropertyValueFactory<>("id"));

        // status column
        statusColumn.setCellValueFactory    (new PropertyValueFactory<>("status"));
    }

    @FXML
    void resetChanges(MouseEvent event) {
        loadUsersToTable();
    }

    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }

    @FXML
    void goBack(MouseEvent event) {
        Parent root = null;
        try {
            switch (UserController.getCurrentuser().getDepartment()) {
                case "customer_service":
                    root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceEmployeeScreens/CustomerServiceEmployeeScreen.fxml"));
                    break;

                case"ceo":
                    root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
                    break;

                default:
                    System.out.println("Unknown!");
                    // TODO: maybe add UnknownScreenException later??
            }
            super.switchScreen(event, root);
        }
        // Catch any exceptions that may occur
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateDataBase(MouseEvent event) {
        ArrayList<User> users = new ArrayList<>();
        for (UserRow user : userList){
            User userToList = new User();
            userToList.setFirstname(user.getFirstname());
            userToList.setLastname(user.getLastname());
            userToList.setId(user.getId());
            userToList.setStatus(user.getStatus().getValue().toString());
            users.add(userToList);
        }
        ClientUI.chat.accept(new Message(users, MessageFromClient.REQUEST_UPDATE_USERS_STATUSES));
        super.alertHandler(MessageHandler.getMessage(), MessageHandler.getMessage().contains("Error"));
    }
}


//    private void loadUsersToTable() {
//        if (!userList.isEmpty())
//            userList.clear();
//        userList = FXCollections.observableArrayList();
//
//        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_CUSTOMERS_FROM_USER_TABLE));
//        for (User user : (ArrayList<User>) MessageHandler.getData()){
//            UserRow userRow = new UserRow();
//            userRow.setFirstname(user.getFirstname());
//            userRow.setLastname(user.getLastname());
//            userRow.setId(user.getId());
//
//            ChoiceBox<String> status = new ChoiceBox<>(FXCollections.observableArrayList("approved", "not approved", "frozen"));
//            status.setMinWidth(95);
//            status.setOnMouseClicked(event -> {
//                TableCell<UserRow, ChoiceBox> cell = (TableCell<UserRow, ChoiceBox>) status.getParent();
//                int rowIndex = cell.getTableRow().getIndex();
//
//                TableView<UserRow> table = cell.getTableView();
//                ObservableList<UserRow> items = table.getItems();
//                UserRow item = items.get(rowIndex);
//                if (item.getStatus().getValue() != null)
//                    addDataToChangesList(item);
//            });
//            status.setValue(user.getStatus());
//            userRow.setStatus(status);
//
//            userList.add(userRow);
//        }
//        UserManagementTable.setItems(userList);
//    }