package common;
import java.io.Serializable;

//import javafx.collections.ObservableList;
//import javafx.scene.control.ComboBox;

//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;


public class Deals implements Serializable {
   private static final long serialVersionUID = 1L;
    private String DealName;
    private int Discount; 
    private String Description;
    private String Type; //  Drink | SNACKS | ALL
    private String Area;  // North | South | UAE
    private String Status; //Approved | Not Approved
 

    public Deals(String DealName,int Discount,String Description,String Type,String Area, String Status) {
    	this.DealName=DealName;
    	this.Discount=Discount;
    	this.Description=Description;
    	this.Type=Type;
    	this.Area=Area;
    	this.Status= Status;
      }

    public String getDealName() {
        return DealName;
    }

    public void setDealName(String DealName) {
        this.DealName =DealName;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int Discount) {
    	this.Discount = Discount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
    	this.Description = Description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type =Type;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area=Area;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status =Status;
    }

  

    @Override
    public String toString() {
        return "Deal:{" +
                "DealName='" + DealName + '\'' +
                ", Discount='" + Discount + '\'' +
                ", Description='" + Description + '\'' +
                ", Type='" + Type + '\'' +
                ", Area=" + Area +
                ", Status=" + Status +
                '}';
    }
}
