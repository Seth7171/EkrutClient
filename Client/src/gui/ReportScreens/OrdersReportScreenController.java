package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import application.client.ChatClient;
import application.client.MessageHandler;
import common.Reports.InventoryReport;
import common.Reports.OrderReport;
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
	    private Label bestIDLabel;

	    @FXML
	    private Label bestLocationLabel;

	    @FXML
	    private Label totalOrdersBestLabel;

	    @FXML
	    private Label totalOrdersWrostLabel;

	    @FXML
	    private Label worstIDLabel;

	    @FXML
	    private Label wrostLocationLabel;
	    
	    
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
		int  ArrlocationSize=0;
		DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);
		
		 OrderReport orderReportData =  (OrderReport) MessageHandler.getData();//getting data from server

		//making the BarChart from data
		 int totalOrdersBestSeller=0;
		 String strIDofBestSeller=null, strLocationOfBest=null;
		 int totalOrdersWrostSeller = 999999;
		 String strIDofWrostSeller=null, strLocationOfWrost=null;
		 
		 XYChart.Series<String, Integer> ser1= new XYChart.Series<>();// tel aviv
		 XYChart.Series<String, Integer> ser2= new XYChart.Series<>();//haifa
		 XYChart.Series<String, Integer> ser3= new XYChart.Series<>();//karmiel
		 ser1.setName("Tel-Aviv");	
		 ser2.setName("Haifa");
		 ser3.setName("Karmiel");	
		for(Map.Entry<String,Integer>ite : orderReportData.getMachineAndAmount().entrySet()){//run on the HASHMAP keys
					
			if(ite.getKey().contains("TA")){//Tel-Aviv
				ser1.getData().add(new XYChart.Data<String, Integer>(ite.getKey(), ite.getValue()));//set the key and value
			}
			if(ite.getKey().contains("HA")){//Haifa
				ser2.getData().add(new XYChart.Data<String, Integer>(ite.getKey(), ite.getValue()));//set the key and value
			}
			if(ite.getKey().contains("KA")){//Karmiel	
				ser3.getData().add(new XYChart.Data<String, Integer>(ite.getKey(), ite.getValue()));//set the key and value
			}
			// analyze data for best seller
			if(ite.getValue() > totalOrdersBestSeller) { 
				totalOrdersBestSeller=ite.getValue(); // get total orders of best machine
				strIDofBestSeller=ite.getKey(); //get ID of best machine
				if(strIDofBestSeller.startsWith("TA"))
					strLocationOfBest = "Tel Aviv";
				else if(strIDofBestSeller.startsWith("HA"))
					strLocationOfBest = "Haifa";
				else if(strIDofBestSeller.startsWith("KA"))
					strLocationOfBest = "Karmiel";
			}
			
			if(ite.getValue() < totalOrdersWrostSeller) { 
				totalOrdersWrostSeller=ite.getValue(); // get total orders of worst machine
				strIDofWrostSeller=ite.getKey(); //get ID of best machine
				if(strIDofWrostSeller.startsWith("TA"))
					strLocationOfWrost = "Tel Aviv";
				else if(strIDofWrostSeller.startsWith("HA"))
					strLocationOfWrost = "Haifa";
				else if(strIDofWrostSeller.startsWith("KA"))
					strLocationOfWrost = "Karmiel";
			}
		}
			
		OrdersChart.getData().addAll(ser1,ser2,ser3);

		//show analyze data
		bestIDLabel.setText("ID: " + strIDofBestSeller);// show the ID  of the best seller
		bestLocationLabel.setText("Location: " + strLocationOfBest);
		totalOrdersBestLabel.setText("Total orders: " + totalOrdersBestSeller);
		worstIDLabel.setText("ID: " + strIDofWrostSeller);// show the ID  of the best seller
		wrostLocationLabel.setText("Location: " + strLocationOfWrost);
		totalOrdersWrostLabel.setText("Total orders: " + totalOrdersWrostSeller);
		
		//TODO:           // calculate The area with the MOST\LOWEST orders..//
		//for example: HAIFA: ((Total Orders = 200+140+36+32 / NumbersOfMachines =4 ))  =====  408\4 =102        LOWEST
		//             Tel-Aviv: ((Total Orders = 110+100+110+100 / NumbersOfMachines =4))  =====  420\4 =105    MOST
	}
}		
		

  
		


