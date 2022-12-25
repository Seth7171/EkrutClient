package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.client.ChatClient;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class OrdersReportScreenController extends ScreenController implements Initializable{
	 @FXML
	    private Button backButton;
	 
	 @FXML
	    private Label DateChooseLabel;

	 @FXML
	    private Button exitApp;
	 @FXML
	    private BarChart<String,Integer> OrdersChart;

	    @FXML
	    private CategoryAxis x;

	    @FXML
	    private NumberAxis y;
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
		DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);
		
		//making the BarChart from data
		XYChart.Series<String, Integer> ser1= new XYChart.Series<>();
		ser1.setName("Haifa");
		ser1.getData().add(new XYChart.Data<String, Integer>("HA01", 200));
		ser1.getData().add(new XYChart.Data<String, Integer>("HA02", 140));
		ser1.getData().add(new XYChart.Data<String, Integer>("HA05", 36));
		ser1.getData().add(new XYChart.Data<String, Integer>("HA09", 32));
		
		XYChart.Series<String, Integer> ser2= new XYChart.Series<>();
		ser2.setName("Tel-Aviv");
		ser2.getData().add(new XYChart.Data<String, Integer>("TA01", 110));
		ser2.getData().add(new XYChart.Data<String, Integer>("TA02", 100));
		ser2.getData().add(new XYChart.Data<String, Integer>("TA08", 110));
		ser2.getData().add(new XYChart.Data<String, Integer>("TA03", 100));
		
		OrdersChart.getData().addAll(ser1,ser2);
	}

}
