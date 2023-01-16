package gui.OrderScreens;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.client.ChatClient;
import application.client.ClientUI;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The PostPaymentController class is the controller class for the post payment screen.
 * This class is responsible for handling the user interactions with the post payment screen and
 * updating the UI accordingly. It also communicates with the server to update the order status and
 * retrieve the order details to display on the screen. This class implements the Initializable interface
 * to handle the initialization of the FXML elements and sets up the necessary data for the screen.
 * @author Nitsan & Ron
 * @version 1.0
 */
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
    
    @FXML
    private Pane asd;
    
    /**
     * Initializes the PostPaymentController.
     * @param arg0 The URL used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        // Set the order number and machine number text fields to the current order's values
		orderNum.setText(ChatClient.currentOrder.getOrderID());
		machineNum.setText(ChatClient.currentOrder.getMachineID());
        // If the supply method is not "machine pickup", hide the machine number text field and the dynamic text field
		if (!ChatClient.currentOrder.getSupplyMethod().equals("machine pickup")) {
			machineNum.setVisible(false);
			dynamicTxt.setVisible(false);
            // If the supply method is "instant pickup", update the order status to "collected" and switch to the execute scene
			if (ChatClient.currentOrder.getSupplyMethod().equals("instant pickup")) {
		        ArrayList<String> msg = new ArrayList<String>();
		   	 	msg.add(ChatClient.currentOrder.getOrderID());
		   	 	msg.add("collected");
		   	 	ClientUI.chat.accept(new Message(msg, MessageFromClient.REQUEST_UPDATE_ORDER_STATUS));
		        Platform.runLater(() -> {
		        	// Run this method on the JavaFX application thread
		        	executeOrder(ChatClient.currentOrder);
		        });
			}
		}
		// reset the currentOrder for the next order
		ChatClient.currentOrder = new Order();
	}

    /**
     * Exits the program when the exit button is clicked.
     * @param event The mouse event that triggered the method call.
     */
	@FXML
    void exit(MouseEvent event) {
		super.closeProgram(event, true);
    }
	
    /**
     * Goes back to the customer main screen when the back button is clicked.
     * @param event The mouse event that triggered the method call.
     */
	@FXML
    void goBack(MouseEvent event) {
		//Stop the delay and reset the cart list and rememberMyCart list views
		// ChatClient.delay.stop();
		ChatClient.cartList = new ArrayList<Product>();
		ChatClient.rememberMyCart = new ListView<Object>();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/CustomerMainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.switchScreen(event, root);        
    }
	
    /**
     * Executes the order by switching to the execute scene.
     * @param order The order to be executed.
     */
	public static void executeOrder(Order order) {
	    // Load the FXML file
	    Parent root = null;
	    try {
	        root = FXMLLoader.load(PostPaymentController.class.getResource("/gui/OrderScreens/ExecuteScene.fxml"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Get the ListView object from the FXML file
	    ListView<Object> listView = (ListView<Object>) root.lookup("#getOrder");

	    // Add items to the ListView
	    listView.setItems(ChatClient.rememberMyCart.getItems());
	    ObservableList<Object> items = listView.getItems();
	    
	    listView.setOpacity(100);

	    // Show the window
	    Scene scene = new Scene(root);
	    Stage stage = new Stage();
	    stage.setScene(scene);

	 // Create a timeline to remove the items from the ListView
	    Timeline timeline = new Timeline();
	    timeline.setCycleCount(items.size());

	    // Create a key frame to remove an item from the ListView and refresh the display
	    KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), event -> {
	        // Only remove an item if the list is not empty
	        if (!items.isEmpty()) {
	            items.remove(0);
	            listView.refresh();
	        }
	    });

	    // Add the key frame to the timeline
	    timeline.getKeyFrames().add(keyFrame);

	    // Close the window after the timeline has finished
	    timeline.setOnFinished(event -> {
	        // Add a 2 second delay before closing the stage
	        Timeline delayTimeline = new Timeline();
	        delayTimeline.setCycleCount(1);
	        KeyFrame delayKeyFrame = new KeyFrame(Duration.seconds(2), event2 -> stage.close());
	        delayTimeline.getKeyFrames().add(delayKeyFrame);
	        delayTimeline.play();
	    });

	    // Start the timeline when the stage is shown
	    stage.setOnShown(event -> timeline.play());

	    stage.show();


	}


}
