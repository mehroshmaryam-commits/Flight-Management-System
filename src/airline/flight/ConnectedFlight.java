package airline.flight;

import java.util.*;

public class ConnectedFlight extends Flight {
    protected Layover layover;
    protected String secondFlightNo;
    protected Date secondDeptTime, secondArrvTime;

    public ConnectedFlight() {
        super();
        this.layover = null;
        this.secondFlightNo = null;
        this.secondDeptTime = null;
        this.secondArrvTime = null;
    }

    public ConnectedFlight(String flightNo, String origin, String destination, Date deptDateTime, Date arrvDateTime,
            double price, int totalSeats, Layover layover, String secondFlightNo, Date secondDeptTime,
            Date secondArrvTime,
            boolean isDomestic) {
        super(new ArrayList<Seat>(), flightNo, origin, destination, deptDateTime, arrvDateTime, price, totalSeats,
                isDomestic);
        this.layover = layover;
        this.secondFlightNo = secondFlightNo;
        this.secondDeptTime = secondDeptTime;
        this.secondArrvTime = secondArrvTime;
    }

    // getters
    public Layover getLayoverInfo() {
        return layover;
    }

    public String getSecondFlightNumber() {
        return secondFlightNo;
    }

    public Date getSecondDepartureTime() {
        return secondDeptTime;
    }

    public Date getSecondArrivalTime() {
        return secondArrvTime;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        if (layover != null) {
            layover.display();
        }
        System.out.println("Second Flight No: " + secondFlightNo + "\nSecond Departure: " + secondDeptTime
                + "\nSecond Arrival: " + secondArrvTime);
    }

    @Override
    public String getFlightType() {
        return "Connecting";
    }

    @Override
    public double getTotalPrice() {
        return price + 5000;
    }
}