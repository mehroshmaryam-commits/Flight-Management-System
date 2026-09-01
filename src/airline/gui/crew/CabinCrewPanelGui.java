package airline.gui.crew;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import airline.flight.FlightManager;
import airline.flight.Aircraft;
import airline.flight.Flight;
import airline.util.ImagePanel;

public class CabinCrewPanelGui {
    private JFrame frame;
    private String crewEmail;
    private JFrame MainFrame;
    private FlightManager flightManager;

    public CabinCrewPanelGui(String email, String role, JFrame MainFrame) {
        this.crewEmail = email;
        this.MainFrame = MainFrame;
        this.flightManager = new FlightManager();
    }

    public void showCrewDashboard() {
        frame = new JFrame("Cabin Crew Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 128, 128));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JPanel logoAndWelcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        logoAndWelcomePanel.setOpaque(false);

        try {
            ImageIcon originalLogo = new ImageIcon("resources/images/logo.png");
            Image scaledLogo = originalLogo.getImage().getScaledInstance(90, 70, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
            logoAndWelcomePanel.add(logoLabel);
        } catch (Exception ex) {
            System.err.println("Could not load logo: " + ex.getMessage());
        }

        JLabel welcomeLabel = new JLabel("Welcome, Flight Attendant");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        logoAndWelcomePanel.add(welcomeLabel);

        headerPanel.add(logoAndWelcomePanel, BorderLayout.WEST);

        JLabel emailLabel = new JLabel("Logged in as: " + crewEmail);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(new Color(178, 255, 255));
        headerPanel.add(emailLabel, BorderLayout.CENTER);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(255, 102, 102));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            if (MainFrame != null) {
                MainFrame.setVisible(true);
            }
            JOptionPane.showMessageDialog(null, "Returning to Home Page.", "Logout", JOptionPane.INFORMATION_MESSAGE);
        });
        headerPanel.add(logoutBtn, BorderLayout.EAST);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));

        tabbedPane.addTab("Assigned Flights", createAssignedFlightsPanel());
        tabbedPane.addTab("Flight Details", createFlightDetailsPanel());
        tabbedPane.addTab("Team Details", createTeamPanel());
        tabbedPane.addTab("History", createHistoryPanel());
        tabbedPane.addTab("Aircraft Info", createAircraftDetailsPanel());

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createAssignedFlightsPanel() {
        JPanel panel = createStyledPanel("resources/images/pexels-lukas-hartmann-304281-1497305.jpg");
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setBackground(new Color(255, 255, 255, 180));
        displayArea.setOpaque(true);
        displayArea.setForeground(Color.BLACK);

        String assignmentText = loadAssignedFlights();
        displayArea.setText(assignmentText);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102), 3));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh Assignments");
        refreshBtn.setBackground(new Color(0, 150, 150));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> {
            displayArea.setText(loadAssignedFlights());
            JOptionPane.showMessageDialog(frame, "Assignments refreshed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    private String loadAssignedFlights() {
        StringBuilder sb = new StringBuilder();
        sb.append("✈️ ASSIGNED FLIGHTS\n");
        sb.append("====================================================================================\n");
        sb.append(String.format("%-15s | %-25s | %-20s | %-20s\n", "FLIGHT ID", "ROUTE", "DEPARTURE", "ARRIVAL"));
        sb.append("====================================================================================\n");

        File assignFile = new File("resources/txt_file/assignments.txt");
        if (!assignFile.exists()) {
            sb.append("No assignments found.\n");
            return sb.toString();
        }

        SimpleDateFormat displayFmt = new SimpleDateFormat("dd-MMM HH:mm");
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(assignFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split(",");
                if (parts.length < 6)
                    continue;

                String flightNo = parts[0].trim();
                String crewList = parts[5].trim();

                // Check if this crew member is assigned
                String[] crewMembers = crewList.split("\\|");
                boolean isAssigned = false;
                for (String crew : crewMembers) {
                    if (crew.trim().equalsIgnoreCase(crewEmail)) {
                        isAssigned = true;
                        break;
                    }
                }

                if (!isAssigned)
                    continue;

                Flight flight = flightManager.searchByNo(flightNo);
                if (flight != null) {
                    String route = flight.getOrigin() + " -> " + flight.getDestination();
                    String depTime = displayFmt.format(flight.getDepartureTime());
                    String arrTime = displayFmt.format(flight.getArrivalTime());

                    sb.append(String.format("%-15s | %-25s | %-20s | %-20s\n",
                            flightNo, route, depTime, arrTime));
                    count++;
                }
            }
        } catch (IOException e) {
            sb.append("Error reading assignments: ").append(e.getMessage()).append("\n");
        }

        if (count == 0) {
            sb.append("No flights assigned to you.\n");
        } else {
            sb.append("====================================================================================\n");
            sb.append("Total Assigned Flights: " + count + "\n");
        }

        return sb.toString();
    }

    private JPanel createFlightDetailsPanel() {
        JPanel panel = createStyledPanel("resources/images/pexels-nguyendesigner-13404730.jpg");
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        List<String> assignedFlightNos = getAssignedFlightNumbers();

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        controlPanel.setOpaque(false);

        JLabel selectLabel = new JLabel("Select Flight:");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectLabel.setForeground(Color.WHITE);

        JComboBox<String> flightSelectBox = new JComboBox<>(assignedFlightNos.toArray(new String[0]));
        flightSelectBox.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton viewBtn = new JButton("View Details");
        viewBtn.setBackground(new Color(0, 102, 204));
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFont(new Font("Arial", Font.BOLD, 14));

        controlPanel.add(selectLabel);
        controlPanel.add(flightSelectBox);
        controlPanel.add(viewBtn);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        detailsArea.setBackground(new Color(255, 255, 255));
        detailsArea.setOpaque(true);
        detailsArea.setText("Select a flight and click 'View Details'.");

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102), 2));

        viewBtn.addActionListener(e -> {
            String selected = (String) flightSelectBox.getSelectedItem();
            if (selected != null && !selected.equals("No flights assigned")) {
                detailsArea.setText(getFlightDetails(selected));
            }
        });

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private List<String> getAssignedFlightNumbers() {
        List<String> flightNos = new ArrayList<>();
        File assignFile = new File("resources/txt_file/assignments.txt");

        if (!assignFile.exists()) {
            flightNos.add("No flights assigned");
            return flightNos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(assignFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String[] crew = parts[5].split("\\|");
                    for (String c : crew) {
                        if (c.trim().equalsIgnoreCase(crewEmail)) {
                            flightNos.add(parts[0].trim());
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (flightNos.isEmpty()) {
            flightNos.add("No flights assigned");
        }
        return flightNos;
    }

    private String getFlightDetails(String flightNo) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

        Flight flight = flightManager.searchByNo(flightNo);
        if (flight == null) {
            return "Flight not found.";
        }

        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("                    FLIGHT DETAILS\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");

        sb.append("Flight Number: ").append(flight.getFlightNo()).append("\n");
        sb.append("Route: ").append(flight.getOrigin()).append(" → ").append(flight.getDestination()).append("\n");
        sb.append("Type: ").append(flight.getFlightType()).append("\n");
        sb.append("Departure: ").append(fmt.format(flight.getDepartureTime())).append("\n");
        sb.append("Arrival: ").append(fmt.format(flight.getArrivalTime())).append("\n\n");

        // Pilot info
        sb.append("───────────────────────────────────────────────────────────\n");
        sb.append("PILOT INFORMATION\n");
        sb.append("───────────────────────────────────────────────────────────\n");
        String pilotEmail = getPilotForFlight(flightNo);
        sb.append("Captain: ").append(pilotEmail != null ? pilotEmail : "Not assigned").append("\n");

        // Crew team
        sb.append("\n───────────────────────────────────────────────────────────\n");
        sb.append("CABIN CREW TEAM\n");
        sb.append("───────────────────────────────────────────────────────────\n");
        List<String> crew = getCrewForFlight(flightNo);
        for (int i = 0; i < crew.size(); i++) {
            String marker = crew.get(i).equalsIgnoreCase(crewEmail) ? " (You)" : "";
            sb.append((i + 1)).append(". ").append(crew.get(i)).append(marker).append("\n");
        }

        return sb.toString();
    }

    private String getPilotForFlight(String flightNo) {
        File assignFile = new File("resources/txt_file/assignments.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(assignFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].trim().equalsIgnoreCase(flightNo)) {
                    return parts[3].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getCrewForFlight(String flightNo) {
        List<String> crew = new ArrayList<>();
        File assignFile = new File("resources/txt_file/assignments.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(assignFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].trim().equalsIgnoreCase(flightNo)) {
                    String[] crewEmails = parts[5].split("\\|");
                    for (String email : crewEmails) {
                        crew.add(email.trim());
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return crew;
    }

    private JPanel createTeamPanel() {
        JPanel panel = createStyledPanel("resources/images/pexels-lukas-hartmann-304281-1497305.jpg");
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextArea teamArea = new JTextArea();
        teamArea.setEditable(false);
        teamArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        teamArea.setBackground(new Color(255, 255, 255));
        teamArea.setOpaque(true);
        teamArea.setText(getTeamDetails());

        JScrollPane scrollPane = new JScrollPane(teamArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102), 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh Team Info");
        refreshBtn.setBackground(new Color(100, 149, 237));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> {
            teamArea.setText(getTeamDetails());
            JOptionPane.showMessageDialog(frame, "Team information refreshed!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    private String getTeamDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("              FLIGHT TEAM INFORMATION\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");

        File assignFile = new File("resources/txt_file/assignments.txt");
        if (!assignFile.exists()) {
            sb.append("No team information available.\n");
            return sb.toString();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(assignFile))) {
            String line;
            int flightCount = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String[] crew = parts[5].split("\\|");
                    boolean isAssigned = false;
                    for (String c : crew) {
                        if (c.trim().equalsIgnoreCase(crewEmail)) {
                            isAssigned = true;
                            break;
                        }
                    }

                    if (isAssigned) {
                        flightCount++;
                        sb.append("Flight ").append(parts[0].trim()).append(":\n");
                        sb.append("  Captain: ").append(parts[3].trim()).append("\n");
                        sb.append("  Cabin Crew:\n");
                        for (int i = 0; i < crew.length; i++) {
                            String marker = crew[i].trim().equalsIgnoreCase(crewEmail) ? " (You)" : "";
                            sb.append("    ").append((i + 1)).append(". ").append(crew[i].trim()).append(marker)
                                    .append("\n");
                        }
                        sb.append("\n");
                    }
                }
            }

            if (flightCount == 0) {
                sb.append("No team assignments found.\n");
            }
        } catch (IOException e) {
            sb.append("Error reading team information: ").append(e.getMessage()).append("\n");
        }

        return sb.toString();
    }

    private JPanel createHistoryPanel() {
        JPanel panel = createStyledPanel("resources/images/pexels-nguyendesigner-13404730.jpg");
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        historyArea.setBackground(new Color(255, 255, 255));
        historyArea.setOpaque(true);
        historyArea.setText(loadServiceHistory());

        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102), 2));
        panel.add(historyScroll, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh History");
        refreshBtn.setBackground(new Color(50, 175, 50));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> {
            historyArea.setText(loadServiceHistory());
            JOptionPane.showMessageDialog(frame, "History refreshed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    private String loadServiceHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("                 SERVICE HISTORY\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");

        File historyFile = new File("resources/txt_file/crew_history_" + crewEmail.replace("@", "_at_") + ".txt");

        if (!historyFile.exists()) {
            sb.append("No service history recorded yet.\n");
            sb.append("\nYour flight history will be automatically recorded after completing flights.\n");
            return sb.toString();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
                count++;
            }
            if (count == 0) {
                sb.append("No service history recorded yet.\n");
            }
        } catch (IOException e) {
            sb.append("Error reading history: ").append(e.getMessage()).append("\n");
        }

        return sb.toString();
    }

    private JPanel createAircraftDetailsPanel() {

    JPanel panel = createStyledPanel("resources/images/pexels-nishantdas-3939833.jpg");
    panel.setLayout(new BorderLayout(20, 20));
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    // Get assigned flights for this crew
    List<String> assignedFlights = getAssignedFlightNumbers();

    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
    topPanel.setOpaque(false);

    JLabel selectLabel = new JLabel("Select Flight:");
    selectLabel.setFont(new Font("Arial", Font.BOLD, 16));
    selectLabel.setForeground(Color.WHITE);

    JComboBox<String> flightBox =
            new JComboBox<>(assignedFlights.toArray(new String[0]));
    flightBox.setFont(new Font("Arial", Font.PLAIN, 16));

    JButton viewBtn = new JButton("View Aircraft Info");
    viewBtn.setBackground(new Color(0, 102, 204));
    viewBtn.setForeground(Color.WHITE);
    viewBtn.setFont(new Font("Arial", Font.BOLD, 14));

    topPanel.add(selectLabel);
    topPanel.add(flightBox);
    topPanel.add(viewBtn);

    JTextArea aircraftArea = new JTextArea();
    aircraftArea.setEditable(false);
    aircraftArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    aircraftArea.setBackground(new Color(255, 255, 255));
    aircraftArea.setOpaque(true);
    aircraftArea.setText("Select a flight and click 'View Aircraft Info'.");

    JScrollPane scrollPane = new JScrollPane(aircraftArea);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102), 2));

    viewBtn.addActionListener(e -> {
        String selectedFlight = (String) flightBox.getSelectedItem();

        if (selectedFlight != null && !selectedFlight.equals("No flights assigned")) {
            aircraftArea.setText(getAircraftInfo(selectedFlight));
        }
    });

    panel.add(topPanel, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
}


    private String getAircraftInfo(String flightNo) {

    StringBuilder sb = new StringBuilder();
    sb.append("═══════════════════════════════════════════════════════════\n");
    sb.append("            AIRCRAFT INFORMATION\n");
    sb.append("═══════════════════════════════════════════════════════════\n\n");

    Flight flight = flightManager.searchByNo(flightNo);

    if (flight == null) {
        sb.append("Flight not found.\n");
        return sb.toString();
    }

    Aircraft aircraft = flight.getAircraft();

    if (aircraft != null) {
        sb.append("Model: ").append(aircraft.getModel()).append("\n");
        sb.append("Airline: ").append(aircraft.getAirline()).append("\n");
        sb.append("Capacity: ").append(aircraft.getSeatCapacity()).append(" Passengers\n");

        int businessSeats = aircraft.getSeatCapacity() / 3;
        int economySeats = aircraft.getSeatCapacity() - businessSeats;

        sb.append("  - Business Class: ").append(businessSeats).append(" seats\n");
        sb.append("  - Economy Class: ").append(economySeats).append(" seats\n");
        sb.append("Status: Active\n");
    } else {
        sb.append("Aircraft: Not yet assigned\n");
    }

    return sb.toString();
}

    private JPanel createStyledPanel(String imagePath) {
        try {
            return new ImagePanel(imagePath);
        } catch (Exception e) {
            JPanel panel = new JPanel();
            panel.setBackground(new Color(240, 255, 255));
            return panel;
        }
    }
}