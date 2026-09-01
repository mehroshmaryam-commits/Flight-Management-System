package airline.gui.customer;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import airline.flight.Flight;
import airline.flight.FlightManager;
import airline.flight.ConnectedFlight;
import airline.flight.Layover;

public class FlightGridViewer4 extends JFrame {
    private boolean isBookingMode;
    private ArrayList<Flight> flights;
    private JPanel gridPanel;
    private CustomerUI customerUI;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a");

    public FlightGridViewer4(boolean bookingMode) {
        this.isBookingMode = bookingMode; // Store the booking mode
        this.flights = FlightManager.INSTANCE.getFlights();

        setTitle(isBookingMode ? "Book a Flight" : "Displaying All Flights");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1250, 750);
        setLocationRelativeTo(null);

        setContentPane(new JLabel(new ImageIcon("resources/images/plane_background.png")));
        setLayout(new BorderLayout());

        // Create top container with heading and filter panel
        JPanel topContainer = new JPanel(new BorderLayout());

        JLabel heading = new JLabel(isBookingMode ? "BOOK A FLIGHT" : "DISPLAYING ALL FLIGHTS", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(Color.WHITE);
        heading.setOpaque(true);
        heading.setBackground(new Color(0, 0, 128));

        topContainer.add(heading, BorderLayout.NORTH);
        topContainer.add(createFilterPanel(), BorderLayout.SOUTH); // Add filter panel
        add(topContainer, BorderLayout.NORTH);

        // Grid panel (2 columns)
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 2, 20, 20));
        gridPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        populateGrid();
        setVisible(true);
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(new Color(230, 230, 250));

        JLabel originLabel = new JLabel("Origin:");
        originLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JTextField originField = new JTextField(15);

        JLabel destLabel = new JLabel("Destination:");
        destLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JTextField destField = new JTextField(15);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(70, 130, 180));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
        searchBtn.setFocusPainted(false);

        searchBtn.addActionListener(e -> {
            String origin = originField.getText().trim();
            String dest = destField.getText().trim();
            ArrayList<Flight> result = FlightManager.INSTANCE.searchFlight(origin, dest);

            if (result.isEmpty()) {
                return;
            }

            this.flights = result;
            populateGrid();
        });

        JButton resetBtn = new JButton("Reset");
        resetBtn.setBackground(new Color(180, 70, 70));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFont(new Font("Arial", Font.BOLD, 14));
        resetBtn.setFocusPainted(false);

        resetBtn.addActionListener(e -> {
            originField.setText("");
            destField.setText("");
            this.flights = FlightManager.INSTANCE.getFlights();
            populateGrid();
        });

        filterPanel.add(originLabel);
        filterPanel.add(originField);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(destLabel);
        filterPanel.add(destField);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(searchBtn);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(resetBtn);

        return filterPanel;
    }

    private void populateGrid() {
        gridPanel.removeAll();
        for (Flight f : flights) {
            gridPanel.add(createFlightPanel(f));
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createFlightPanel(Flight f) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(new LineBorder(Color.BLUE, 3),
                new EmptyBorder(10, 10, 10, 10)));
        panel.setLayout(new BorderLayout());

        // Flight Number Heading
        JLabel flightNoLabel = new JLabel(f.getFlightNo(), SwingConstants.CENTER);
        flightNoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        flightNoLabel.setForeground(Color.BLUE);
        panel.add(flightNoLabel, BorderLayout.NORTH);

        // Center content
        JPanel centerPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        centerPanel.setOpaque(false);

        // Flight type label (blue)
        JLabel typeLabel = new JLabel("Type: " + f.getFlightType());
        typeLabel.setForeground(Color.BLUE);
        centerPanel.add(typeLabel);
        centerPanel.add(new JLabel("")); // placeholder

        // Outbound details
        addFlightDetails(centerPanel, f, false);

        // Layover button for connecting flights
        JButton layoverBtn = null;
        if (f.getFlightType().equalsIgnoreCase("Connecting")) {
            layoverBtn = new JButton("Show Layover Info");
            layoverBtn.setForeground(Color.BLUE);
            JButton finalLayoverBtn = layoverBtn;

            layoverBtn.addActionListener(e -> toggleLayover(centerPanel, f, finalLayoverBtn));
            centerPanel.add(layoverBtn);
            centerPanel.add(new JLabel(""));
        }

        // Return flight toggle
        if (f.getReturnFlight() != null) {
            JLabel returnLabel = new JLabel("Return Flight (Click to show details)");
            returnLabel.setForeground(Color.BLUE.darker());
            returnLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            returnLabel.addMouseListener(new MouseAdapter() {
                boolean showingReturn = false;

                @Override
                public void mouseClicked(MouseEvent e) {
                    showingReturn = !showingReturn;
                    centerPanel.removeAll();

                    // Re-add type label
                    JLabel typeLbl = new JLabel("Type: " + f.getFlightType());
                    typeLbl.setForeground(Color.BLUE);
                    centerPanel.add(typeLbl);
                    centerPanel.add(new JLabel(""));

                    if (showingReturn) {
                        addFlightDetails(centerPanel, f.getReturnFlight(), true);
                        if (f.getReturnFlight().getFlightType().equalsIgnoreCase("Connecting")) {
                            JButton layoverBtn2 = new JButton("Show Layover Info");
                            layoverBtn2.setForeground(Color.BLUE);
                            layoverBtn2.addActionListener(
                                    ev -> toggleLayover(centerPanel, f.getReturnFlight(), layoverBtn2));
                            centerPanel.add(layoverBtn2);
                            centerPanel.add(new JLabel(""));
                        }
                    } else {
                        addFlightDetails(centerPanel, f, false);
                        if (f.getFlightType().equalsIgnoreCase("Connecting")) {
                            JButton layoverBtn2 = new JButton("Show Layover Info");
                            layoverBtn2.setForeground(Color.BLUE);
                            layoverBtn2.addActionListener(ev -> toggleLayover(centerPanel, f, layoverBtn2));
                            centerPanel.add(layoverBtn2);
                            centerPanel.add(new JLabel(""));
                        }
                    }

                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            });

            panel.add(returnLabel, BorderLayout.SOUTH);
        }

        panel.add(centerPanel, BorderLayout.CENTER);

        // City image (side)
        String imgPath = "resources/images/" + f.getDestination().toLowerCase().replaceAll(" ", "_") + ".jpg";
        ImageIcon icon = new ImageIcon(imgPath);
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        panel.add(imgLabel, BorderLayout.EAST);

        // Warda-----------------------------------------------------------------
        if (isBookingMode) {
            JButton bookBtn = new JButton("Book Flight");
            bookBtn.setBackground(new Color(0, 128, 0));
            bookBtn.setForeground(Color.WHITE);
            bookBtn.setFont(new Font("Arial", Font.BOLD, 14));
            bookBtn.setFocusPainted(false);
            bookBtn.addActionListener(e -> {
                int confirmBooking = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to book this flight with your logged-in credentials?",
                        "Confirm Booking",
                        JOptionPane.YES_NO_OPTION);
                if (confirmBooking == JOptionPane.YES_OPTION) {
                    customerUI.bookingHandler(f);

                }

            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.add(bookBtn);
            panel.add(buttonPanel, BorderLayout.SOUTH);
        }

        return panel;
    }

    private void addFlightDetails(JPanel centerPanel, Flight f, boolean isReturn) {

        String depText = isReturn ? "Departure (Return):" : "Departure (Outbound):";
        String arrText = isReturn ? "Arrival (Return):" : "Arrival (Outbound):";

        JLabel originLabel = new JLabel("Origin:");
        originLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(originLabel);

        JLabel originValue = new JLabel(f.getOrigin());
        originValue.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(originValue);

        JLabel destLabel = new JLabel("Destination:");
        destLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(destLabel);

        JLabel destValue = new JLabel(f.getDestination());
        destValue.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(destValue);

        JLabel depLabel = new JLabel(depText);
        depLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(depLabel);

        JLabel depValue = new JLabel(sdf.format(f.getDepartureTime()));
        depValue.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(depValue);

        JLabel arrLabel = new JLabel(arrText);
        arrLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(arrLabel);

        JLabel arrValue = new JLabel(sdf.format(f.getArrivalTime()));
        arrValue.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(arrValue);

        // individual prices for outbound flights
        if (!isReturn && f.getReturnFlight() == null) {
            JLabel priceLabel = new JLabel("Price:");
            priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
            centerPanel.add(priceLabel);

            JLabel priceValue = new JLabel("PKR " + f.getPrice());
            priceValue.setFont(new Font("Arial", Font.BOLD, 16));
            centerPanel.add(priceValue);
        }

        // Total price for round-trip
        if (!isReturn && f.getReturnFlight() != null) {
            double totalPrice = f.getPrice() + f.getReturnFlight().getPrice();

            JLabel totalLabel = new JLabel("Total Price (Package):");
            totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
            centerPanel.add(totalLabel);

            JLabel totalValue = new JLabel("PKR " + totalPrice);
            totalValue.setFont(new Font("Arial", Font.BOLD, 16));
            centerPanel.add(totalValue);
        }
    }

    private void toggleLayover(JPanel centerPanel, Flight f, JButton btn) {
        boolean showingLayover = btn.getText().startsWith("Hide");
        centerPanel.removeAll();

        // Re-add flight type
        JLabel typeLabel = new JLabel("Type: " + f.getFlightType());
        typeLabel.setForeground(Color.BLUE);
        centerPanel.add(typeLabel);
        centerPanel.add(new JLabel(""));

        if (showingLayover) {
            // Show normal flight details
            addFlightDetails(centerPanel, f, false);
            if (f.getFlightType().equalsIgnoreCase("Connecting")) {
                JButton layoverBtn = new JButton("Show Layover Info");
                layoverBtn.setForeground(Color.BLUE);
                layoverBtn.addActionListener(e -> toggleLayover(centerPanel, f, layoverBtn));
                centerPanel.add(layoverBtn);
                centerPanel.add(new JLabel(""));
            }
        } else {
            // Show layover info
            if (f instanceof ConnectedFlight) {
                ConnectedFlight cf = (ConnectedFlight) f;
                Layover l = cf.getLayoverInfo();

                JLabel heading = new JLabel("Showing Layover Info");
                heading.setFont(new Font("Arial", Font.BOLD, 16));
                heading.setForeground(Color.BLUE);
                centerPanel.add(heading);
                centerPanel.add(new JLabel(""));

                JLabel flightNoLabel = new JLabel("Flight No:");
                flightNoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(flightNoLabel);

                JLabel flightNoValue = new JLabel(cf.getFlightNo());
                flightNoValue.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(flightNoValue);

                JLabel cityLabel = new JLabel("Layover City:");
                cityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(cityLabel);

                JLabel cityValue = new JLabel(l.getCity());
                cityValue.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(cityValue);

                JLabel durationLabel = new JLabel("Duration (mins):");
                durationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(durationLabel);

                JLabel durationValue = new JLabel(String.valueOf(l.getDuration()));
                durationValue.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(durationValue);

                JLabel depTimeLabel = new JLabel("Departure Time:");
                depTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(depTimeLabel);

                JLabel depTimeValue = new JLabel(sdf.format(cf.getSecondDepartureTime()));
                depTimeValue.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(depTimeValue);

                JLabel arrTimeLabel = new JLabel("Arrival Time:");
                arrTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(arrTimeLabel);

                JLabel arrTimeValue = new JLabel(sdf.format(cf.getSecondArrivalTime()));
                arrTimeValue.setFont(new Font("Arial", Font.PLAIN, 14));
                centerPanel.add(arrTimeValue);

                // Button to hide layover
                JButton hideBtn = new JButton("Hide Layover Info");
                hideBtn.setForeground(Color.BLUE);
                hideBtn.addActionListener(e -> toggleLayover(centerPanel, f, hideBtn));
                centerPanel.add(hideBtn);
                centerPanel.add(new JLabel(""));
            }
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void setCustomerUI(CustomerUI ui) {
        this.customerUI = ui;
    }

}
