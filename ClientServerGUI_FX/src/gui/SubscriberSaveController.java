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

public class SubscriberSaveController implements Initializable {
	private Subscriber s;
	@FXML
	private Label lblName;
	@FXML
	private Label lblSurname;
	@FXML
	private Label lblFaculty;
	
	@FXML
	private TextField txtID;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtSurname;
	
	@FXML
	private Button btnclose=null;
	
	@FXML
	private Button btnSave=null;
		
	public void loadSubscriber(Subscriber s1) {
		s=s1;
		txtID.setText(s.getId());
		this.txtName.setText(s.getFirstname());
		this.txtSurname.setText(s.getLastname());		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
	}
	
	public void Closebtn(ActionEvent event) throws Exception {
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/AcademicFrame.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/AcademicFrame.css").toExternalForm());
        primaryStage.setTitle("Academic Managment Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void Savebtn(ActionEvent event) throws Exception {
		Subscriber s1 = new Subscriber(null, null, null, null, null, null, null);
		s1.setId(txtID.getText());
		s1.setCreditcardnumber(txtName.getText());
		s1.setSubscribernumber(txtSurname.getText());

		//ClientUI.chat.accept("1," + this.txtID.getText() + "," + this.txtName.getText() + "," + this.txtSurname.getText() + "," + (String)cmbFaculty.getValue());
		ClientUI.chat.accept("change " + this.txtID.getText() + " " + this.txtName.getText() + " " + this.txtSurname.getText() + " " + "");
		if (ChatClient.s1.getId().equals("Error")) {
			System.out.println("Student ID Not Found");

		} else {
			System.out.println("Student ID Updated");
		}
	}
	
	
}
