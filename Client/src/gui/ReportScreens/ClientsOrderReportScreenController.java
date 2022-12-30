package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.MessageHandler;
import common.Reports.ClientReport;
import common.Reports.OrderReport;
import common.connectivity.User;
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
		
		HashMap<User,Integer> clientReportData =  (HashMap<User,Integer>) MessageHandler.getData();//get the data
		
		System.out.println("MAP:: " +clientReportData.toString());
		
//		for (Map.Entry<User, Integer> entry : clientReportData.entrySet()) {
//		    String key = entry.getKey().getFirstname();
//		    Integer value = entry.getValue();
//		    System.out.println("key: " +entry.getKey() + "   value:" + entry.getValue());
//
//		} 
//		
//		int totalOrders=0, totalClients=0, minOrders=9999,biggestOrders=0;
//		int numOfLines=0;
		
		
		//making the BarChart from data
		 //XYChart.Series<String, Integer> ser1= new XYChart.Series<>();// North
		// ser1.setName("Range of Purchase");


		// LIOR: i have commented this for loop because it conflicts with the map
//		 for(ClientReport clientor : clientReportData){
//			 	totalOrders+= clientor.getTotalOrders();
//			 	totalClients++;
//			 	if(minOrders>clientor.getTotalOrders())//calculate what is the Lowest number of orders from all client
//			 			minOrders=clientor.getTotalOrders();
//			 	if(biggestOrders<clientor.getTotalOrders())//calculate what is the Biggest number of orders from all client
//			 			biggestOrders=clientor.getTotalOrders();
//		 }
		// /////////////////////////////
		// numOfLines=(int) Math.sqrt(minOrders+biggestOrders);//calculate how many columns
		 //setting the data on the BarChart
//		 for(ClientReport clientor : clientReportData){
//		 ser1.getData().add(new XYChart.Data<String, Integer>("0-9",clientor.));
//		 ser1.getData().add(new XYChart.Data<String, Integer>("10-19",50));
//		 ser1.getData().add(new XYChart.Data<String, Integer>("20-29",30));
//		 ser1.getData().add(new XYChart.Data<String, Integer>("30-39",30));
//		 ser1.getData().add(new XYChart.Data<String, Integer>("40-49",30));
//		 ser1.getData().add(new XYChart.Data<String, Integer>("50-59",30));
//		 ser1.getData().add(new XYChart.Data<String, Integer>("60-69",30));
//		 ser1.getData().add(new XYChart.Data<String, Integer>("70-79",30));
//		 ser1.getData().add(new XYChart.Data<String, Integer>("80-89",30));
//		 ser1.getData().add(new XYChart.Data<String, Integer>("90-99",30));
//	    	 }
		 
		 
		 
		 //ClientChart.getData().addAll(ser1);

		//show analyze data on the screen
		DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);
		
	}

}
