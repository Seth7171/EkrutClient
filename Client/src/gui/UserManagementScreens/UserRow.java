package gui.UserManagementScreens;

import javafx.scene.control.ChoiceBox;


/**
*
* This class represents a row in a table of users, which contains the first name, last name, id and status of a user.
* It contains getters and setters for each attribute, as well as a toString method for displaying the row's information.
*/
public class UserRow {
    private String firstname;
    private String lastname;
    private String id;
    private ChoiceBox<String> status;
 
    /**
     *
     * Constructs an empty UserRow object.
     */
    public UserRow() {
    }

    /**
     *
     * Gets the first name of the user.
     * @return the first name of the user.
    */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * Sets the first name of the user.
     * @param firstname the first name to set for the user.
    */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
 
    /**
     *
     * Gets the last name of the user.
     * @return the last name of the user.
    */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * Sets the last name of the user.
     * @param lastname the last name to set for the user.
    */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     *
     * Gets the id of the user.
     * @return the id of the user.
    */
    public String getId() {
        return id;
    }

    /**
     *
     * Sets the id of the user.
     * @param id the id to set for the user.
    */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * Gets the status of the user.
     * @return the status of the user.
    */
    public ChoiceBox getStatus() {
        return status;
    }

    /**
     * Sets the status of the user.
     * @param status the status to set for the user.
     */
    public void setStatus(ChoiceBox status) {
        this.status = status;
    }

    /**
    * Returns a string representation of the UserRow object, including the first name, last name, id and status of the user.
    * @return a string representation of the UserRow object.
    */
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
