package gui.CEOScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CEOMainScreenController extends ScreenController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button grabOrderButton;

    @FXML
    private Button newOrderButton;

    @FXML
    private Text welcomeBackText;

    @FXML
    private Button viewRefilOrdersButton;
    @FXML
    private Button manageUsersButton;
    @FXML
    private Button viewReportsButton;

    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }

    @FXML
    void logOut(MouseEvent event) {
        ArrayList<String> cred = new ArrayList<String>();
        cred.add(UserController.getCurrentuser().getUsername());
        ClientUI.chat.accept(new Message(cred, MessageFromClient.REQUEST_LOGOUT));

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/LogInScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    @FXML
    void openUserManagementScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserManagementScreens/userManagementScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    @FXML
    void openViewReportsScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ReportScreens/ReportsMainScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }
    @FXML
    void openManageProductsScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ProductControlScreens/productManagementScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    @FXML
    void openRefilOrdersScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/ProductControlScreens/RefillOrderScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    @FXML
    void openCustomerScreen(MouseEvent event) {
        Parent root = null;
        try {
            ClientUI.chat.accept(new Message(UserController.getCurrentuser().getId(), MessageFromClient.REQUEST_CHECK_IF_SUB));
            if (MessageHandler.getMessage().contains("not"))
                root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ProductCatalogScreen.fxml"));
            else
                root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/DeliveryOprtionsScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    @FXML
    void openGrabOrderScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/GrabOrderScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.switchScreen(event, root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeBackText.setText("Welcome back " + UserController.getCurrentuser().getFirstname());
    }
}
