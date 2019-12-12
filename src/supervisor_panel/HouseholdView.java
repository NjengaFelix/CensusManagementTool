package supervisor_panel;

import javafx.beans.property.SimpleStringProperty;

public class HouseholdView {
        private final SimpleStringProperty householdNo;
        private final SimpleStringProperty location;
        private final SimpleStringProperty capacity;
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;

        HouseholdView(String household,String location,String capacity ,String firstName, String lastName) {
            this.householdNo = new SimpleStringProperty(household);
            this.location = new SimpleStringProperty(location);
            this.capacity = new SimpleStringProperty(capacity);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
        }

    public String gethouseholdNo() {
        return householdNo.get();
    }

    public SimpleStringProperty householdNoProperty() {
        return householdNo;
    }

    public void sethouseholdNo(String householdNo) {
        this.householdNo.set(householdNo);
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getCapacity() {
        return capacity.get();
    }

    public SimpleStringProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity.set(capacity);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
}

