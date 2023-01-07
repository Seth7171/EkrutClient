package gui.DeliveryEmployeeScreens;

import java.util.ArrayList;

import common.orders.Order;
import common.orders.Product;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

public class TableOrder extends Order {
    private ChoiceBox<String> status_co;
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

	public ChoiceBox<String> getStatus_co() {
		return status_co;
	}

	public void setStatus_co(ChoiceBox<String> status_co) {
		this.status_co = status_co;
	}
	
}
