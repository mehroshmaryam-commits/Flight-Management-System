package airline.admin;

import airline.app.User;

public class Admin extends User {
    private String role;

    public Admin(String name, String gender, String phoneNumber, String postalAddress, String email, String password,
            String CNIC, String role) {
        super(name, gender, phoneNumber, postalAddress, email, password, CNIC);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // For saving to file
    public String toFileString() {
        return getName() + "," + getGender() + "," + getPhoneNumber() + "," + getPostalAddress() + "," + getEmail()
                + ","
                + getPassword() + "," + getCNIC();
    }
}