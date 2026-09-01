package airline.crew;

import java.util.Date;
import java.util.List;

public class Assignment {
    private String flightNo;
    private Date departure;
    private Date arrival;
    private String pilotEmail;
    private String coPilotEmail;
    private List<String> crewEmails;

    public Assignment(String flightNo, Date departure, Date arrival, String pilotEmail, String coPilotEmail,
            List<String> crewEmails) {
        this.flightNo = flightNo;
        this.departure = departure;
        this.arrival = arrival;
        this.pilotEmail = pilotEmail;
        this.coPilotEmail = coPilotEmail;
        this.crewEmails = crewEmails;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public Date getDeparture() {
        return departure;
    }

    public Date getArrival() {
        return arrival;
    }

    public String getPilotEmail() {
        return pilotEmail;
    }

    public String getCoPilotEmail() {
        return coPilotEmail;
    }

    public List<String> getCrewEmails() {
        return crewEmails;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "flightNo='" + flightNo + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", pilotEmail='" + pilotEmail + '\'' +
                ", coPilotEmail='" + coPilotEmail + '\'' +
                ", crewEmails=" + crewEmails +
                '}';
    }
}
