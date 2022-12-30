package gui.ProductControlScreens;

import application.client.ClientUI;
import application.client.MessageHandler;
import common.RefillOrder;
import common.connectivity.Message;
import common.connectivity.MessageFromClient;
import gui.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RefillOrderScreenControllerCOPYYYY extends ScreenController implements Initializable {

    @FXML
    private Text chooseProductIDTXT;

    @FXML
    private ChoiceBox<String> chooseProductIDtoRefillChoiceBox;

    @FXML
    private Button exitButton;

    @FXML
    private Button goBackButton;

    @FXML
    private ChoiceBox<String> machineToRefillChoiceBox;

    @FXML
    private Text newAmountTXT;

    @FXML
    private TextField newAmountTextField;

    @FXML
    private Button updateButton;

    @FXML
    private Text orderDateTXT;

    @FXML
    private Text orderIDTXT;

    private ArrayList<RefillOrder> refillOrderList;
    private RefillOrder selectedOrder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseProductIDTXT.setVisible(false);
        chooseProductIDtoRefillChoiceBox.setVisible(false);
        orderIDTXT.setVisible(false);
        orderDateTXT.setVisible(false);
        newAmountTXT.setVisible(false);
        newAmountTextField.setVisible(false);
        updateButton.setVisible(false);
        initElements();
    }

    private void initElements(){
        ClientUI.chat.accept(new Message(null, MessageFromClient.REQUEST_REFILL_ORDERS));
        if (!machineToRefillChoiceBox.getItems().isEmpty())
            machineToRefillChoiceBox.getItems().clear();
        if (!chooseProductIDtoRefillChoiceBox.getItems().isEmpty())
            chooseProductIDtoRefillChoiceBox.getItems().clear();
        refillOrderList = new ArrayList<>();
        machineToRefillChoiceBox.getItems().add("Choose machine");
        machineToRefillChoiceBox.setValue("Choose machine");
        refillOrderList = (ArrayList<RefillOrder>) MessageHandler.getData();
        chooseProductIDtoRefillChoiceBox.getItems().add("choose product id");
        for (RefillOrder ord : refillOrderList){
            if (machineToRefillChoiceBox.getItems().contains(ord.getMachineID()))
                continue;
            machineToRefillChoiceBox.getItems().add(ord.getMachineID());
        }

        machineToRefillChoiceBox.setOnAction(event -> {

            if (machineToRefillChoiceBox.getValue() != null &&machineToRefillChoiceBox.getValue().equals("Choose machine")){
                orderIDTXT.setVisible(false);
                orderDateTXT.setVisible(false);
                newAmountTXT.setVisible(false);
                newAmountTextField.setVisible(false);
                updateButton.setVisible(false);
                chooseProductIDTXT.setVisible(false);
                chooseProductIDtoRefillChoiceBox.setVisible(false);
                return;
            }

            if (machineToRefillChoiceBox.getValue() != null && !machineToRefillChoiceBox.getValue().equals("Choose machine")){
                chooseProductIDTXT.setVisible(true);
                chooseProductIDtoRefillChoiceBox.setVisible(true);

                chooseProductIDtoRefillChoiceBox.getItems().clear();
                for (RefillOrder ord : refillOrderList){
                    if (machineToRefillChoiceBox.getValue().equals(ord.getMachineID())){
                        chooseProductIDtoRefillChoiceBox.getItems().add(ord.getProductID());
                    }
                }
                chooseProductIDtoRefillChoiceBox.setOnAction(event1 -> {
                    if (chooseProductIDtoRefillChoiceBox.getValue() != null && !chooseProductIDtoRefillChoiceBox.getValue().equals("choose product id")){
                        orderIDTXT.setVisible(true);
                        orderDateTXT.setVisible(true);
                        newAmountTXT.setVisible(true);
                        newAmountTextField.setVisible(true);
                        updateButton.setVisible(true);
                        for (RefillOrder ord : refillOrderList){
                            if (machineToRefillChoiceBox.getValue().equals(ord.getMachineID()) && chooseProductIDtoRefillChoiceBox.getValue().equals(ord.getProductID())){
                                orderIDTXT.setText("order ID: " + ord.getOrderID());
                                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd");
                                SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");

                                try {
                                    Date date = inputFormat.parse(ord.getCreationDate());
                                    String formattedDate = outputFormat.format(date);
                                    orderDateTXT.setText("order created: " + formattedDate);
                                    selectedOrder = ord;
                                    return;
                                } catch (ParseException e) {
                                    System.out.println("Error parsing date: " + e.getMessage());
                                }

                            }
                        }
                    }
                });
            }
        });
    }

    @FXML
    void backToMainScreen(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/CEOScreens/CEOMainScreen.fxml"));
        }catch (IOException exception){
            exception.printStackTrace();
        }
        super.switchScreen(event, root);
    }


    @FXML
    void updateAmountInDataBase(MouseEvent event) {
        // check for valid amount
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(newAmountTextField.getText());
        if (!matcher.matches()){
            super.alertHandler("amount field must only contain numbers", true);
            return;
        }

        if (selectedOrder.getAmountAtRequest() >= Integer.parseInt(newAmountTextField.getText())){
            super.alertHandler("amount cannot be lower or same as alert amount!", true);
            return;
        }


        ArrayList<String> productData = new ArrayList<>();
        productData.add(machineToRefillChoiceBox.getValue());
        productData.add(chooseProductIDtoRefillChoiceBox.getValue());
        productData.add(newAmountTextField.getText());
        ClientUI.chat.accept(new Message(productData, MessageFromClient.REQUEST_UPDATE_MACHINE_PRODUCT_AMOUNT));

        if (MessageHandler.getMessage().contains("Error")){
            super.alertHandler("failed to update amount!", true);
            return;
        }
        super.alertHandler("amount updated successfully!", false);
        initElements();
    }


    @FXML
    void exit(MouseEvent event) {
        super.closeProgram(event, true);
    }
}
