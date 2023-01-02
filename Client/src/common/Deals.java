package common;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.io.Serializable;


public class Deals implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dealID;
    private String DealName;
    private float Discount;
    private String Description;
    private String Type; //  Drink | SNACKS | ALL 
    private ChoiceBox<String> Area_co;  // North | South | UAE  -->> for Manager
    private ChoiceBox<String> Status_co; //Approved | Not Approved  -->> for Manager
    private String Area;     //-->> for Employee
    private String StatusString;


    public Deals() {
    }


    public Deals(String DealName, float Discount, String Description,String Type, String Area , String StatusString) {//result from DB
        this.DealName=DealName;
        this.Discount=Discount;
        this.Description=Description;
        this.Type=Type;
        this.Area=Area;
        this.StatusString=StatusString;
    }


    public String getDealID() {
        return dealID;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    public String getStatusString() {
        return StatusString;
    }

    public void setStatusString(String statusString) {
        StatusString = statusString;
    }

    public String getDealName() {
        return DealName;
    }

    public void setDealName(String DealName) {
        this.DealName =DealName;
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float Discount) {
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

    public ChoiceBox getArea() {
        return Area_co;
    }
    public String getAreaS() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setArea(ChoiceBox Area_co) {
        this.Area_co=Area_co;
    }

    public ChoiceBox getStatus() {
        return Status_co;
    }

    public void setStatus(ChoiceBox Status) {
        this.Status_co =Status;
    }

    @Override
    public String toString() {
        return "Deals{" +
                "dealID='" + dealID + '\'' +
                ", DealName='" + DealName + '\'' +
                ", Discount=" + Discount +
                ", Description='" + Description + '\'' +
                ", Type='" + Type + '\'' +
                ", Area='" + Area + '\'' +
                ", StatusString='" + StatusString + '\'' +
                '}';
    }
}
