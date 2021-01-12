package Test.database.tables;

public class CustomerUI {

    private String firstname;
    private String lastName;

    protected CustomerUI() {

    }

    public CustomerUI(String firstname, String lastName) {
        this.firstname = firstname;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toString() {
        return String.format("Customer[firstName='%s', lastName='%s']", firstname, lastName);
    }
}
