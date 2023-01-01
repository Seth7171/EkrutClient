package gui.UserManagementScreens;

import javafx.scene.control.ChoiceBox;

public class UserRow {
    private String firstname;
    private String lastname;
    private String id;
    private ChoiceBox<String> status;

    public UserRow() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChoiceBox getStatus() {
        return status;
    }

    public void setStatus(ChoiceBox status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserRow{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", id='" + id + '\'' +
                ", status=" + status.getValue() +
                '}';
    }
}
