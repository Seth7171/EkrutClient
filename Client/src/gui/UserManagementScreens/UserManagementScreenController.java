package gui.UserManagementScreens;

import application.user.UserController;
import common.connectivity.User;
import gui.ScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserManagementScreenController extends ScreenController implements Initializable {
    @FXML
    private TableView<User> UserManagementTable;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> LastNameColumn;
    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> statusColumn;

    @FXML
    private Button backButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button updateButton;
    public static ObservableList<User> userList;
    ArrayList<User> usersToUpdate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersToUpdate = new ArrayList<>();
        initCols();
        getUsers();
        loadUsersToTable();
    }

    private void loadUsersToTable() {
        userList = FXCollections.observableArrayList();
        userList.add(UserController.getCurrentuser());
        UserManagementTable.setItems(userList);
    }

    private void initCols() {
        // first name column
        firstNameColumn.setCellValueFactory (new PropertyValueFactory<>("firstname"));

        // last name column
        LastNameColumn.setCellValueFactory  (new PropertyValueFactory<>("lastname"));

        // id column column
        idColumn.setCellValueFactory        (new PropertyValueFactory<>("id"));

        // TODO: fix it!!!!
        statusColumn.setCellValueFactory    (new PropertyValueFactory<>("status"));
        ChoiceBox <String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList());
        statusColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn());
        //    statusColumn.setOnEditCommit       (event -> {
//            event.getTableView().getItems().get(event.getTablePosition().getRow()));
//            checkAndReplace(event.getTableView().getItems().get(event.getTablePosition().getRow()));
//        });
        // TODO: /////////////////////////////////////////////////////////////////////////////
    }

    private void getUsers() {
        // todo: request users from database
        // todo: add users to userList
    }

    private void addDataToChangesList(){
        // todo: add data to list
    }


    @FXML
    void exit(MouseEvent event) {
        usersToUpdate.add(UserController.getCurrentuser());
        if (usersToUpdate.isEmpty())
            super.closeProgram(event, true);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm()); // todo: fix alert appearance...
        alert.getDialogPane().getStyleClass().add("alert");
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?\n All changes will be lost.");

        ButtonType buttonTypeYes = new ButtonType("Yes, I'm sure!");
        ButtonType buttonTypeNo = new ButtonType("Not sure...");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes){
            super.closeProgram(event, true);
        }
    }

    @FXML
    void goBack(MouseEvent event) {

    }

    @FXML
    void updateDataBase(MouseEvent event) {

    }
}
