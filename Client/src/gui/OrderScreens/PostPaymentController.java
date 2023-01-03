package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.client.ChatClient;
import common.orders.Order;
import common.orders.Product;
import gui.ScreenController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PostPaymentController extends ScreenController implements Initializable{

    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Text orderNum;
    
    @FXML
    private Text machineNum;
    
    @FXML
    private Text dynamicTxt;
    
    @FXML
    private ListView<Object> getOrder = new ListView<Object>();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		orderNum.setText(ChatClient.currentOrder.getOrderID());
		machineNum.setText(ChatClient.currentOrder.getMachineID());
		if (ChatClient.currentOrder.getSupplyMethod().equals("instant pickup")) {
			machineNum.setVisible(false);
			dynamicTxt.setVisible(false);
	        Platform.runLater(() -> {
	            // Run this method on the JavaFX application thread
	        	executeOrder();
	        });
			
		}
		// reset the currentOrder for the next order
		ChatClient.currentOrder = new Order();
	}

	@FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
	
	@FXML
    void goBack(MouseEvent event) {
		ChatClient.cartList = new ArrayList<Product>();
		ChatClient.rememberMyCart = new ListView<Object>();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/CustomerMainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreenWithTimerCustomersOnly(event, root);        
    }
	
	void executeOrder() {
	    // Load the FXML file
	    Parent root = null;
	    try {
	        root = FXMLLoader.load(getClass().getResource("/gui/OrderScreens/ExecuteScene.fxml"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Get the ListView object from the FXML file
	    ListView<Object> listView = (ListView<Object>) root.lookup("#getOrder");

	    // Add items to the ListView
	    listView.setItems(ChatClient.rememberMyCart.getItems());
	    ObservableList<Object> items = listView.getItems();

	    // Show the window
	    Scene scene = new Scene(root);
	    Stage stage = new Stage();
	    stage.setScene(scene);

	    // Create a timeline to remove the items from the ListView
	    Timeline timeline = new Timeline();
	    timeline.setCycleCount(items.size());

	    // Create a key frame to remove an item from the ListView and refresh the display
	    KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
	        items.remove(0);
	        listView.refresh();
	    });

	    // Add the key frame to the timeline
	    timeline.getKeyFrames().add(keyFrame);

	    // Close the window after the timeline has finished
	    timeline.setOnFinished(event -> stage.close());

	    // Start the timeline when the stage is shown
	    stage.setOnShown(event -> timeline.play());
	    
	 // Close the window after the stage has been hidden
	    stage.setOnHidden(event -> stage.close());

	    stage.show();

	    backButton.fire();
	}

}
