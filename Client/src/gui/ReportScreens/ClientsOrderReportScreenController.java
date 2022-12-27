package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.MessageHandler;
import common.Reports.OrderReport;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ClientsOrderReportScreenController extends ScreenController implements Initializable{
	 @FXML
	    private Button backButton;
	 
	  @FXML
	    private Label DateChooseLabel;

	    @FXML
	    private Button exitApp;
	    @FXML
	    private BarChart<String, Integer> ClientChart;

	    
    @FXML
    void ClickBackButton(MouseEvent event) {
    	Parent root = null;
    	try {
		root = FXMLLoader.load(getClass().getResource("ReportsMainScreen.fxml"));
		}
    	catch (IOException exception){exception.printStackTrace();}
    	super.switchScreen(event, root);
	}
    

    @FXML
    void ClickLogOutButton(MouseEvent event) {
    	super.closeProgram(event, true);	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		//making the BarChart from data
		 XYChart.Series<String, Integer> ser1= new XYChart.Series<>();// North
		 ser1.setName("Range of Purchase");	
		 ser1.getData().add(new XYChart.Data<String, Integer>("0-4",15));//set data
		 ser1.getData().add(new XYChart.Data<String, Integer>("5-9",30));
		 ser1.getData().add(new XYChart.Data<String, Integer>("10-14",50));
		 ser1.getData().add(new XYChart.Data<String, Integer>("15-20",30));
		 
		 
		 ClientChart.getData().addAll(ser1);

		//show analyze data on the screen
		DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);
		
	}

}
