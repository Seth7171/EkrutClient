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
import javafx.scene.chart.XYChart.Series;
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
	  private Label avgPurchaseLabel;

	  @FXML
	  private Label leastOrdersClientIDLabel;

	  @FXML
	  private Label leastOrdersClientNameLabel;

	  @FXML
	  private Label leastOrdersClientTotalOrdersLabel;

	  @FXML
	  private Label mostOrdersClientIDLabel;

	  @FXML
	  private Label mostOrdersClientNameLabel;

	  @FXML
	  private Label mostOrdersClientTotalOrdersLabel;

	  @FXML
	  private Label totalPurchaseLabel;
	    
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
		int totalOrders=0, totalClients=0, minOrder=9999,biggestOrder=0,topRange=0,tmpi=0,tmpj=9999999,range=0;
		int numOfLines=0, columnRange=0,cnt=0;
		boolean prime=true;
		String strMinOrder=null;
		String strBiggestOrder=null;
		User bigUser = null;
		User lowUser = null;

		HashMap<User,Integer> clientReportData =  (HashMap<User,Integer>) ((ClientReport) MessageHandler.getData()).getUserOrderAmount();//get the data
		
		for (Map.Entry<User, Integer> clientor : clientReportData.entrySet()) {//find the min and max orderValue
		    String key = clientor.getKey().getFirstname();//new key
		    Integer value = clientor.getValue();//value
		    System.out.println("key " +key + "   value:" + value);
		  	if(biggestOrder<value) {biggestOrder=value; strBiggestOrder=key; bigUser=clientor.getKey();}//find the MAX Order
		  	if(minOrder>value) {minOrder=value; strMinOrder=key; lowUser=clientor.getKey();}//find the MIN Order
		  	
		  	totalOrders += clientor.getValue();
		  	totalClients++;
		}
		 System.out.println("biggestOrder: "+biggestOrder + "minOrder: " + minOrder);
		//making the BarChart from data
		 XYChart.Series<String, Integer> ser1= new XYChart.Series<>();//ranges
		 ser1.setName("Range of Purchase");
		 //calculate the columns range
		 topRange=biggestOrder-minOrder;//topRange calculate 50-0
		
		for(int k=1; k<topRange;k++){//check if the topRange is a Prime
			if(topRange%k==0) 
				cnt++; 
			if(cnt>1) {prime=false;break;}//if not a prime
		}
		if(prime)topRange+=1;//if a the range is prime --> topRange+=1	
		System.out.println("prime: "+prime);
		for(int i=1; i<topRange/2; i++)//find the lowest  range between topRange multiplies number
			 	for(int j=0; j<=topRange;j++)
			 		if(i*j==(float)topRange)///// TODO: check it on my DB !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
			 			if(tmpj-tmpi>j-i) {tmpj=j; tmpi=i;}
		 columnRange=tmpj-tmpi;
	
		 System.out.println("columnRange: " +columnRange );
		 
		 //setting the data on the BarChart
		 for(int p=minOrder; p<=biggestOrder ;p=minOrder){// search for clients in specific area
			 int amount=0;
			 for(Map.Entry<User, Integer> clientor : clientReportData.entrySet()) {
				 if(clientor.getValue()>=minOrder && clientor.getValue()<=(minOrder+columnRange-1)) 
					 amount++;
			 }
			 ser1.getData().add(new XYChart.Data<String, Integer>(Integer.toString(minOrder) + "-" + Integer.toString(minOrder+columnRange-1),amount));
			 minOrder=minOrder+columnRange;
			// columnRange=columnRange+minOrder;
	    }
		 ClientChart.getData().addAll(ser1);
		 
		//show analyze data on the screen
		DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);
		mostOrdersClientIDLabel.setText("ID: " + bigUser.getId());
		mostOrdersClientNameLabel.setText("Name: " + capitalLetter(bigUser.getFirstname()) + " " + capitalLetter(bigUser.getLastname()));
		mostOrdersClientTotalOrdersLabel.setText("Total orders: " + clientReportData.get(bigUser));
		leastOrdersClientIDLabel.setText("ID: " + lowUser.getId());
		leastOrdersClientNameLabel.setText("Name: " + capitalLetter(lowUser.getFirstname()) + " " + capitalLetter(lowUser.getLastname()));
		leastOrdersClientTotalOrdersLabel.setText("Total orders: " + clientReportData.get(lowUser));
		totalPurchaseLabel.setText("Total Purchases: " + totalOrders);
		avgPurchaseLabel.setText("Average Purchases per Client: " + totalOrders/totalClients + " (" + totalClients + " clients)");
	}
	
	// return the string with first letter capital and all lowercase letters
	public String capitalLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

}
