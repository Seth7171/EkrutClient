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

		HashMap<User,Integer> clientReportData =  (HashMap<User,Integer>) ((ClientReport) MessageHandler.getData()).getUserOrderAmount();//get the data
		
		for (Map.Entry<User, Integer> clientor : clientReportData.entrySet()) {//find the min and max orderValue
		    String key = clientor.getKey().getFirstname();//new key
		    Integer value = clientor.getValue();//value
		    System.out.println("key " +key + "   value:" + value);
		  	if(biggestOrder<value) {biggestOrder=value; strBiggestOrder=key;}//find the MAX Order
		  	if(minOrder>value) {minOrder=value; strMinOrder=key;}//find the MIN Order
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
			 		if(i*j==(float)topRange)///// TODO: check it !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
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
		
	}

}
