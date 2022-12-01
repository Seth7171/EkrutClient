package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Subscriber;

public class SucscriberEditController implements Initializable {
	private Subscriber s;
		
	@FXML
	private Label lblName;
	@FXML
	private Label lblSurname;
	@FXML
	private Label lblFaculty;
	
	@FXML
	private TableColumn<Subscriber,String> txtFirstname;
	
	@FXML
	private TableColumn<Subscriber,String> txtLastname;
	
	@FXML
	private TableColumn<Subscriber,String> txtId;
	
	@FXML
	private TableColumn<Subscriber,String> txtPhonenumber;
	
	@FXML
	private TableColumn<Subscriber,String> txtEmailaddress;
	
	@FXML
	private TableColumn<Subscriber,String> txtCreditcardnumber;
	
	@FXML
	private TableColumn<Subscriber,String> txtSubscribernumber;
	
	@FXML
	private Button btnclose=null;
	
	@FXML
	private Button btnSave=null;
	
	 @FXML
	  private TableView<Subscriber> Table;
	
	
	
	ObservableList<String> list;
		
	public void loadSubscriber(Subscriber s1) {
		this.s=s1;
		this.txtFirstname.setText(s.getFirstname());
		this.txtLastname.setText(s.getLastname());
		this.txtId.setText(s.getId());
		this.txtPhonenumber.setText(s.getPhonenumber());
		this.txtEmailaddress.setText(s.getEmailaddress());
		this.txtCreditcardnumber.setText(s.getCreditcardnumber());
		this.txtSubscribernumber.setText(s.getSubscribernumber());		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		ClientUI.chat.accept("login");
		System.out.println(ChatClient.subs.get(0));
	   /* if (ChatClient.subs != null) {
	        ObservableList<Subscriber> Subs = FXCollections.observableArrayList(ChatClient.subs);
	        this.TableView.setItems(Subs);*/
		//for (int i  = 0 ; i<ChatClient.subs.size() ; i++) {	
			//loadSubscriber(ChatClient.subs.get(0));
		//}
		
	}
	
	public void Closebtn(ActionEvent event) throws Exception {
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientConnector.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/ClientConnector.css").toExternalForm());
        primaryStage.setTitle("Academic Managment Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void Savebtn(ActionEvent event) throws Exception {
		Subscriber s1 = new Subscriber(null, null, null, null, null, null, null);
		s1.setFirstname(txtFirstname.getText());
		s1.setLastname(txtLastname.getText());
		s1.setId(txtId.getText());
		s1.setPhonenumber(txtPhonenumber.getText());
		s1.setEmailaddress(txtEmailaddress.getText());
		s1.setCreditcardnumber(txtCreditcardnumber.getText());
		s1.setSubscribernumber(txtSubscribernumber.getText());

		
		//ClientUI.chat.accept("1," + this.txtID.getText() + "," + this.txtName.getText() + "," + this.txtSurname.getText() + "," + (String)cmbFaculty.getValue());
		ClientUI.chat.accept("updateUser " + this.txtId.getText() + " " + this.txtCreditcardnumber.getText()+ " " + this.txtSubscribernumber.getText() + " ");
		if (ChatClient.s1.getId().equals("Error")) {
			System.out.println("Subscriber ID Not Found");

		} else {
			System.out.println("Subscriber ID Updated");
		}
	}
	
}
