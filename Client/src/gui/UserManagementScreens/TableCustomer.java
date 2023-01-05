package gui.UserManagementScreens;

import common.connectivity.Customer;
import javafx.scene.control.ChoiceBox;


public class TableCustomer extends Customer {
    ChoiceBox<String> customerStatusBox;

    public TableCustomer() {
    }

    public ChoiceBox<String> getCustomerStatusBox() {
        return customerStatusBox;
    }

    public void setCustomerStatusBox(ChoiceBox<String> customerStatusBox) {
        this.customerStatusBox = customerStatusBox;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTableCustomer{" +
                "customerStatusBox=" + customerStatusBox +
                '}';
    }
}
