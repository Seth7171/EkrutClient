package gui;

import java.awt.print.Book;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Subscriber;

public class SubscriberEditController implements Initializable {
	private Subscriber s;
		
	@FXML
	private Label lblName;
	@FXML
	private Label lblSurname;
	@FXML
	private Label lblFaculty;
	
	@FXML
	private TableColumn<Subscriber, String>  txtFirstname;
	
	@FXML
	private TableColumn<Subscriber, String>   txtLastname;
	
	@FXML
	private TableColumn<Subscriber, String>   txtId;
	
	@FXML
	private TableColumn<Subscriber, String>   txtPhonenumber;
	
	@FXML
	private TableColumn<Subscriber, String>   txtEmailaddress;
	
	@FXML
	private TableColumn<Subscriber, String>  txtCreditcardnumber;
	
	@FXML
	private TableColumn<Subscriber, String>  txtSubscribernumber;
	
	@FXML
	private Button btnclose=null;
	
	@FXML
	private Button btnUpdate=null;
	
	@FXML
	 private TableView<Subscriber> Table;
	
	
	private ObservableList<Subscriber> observablesubs = FXCollections.observableArrayList();
	ObservableList<String> list;
		
	public void loadSubscriber(Subscriber s1) {
		ClientUI.chat.accept("login");
		Table.getItems().removeAll();
		
        for(Subscriber s : ChatClient.subs){
            if (s.toString().equals("null"))
                continue;
            Subscriber subsData = new Subscriber("", "" ,"","","","","");
            subsData.setFirstname(s.getFirstname());
            subsData.setLastname(s.getLastname());
            subsData.setId(s.getId());
            subsData.setPhonenumber(s.getPhonenumber());
            subsData.setEmailaddress(s.getEmailaddress());
            subsData.setCreditcardnumber(s.getCreditcardnumber());
            subsData.setSubscribernumber(s.getSubscribernumber());
            Table.getItems().add(subsData);
        }
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		System.out.println(ChatClient.subs);
		txtFirstname.setCellValueFactory(new PropertyValueFactory<>("Firstname"));
		txtLastname.setCellValueFactory(new PropertyValueFactory<>("Lastname"));
		txtId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		txtPhonenumber.setCellValueFactory(new PropertyValueFactory<>("Phonenumber"));
		txtEmailaddress.setCellValueFactory(new PropertyValueFactory<>("Emailaddress"));
		txtCreditcardnumber.setCellValueFactory(new PropertyValueFactory<>("Creditcardnumber"));
		txtSubscribernumber.setCellValueFactory(new PropertyValueFactory<>("Subscribernumber"));
		Table.setItems(observablesubs);
		loadSubscriber(s);
		
	}
	
	public void Closebtn(ActionEvent event) throws Exception {
		ClientUI.chat.accept("disconnect");
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientConnector.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/ClientConnector.css").toExternalForm());
        primaryStage.setTitle("Academic Managment Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void Updatebtn(ActionEvent event) throws Exception {
        if (Table.getSelectionModel().getSelectedItem() != null) {
            Subscriber selectedsub = Table.getSelectionModel().getSelectedItem();
            Stage primaryStage = new Stage();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SubscriberSave.fxml"));
            Parent root = loader.<Parent>load();
            Scene scene = new Scene(root);
            SubscriberSaveController subscribersavecontroller = loader.<SubscriberSaveController>getController();
            subscribersavecontroller.loadSubscriber(selectedsub);
            primaryStage.setTitle("Save Subscriber Details");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
		 } 
	}
	
}
