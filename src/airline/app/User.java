package airline.app;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 2L;
    private String name;
    private String gender;
    private String phoneNumber;
    private String postalAddress;
    private String email;
    private String password;
    private String CNIC;

    public User() {
    }

    public User(String name, String gender, String phoneNumber, String postalAddress,
            String email, String password, String CNIC) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.postalAddress = postalAddress;
        this.email = email;
        this.password = password;
        this.CNIC = CNIC;

    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

}
