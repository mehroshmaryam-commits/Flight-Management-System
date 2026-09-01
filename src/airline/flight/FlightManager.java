package airline.flight;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import airline.util.FileHandler;
import airline.admin.EmployeeManager;
import airline.crew.Assignment;
import airline.crew.Pilot;
import airline.crew.CabinCrew;

public class FlightManager {
    public static final FlightManager INSTANCE = new FlightManager();
    private ArrayList<Flight> flights;
    private ArrayList<Aircraft> aircrafts; // aircrafts ki array

    public FlightManager() {
        this.aircrafts = new ArrayList<>(); // initialize krwae
        this.flights = new ArrayList<>();
        this.flights = FileHandler.loadFlights();
        this.aircrafts = FileHandler.loadAircrafts();

    }

    public FlightManager(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void addFlight(Flight fl) {
        flights.add(fl);
        FileHandler.saveFlights(flights);
    }

    public boolean removeFlight(String flightNo) {
        for (Flight f : flights) {
            if (f.getFlightNo().equals(flightNo)) {
                flights.remove(f);
                return true;
            }
        }
        return false;
    }

    public Flight searchByNo(String flightNo) {
        for (Flight f : flights) {
            if (f.getFlightNo().equals(flightNo)) {
                return f;
            }
        }
        return null;
    }

    public ArrayList<Flight> searchByOrigin(String origin) {
        ArrayList<Flight> found = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getOrigin().equalsIgnoreCase(origin)) {
                found.add(f);
            }
        }
        return found;
    }

    public ArrayList<Flight> searchByDestination(String destination) {
        ArrayList<Flight> found = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDestination().equalsIgnoreCase(destination)) {
                found.add(f);
            }
        }
        return found;
    }

    public ArrayList<Flight> searchByRoute(String origin, String destination) {
        ArrayList<Flight> found = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getOrigin().equalsIgnoreCase(origin) && f.getDestination().equalsIgnoreCase(destination)) {
                found.add(f);
            }
        }
        return found;
    }

   
    public ArrayList<Flight> searchFlight(String origin, String destination) {
        ArrayList<Flight> filteredFlights = new ArrayList<>();
        String filteredOrigin = origin.trim();
        String filteredDestination = destination.trim();
        if (!filteredDestination.isEmpty() && !filteredDestination.isBlank() && filteredDestination != null &&
                !filteredOrigin.isEmpty() && !filteredOrigin.isBlank() && filteredOrigin != null) {
            for (Flight f : flights) {
                if (f.getOrigin().equals(origin) && f.getDestination().equals(destination)) {
                    filteredFlights.add(f);
                }
            }
        } else if (!filteredOrigin.isEmpty() && !filteredOrigin.isBlank() && filteredOrigin != null) {
            for (Flight f : flights) {
                if (f.getOrigin().equals(origin)) {
                    filteredFlights.add(f);
                }
            }
        } else {
            for (Flight f : flights) {
                if (f.getDestination().equals(destination)) {
                    filteredFlights.add(f);
                }
            }
        }
        return filteredFlights;

    }

    // adding seat method as it is composition ----Warda
    public void addAircraft(Aircraft a) {
        this.aircrafts.add(a);
        assignSeats(a);
        FileHandler.saveAircrafts(aircrafts);
    }

    private void assignSeats(Aircraft a) {
        int total = a.getSeatCapacity();
        int businessCount = total / 3;
        int economyCount = total - businessCount;

        String modelCode = extractModelCode(a.getModel());
        ArrayList<Seat> seatsList = new ArrayList<>();

        // Business seats
        for (int i = 0; i < businessCount; i++) {
            String seatCode = modelCode + "-B-" + (i + 1);
            seatsList.add(new Seat(seatCode, "Business"));
        }

        // Economy seats
        for (int i = 0; i < economyCount; i++) {
            String seatCode = modelCode + "-E-" + (i + 1);
            seatsList.add(new Seat(seatCode, "Economy"));
        }

        a.getSeats().clear();
        a.getSeats().addAll(seatsList);
    }

    private String extractModelCode(String model) {
        return model.replaceAll("[^A-Za-z0-9]", "");
    }

    // // Assigning flights to the pilots
    // public void clearAssignments() {
    //     try {
    //         File assignFile = new File("resources/txt_file/assignments.txt");
    //         if (assignFile.exists()) {
    //             new FileWriter(assignFile, false).close(); // clear content
    //             System.out.println("Assignments cleared for fresh run.");
    //         }
    //     } catch (IOException e) {
    //         System.err.println("Error clearing assignments: " + e.getMessage());
    //     }
    // }

    public List<Assignment> assignFlights(EmployeeManager employeeManager) {
        List<Assignment> assigned = new ArrayList<>();
        SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        // Load flights from binary
        ArrayList<Flight> loaded = FileHandler.loadFlights();
        if (loaded == null || loaded.isEmpty()) {
            System.out.println("No flights found in binary file.");
            return assigned;
        }

        // Check already assigned flights
        ArrayList<String> alreadyAssigned = new ArrayList<>();
        File assignFile = new File("resources/txt_file/assignments.txt");
        if (assignFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(assignFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length > 0) {
                            alreadyAssigned.add(parts[0].trim());
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error reading assignments: " + e.getMessage());
            }
        }

        // Sort by departure
        loaded.sort(new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                return f1.getDepartureTime().compareTo(f2.getDepartureTime());
            }
        });

        // Track each employee's assignments using parallel ArrayLists
        ArrayList<String> employeeEmails = new ArrayList<>();
        ArrayList<ArrayList<String>> employeeFlights = new ArrayList<>();
        ArrayList<ArrayList<Date>> employeeDepartures = new ArrayList<>();
        ArrayList<ArrayList<Date>> employeeArrivals = new ArrayList<>();
        ArrayList<ArrayList<String>> employeeOrigins = new ArrayList<>();
        ArrayList<ArrayList<String>> employeeDestinations = new ArrayList<>();

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  STARTING FLIGHT ASSIGNMENTS (2 Pilots + 3 Crew each)    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        for (Flight flight : loaded) {
            if (flight == null)
                continue;

            String flightNo = flight.getFlightNo();
            if (flightNo == null || flightNo.isEmpty())
                continue;

            // Skip if already assigned
            boolean isAlreadyAssigned = false;
            for (String assignedFlight : alreadyAssigned) {
                if (assignedFlight.equalsIgnoreCase(flightNo)) {
                    isAlreadyAssigned = true;
                    break;
                }
            }
            if (isAlreadyAssigned) {
                System.out.println("⊗ " + flightNo + ": Already assigned (skipped)");
                continue;
            }

            Date dep = flight.getDepartureTime();
            Date arr = flight.getArrivalTime();
            String origin = flight.getOrigin();
            String destination = flight.getDestination();

            if (dep == null || arr == null)
                continue;

            // ===== FIND CAPTAIN (prefer Senior, shuffle for randomness) =====
            String chosenCaptain = null;
            List<Pilot> allPilots = employeeManager.getPilots();

            // Shuffle pilots for random selection
            ArrayList<Pilot> shuffledPilots = new ArrayList<>(allPilots);
            shufflePilots(shuffledPilots);

            // Try to find Senior pilot first
            for (Pilot p : shuffledPilots) {
                if (p.getPilotRank().equalsIgnoreCase("Senior")) {
                    if (isEmployeeAvailable(p.getEmail(), dep, arr, origin, destination,
                            employeeEmails, employeeFlights, employeeDepartures,
                            employeeArrivals, employeeOrigins, employeeDestinations)) {
                        chosenCaptain = p.getEmail();
                        break;
                    }
                }
            }

            // If no Senior available, use any available pilot as Captain
            if (chosenCaptain == null) {
                for (Pilot p : shuffledPilots) {
                    if (isEmployeeAvailable(p.getEmail(), dep, arr, origin, destination,
                            employeeEmails, employeeFlights, employeeDepartures,
                            employeeArrivals, employeeOrigins, employeeDestinations)) {
                        chosenCaptain = p.getEmail();
                        break;
                    }
                }
            }

            if (chosenCaptain == null) {
                System.out.println("✗ " + flightNo + ": No captain available");
                continue;
            }

            // ===== FIND CO-PILOT (must be different from captain) =====
            String chosenCoPilot = null;
            for (Pilot p : shuffledPilots) {
                String email = p.getEmail();

                // Skip if this is the captain
                if (email.equalsIgnoreCase(chosenCaptain)) {
                    continue;
                }

                if (isEmployeeAvailable(email, dep, arr, origin, destination,
                        employeeEmails, employeeFlights, employeeDepartures,
                        employeeArrivals, employeeOrigins, employeeDestinations)) {
                    chosenCoPilot = email;
                    break;
                }
            }

            if (chosenCoPilot == null) {
                System.out.println("✗ " + flightNo + ": No co-pilot available");
                continue;
            }

            // ===== FIND 3 CABIN CREW (shuffled for randomness) =====
            ArrayList<String> chosenCrew = new ArrayList<>();
            List<CabinCrew> allCrew = employeeManager.getCabinCrew();

            // Shuffle crew for random selection
            ArrayList<CabinCrew> shuffledCrew = new ArrayList<>(allCrew);
            shuffleCrew(shuffledCrew);

            for (CabinCrew cc : shuffledCrew) {
                if (chosenCrew.size() >= 3)
                    break;

                String email = cc.getEmail();
                if (email == null)
                    continue;

                if (isEmployeeAvailable(email, dep, arr, origin, destination,
                        employeeEmails, employeeFlights, employeeDepartures,
                        employeeArrivals, employeeOrigins, employeeDestinations)) {
                    chosenCrew.add(email);
                }
            }

            if (chosenCrew.size() < 3) {
                System.out.println("✗ " + flightNo + ": Not enough crew (" + chosenCrew.size() + "/3)");
                continue;
            }

            // Record assignment for CAPTAIN
            addEmployeeAssignment(chosenCaptain, flightNo, dep, arr, origin, destination,
                    employeeEmails, employeeFlights, employeeDepartures,
                    employeeArrivals, employeeOrigins, employeeDestinations);

            // Record assignment for CO-PILOT
            addEmployeeAssignment(chosenCoPilot, flightNo, dep, arr, origin, destination,
                    employeeEmails, employeeFlights, employeeDepartures,
                    employeeArrivals, employeeOrigins, employeeDestinations);

            // Record assignment for each crew member
            for (int i = 0; i < chosenCrew.size(); i++) {
                addEmployeeAssignment(chosenCrew.get(i), flightNo, dep, arr, origin, destination,
                        employeeEmails, employeeFlights, employeeDepartures,
                        employeeArrivals, employeeOrigins, employeeDestinations);
            }

            Assignment assignment = new Assignment(flightNo, dep, arr, chosenCaptain, chosenCoPilot, chosenCrew);
            assigned.add(assignment);
            alreadyAssigned.add(flightNo);

            // Save to file (NEW FORMAT: FlightNo,Dep,Arr,Captain,CoPilot,Crew1|Crew2|Crew3)
            String crewJoined = "";
            for (int i = 0; i < chosenCrew.size(); i++) {
                crewJoined += chosenCrew.get(i);
                if (i < chosenCrew.size() - 1) {
                    crewJoined += "|";
                }
            }

            String line = flightNo + "," + iso.format(dep) + "," + iso.format(arr) + "," +
                    chosenCaptain + "," + chosenCoPilot + "," + crewJoined + "\n";
            try (FileWriter fw = new FileWriter(assignFile, true)) {
                fw.write(line);
            } catch (Exception e) {
                System.err.println("Failed to save assignment: " + e.getMessage());
            }

            System.out.println("✓ " + flightNo + " → Captain: " + getShortEmail(chosenCaptain) +
                    " | Co-Pilot: " + getShortEmail(chosenCoPilot) +
                    " | Crew: " + chosenCrew.size());
        }

        return assigned;
    }

    // Helper to show short email (first part only)
    private String getShortEmail(String email) {
        if (email == null)
            return "N/A";
        int atIndex = email.indexOf('@');
        return atIndex > 0 ? email.substring(0, atIndex) : email;
    }

    // Shuffle pilots for random selection
    private void shufflePilots(ArrayList<Pilot> pilots) {
        for (int i = pilots.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Pilot temp = pilots.get(i);
            pilots.set(i, pilots.get(j));
            pilots.set(j, temp);
        }
    }

    // Shuffle crew for random selection
    private void shuffleCrew(ArrayList<CabinCrew> crew) {
        for (int i = crew.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            CabinCrew temp = crew.get(i);
            crew.set(i, crew.get(j));
            crew.set(j, temp);
        }
    }

    private boolean isEmployeeAvailable(String email, Date dep, Date arr,
            String origin, String destination,
            ArrayList<String> employeeEmails,
            ArrayList<ArrayList<String>> employeeFlights,
            ArrayList<ArrayList<Date>> employeeDepartures,
            ArrayList<ArrayList<Date>> employeeArrivals,
            ArrayList<ArrayList<String>> employeeOrigins,
            ArrayList<ArrayList<String>> employeeDestinations) {

        // Find this employee's index
        int empIndex = -1;
        for (int i = 0; i < employeeEmails.size(); i++) {
            if (employeeEmails.get(i).equalsIgnoreCase(email)) {
                empIndex = i;
                break;
            }
        }

        // If employee has no assignments yet, they're available
        if (empIndex == -1) {
            return true;
        }

        // Get this employee's flight list
        ArrayList<Date> empDeps = employeeDepartures.get(empIndex);
        ArrayList<Date> empArrs = employeeArrivals.get(empIndex);
        ArrayList<String> empOrigins = employeeOrigins.get(empIndex);
        ArrayList<String> empDests = employeeDestinations.get(empIndex);

        long twoHours = 2 * 60 * 60 * 1000; // 2 hours in milliseconds

        // Check each existing flight
        for (int i = 0; i < empDeps.size(); i++) {
            Date existingDep = empDeps.get(i);
            Date existingArr = empArrs.get(i);
            String existingOrigin = empOrigins.get(i);
            String existingDest = empDests.get(i);

            // CHECK 1: Time overlap
            if (dep.before(existingArr) && arr.after(existingDep)) {
                return false; // Overlapping flights
            }

            // CHECK 2: Minimum 2-hour gap
            long gapBefore = dep.getTime() - existingArr.getTime();
            long gapAfter = existingDep.getTime() - arr.getTime();

            if (gapBefore > 0 && gapBefore < twoHours) {
                return false; // Not enough rest after previous flight
            }
            if (gapAfter > 0 && gapAfter < twoHours) {
                return false; // Not enough rest before next flight
            }

            // CHECK 3: Location continuity (employee finishes before new flight starts)
            if (existingArr.before(dep) || existingArr.equals(dep)) {
                if (!existingDest.equalsIgnoreCase(origin)) {
                    return false; // Can't teleport - wrong city
                }
            }

            // CHECK 4: Forward continuity (new flight finishes before existing starts)
            if (arr.before(existingDep) || arr.equals(existingDep)) {
                if (!destination.equalsIgnoreCase(existingOrigin)) {
                    return false; // Next flight from wrong city
                }
            }

            // CHECK 5: Max 2 flights per day
            Calendar existingCal = Calendar.getInstance();
            existingCal.setTime(existingDep);
            Calendar newCal = Calendar.getInstance();
            newCal.setTime(dep);

            if (existingCal.get(Calendar.YEAR) == newCal.get(Calendar.YEAR) &&
                    existingCal.get(Calendar.DAY_OF_YEAR) == newCal.get(Calendar.DAY_OF_YEAR)) {

                // Count flights on same day
                int count = 0;
                for (int j = 0; j < empDeps.size(); j++) {
                    Calendar flightCal = Calendar.getInstance();
                    flightCal.setTime(empDeps.get(j));
                    if (flightCal.get(Calendar.YEAR) == newCal.get(Calendar.YEAR) &&
                            flightCal.get(Calendar.DAY_OF_YEAR) == newCal.get(Calendar.DAY_OF_YEAR)) {
                        count++;
                    }
                }
                if (count >= 2) {
                    return false; // Already has 2 flights today
                }
            }
        }

        return true; // All checks passed
    }

    private void addEmployeeAssignment(String email, String flightNo,
            Date dep, Date arr, String origin, String destination,
            ArrayList<String> employeeEmails,
            ArrayList<ArrayList<String>> employeeFlights,
            ArrayList<ArrayList<Date>> employeeDepartures,
            ArrayList<ArrayList<Date>> employeeArrivals,
            ArrayList<ArrayList<String>> employeeOrigins,
            ArrayList<ArrayList<String>> employeeDestinations) {

        // Find employee index
        int empIndex = -1;
        for (int i = 0; i < employeeEmails.size(); i++) {
            if (employeeEmails.get(i).equalsIgnoreCase(email)) {
                empIndex = i;
                break;
            }
        }

        // If employee doesn't exist, add them
        if (empIndex == -1) {
            employeeEmails.add(email);
            employeeFlights.add(new ArrayList<String>());
            employeeDepartures.add(new ArrayList<Date>());
            employeeArrivals.add(new ArrayList<Date>());
            employeeOrigins.add(new ArrayList<String>());
            employeeDestinations.add(new ArrayList<String>());
            empIndex = employeeEmails.size() - 1;
        }

        // Add flight details
        employeeFlights.get(empIndex).add(flightNo);
        employeeDepartures.get(empIndex).add(dep);
        employeeArrivals.get(empIndex).add(arr);
        employeeOrigins.get(empIndex).add(origin);
        employeeDestinations.get(empIndex).add(destination);
    }
}
