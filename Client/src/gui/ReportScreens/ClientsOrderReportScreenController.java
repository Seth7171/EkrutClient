package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import application.client.ChatClient;
import application.client.MessageHandler;
import common.Reports.ClientReport;
import common.connectivity.User;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/** Description of Clients Report Controller.
 * @author Ravid Goldin
 * @author Ben Ben Baruch
 */

public class ClientsOrderReportScreenController extends ScreenController implements Initializable{
	@FXML
	private Button backButton;

	@FXML
	private NumberAxis y;

	@FXML
	private Label DateChooseLabel;

	@FXML
	private Button exitApp;

	@FXML
	private BarChart<String, Integer> ClientChart;

	@FXML
	private Text avgPurchaseText;

	@FXML
	private Text leastOrdersClientIDText;

	@FXML
	private Text leastOrdersClientNameText;

	@FXML
	private Text leastOrdersClientTotalOrdersText;

	@FXML
	private Text mostOrdersClientIDText;

	@FXML
	private Text mostOrdersClientNameText;

	@FXML
	private Text mostOrdersClientTotalOrdersText;

	@FXML
	private Text totalPurchaseText;
	
	/**
     * Goes back to Report Main Screen.
     * @param event the mouse event that triggered the method call
     */
    @FXML
    void ClickBackButton(MouseEvent event) {
    	Parent root = null;
    	try {
		root = FXMLLoader.load(getClass().getResource("ReportsMainScreen.fxml"));
		}
    	catch (IOException exception){exception.printStackTrace();}
    	super.switchScreen(event, root);
	}
    
    
    /**
     * Log out from Ekrut.
     * @param event the mouse event that triggered the method call
     */
    @FXML
    void ClickLogOutButton(MouseEvent event) {
    	super.closeProgram(event, true);	
    }
    
    /**
     * Initializes the screen.
     * Calculate the amount of bars and ranges needed to the graph based on orders statistics.
     * Find users with highest or lowest amount of orders and show it on screen.
     * Set all data into graph and labels.
     * @param arg0 the location of the root object
     * @param arg1 the resources used to localize the root object
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int totalOrders=0, totalClients=0, minOrder=9999,biggestOrder=0,topRange=0,tmpi=0,tmpj=9999999,range=0;
		int columnRange=0,cnt=0, upperBound=0;
		boolean prime=true;
		User bigUser = null;
		User lowUser = null;

		HashMap<User,Integer> clientReportData =  (HashMap<User,Integer>) ((ClientReport) MessageHandler.getData()).getUserOrderAmount();//get the data
		
		for (Map.Entry<User, Integer> clientor : clientReportData.entrySet()) {//find the min and max orderValue
		    String key = clientor.getKey().getFirstname();//new key
		    Integer value = clientor.getValue();//value
		  	if(biggestOrder<value) {biggestOrder=value; bigUser=clientor.getKey();}//find the MAX Order
		  	if(minOrder>value) {minOrder=value; lowUser=clientor.getKey();}//find the MIN Order
		  	
		  	totalOrders += clientor.getValue();
		  	totalClients++;
		}
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
		for(int i=1; i<(topRange/2)+1; i++)//find the lowest  range between topRange multiplies number
			 	for(int j=0; j<=topRange;j++)
			 		if(i*j==(float)topRange) //
			 			if(tmpj-tmpi>j-i &&j-i>1 ) {tmpj=j; tmpi=i;}
		 columnRange=tmpj-tmpi;
	
		 System.out.println("columnRange: " +columnRange );
		 
		 //setting the data on the BarChart
		 for(int p=minOrder; p<=biggestOrder ;p=minOrder){// search for clients in specific area
			 int amount=0;
			 for(Map.Entry<User, Integer> clientor : clientReportData.entrySet()) {
				 if(clientor.getValue()>=minOrder && clientor.getValue()<=(minOrder+columnRange-1)) 
					 amount++;
				 if(amount>upperBound)upperBound=amount;
			 }
			 ser1.getData().add(new XYChart.Data<String, Integer>(Integer.toString(minOrder) + "-" + Integer.toString(minOrder+columnRange-1),amount));	
			 minOrder=minOrder+columnRange;
	     }
	     y.setUpperBound(upperBound+=5);
		 ClientChart.getData().addAll(ser1);
		 
		//show analyze data on the screen
		DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);
		mostOrdersClientIDText.setText("ID: " + bigUser.getId());
		mostOrdersClientNameText.setText("Name: " + capitalLetter(bigUser.getFirstname()) + " " + capitalLetter(bigUser.getLastname()));
		mostOrdersClientTotalOrdersText.setText("Total orders: " + clientReportData.get(bigUser));
		leastOrdersClientIDText.setText("ID: " + lowUser.getId());
		leastOrdersClientNameText.setText("Name: " + capitalLetter(lowUser.getFirstname()) + " " + capitalLetter(lowUser.getLastname()));
		leastOrdersClientTotalOrdersText.setText("Total orders: " + clientReportData.get(lowUser));
		totalPurchaseText.setText("Total Purchases: " + totalOrders);
		avgPurchaseText.setText("Average Purchases per Client: " + totalOrders/totalClients + " (" + totalClients + " clients)");
		
		
		for(XYChart.Series<String, Integer> series: ClientChart.getData()) {//display the value on each bar
		    for (XYChart.Data<String, Integer> data : series.getData()) {
		        Node node = data.getNode();
		            Pane pane = (Pane) node;
		            Label label = new Label(Integer.toString((int) data.getYValue()));
		            label.setTextFill(Color.WHITE);
		            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		            pane.getChildren().add(label);
		    }
		}
	}
	
	/**
	 * Make the string with capital letter first and all rest are lower-case letters. (yossi -> Yossi)
	 * @param str String input
	 * @return String with capital letter.
	 */
	public String capitalLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

}
