package gui;

import application.client.ClientUI;
import application.user.UserController;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Lior Jigalo
 * This class switches scenes.
 */
public class ScreenController {
    private double xoffset;
    private double yoffset;

    /**
     * @param event to get the window to switch screen in.
     * @param root loaded fxml file to show.
     * This method displays the scene passed as a parameter.
     */
    public void switchScreen(Event event, Parent root){
        Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        root.setOnMousePressed(event1 -> {
            xoffset = event1.getSceneX();
            yoffset = event1.getSceneY();
        });

        // event handler for when the mouse is pressed AND dragged to move the window
        root.setOnMouseDragged(event1 -> {
            primaryStage.setX(event1.getScreenX()-xoffset);
            primaryStage.setY(event1.getScreenY()-yoffset);
        });
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
        fadeTransition.play();
    }

    public void switchScreen(MouseEvent event, Parent root){
        Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        root.setOnMousePressed(event1 -> {
            xoffset = event1.getSceneX();
            yoffset = event1.getSceneY();
        });

        // event handler for when the mouse is pressed AND dragged to move the window
        root.setOnMouseDragged(event1 -> {
            primaryStage.setX(event1.getScreenX()-xoffset);
            primaryStage.setY(event1.getScreenY()-yoffset);
        });
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
        fadeTransition.play();
    }

    /**
     * @param event event to find the primary stage
     * @param needLogout indicates if logout is needed or not
     */
    protected void closeProgram(MouseEvent event, boolean needLogout) {

        ClientUI.chat.accept("disconnect");
        if (needLogout){
            ArrayList<String> cred = new ArrayList<String>();
            cred.add(UserController.getCurrentuser().getUsername());
            ClientUI.chat.accept(new Message(cred, MessageFromClient.REQUEST_LOGOUT));
        }
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/goodByeScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        switchScreen(event, root);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Platform.exit();
        System.exit(0);
    }
}
