package common;

import java.io.Serializable;

public class RefillOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private String orderID;
    private String ProductID;
    private String MachineID;
    private String creationDate;
    private int amountAtRequest;
    private int newAmount = 0;
    private String assignedEmployee;

    public RefillOrder() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getMachineID() {
        return MachineID;
    }

    public void setMachineID(String machineID) {
        MachineID = machineID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getAmountAtRequest() {
        return amountAtRequest;
    }

    public void setAmountAtRequest(int amountAtRequest) {
        this.amountAtRequest = amountAtRequest;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(String assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public int getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(int newAmount) {
        this.newAmount = newAmount;
    }

    @Override
    public String toString() {
        return "RefillOrder{" +
                "orderID='" + orderID + '\'' +
                ", ProductID='" + ProductID + '\'' +
                ", MachineID='" + MachineID + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", amountAtRequest=" + amountAtRequest +
                ", newAmount=" + newAmount +
                ", assignedEmployee='" + assignedEmployee + '\'' +
                '}';
    }
}
