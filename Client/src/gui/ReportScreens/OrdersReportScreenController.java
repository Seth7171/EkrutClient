package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import application.client.ChatClient;
import application.client.MessageHandler;
import common.Reports.InventoryReport;
import common.Reports.OrderReport;
import common.orders.Order;
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
	    private Label bestArea;

	    @FXML
	    private Label worstArea;
	    
	    
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

		ArrayList<OrderReport> orderReportData = (ArrayList<OrderReport>)  MessageHandler.getData();

		//making the BarChart from data
		 int totalOrdersBestSeller=0,NorthMachinesAmount=0,SouthMachinesAmount=0,UAEMachinesAmount=0,totalOrdersNorth=0,totalOrdersSouth=0,totalOrdersUAE=0,totalOrdersBestArea=0;
		 String strIDofBestSeller=null, strLocationOfBest=null ,strBestArea=null;
		 int totalOrdersWrostSeller = 999999;
		 String strIDofWrostSeller=null, strLocationOfWrost=null,strWorstArea=null;
		 double totalNorth=0,totalSouth=0, totalUAE=0;
		 
		 XYChart.Series<String, Integer> ser1= new XYChart.Series<>();// North
		 XYChart.Series<String, Integer> ser2= new XYChart.Series<>();//South
		 XYChart.Series<String, Integer> ser3= new XYChart.Series<>();//UAE
		 ser1.setName("North");	
		 ser2.setName("South");
		 ser3.setName("UAE");	
		for(OrderReport ordrep : orderReportData){

			if (ordrep.getMachineLocation().equals("haifa")) {
				ser1.getData().add(new XYChart.Data<String, Integer>(ordrep.getMachineid(), ordrep.getNumberOfOrders()));//set data
				NorthMachinesAmount++;}

			if (ordrep.getMachineLocation().equals("tel aviv")) {
				ser2.getData().add(new XYChart.Data<String, Integer>(ordrep.getMachineid(), ordrep.getNumberOfOrders()));//set data
				SouthMachinesAmount++;}

				
			if (ordrep.getMachineLocation().equals("karmiel")) {
				ser3.getData().add(new XYChart.Data<String, Integer>(ordrep.getMachineid(), ordrep.getNumberOfOrders()));//set data
				UAEMachinesAmount++;}
			
			// analyze data for best seller
			if(ordrep.getNumberOfOrders() > totalOrdersBestSeller) {
				totalOrdersBestSeller = ordrep.getNumberOfOrders(); // get total orders of best machine
				strIDofBestSeller = ordrep.getMachineid(); //get ID of best machine
				strLocationOfBest = ordrep.getMachineLocation();
			}
			
			if(ordrep.getNumberOfOrders() < totalOrdersWrostSeller) {
				totalOrdersWrostSeller=ordrep.getNumberOfOrders(); // get total orders of worst machine
				strIDofWrostSeller = ordrep.getMachineid(); //get ID of worst machine
				strLocationOfWrost = ordrep.getMachineLocation();
			}
			// analyze data for best AREA
			if(ordrep.getMachineLocation().equals("haifa")) {
				totalOrdersNorth += ordrep.getNumberOfOrders();
				NorthMachinesAmount++;
			}
			if(ordrep.getMachineLocation().equals("tel aviv")) {
				totalOrdersSouth += ordrep.getNumberOfOrders();
				SouthMachinesAmount++;
			}
			if(ordrep.getMachineLocation().equals("karmiel")) {
				totalOrdersUAE += ordrep.getNumberOfOrders();
				UAEMachinesAmount++;
			}
		}
			
		OrdersChart.getData().addAll(ser1,ser2,ser3);
		
		//calculate data for who is the best area
		totalNorth=totalOrdersNorth/NorthMachinesAmount;
		totalSouth=totalOrdersSouth/SouthMachinesAmount;
		totalUAE=totalOrdersUAE/UAEMachinesAmount;
		if((totalNorth>totalSouth) && (totalSouth>totalUAE))//NORTH is best area + UAE is the worst
		{strBestArea="North"; strWorstArea="UAE";}
		if((totalNorth>totalUAE) && (totalUAE>totalSouth))//NORTH is best area + South is the worst
		{strBestArea="North"; strWorstArea="South";}
		if((totalSouth>totalNorth) && (totalSouth>totalUAE))//South is best area + UAE is the worst
		{strBestArea="South"; strWorstArea="UAE";}
		if((totalSouth>totalNorth) && (totalNorth<totalUAE))//South is best area + North is the worst	
		{strBestArea="South"; strWorstArea="North";}
		if((totalUAE>totalNorth) && (totalSouth>totalNorth))//UAE is best area + North is the worst
		{strBestArea="UAE"; strWorstArea="North";}
		if((totalUAE>totalNorth) && (totalSouth<totalNorth))//UAE is best area + South is the worst	
		{strBestArea="UAE"; strWorstArea="South";}
		
		//show analyze data
		bestIDLabel.setText("ID: " + strIDofBestSeller);// show the ID  of the BEST seller
		strLocationOfBest = strLocationOfBest.substring(0, 1).toUpperCase() + strLocationOfBest.substring(1).toLowerCase();// make the location with capital letter
		bestLocationLabel.setText("Location: " + strLocationOfBest);
		totalOrdersBestLabel.setText("Total orders: " + totalOrdersBestSeller);
		worstIDLabel.setText("ID: " + strIDofWrostSeller);// show the ID  of the WORST seller
		strLocationOfWrost = strLocationOfWrost.substring(0, 1).toUpperCase() + strLocationOfWrost.substring(1).toLowerCase();// make the location with capital letter
		wrostLocationLabel.setText("Location: " + strLocationOfWrost);
		totalOrdersWrostLabel.setText("Total orders: " + totalOrdersWrostSeller);
		bestArea.setText("The area with the MOST orders is: " + strBestArea);// show the Best area
		worstArea.setText("The area with the LOWEST orders is: " + strWorstArea);// show the worst area
		
	}
}		
		

  
		


