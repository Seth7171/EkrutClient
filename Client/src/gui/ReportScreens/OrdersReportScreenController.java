package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import application.client.ChatClient;
import application.client.MessageHandler;
import application.user.UserController;
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

/** Description of Orders Report Controller.
 * @author Ravid Goldin
 * @author Ben Ben Baruch
 */

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
	    private Label valueLabel;

	    
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
  	 * Logout from Ekrut.
  	 * @param event the mouse event that triggered the method call
  	 */
    @FXML
    void ClickLogOutButton(MouseEvent event) {
    	super.closeProgram(event, true);
    }
    
    /**
     * Initializes the screen.
     * Making BarChart for all areas that display total orders of all machines from a specific area (North, South, UAE).
     * In addition, it finds and show: 
     * The best and worst area depends on sales.
     * The best and worst machine depends on sales.
     * @param arg0 the location of the root object
     * @param arg1 the resources used to localize the root object
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//get the data
		ArrayList<OrderReport> orderReportData = (ArrayList<OrderReport>)  MessageHandler.getData();
		
		 int totalOrdersBestSeller=0,NorthMachinesAmount=0,SouthMachinesAmount=0,UAEMachinesAmount=0,totalOrdersNorth=0,totalOrdersSouth=0,totalOrdersUAE=0,totalOrdersBestArea=0;
		 String strIDofBestSeller=null, strLocationOfBest=null ,strBestArea=null;
		 int totalOrdersWrostSeller = 999999, totalBorder=0;
		 String strIDofWrostSeller=null, strLocationOfWrost=null,strWorstArea=null;
		 float totalNorth=0,totalSouth=0, totalUAE=0;
		 
		//making the BarChart from data for specific department : CEO | SOUTH | NORTH | UAE
	 switch (UserController.getCurrentuser().getDepartment()) {
		 
		  	//NORTH manager Screen
           case"area_manager_north":
        	 XYChart.Series<String, Integer> north= new XYChart.Series<>();// North
        	 north.setName("North");
        	 for(OrderReport ordrep : orderReportData){
     			if (ordrep.getMachineLocation().equals("north")) {
     					north.getData().add(new XYChart.Data<String, Integer>(ordrep.getMachineid(), ordrep.getNumberOfOrders()));//set data
     			if(ordrep.getNumberOfOrders() > totalOrdersBestSeller) {
    				totalOrdersBestSeller = ordrep.getNumberOfOrders(); // get total orders of best machine
    				strIDofBestSeller = ordrep.getMachineid(); //get ID of best machine
     				}
    			   	if(ordrep.getNumberOfOrders() < totalOrdersWrostSeller) {
        				totalOrdersWrostSeller=ordrep.getNumberOfOrders(); // get total orders of worst machine
        				strIDofWrostSeller = ordrep.getMachineid(); //get ID of worst machine
                   	  }	
                 }
             }
        	 totalBorder=totalOrdersBestSeller+5;
     		y.setUpperBound(totalBorder);
     		OrdersChart.getData().addAll(north);
        	 wrostLocationLabel.setText("Location: North" );
     		bestLocationLabel.setText("Location: North" );
     		bestArea.setVisible(false);
     		worstArea.setVisible(false);
        	
        	 break;
        	 
        	 //SOUTH manager Screen
           case"area_manager_south":
        	 XYChart.Series<String, Integer> south= new XYChart.Series<>();// south
          	 south.setName("South");
          	 for(OrderReport ordrep : orderReportData){
               	if (ordrep.getMachineLocation().equals("south")) {
               			south.getData().add(new XYChart.Data<String, Integer>(ordrep.getMachineid(), ordrep.getNumberOfOrders()));//set data
               	if(ordrep.getNumberOfOrders() > totalOrdersBestSeller) {
    				totalOrdersBestSeller = ordrep.getNumberOfOrders(); // get total orders of best machine
    				strIDofBestSeller = ordrep.getMachineid(); //get ID of best machine
               		}
               	if(ordrep.getNumberOfOrders() < totalOrdersWrostSeller) {
    				totalOrdersWrostSeller=ordrep.getNumberOfOrders(); // get total orders of worst machine
    				strIDofWrostSeller = ordrep.getMachineid(); //get ID of worst machine
               	  }	
               	}
              }
          	totalBorder=totalOrdersBestSeller+1;
     		y.setUpperBound(totalBorder);
     		OrdersChart.getData().addAll(south);
          	wrostLocationLabel.setText("Location:  South");
    		bestLocationLabel.setText("Location:  South");
    		bestArea.setVisible(false);
     		worstArea.setVisible(false);
          	 break;
          	 
          	//UAE manager Screen
           case"area_manager_uae":
        	 XYChart.Series<String, Integer> uae= new XYChart.Series<>();// uae
          	 uae.setName("UAE");
          	 for(OrderReport ordrep : orderReportData){
          		 if (ordrep.getMachineLocation().equals("uae")) {
          			 uae.getData().add(new XYChart.Data<String, Integer>(ordrep.getMachineid(), ordrep.getNumberOfOrders()));//set data
          		if(ordrep.getNumberOfOrders() > totalOrdersBestSeller) {
    				totalOrdersBestSeller = ordrep.getNumberOfOrders(); // get total orders of best machine
    				strIDofBestSeller = ordrep.getMachineid(); //get ID of best machine
          			}
          	 	if(ordrep.getNumberOfOrders() < totalOrdersWrostSeller) {
    				totalOrdersWrostSeller=ordrep.getNumberOfOrders(); // get total orders of worst machine
    				strIDofWrostSeller = ordrep.getMachineid(); //get ID of worst machine
               	  }	
               	}
              }
          	totalBorder=totalOrdersBestSeller+5;
     		y.setUpperBound(totalBorder);
     		OrdersChart.getData().addAll(uae);
           	wrostLocationLabel.setText("Location:  UAE");
    		bestLocationLabel.setText("Location:  UAE");
    		bestArea.setVisible(false);
     		worstArea.setVisible(false);
          	 	break;
          	 	
          	//CEO Screen
           case"ceo":
          	 XYChart.Series<String, Integer> ser1= new XYChart.Series<>();// North
        		 XYChart.Series<String, Integer> ser2= new XYChart.Series<>();//South
        		 XYChart.Series<String, Integer> ser3= new XYChart.Series<>();//UAE
        		 ser1.setName("North");	
        		 ser2.setName("South");
        		 ser3.setName("UAE");	
       
		for(OrderReport ordrep : orderReportData){

			if (ordrep.getMachineLocation().equals("north")) {
				ser1.getData().add(new XYChart.Data<String, Integer>(ordrep.getMachineid(), ordrep.getNumberOfOrders()));//set data
				totalOrdersNorth += ordrep.getNumberOfOrders();//analyze data for best AREA
				NorthMachinesAmount++;}

			if (ordrep.getMachineLocation().equals("south")) {
				ser2.getData().add(new XYChart.Data<String, Integer>(ordrep.getMachineid(), ordrep.getNumberOfOrders()));//set data
				totalOrdersSouth += ordrep.getNumberOfOrders();//analyze data for best AREA
				SouthMachinesAmount++;}

				
			if (ordrep.getMachineLocation().equals("uae")) {
				ser3.getData().add(new XYChart.Data<String, Integer>(ordrep.getMachineid(), ordrep.getNumberOfOrders()));//set data
				totalOrdersUAE += ordrep.getNumberOfOrders();//analyze data for best AREA
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
			
		}
 
        	totalBorder=totalOrdersBestSeller+5;
		y.setUpperBound(totalBorder);
		OrdersChart.getData().addAll(ser1,ser2,ser3);
 
		//calculate data for who is the best area
		totalNorth=(float)totalOrdersNorth/NorthMachinesAmount;
		totalSouth=(float)totalOrdersSouth/SouthMachinesAmount;
		totalUAE=(float)totalOrdersUAE/UAEMachinesAmount;
		if((totalNorth>=totalSouth) && (totalSouth>totalUAE))//NORTH is best area + UAE is the worst
		{strBestArea="North"; strWorstArea="UAE";System.out.println("1");}
		if((totalNorth>=totalUAE) && (totalUAE>=totalSouth))//NORTH is best area + South is the worst
		{strBestArea="North"; strWorstArea="South";System.out.println("2");}
		if((totalSouth>=totalNorth) && (totalSouth>=totalUAE))//South is best area + UAE is the worst
		{strBestArea="South"; strWorstArea="UAE";System.out.println("3");}
		if((totalSouth>=totalNorth) && (totalNorth<=totalUAE))//South is best area + North is the worst	
		{strBestArea="South"; strWorstArea="North";System.out.println("4");}
		if((totalUAE>=totalNorth) && (totalSouth>totalNorth))//UAE is best area + North is the worst
		{strBestArea="UAE"; strWorstArea="North";System.out.println("5");}
		if((totalUAE>=totalNorth) && (totalSouth<=totalNorth))//UAE is best area + South is the worst	
		{strBestArea="UAE"; strWorstArea="South";System.out.println("6");}
		break;
    default:
     System.out.println("Unknown!");
                      // TODO: maybe add UnknownScreenException later??
}
		//show analyze data on the screen (all screens)
		DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);//show the date
		bestIDLabel.setText("ID: " + strIDofBestSeller);// show the ID  of the BEST seller
		totalOrdersBestLabel.setText("Total orders: " + totalOrdersBestSeller);
		worstIDLabel.setText("ID: " + strIDofWrostSeller);// show the ID  of the WORST seller
		totalOrdersWrostLabel.setText("Total orders: " + totalOrdersWrostSeller);
		//only for ceo screen
		if(UserController.getCurrentuser().getDepartment().equals("ceo")) {
			strLocationOfBest = strLocationOfBest.substring(0, 1).toUpperCase() + strLocationOfBest.substring(1).toLowerCase();// make the location with capital letter *for CEO*
			bestLocationLabel.setText("Location: " + strLocationOfBest);
			strLocationOfWrost = strLocationOfWrost.substring(0, 1).toUpperCase() + strLocationOfWrost.substring(1).toLowerCase();// make the location with capital letter
			wrostLocationLabel.setText("Location: " + strLocationOfWrost);
			bestArea.setText("The area with the MOST orders is: " + strBestArea);// show the Best area
			worstArea.setText("The area with the LOWEST orders is: " + strWorstArea);// show the worst area
		}
	}
}		
		

  
		


