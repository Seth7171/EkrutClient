package gui.UserManagementScreens;

import common.connectivity.Customer;
import javafx.scene.control.ChoiceBox;

/**
 * The TableCustomer class is a subclass of the Customer class and represents a customer who is seated at a table.
 * It contains additional information about the customer's status, represented by a ChoiceBox.
 * @author Lior 
 */
public class TableCustomer extends Customer {
	// ChoiceBox to represent the customer's status
    ChoiceBox<String> customerStatusBox;

    /**
     * Constructor for the TableCustomer class.
     */
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
