package airline.gui.customer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import airline.flight.FlightManager;
import airline.flight.Flight;

public class FlightSearchViewer extends JFrame {

    private FlightManager manager;
    private JPanel cardsPanel;

    // Search controls
    private JComboBox<String> typeBox;
    private JComboBox<String> originCombo;
    private JComboBox<String> destinationCombo;

    public FlightSearchViewer(FlightManager manager) {
        this.manager = manager;

        setTitle("Flight Search & Browse");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSearchPanel(), BorderLayout.NORTH);
        add(createCardsSection(), BorderLayout.CENTER);

        loadFlights(manager.getFlights());
        setVisible(true);
    }

    // searching fields
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3, 1, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton searchBtn = new JButton("Search");

        // Row 1: Flight type filter
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Flight Type:"));
        typeBox = new JComboBox<>(new String[] { "All", "Direct", "Connecting", "One-way", "Return Package" });
        row1.add(typeBox);

        // search by origin
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Search By Origin:"));
        originCombo = new JComboBox<>(new String[] { "None", "Islamabad", "Karachi", "Lahore", "Multan", "London",
                "Dubai", "Paris", "New York" });
        row2.add(originCombo);

        // search by destination
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JLabel("Search by Destination: "));
        destinationCombo = new JComboBox<>(new String[] { "None", "Islamabad", "Karachi", "Lahore", "Multan", "London",
                "Dubai", "Paris", "New York" });
        row3.add(destinationCombo);
        row3.add(searchBtn);

        // add rows
        searchPanel.add(row1); // flight type
        searchPanel.add(row2); // origin and destination
        searchPanel.add(row3);

        // actions
        typeBox.addActionListener(e -> applySearch());
        originCombo.addActionListener(e -> applySearch());
        destinationCombo.addActionListener(e -> applySearch());
        searchBtn.addActionListener(e -> applySearch());

        return searchPanel;
    }

    // cards
    private JScrollPane createCardsSection() {
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(cardsPanel);
        return scroll;
    }

    // loading flights
    private void loadFlights(ArrayList<Flight> flights) {
        cardsPanel.removeAll();
        for (Flight f : flights) {
            cardsPanel.add(createFlightCard(f));
        }
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private JPanel createFlightCard(Flight f) {
        JPanel card = new JPanel(new GridLayout(0, 1));

        card.setPreferredSize(new Dimension(250, 150));
        card.setMaximumSize(new Dimension(250, 150));
        card.setMinimumSize(new Dimension(250, 150));
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel flightNo = new JLabel(f.getFlightNo());
        flightNo.setForeground(Color.BLUE);
        flightNo.setFont(new Font("Arial", Font.BOLD, 16));

        card.add(flightNo);
        card.add(new JLabel("Flight Type: " + f.getFlightType()));
        card.add(new JLabel("Origin: " + f.getOrigin()));
        card.add(new JLabel("Destination: " + f.getDestination()));
        card.add(new JLabel("Price: PKR " + f.getPrice()));
        if (f.getReturnFlight() != null) {
            card.add(new JLabel("Return Flight"));
        } else
            card.add(new JLabel("One-way Flight"));

        return card;
    }

    // method to update cards
    private void applySearch() {
        ArrayList<Flight> result = new ArrayList<>(manager.getFlights());

        // filter by flight type
        String type = (String) typeBox.getSelectedItem();
        if (!type.equals("All")) {
            result.removeIf(f -> {
                if (type.equals("Direct"))
                    return !f.getFlightType().equalsIgnoreCase("Direct");
                if (type.equals("Connecting"))
                    return !f.getFlightType().equalsIgnoreCase("Connecting");
                if (type.equals("One-way"))
                    return f.getReturnFlight() != null;
                if (type.equals("Return Package"))
                    return f.getReturnFlight() == null;
                return false;
            });
        }

        // Search by origin/destination
        String originComboVal = (String) originCombo.getSelectedItem();
        String destComboVal = (String) destinationCombo.getSelectedItem();

        if (!originComboVal.equals("None")) {
            result.removeIf(f -> !f.getOrigin().equalsIgnoreCase(originComboVal));
        }

        if (!destComboVal.equals("None")) {
            result.removeIf(f -> !f.getDestination().equalsIgnoreCase(destComboVal));
        }

        loadFlights(result);
    }
}
