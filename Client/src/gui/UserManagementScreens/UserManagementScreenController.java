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

/**
*
* The UserManagementScreenController class is a controller class that is responsible for handling the User Management screen.
* It implements the Initializable interface and contains methods that handle the user interactions with the screen such as
* loading the users data into the table, handling the back button, update and revert changes buttons, and exiting the program.
* The class contains several FXML variables that are used to represent the different components of the screen such as
* TableView, TableColumn, and Button. It also contains a static ObservableList of UserRow objects and an ArrayList of
* UserRow objects.
* @author Lior
*/
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

    /**
     * Initializes the User Management screen, initializing the columns of the table and loading
     * the users from the server to the table.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersToUpdate = new ArrayList<>();
        initCols();
        loadUsersToTable();
    }

    /**
     * Loads all of the users from the server to the table view.
     */
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

    /**
     * Initializes the columns of the table view
     */
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

    /**
     * Resets the changes made to the user's status by reloading the data from the server.
     *
     * @param event The mouse event that triggered this method.
     */
    @FXML
    void resetChanges(MouseEvent event) {
        loadUsersToTable();
    }

    /**
     * Exits the program.
     *
     * @param event The mouse event that triggered this method.
     */
    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }

    /**
     * Goes back to the previous screen.
     *
     * @param event The mouse event that triggered this method.
     */
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

                case"area_manager_north":
                case"area_manager_south":
                case"area_manager_uae":
                    root = FXMLLoader.load(getClass().getResource("/gui/AreaManagersScreens/AreaManagerScreen.fxml"));
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

    /**
     * Updates the database with the changes made to the user's status.
     *
     * @param event The mouse event that triggered this method.
     */
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