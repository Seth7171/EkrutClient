package gui.SubscriberSaveAndEditOldPrototypeScreens;

import java.net.URL;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.ClientUI;
import common.connectivity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SubscriberSaveController implements Initializable {
	private User s;
	@FXML
	private Label lblName;
	@FXML
	private Label lblSurname;
	@FXML
	private Label lblsuccess;
	@FXML
	private Label lblFailed;
	
	@FXML
	private TextField txtID;
	
	@FXML
	private TextField txtFisrtName;
	
	@FXML
	private TextField txtLastNameame;
	
	@FXML
	private TextField txtCreditCardNumber;
	
	@FXML
	private TextField txtSubNumber;
	
	@FXML
	private Button btnclose=null;
	
	@FXML
	private Button btnSave=null;
		
	public void loadSubscriber(User s1) {
		s=s1;
		txtID.setText(s.getId());
		txtFisrtName.setText(s.getFirstname());
		txtLastNameame.setText(s.getLastname());		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
	}
	
	public void Closebtn(ActionEvent event) throws Exception {
        ((Node)event.getSource()).getScene().getWindow().hide();
	}
	
//	public void Savebtn(ActionEvent event) throws Exception {
//		User s1 = new User(null, null, null, null, null, null, null);
//		s1.setId(txtID.getText());
//		s1.setCreditcardnumber(txtCreditCardNumber.getText());
//		s1.setSubscribernumber(txtSubNumber.getText());
//		if (s1.getCreditcardnumber().contains(" ")) {
//			txtCreditCardNumber.clear();
//			txtCreditCardNumber.setPromptText("Please no Spaces");
//			return;
//		}
//		if (s1.getSubscribernumber().contains(" ")) {
//			txtSubNumber.clear();
//			txtSubNumber.setPromptText("Please no Spaces");
//			return;
//		}
//		if (s1.getCreditcardnumber().trim().isEmpty() || s1.getSubscribernumber() == null) {
//			s1.setCreditcardnumber("null");
//		}
//		if (s1.getSubscribernumber().trim().isEmpty()  || s1.getCreditcardnumber() == null) {
//			s1.setSubscribernumber("null");
//		}
//		System.out.println("updateUser " + s1.getId() + " " + s1.getCreditcardnumber() + " " + s1.getSubscribernumber());
//		ClientUI.chat.accept("updateUser " + s1.getId() + " " + s1.getCreditcardnumber() + " " + s1.getSubscribernumber());
//		if (!ChatClient.servermsg.equals("true")) {
//			System.out.println("Subscriber Save Failed");
//			lblFailed.setVisible(true);
//
//		} else {
//			System.out.println("Subscriber Save Succeed");
//			lblsuccess.setVisible(true);
//		}
//	}
	
	
}
