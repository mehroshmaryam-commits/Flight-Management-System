package airline.flight;

import java.io.Serializable;

public class Layover implements Serializable {

    private static final long UID = 1L;
    private String city;
    private int duration;

    public Layover() {
        this.city = null;
        this.duration = 0;
    }

    public Layover(String city, int duration) {
        this.city = city;
        this.duration = duration;
    }

    // getters
    public String getCity() {
        return city;
    }

    public int getDuration() {
        return duration;
    }

    public void display() {
        System.out.println(
                "Layover City: " + city + "\nDuration: " + duration + " minutes");
    }
}
