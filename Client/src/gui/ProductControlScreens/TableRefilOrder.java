package gui.ProductControlScreens;

import javafx.scene.control.ChoiceBox;

public class TableRefilOrder {
    ChoiceBox<String> assignedEmployeeBox;

    public ChoiceBox<String> getAssignedEmployeeBox() {
        return assignedEmployeeBox;
    }

    public void setAssignedEmployeeBox(ChoiceBox<String> assignedEmployeeBox) {
        this.assignedEmployeeBox = assignedEmployeeBox;
    }
}
