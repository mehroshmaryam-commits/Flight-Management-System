package airline.flight;

import java.util.*;

public class DirectFlight extends Flight {

    public DirectFlight() {
        super();
    }

    public DirectFlight(String flightNo, String origin, String destination,
            Date deptDateTime, Date arrvDateTime,
            double price, int totalSeats,
            boolean isDomestic) {

        super(new ArrayList<Seat>(), flightNo, origin, destination,
                deptDateTime, arrvDateTime, price, totalSeats, isDomestic);
    }
}