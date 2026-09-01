package airline.flight;

import java.io.*;
import java.util.*;

public class Flight implements Serializable {
    private static final long UID = 1L;

    protected Flight returnFlight;
    protected ArrayList<Seat> seats;
    protected String flightNo;
    protected String origin, destination;
    protected Date deptDateTime, arrvDateTime;
    protected String gateNumber;

    protected double price;
    protected int totalSeats;
    protected Aircraft aircraft; // flight jis aircraft ki hogi
    protected boolean isDomestic;

    public Flight() {
        this.flightNo = null;
        this.origin = null;
        this.destination = null;
        this.deptDateTime = null;
        this.arrvDateTime = null;
        this.price = 0;
        this.totalSeats = 0;
        this.seats = null;
        this.isDomestic = false;
    }

    public Flight(ArrayList<Seat> seats, String flightNo, String origin, String destination, Date deptDateTime,
            Date arrvDateTime, double price, int totalSeats, boolean isDomestic) {
        this.seats = seats;
        this.flightNo = flightNo;
        this.origin = origin;
        this.destination = destination;
        this.deptDateTime = deptDateTime;
        this.arrvDateTime = arrvDateTime;
        this.price = price;
        this.totalSeats = totalSeats;
        this.isDomestic = isDomestic;
    }

    // getters
    public Flight getReturnFlight() {
        return returnFlight;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public Date getDepartureTime() {
        return deptDateTime;
    }

    public Date getArrivalTime() {
        return arrvDateTime;
    }

    public double getPrice() {
        return price;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    // setters
    public void setReturnFlight(Flight f) {
        this.returnFlight = f;
    }

    public void setDepartureTime(Date deptDateTime) {
        this.deptDateTime = deptDateTime;
    }

    public void setArrivalTime(Date arrvDateTime) {
        this.arrvDateTime = arrvDateTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public int getAvailableSeats() {
        int count = 0;
        for (Seat s : seats) {
            if (!s.isBooked()) {
                count++;
            }
        }
        return count;
    }

    public void displayInfo() {
        System.out.println("Flight Number: " + flightNo + "\nFlight Type: " + getFlightType() + "\nOrigin: " + origin +
                "\nDestination: " + destination + "\nDeparture Time: " + deptDateTime + "\nArrival Time: "
                + arrvDateTime + "\nPrice: " + price +
                "\nAvailable Seats: " + getAvailableSeats() + "\nTotal Seats: " + totalSeats + "\n");
    }

    public String getFlightType() {
        return "Direct";
    }

    public double getTotalPrice() {
        if (returnFlight != null) {
            return price + returnFlight.getPrice();
        } else {
            return price;
        }
    }

    public void setAircraft(Aircraft a) {
        this.aircraft = a;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public boolean isDomestic() {
        return isDomestic;
    }

    public Object getDepartureDate() {
        return null;
    }
}