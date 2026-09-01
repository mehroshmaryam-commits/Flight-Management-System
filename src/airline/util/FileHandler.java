package airline.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import airline.flight.Flight;
import airline.flight.Aircraft;

public class FileHandler {

    private static final String FLIGHT_FILE = "resources/bin_file/flights.dat";
    private static final String AIRCRAFT_FILE = "resources/bin_file/aircrafts.dat";

    // --- Flights ---
    public static void saveFlights(List<Flight> flights) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FLIGHT_FILE))) {
            out.writeObject(flights);
            System.out.println("Flights saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving flights: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ArrayList<Flight> loadFlights() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FLIGHT_FILE))) {
            ArrayList<Flight> flights = (ArrayList<Flight>) in.readObject();
            System.out.println("Flights loaded successfully.");
            return flights;
        } catch (IOException e) {
            System.out.println("No flights file found. Starting with empty list.");
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // --- Aircrafts ---
    public static void saveAircrafts(ArrayList<Aircraft> aircrafts) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(AIRCRAFT_FILE))) {
            out.writeObject(aircrafts);
            System.out.println("Aircrafts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving aircrafts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ArrayList<Aircraft> loadAircrafts() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(AIRCRAFT_FILE))) {
            ArrayList<Aircraft> aircrafts = (ArrayList<Aircraft>) in.readObject();
            System.out.println("Aircrafts loaded successfully.");
            return aircrafts;
        } catch (IOException e) {
            System.out.println("No aircraft file found. Starting with empty list.");
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
