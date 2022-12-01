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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Faculty;
import logic.Student;

public class SucscriberEditController implements Initializable {
	private Student s;
		
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
	
	
	
	ObservableList<String> list;
		
	public void loadStudent(Student s1) {
		this.s=s1;
		this.txtID.setText(s.getId());
		this.txtName.setText(s.getPName());
		this.txtSurname.setText(s.getLName());		
		//this.cmbFaculty.setValue(s.getFc().getFName());
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		//setFacultyComboBox();		
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
		Student s1 = new Student(null, null, null, null);
		s1.setId(txtID.getText());
		s1.setPName(txtName.getText());
		s1.setLName(txtSurname.getText());

		//ClientUI.chat.accept("1," + this.txtID.getText() + "," + this.txtName.getText() + "," + this.txtSurname.getText() + "," + (String)cmbFaculty.getValue());
		ClientUI.chat.accept("updateUser " + this.txtID.getText() + " " + this.txtName.getText() + " " + this.txtSurname.getText() + " " + " ");
		if (ChatClient.s1.getId().equals("Error")) {
			System.out.println("Student ID Not Found");

		} else {
			System.out.println("Student ID Updated");
		}
	}
	
}
