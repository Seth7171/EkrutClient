package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import application.client.ChatClient;
import application.client.ClientUI;
import application.user.CustomerController;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import common.orders.Product;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class InactivityTimer {
    private static Timer timer;
    private static TimerTask task;
    private static long duration = 10000; // 10 seconds
    private static Stage primaryStage;

    public static void startTimer(Stage primaryStage) {
        InactivityTimer.primaryStage = primaryStage;
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                // code to return the user to the main screen
                System.out.println("User has been inactive for too long. Returning to main screen...");
                ArrayList<String> credentials = new ArrayList<String>();
                credentials.add(UserController.getCurrentuser().getUsername());
                ClientUI.chat.accept(new Message(credentials, MessageFromClient.REQUEST_LOGOUT));
                UserController.setCurrentuser(null);
                CustomerController.setCurrentCustomer(null);
                ChatClient.rememberMyCart = new ListView<Object>();
                ChatClient.cartList = new ArrayList<Product>();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/gui/UserScreens/LogInScreen.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);

              
                primaryStage.setTitle("Client Editor");

                primaryStage.setScene(scene);
                primaryStage.centerOnScreen();


                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.millis(300));
                fadeTransition.setNode(root);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.setOnFinished((ActionEvent finishevt) -> {
                    primaryStage.show();
                });
            }
        };
        timer.schedule(task, duration);
    }

    public static void resetTimer() {
        if (timer != null) {
            timer.cancel();
            startTimer(primaryStage);
        }
    }
    
    public static void stopTimer(){
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}