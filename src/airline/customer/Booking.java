package airline.customer;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import airline.flight.Flight;
import airline.flight.Seat;
import airline.flight.Aircraft;

public class Booking implements Serializable {
    private static final long serialVersionUID = 4L;

    private String bookingId;
    private Flight flight;
    private Seat seat;
    private Payment payment;
    private String status; // "Confirmed", "Cancelled", "Completed"
    private LocalDateTime bookingDate;

    private boolean checkedIn;
    private Date checkInTime;
    private String boardingPassId;

    public Booking() {
    }

    public Booking(String bookingId, Flight flight) {
        this.bookingId = bookingId;
        this.flight = flight;
        this.status = "Confirmed";
        this.checkedIn = false;
        this.checkInTime = null;
    }

    // Getters and Setters
    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime() {
        this.checkInTime = new Date();
    }

    public void setBookingDate() {
        this.bookingDate = LocalDateTime.now();
    }

    public LocalDateTime getBookingDate() {
        return this.bookingDate;
    }

    public String getBoardingPassId() {
        return boardingPassId;
    }

    public void setBoardingPassId(String boardingPassId) {
        this.boardingPassId = boardingPassId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Flight getFlight() {
        return flight;
    }

    public Seat getSeat() {
        return this.seat;
    }

    public double getTotalFare() {
        if (payment != null) {
            return payment.getAmount();
        }
        return 0.0;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean setSeat(Aircraft a, String seatClass) {
        ArrayList<Seat> seats = a.getSeats();
        for (Seat s : seats) {
            if (s.getSeatClass().equalsIgnoreCase(seatClass) && !s.isBooked()) {
                this.seat = new Seat(s);
                this.seat.bookSeat();
                return true;
            }
        }

        return false;
    }

    public void setPayment(Payment payment) {
        this.payment = new Payment(payment);
    }

    public String getFormattedBookingDate() {
        if (bookingDate == null)
            return "Not Available";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return bookingDate.format(formatter);
    }

    public boolean canCancel() {
        Date now = new Date(); // current time
        Date departure = flight.getDepartureTime();

        long diffMillis = departure.getTime() - now.getTime();
        long diffHours = diffMillis / (1000 * 60 * 60);

        return diffHours >= 24;
    }

    public boolean isCheckInDeadlinePassed() {
        Date now = new Date();
        Date departure = this.flight.getDepartureTime();

        long checkInCutoffMillis = departure.getTime() - (60 * 60 * 1000);
        return now.getTime() > checkInCutoffMillis;
    }

    public boolean isCheckInOpen() {
        Date now = new Date();
        Date departure = flight.getDepartureTime();

        long diffMillis = departure.getTime() - now.getTime();
        return diffMillis <= (4 * 60 * 60 * 1000) && !isCheckInDeadlinePassed();
    }

}