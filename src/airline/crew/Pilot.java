package airline.crew;

import airline.admin.Admin;

public class Pilot extends Admin {
    private String pilotRank;
    private String licenseNumber;

    public Pilot(String name, String gender, String phoneNumber, String postalAddress, String email,
            String password, String CNIC, String pilotRank, String licenseNumber) {
        super(name, gender, phoneNumber, postalAddress, email, password, CNIC, "Pilot");
        this.pilotRank = pilotRank;
        this.licenseNumber = licenseNumber;
    }

    public String getPilotRank() {
        return pilotRank;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setPilotRank(String pilotRank) {
        this.pilotRank = pilotRank;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "," + pilotRank + "," + licenseNumber;
    }
}