package airline.gui.crew;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import airline.flight.FlightManager;
import airline.flight.Flight;
import airline.flight.Aircraft;
import airline.util.ImagePanel;

public class PilotPanelGui {
    private JFrame frame;
    private String pilotEmail;
    private String pilotRank;
    private JFrame MainFrame;
    private FlightManager flightManager;

    public PilotPanelGui(String email, String rank, JFrame MainFrame) {
        this.pilotEmail = email;
        this.pilotRank = rank;
        this.MainFrame = MainFrame;
        this.flightManager = new FlightManager();
    }

    public void showPilotDashboard() {
        frame = new JFrame(pilotRank + " Pilot Dashboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 51, 102));
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

        JLabel welcomeLabel = new JLabel("Welcome, " + pilotRank + " Pilot");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        logoAndWelcomePanel.add(welcomeLabel);

        headerPanel.add(logoAndWelcomePanel, BorderLayout.WEST);

        JLabel emailLabel = new JLabel("Logged in as: " + pilotEmail);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(new Color(173, 216, 230));
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
        tabbedPane.addTab("Flight History", createHistoryPanel());


        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createAssignedFlightsPanel() {
        JPanel panel = createStyledPanel("resources/images/bg6.jpg");
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setForeground(Color.BLACK);
        displayArea.setBackground(new Color(255, 255, 255, 200));

        String assignmentText = loadAssignedFlights();
        displayArea.setText(assignmentText);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 3));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh Assignments");
        refreshBtn.setBackground(new Color(70, 130, 180));
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
        sb.append("✈️ ASSIGNED FLIGHTS FOR ").append(pilotRank.toUpperCase()).append(" PILOT\n");
        sb.append("====================================================================================\n");
        sb.append(String.format("%-15s | %-25s | %-20s | %-20s\n", "FLIGHT ID", "ROUTE", "DEPARTURE", "ARRIVAL"));
        sb.append("====================================================================================\n");

        File assignFile = new File("resources/txt_file/assignments.txt");
        if (!assignFile.exists()) {
            sb.append("No assignments found.\n");
            return sb.toString();
        }

        SimpleDateFormat displayFmt = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(assignFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split(",");
                if (parts.length < 4)
                    continue;

            String flightNo = parts[0].trim();
            String captainEmail = parts[3].trim();        // Captain at index 3
            String coPilotEmail = parts[4].trim();        // Co-pilot at index 4

            // Check if pilot is EITHER captain OR co-pilot
            boolean isCaptain = captainEmail.equalsIgnoreCase(pilotEmail);
            boolean isCoPilot = coPilotEmail.equalsIgnoreCase(pilotEmail);

            if (!isCaptain && !isCoPilot) {
                continue;  // Not assigned to this flight
            }

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
        JPanel panel = createStyledPanel("resources/images/airplane-4717538_1280.jpg");
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Get assigned flights
        List<String> assignedFlightNos = getAssignedFlightNumbers();

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        controlPanel.setOpaque(false);

        JLabel selectLabel = new JLabel("Select Flight:");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectLabel.setForeground(Color.WHITE);

        JComboBox<String> flightSelectBox = new JComboBox<>(assignedFlightNos.toArray(new String[0]));
        flightSelectBox.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton viewBtn = new JButton("View Details");
        viewBtn.setBackground(new Color(0, 72, 174));
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
        detailsArea.setText("Select a flight and click 'View Details' to see information.");

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));

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
            if (parts.length >= 5) {
                String captainEmail = parts[3].trim();
                String coPilotEmail = parts[4].trim();
                if (pilotEmail.equalsIgnoreCase(captainEmail) || pilotEmail.equalsIgnoreCase(coPilotEmail)) {
                    flightNos.add(parts[0].trim());
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

        sb.append("───────────────────────────────────────────────────────────\n");
        sb.append("               FLIGHT DETAILS\n");
        sb.append("───────────────────────────────────────────────────────────\n\n");

        sb.append("Flight Number: ").append(flight.getFlightNo()).append("\n");
        sb.append("Route: ").append(flight.getOrigin()).append(" → ").append(flight.getDestination()).append("\n");
        sb.append("Type: ").append(flight.getFlightType()).append("\n");
        sb.append("Departure: ").append(fmt.format(flight.getDepartureTime())).append("\n");
        sb.append("Arrival: ").append(fmt.format(flight.getArrivalTime())).append("\n");
        sb.append("Price: PKR ").append(flight.getPrice()).append("\n\n");
        

        // Crew members
        sb.append("───────────────────────────────────────────────────────────\n");
        sb.append("              CABIN CREW MEMBERS\n");
        sb.append("───────────────────────────────────────────────────────────\n");

        List<String> crewMembers = getCrewForFlight(flightNo);
        if (crewMembers.isEmpty()) {
            sb.append("No crew assigned yet.\n");
        } else {
            for (int i = 0; i < crewMembers.size(); i++) {
                sb.append((i + 1)).append(". ").append(crewMembers.get(i)).append("\n");
            }
        }

        sb.append("───────────────────────────────────────────────────────────\n");
        sb.append("              Aircraft Information \n");
        sb.append("───────────────────────────────────────────────────────────\n");
        
        Aircraft aircraft = flight.getAircraft();

        if (aircraft != null) {
            sb.append("Model: ").append(aircraft.getModel()).append("\n");
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

    private JPanel createHistoryPanel() {
        JPanel panel = createStyledPanel("resources/images/airplane-7116299.jpg");
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        historyArea.setBackground(new Color(255, 255, 255, 220));
        historyArea.setText(loadFlightHistory());

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh History");
        refreshBtn.setBackground(new Color(50, 175, 50));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> {
            historyArea.setText(loadFlightHistory());
            JOptionPane.showMessageDialog(frame, "History refreshed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    private String loadFlightHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("                 FLIGHT HISTORY\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");

        File historyFile = new File("resources/txt_file/pilot_history_" + pilotEmail.replace("@", "_at_") + ".txt");

        if (!historyFile.exists()) {
            sb.append("No flight history recorded yet.\n");
            sb.append("\nFlight history will be automatically recorded after flights are completed.\n");
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
                sb.append("No flight history recorded yet.\n");
            }
        } catch (IOException e) {
            sb.append("Error reading history: ").append(e.getMessage()).append("\n");
        }

        return sb.toString();
    }


    private JPanel createStyledPanel(String imagePath) {
        try {
            return new ImagePanel(imagePath);
        } catch (Exception e) {
            JPanel panel = new JPanel();
            panel.setBackground(new Color(240, 246, 255));
            return panel;
        }
    }
}