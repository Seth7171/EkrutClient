package common;

import java.io.Serializable;

public class RefillOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private String orderID;
    private String ProductID;
    private String MachineID;
    private String creationDate;

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

    @Override
    public String toString() {
        return "RefillOrder{" +
                "orderID='" + orderID + '\'' +
                ", ProductID='" + ProductID + '\'' +
                ", MachineID='" + MachineID + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
