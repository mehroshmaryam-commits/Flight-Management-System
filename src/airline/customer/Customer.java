package airline.customer;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import airline.app.User;

public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 3L;
    private String passportNumber;
    private String visaNumber;
    private String myPromoCode;
    private String dateOfBirth;
    private String emergencyContact;
    private ArrayList<Booking> bookings;

    public Customer(String name, String gender, String phoneNumber, String postalAddress,
            String email, String password, String CNIC, String dob, String emergency) {
        super(name, gender, phoneNumber, postalAddress, email, password, CNIC);
        this.dateOfBirth = dob;
        this.emergencyContact = emergency;
        loadBookings();
    }

    public String getPromoCode() {
        return this.myPromoCode;
    }

    public void setPromoCode(String p) {
        this.myPromoCode = p;
    }

    public void setPassportNumber(String number) {
        this.passportNumber = number;
    }

    public void setVisaNumber(String n) {
        this.visaNumber = n;
    }

    public String getVisaNumber() {
        return this.visaNumber;
    }

    public String toString() {
        return this.getName() + this.getEmail() + this.getPassword();
    }

    public ArrayList<Booking> getBookings() {
        return this.bookings;
    }

    public boolean isPassportValid(String p) {
        if (p == null || p.isEmpty())
            return false;
        return p.matches("[A-Z0-9]{8,9}");
    }

    public boolean isVisaValid(String v) {
        if (v == null || v.isEmpty())
            return false;
        return v.matches("[A-Z0-9]{6,10}");
    }

    public void addBooking(Booking b) {
        if (this.bookings == null) {
            this.bookings = new ArrayList<>();
        }
        this.bookings.add(b);
        saveBooking();
    }

    public void saveBooking() {
        File file = new File("resources/bin_file/" + this.getCNIC() + "_bookings.dat");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bookings);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBookings() {
        File file = new File("resources/bin_file/" + this.getCNIC() + "_bookings.dat");
        if (!file.exists())
            return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.bookings = (ArrayList<Booking>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadBookings() {
        loadBookings();
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getPassportNumber() {
        return this.passportNumber;
    }

    public boolean cancelBooking(String bookingId) {
        if (bookings == null)
            return false;

        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId) && booking.getStatus().equals("Confirmed")) {
                booking.setStatus("Cancelled");
                booking.getPayment().setStatus("Refund");
                saveBooking();
                return true;
            }
        }
        return false;
    }

    public String checkInFlight(String bookingId) {
        Booking bookingToProcess = null;
        for (Booking b : this.bookings) {
            if (b.getBookingId().equals(bookingId)) {
                bookingToProcess = b;
                break;
            }
        }

        if (bookingToProcess == null) {
            return "Booking not found.";
        }

        // 'Confirmed'?
        if (!"Confirmed".equals(bookingToProcess.getStatus())) {
            if ("Completed".equals(bookingToProcess.getStatus())) {
                return "already checked in";
            }
            return "Booking status is " + bookingToProcess.getStatus() + ". Check-in not allowed.";
        }

        // Check-In Deadline Passed?
        if (bookingToProcess.isCheckInDeadlinePassed()) {
            return "Check-in deadline has passed. Contact the airline counter.";
        }

        // Check-In Open Yet? 4
        Date departure = bookingToProcess.getFlight().getDepartureTime();
        Date now = new Date();

        long diffMillis = departure.getTime() - now.getTime();
        final long FOUR_HOURS_IN_MILLIS = 4 * 60 * 60 * 1000;

        if (diffMillis > FOUR_HOURS_IN_MILLIS) {
            return "Check-in is not yet open. Opens 4 hours before departure.";
        }

        // Set the booking status
        bookingToProcess.setStatus("Completed");

        saveBooking();

        return "checked in successfully";
    }

    public ArrayList<Booking> getAvailableCheckInBookings() {
        ArrayList<Booking> eligibleBookings = new ArrayList<>();

        if (this.bookings == null) {
            return eligibleBookings;
        }

        for (Booking booking : this.bookings) {
            if ("Confirmed".equals(booking.getStatus()) && booking.isCheckInOpen()) {
                eligibleBookings.add(booking);
            }
        }

        return eligibleBookings;
    }

}