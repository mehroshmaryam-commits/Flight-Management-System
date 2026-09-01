package airline.flight;

import java.io.Serializable;

public class Seat implements Serializable {

    private static final long UID = 1L;
    private String seatNumber;
    private String seatClass;
    private boolean isBooked;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    public Seat() {
        this.seatNumber = null;
        this.seatClass = null;
        this.isBooked = false;
    }

    public Seat(String seatNumber, String seatClass) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.isBooked = false;
    }

    public Seat(Seat s) {
        this.seatNumber = s.seatNumber;
        this.seatClass = s.seatClass;
        this.isBooked = s.isBooked;
    }

    // getters
    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public boolean bookSeat() {
        if (!isBooked) {
            isBooked = true;
            return true;
        }
        return false;
    }

    public void cancelSeat() {
        isBooked = false;
    }

    public void display() {
        System.out.println(
                "Seat: " + seatNumber + " Class: " + seatClass + " Status: " + (isBooked ? "Booked" : "Available"));
    }
}
