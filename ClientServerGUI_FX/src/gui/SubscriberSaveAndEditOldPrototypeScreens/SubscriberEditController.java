package gui.SubscriberSaveAndEditOldPrototypeScreens;

import java.net.URL;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.ClientUI;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SubscriberEditController implements Initializable {
		
	@FXML
	private Label lblName;
	@FXML
	private Label lblSurname;
	@FXML
	private Label lblselect;
	
	@FXML
	private TableColumn<User, String>  txtFirstname;
	
	@FXML
	private TableColumn<User, String>   txtLastname;
	
	@FXML
	private TableColumn<User, String>   txtId;
	
	@FXML
	private TableColumn<User, String>   txtPhonenumber;
	
	@FXML
	private TableColumn<User, String>   txtEmailaddress;
	
	@FXML
	private TableColumn<User, String>  txtCreditcardnumber;
	
	@FXML
	private TableColumn<User, String>  txtSubscribernumber;
	
	@FXML
	private Button btnclose=null;
	
	@FXML
	private Button btnUpdate=null;
	
	@FXML
	private Button btnRefresh=null;
	
	@FXML
	 private TableView<User> Table;
	
	
	private ObservableList<User> observablesubs = FXCollections.observableArrayList();
	ObservableList<String> list;
	double xoffset;
	double yoffset;
	public void loadSubscriber() {
//		ClientUI.chat.accept("login"); TODO: here was the request for the Subscriber table. commented out because server does not support yet.
		Table.getItems().clear();
		
        for(User s : ChatClient.subs){
            if (s.toString().equals("null"))
                continue;
            User subsData = new User();
            subsData.setFirstname(s.getFirstname());
            subsData.setLastname(s.getLastname());
            subsData.setId(s.getId());
            subsData.setPhonenumber(s.getPhonenumber());
            subsData.setEmailaddress(s.getEmailaddress());

            Table.getItems().add(subsData);
        }
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		System.out.println();
		txtFirstname.setCellValueFactory(new PropertyValueFactory<>("Firstname"));
		txtLastname.setCellValueFactory(new PropertyValueFactory<>("Lastname"));
		txtId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		txtPhonenumber.setCellValueFactory(new PropertyValueFactory<>("Phonenumber"));
		txtEmailaddress.setCellValueFactory(new PropertyValueFactory<>("Emailaddress"));
		txtCreditcardnumber.setCellValueFactory(new PropertyValueFactory<>("Creditcardnumber"));
		txtSubscribernumber.setCellValueFactory(new PropertyValueFactory<>("Subscribernumber"));
		Table.setItems(observablesubs);
		loadSubscriber();
		
	}
	
	public void Closebtn(ActionEvent event) throws Exception {
		ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_DISCONNECT_CLIENT));
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/ConnectionScreens/ClientConnector.fxml"));
        Scene scene = new Scene(root);
        root.setOnMousePressed(event1 -> {
            xoffset = event1.getSceneX();
            yoffset = event1.getSceneY();
        });

        // event handler for when the mouse is pressed AND dragged to move the window
        root.setOnMouseDragged(event1 -> {
            primaryStage.setX(event1.getScreenX()-xoffset);
            primaryStage.setY(event1.getScreenY()-yoffset);
        });
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Academic Managment Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void Updatebtn(ActionEvent event) throws Exception {
        if (Table.getSelectionModel().getSelectedItem() != null) {
        	User selectedsub = Table.getSelectionModel().getSelectedItem();
            Stage primaryStage = new Stage();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SubscriberSaveAndEditOldPrototypeScreens/SubscriberSave.fxml"));
            
            Parent root = loader.<Parent>load();
            Scene scene = new Scene(root);
            SubscriberSaveController subscribersavecontroller = loader.<SubscriberSaveController>getController();
            subscribersavecontroller.loadSubscriber(selectedsub);
            root.setOnMousePressed(event1 -> {
                xoffset = event1.getSceneX();
                yoffset = event1.getSceneY();
            });

            // event handler for when the mouse is pressed AND dragged to move the window
            root.setOnMouseDragged(event1 -> {
                primaryStage.setX(event1.getScreenX()-xoffset);
                primaryStage.setY(event1.getScreenY()-yoffset);
            });
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setTitle("Save Subscriber Details");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
            lblselect.setVisible(false);
		 } 
        else {
        	lblselect.setVisible(true);
        }
	}
	
}
