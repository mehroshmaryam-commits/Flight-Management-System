package airline.crew;

import airline.admin.Admin;

public class CabinCrew extends Admin {
    public CabinCrew(String name, String gender, String phoneNumber, String postalAddress,
            String email, String password, String CNIC) {
        super(name, gender, phoneNumber, postalAddress, email, password, CNIC, "CabinCrew");
    }
}