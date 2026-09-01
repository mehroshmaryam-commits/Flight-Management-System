package airline.flight;

import java.io.Serializable;
import java.util.ArrayList;

public class Aircraft implements Serializable {
    private String model;
    private int seatCapacity;
    private String airline;
    private ArrayList<Seat> seats;

    // Constructor
    public Aircraft(String model, int seatCapacity, String airline) {
        this.model = model;
        this.seatCapacity = seatCapacity;
        this.airline = airline;
        this.seats = new ArrayList<>();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

}
