package gui.DeliveryEmployeeScreens;


import common.orders.Order;
import javafx.scene.control.ChoiceBox;

/**
*  TableOrder is a class that extends Order and contains additional fields for displaying information in a table
* @author Nitsan & Ron
* @version 1.0
*/
public class TableOrder extends Order {
    private ChoiceBox<String> status_co;
    
    /**
    * Constructs a TableOrder object with the same properties as the given Order object
    * @param order the order that this TableOrder is based on
    */
	public TableOrder(Order order) {
		super(order.getOrderID(), 
				order.getOverallPrice(), 
				order.getProducts(), 
				order.getMachineID(), 
				order.getOrderDate(), 
				order.getEstimatedDeliveryTime(), 
				order.getConfirmationDate(), 
				order.getOrderStatus(), 
				order.getCustomerID(), 
				order.getSupplyMethod(), 
				order.getPaidWith(), 
				order.getAddress());
	}

    /**
    * Returns the status_co choicebox
    * @return status_co
    */
	public ChoiceBox<String> getStatus_co() {
		return status_co;
	}

    /**
    * Set the status_co choicebox
    * @param status_co choicebox
    */
	public void setStatus_co(ChoiceBox<String> status_co) {
		this.status_co = status_co;
	}
	
}
