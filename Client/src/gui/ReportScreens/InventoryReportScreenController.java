package gui.ReportScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import application.client.ClientUI;
import application.client.MessageHandler;
import application.user.UserController;
import common.Reports.InventoryReport;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.connectivity.MessageFromServer;
import common.orders.Product;
import gui.ScreenController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/** Description of Inventory Report Controller.
 * @author Ravid Goldin
 * @author Ben Ben Baruch
 */

public class InventoryReportScreenController extends ScreenController implements Initializable{

	@FXML
	private Button backButton;

	@FXML
	private Button exitApp;

	@FXML
	private Label MachineIDLabel;

	@FXML
	private Label DateChooseLabel;

	@FXML
	private Label locationMachineLabel;

	@FXML
	private Label totalWorthLabel;

	@FXML
	private Label notInStockLabel;

	@FXML
	private Label inStockItemsLabel;

	@FXML
	private Label snacksAmountLabel;

	@FXML
	private Label drinksAmountLabel;

	@FXML
	private TableColumn<Product, Integer> AvailableColumn;

	@FXML
	private TableColumn<Product, String> IDColumn;

	@FXML
	private TableView<Product> tbData;

	@FXML
	private TableColumn<Product, String> ProductNameColumn;

	private ArrayList<Product> tempProd = new ArrayList<>();
	
	/** Variables needed for TableView */
	private ObservableList<Product> observablesubs = FXCollections.observableArrayList();
	ObservableList<String> list;

	/** 
	 * Load products and insert to table.
	 */
	public void loadProducts() {
		tbData.getItems().clear();
		// here we insert the data for the table.
		for(Product p : tempProd){	
			Product productData = new Product();
			productData.setProductId(p.getProductId());
			productData.setDescription(p.getDescription());
			productData.setAmount(p.getAmount());
			tbData.getItems().add(productData);
		}
	}
	
	/**
     * Initializes the screen.
     * Set all data relevant to Inventory report.
     * Inventory report based on specific machine ID. It shows all products in that machine and more information 
     * such as stock amounts, type of products, total worth of stock.
     * @param arg0 the location of the root object
     * @param arg1 the resources used to localize the root object
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		float totalWorthItems = 0;
		int notInStockItems = 0;
		int inStockItems = 0;
		int snacksAmount = 0;
		int drinksAmount = 0;
		MachineIDLabel.setText("Machine ID: " + ChatClient.returnMachineID);//get the Machine Id that has been choose
		DateChooseLabel.setText("*Report is relevant to " + ChatClient.returnMonthChoose + "/" + ChatClient.returnYearChoose);//get the Date that has been choose
		String locationMachine = ChatClient.returnLocationChoose;
		locationMachine = locationMachine.substring(0, 1).toUpperCase() + locationMachine.substring(1).toLowerCase();// make the location with capital letter
		locationMachineLabel.setText("Location: " + locationMachine);//get the Location  that has been choose

		InventoryReport currentReportData = (InventoryReport) MessageHandler.getData();//getting data from server

		for (Product product : currentReportData.getProducts()) {//calculate statistics 
			if (product.getAmount() == 0)
				notInStockItems++;
			inStockItems += product.getAmount();
			if(product.getType().equals("DRINK"))
				drinksAmount++;
			else
				snacksAmount++;
		}
		totalWorthItems = currentReportData.getTotalValue();
		//show result on screen
		notInStockLabel.setText("Total items not in stock: " + notInStockItems);
		inStockItemsLabel.setText("Total items in stock: " + inStockItems);
		totalWorthLabel.setText("Total worth stock: " + totalWorthItems + " ¤");
		snacksAmountLabel.setText("Snacks: " + snacksAmount);
		drinksAmountLabel.setText("Drinks: " + drinksAmount);

		// tempProd to see if I get all details(name,amount,price......) about products in the report.
		tempProd = currentReportData.getProducts();
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
		ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
		AvailableColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
		tbData.setItems(observablesubs);
		loadProducts();
	}


	/**
     * Goes back to Report Main Screen.
     * @param event the mouse event that triggered the method call
     */
	@FXML
	void clickBackButton(MouseEvent event) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("ReportsMainScreen.fxml"));
		}
		catch (IOException exception){exception.printStackTrace();}
		super.switchScreen(event, root);
	}

	/**
  	 * exit application
  	 * @param event the mouse event that triggered the method call
  	 */
	@FXML
	void exitApplication(MouseEvent event) {
		super.closeProgram(event, true);	
	}

}