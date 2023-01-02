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
    private ChoiceBox<String> Type_co; //  Drink | SNACKS | ALL -->> for Manager
    private ChoiceBox<String> Area_co;  // North | South | UAE  -->> for Manager
    private ChoiceBox<String> Status_co; //Approved | Not Approved  -->> for Manager
    private String Type; //  Drink | SNACKS | ALL  -->> for Employee
    private String Area; // North | South | UAE     -->> for Employee
    private String StatusString;


    public Deals() {
    }

//    public Deals(String DealName, float Discount, String Description, ObservableList Type, ObservableList Area , ObservableList dataManager) {//with COMBOBOX for Manager
//        this.DealName=DealName;
//        this.Discount=Discount;
//        this.Description=Description;
//        this.Type_co=new ComboBox(Type);
//        this.Area_co=new ComboBox(Area);
//        this.Status_co = new ComboBox(dataManager);
//   }
    public Deals(String DealName, float Discount, String Description, String Type, String Area , String StatusString) {//result from DB
        this.DealName=DealName;
        this.Discount=Discount;
        this.Description=Description;
        this.Type=Type;
        this.Area=Area;
        this.StatusString=StatusString;
    }

//    public Deals(String DealName, float Discount, String Description, String Type, ObservableList dataEmp) {// for employee
//        this.DealName=DealName;
//        this.Discount=Discount;
//        this.Description=Description;
//        this.Type=Type;
//        this.Status_co = new ComboBox(dataEmp);
//    }

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

    public ChoiceBox getType() {
        return Type_co;
    }

    public void setType(ChoiceBox Type_co) {
        this.Type_co =Type_co;
    }

    public void setType(String Type) {
        this.Type =Type;
    }

    public String getTypeStr() {
        return Type;
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
