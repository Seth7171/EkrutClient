package common.Reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import common.connectivity.User;

public class ClientReport implements Serializable{
    private static final long serialVersionUID = 1L;
    private User user;
	private int totalOrders;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
}
