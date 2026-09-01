package airline.customer;

import javax.swing.*;
import java.awt.*;
import airline.app.HomePageGui;

public class EditProfile extends JFrame {
    private Customer loggedInCustomer;
    private CustomerManager customerManager;
    private HomePageGui homePageGui;
    private JFrame profileFrame;

    public EditProfile(Customer customer, CustomerManager manager, HomePageGui homeGui) {
        this.loggedInCustomer = customer;
        this.customerManager = manager;
        this.homePageGui = homeGui;
    }

    public void showProfile() {
        profileFrame = new JFrame("My Profile");
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setSize(450, 650);

        profileFrame.setLocationRelativeTo(null);
        profileFrame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(450, 80));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Profile Icon
        ImageIcon originalIcon = new ImageIcon("resources/user.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel profileIcon = new JLabel(new ImageIcon(scaledImage));

        JLabel headerLabel = new JLabel("My Profile");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 26));
        headerLabel.setForeground(Color.WHITE);

        headerPanel.add(profileIcon);
        headerPanel.add(Box.createHorizontalStrut(10));
        headerPanel.add(headerLabel);

        Customer currentCustomer = customerManager.getCustomerByEmail(loggedInCustomer.getEmail());
        if (currentCustomer != null) {
            loggedInCustomer = currentCustomer;
        }

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Add info fields
        infoPanel.add(createInfoRow("Name:", loggedInCustomer.getName()));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createInfoRow("Gender:", loggedInCustomer.getGender()));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createInfoRow("Email:", loggedInCustomer.getEmail()));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createInfoRow("Phone:", loggedInCustomer.getPhoneNumber()));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createInfoRow("Address:", loggedInCustomer.getPostalAddress()));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createInfoRow("CNIC:", loggedInCustomer.getCNIC()));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createInfoRow("Date of Birth:", loggedInCustomer.getDateOfBirth()));
        infoPanel.add(Box.createVerticalStrut(15));

        infoPanel.add(createInfoRow("Emergency Contact:", loggedInCustomer.getEmergencyContact()));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton editButton = new JButton("Edit Profile");
        editButton.setFont(new Font("Arial", Font.BOLD, 16));
        editButton.setBackground(new Color(70, 130, 180));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setPreferredSize(new Dimension(140, 40));
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 16));
        homeButton.setBackground(new Color(0, 51, 102));
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setPreferredSize(new Dimension(140, 40));
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        editButton.addActionListener(e -> {
            profileFrame.dispose();
            showEditProfileForm();
        });

        homeButton.addActionListener(e -> {
            profileFrame.dispose();
        });

        buttonPanel.add(editButton);
        buttonPanel.add(homeButton);

        // Add to frame
        profileFrame.add(headerPanel, BorderLayout.NORTH);
        profileFrame.add(new JScrollPane(infoPanel), BorderLayout.CENTER); // ✅ Added ScrollPane
        profileFrame.add(buttonPanel, BorderLayout.SOUTH);

        profileFrame.setVisible(true);
    }

    private JPanel createInfoRow(String label, String value) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Arial", Font.BOLD, 15));
        labelText.setForeground(new Color(70, 130, 180));

        JLabel valueText = new JLabel(value != null ? value : "N/A");
        valueText.setFont(new Font("Arial", Font.PLAIN, 15));
        valueText.setForeground(new Color(50, 50, 50));

        rowPanel.add(labelText, BorderLayout.WEST);
        rowPanel.add(valueText, BorderLayout.CENTER);

        return rowPanel;
    }

    private void showEditProfileForm() {
        JFrame editFrame = new JFrame("Edit Profile");
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editFrame.setSize(500, 650); // ✅ Increased height
        editFrame.setLocationRelativeTo(null);
        editFrame.setLayout(new BoxLayout(editFrame.getContentPane(), BoxLayout.Y_AXIS));
        editFrame.getContentPane().setBackground(new Color(240, 246, 255));

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Edit Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Name panel
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(new Color(240, 246, 255));
        JLabel nameLabel = new JLabel("Full Name: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField nameField = new JTextField(loggedInCustomer.getName(), 20);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // Gender panel
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(new Color(240, 246, 255));
        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        String[] genders = { "Male", "Female", "Other" };
        JComboBox<String> genderBox = new JComboBox<>(genders);
        genderBox.setSelectedItem(loggedInCustomer.getGender());
        genderBox.setFont(new Font("Arial", Font.PLAIN, 16));
        genderPanel.add(genderLabel);
        genderPanel.add(genderBox);

        // Email panel (read-only display)
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.setBackground(new Color(240, 246, 255));
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel emailDisplay = new JLabel(loggedInCustomer.getEmail());
        emailDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
        emailDisplay.setForeground(new Color(100, 100, 100));
        emailPanel.add(emailLabel);
        emailPanel.add(emailDisplay);

        // Password panel
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(new Color(240, 246, 255));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JPasswordField passwordField = new JPasswordField(loggedInCustomer.getPassword(), 20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Phone panel
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        phonePanel.setBackground(new Color(240, 246, 255));
        JLabel phoneLabel = new JLabel("Phone: ");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        String fullPhone = loggedInCustomer.getPhoneNumber();
        String code = fullPhone.substring(0, fullPhone.indexOf(" ") > 0 ? fullPhone.indexOf(" ") : 3);
        String number = fullPhone.substring(code.length());

        String[] codes = { "+1", "+92", "+44", "+61", "+91" };
        JComboBox<String> codeBox = new JComboBox<>(codes);
        codeBox.setSelectedItem(code);
        JTextField phoneField = new JTextField(number, 15);
        phonePanel.add(phoneLabel);
        phonePanel.add(codeBox);
        phonePanel.add(phoneField);

        // Address panel
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addressPanel.setBackground(new Color(240, 246, 255));
        JLabel addressLabel = new JLabel("Postal Address: ");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField addressField = new JTextField(loggedInCustomer.getPostalAddress(), 20);
        addressPanel.add(addressLabel);
        addressPanel.add(addressField);

        JPanel cnicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cnicPanel.setBackground(new Color(240, 246, 255));
        JLabel cnicLabel = new JLabel("CNIC: ");
        cnicLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel cnicDisplay = new JLabel(loggedInCustomer.getCNIC());
        cnicDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
        cnicDisplay.setForeground(new Color(100, 100, 100));
        cnicPanel.add(cnicLabel);
        cnicPanel.add(cnicDisplay);

        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dobPanel.setBackground(new Color(240, 246, 255));
        JLabel dobLabel = new JLabel("Date of Birth: ");
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField dobField = new JTextField(loggedInCustomer.getDateOfBirth(), 20);
        dobField.setToolTipText("Format: DD-MM-YYYY");
        dobPanel.add(dobLabel);
        dobPanel.add(dobField);

        JPanel emergencyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emergencyPanel.setBackground(new Color(240, 246, 255));
        JLabel emergencyLabel = new JLabel("Emergency: ");
        emergencyLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        String emergencyFull = loggedInCustomer.getEmergencyContact();
        String emergencyCode = "";
        String emergencyNum = "";
        if (emergencyFull != null && emergencyFull.contains("-")) {
            String[] parts = emergencyFull.split("-");
            emergencyCode = parts[0];
            emergencyNum = parts.length > 1 ? parts[1] : "";
        }

        JComboBox<String> emergencyCodeBox = new JComboBox<>(codes);
        emergencyCodeBox.setSelectedItem(emergencyCode);
        JTextField emergencyField = new JTextField(emergencyNum, 15);
        emergencyPanel.add(emergencyLabel);
        emergencyPanel.add(emergencyCodeBox);
        emergencyPanel.add(emergencyField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 246, 255));

        JButton submitBtn = new JButton("Save Changes");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBackground(new Color(70, 130, 180));
        submitBtn.setPreferredSize(new Dimension(150, 40));
        submitBtn.setFocusPainted(false);

        JButton homeBtn = new JButton("Home");
        homeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setBackground(new Color(0, 51, 102));
        homeBtn.setPreferredSize(new Dimension(150, 40));
        homeBtn.setFocusPainted(false);

        buttonPanel.add(submitBtn);
        buttonPanel.add(homeBtn);

        // Add all panels
        editFrame.add(titlePanel);
        editFrame.add(Box.createVerticalStrut(10));
        editFrame.add(namePanel);
        editFrame.add(genderPanel);
        editFrame.add(emailPanel);
        editFrame.add(passwordPanel);
        editFrame.add(phonePanel);
        editFrame.add(addressPanel);
        editFrame.add(cnicPanel);
        editFrame.add(dobPanel);
        editFrame.add(emergencyPanel);
        editFrame.add(Box.createVerticalStrut(10));
        editFrame.add(buttonPanel);

        editFrame.setVisible(true);

        // Submit button action
        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String gender = (String) genderBox.getSelectedItem();
            String password = new String(passwordField.getPassword()).trim();
            String phone = phoneField.getText().trim();
            String code2 = (String) codeBox.getSelectedItem();
            String fullPhone2 = code2 + phone;
            String address = addressField.getText().trim();
            String dob = dobField.getText().trim();
            String emergencyPhone = emergencyField.getText().trim();
            String emergencyCode2 = (String) emergencyCodeBox.getSelectedItem();
            String fullEmergency = emergencyCode2 + "-" + emergencyPhone;

            // Validation
            if (name.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(editFrame,
                        "Please fill in all required fields.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(editFrame,
                        "Phone number must be 10 digits.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (gender == null || gender.isEmpty()) {
                JOptionPane.showMessageDialog(editFrame,
                        "Please select your gender.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //
            if (dob.isEmpty() || !dob.matches("(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}")) {
                JOptionPane.showMessageDialog(editFrame,
                        "Invalid Date of Birth!\nFormat: DD-MM-YYYY",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //
            if (emergencyPhone.isEmpty() || !emergencyPhone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(editFrame,
                        "Emergency contact must be 10 digits.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String oldCNIC = loggedInCustomer.getCNIC();

            customerManager.updateCustomerProfile(
                    oldCNIC,
                    name,
                    gender,
                    fullPhone2,
                    address,
                    loggedInCustomer.getEmail(),
                    password,
                    oldCNIC,
                    dob,
                    fullEmergency);

            // Update the logged-in customer reference with current data
            loggedInCustomer = customerManager.searchByCNIC(oldCNIC);

            JOptionPane.showMessageDialog(editFrame,
                    "Profile updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            editFrame.dispose();
            showProfile();
        });

        homeBtn.addActionListener(e -> {
            editFrame.dispose();
        });
    }
}