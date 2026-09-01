package airline.gui.admin;

import javax.swing.*;
import java.awt.*;
import airline.admin.EmployeeManager;
import airline.admin.Admin;
import airline.crew.Pilot;
import airline.util.ImagePanel;

public class HRManagerPanelGui {
    private JFrame frame;
    private JFrame parentHomeFrame;
    private EmployeeManager employeeManager;

    private JTextField editNameField, editEmailField, editCnicField, editRankField, editLicenseField;
    private JComboBox<String> editGenderBox, editRoleBox;
    private Admin currentEmployee; // currently loaded employ ki info idr hogi

    private final Color PRIMARY_COLOR = new Color(0, 51, 102);
    private final Color ACCENT_COLOR = new Color(255, 165, 0);
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color CARD_BACKGROUND = new Color(248, 248, 255);

    public HRManagerPanelGui(EmployeeManager manager, JFrame parent, String hrEmail) {
        this.employeeManager = manager;
        this.parentHomeFrame = parent;
        
    }

    public void showHRDashboard() {
        frame = new JFrame("HR Manager Dashboard - Employee Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JPanel logoAndTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        logoAndTitlePanel.setOpaque(false);

        try {
            ImageIcon originalLogo = new ImageIcon("resources/images/logo.png");
            Image scaledLogo = originalLogo.getImage().getScaledInstance(90, 70, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
            logoAndTitlePanel.add(logoLabel);
        } catch (Exception ex) {
            System.err.println("Logo file not found: images/logo.png. Using text fallback.");
        }

        JLabel titleLabel = new JLabel("HR Administration Hub");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(ACCENT_COLOR);
        logoAndTitlePanel.add(titleLabel);

        headerPanel.add(logoAndTitlePanel, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 20, 60));
        logoutBtn.setForeground(TEXT_COLOR);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 16));
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            parentHomeFrame.setVisible(true);
        });
        headerPanel.add(logoutBtn, BorderLayout.EAST);

        frame.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));
        tabbedPane.setBackground(CARD_BACKGROUND);

        tabbedPane.addTab("➕ Add New Employee", createAddEmployeePanel());
        tabbedPane.addTab("📝 View & Edit Profiles", createViewEditPanel());
        tabbedPane.addTab("❌ Delete Employee", createDeleteEmployeePanel());

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createAddEmployeePanel() {
        JPanel panel = createStyledPanel("resources/images/pexels-ahmetyuksek-33889462.jpg");
        panel.setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 20, 15));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 3),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(),
                        "New Employee Registration",
                        javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 16),
                        PRIMARY_COLOR)));
        formPanel.setBackground(Color.WHITE);

        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField cnicField = new JTextField(20);
        JComboBox<String> roleBox = new JComboBox<>(new String[] { "Pilot", "CabinCrew" });
        JComboBox<String> genderBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });

        JTextField rankField = new JTextField(20);
        JTextField licenseField = new JTextField(20);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        formPanel.add(createLabel("Role:", labelFont, PRIMARY_COLOR));
        formPanel.add(roleBox);
        formPanel.add(createLabel("Full Name:", labelFont, PRIMARY_COLOR));
        formPanel.add(nameField);
        formPanel.add(createLabel("Gender:", labelFont, PRIMARY_COLOR));
        formPanel.add(genderBox);
        formPanel.add(createLabel("Email (Login):", labelFont, PRIMARY_COLOR));
        formPanel.add(emailField);
        formPanel.add(createLabel("Initial Password:", labelFont, PRIMARY_COLOR));
        formPanel.add(passwordField);
        formPanel.add(createLabel("CNIC:", labelFont, PRIMARY_COLOR));
        formPanel.add(cnicField);
        formPanel.add(createLabel("Pilot Rank (if Pilot):", labelFont, PRIMARY_COLOR));
        formPanel.add(rankField);
        formPanel.add(createLabel("License Number (if Pilot):", labelFont, PRIMARY_COLOR));
        formPanel.add(licenseField);

        JButton submitBtn = new JButton("Add Employee & Save");
        submitBtn.setBackground(new Color(0, 150, 0));
        submitBtn.setForeground(TEXT_COLOR);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 18));

        submitBtn.addActionListener(e -> {
            String role = (String) roleBox.getSelectedItem();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String cnic = cnicField.getText().trim();

            if (email.isEmpty() || password.isEmpty() || nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name, Email, and Password are required.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (employeeManager.findEmployeeByEmail(email) != null) {
                JOptionPane.showMessageDialog(frame, "Error: Email is already registered.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!cnic.matches("\\d{13}")) {
                JOptionPane.showMessageDialog(frame,
                        "CNIC must be 13 digits.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            Admin newEmployee = null;

            if (role.equals("Pilot")) {
                newEmployee = new Pilot(nameField.getText(),
                        (String) genderBox.getSelectedItem(),
                        "N/A", "N/A",
                        email,
                        password, 
                        cnicField.getText(),
                        rankField.getText(),
                        licenseField.getText());
            } else if (role.equals("CabinCrew")) {
                newEmployee = new Admin(nameField.getText(),
                        (String) genderBox.getSelectedItem(),
                        "N/A", "N/A",
                        email,
                        password,
                        cnicField.getText(),
                        "CabinCrew");
            }
            if (newEmployee != null && employeeManager.addEmployee(newEmployee)) {
                JOptionPane.showMessageDialog(frame, "Employee " + email + " added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                cnicField.setText("");
                rankField.setText("");
                licenseField.setText(""); // Clear pilot fields too
            } else {
                JOptionPane.showMessageDialog(frame, "Error creating employee or email exists.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(formPanel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(submitBtn, gbc);

        return panel;
    }

    private JPanel createViewEditPanel() {
        JPanel panel = createStyledPanel("resources/images/travel-1767532_1280.jpg");
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        searchPanel.setOpaque(false);

        JTextField searchField = new JTextField(25);
        JButton searchBtn = new JButton("Search & Load Details");
        searchBtn.setBackground(ACCENT_COLOR);
        searchBtn.setForeground(PRIMARY_COLOR);
        searchBtn.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel searchLabel = new JLabel("Employee Email:");
        searchLabel.setForeground(Color.WHITE);
        searchPanel.add(searchLabel);

        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        panel.add(searchPanel, BorderLayout.NORTH);

        JPanel editFormContainer = new JPanel(new GridBagLayout());
        editFormContainer.setOpaque(false);

        JPanel editFormPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        editFormPanel.setBorder(BorderFactory.createTitledBorder("Editable Profile Details"));
        editFormPanel.setBackground(new Color(255, 255, 255, 220));

        editRoleBox = new JComboBox<>(new String[] { "Pilot", "CabinCrew" });
        editRoleBox.setEnabled(false);
        editNameField = new JTextField(20);
        editGenderBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });
        editEmailField = new JTextField(20);
        editCnicField = new JTextField(20);
        editRankField = new JTextField(20);
        editLicenseField = new JTextField(20);

        Font formLabelFont = new Font("Arial", Font.BOLD, 12);

        editFormPanel.add(createLabel("Role:", formLabelFont, PRIMARY_COLOR));
        editFormPanel.add(editRoleBox);
        editFormPanel.add(createLabel("Name:", formLabelFont, PRIMARY_COLOR));
        editFormPanel.add(editNameField);
        editFormPanel.add(createLabel("Gender:", formLabelFont, PRIMARY_COLOR));
        editFormPanel.add(editGenderBox);
        editFormPanel.add(createLabel("Email (Non-Editable):", formLabelFont, PRIMARY_COLOR));
        editFormPanel.add(editEmailField);
        editEmailField.setEditable(false);
        editFormPanel.add(createLabel("CNIC:", formLabelFont, PRIMARY_COLOR));
        editFormPanel.add(editCnicField);
        editFormPanel.add(createLabel("Rank (Pilot):", formLabelFont, PRIMARY_COLOR));
        editFormPanel.add(editRankField);
        editFormPanel.add(createLabel("License (Pilot):", formLabelFont, PRIMARY_COLOR));
        editFormPanel.add(editLicenseField);

        editFormContainer.add(editFormPanel);
        panel.add(editFormContainer, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save Profile Changes");
        saveBtn.setBackground(new Color(34, 139, 34));
        saveBtn.setForeground(TEXT_COLOR);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(saveBtn, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> {
            currentEmployee = employeeManager.findEmployeeByEmail(searchField.getText());
            if (currentEmployee != null) {
                editNameField.setText(currentEmployee.getName());
                editEmailField.setText(currentEmployee.getEmail());
                editCnicField.setText(currentEmployee.getCNIC());
                editGenderBox.setSelectedItem(currentEmployee.getGender());
                editRoleBox.setSelectedItem(currentEmployee.getRole());

                if (currentEmployee.getRole().equals("Pilot")) {
                    Pilot p = (Pilot) currentEmployee;
                    editRankField.setText(p.getPilotRank());
                    editLicenseField.setText(p.getLicenseNumber());
                } else {
                    editRankField.setText("N/A");
                    editLicenseField.setText("N/A");
                }
                JOptionPane.showMessageDialog(frame, "Employee details loaded successfully.", "Found",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveBtn.addActionListener(e -> {
            if (currentEmployee == null) {
                JOptionPane.showMessageDialog(frame, "Please search and load an employee first.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentEmployee.setName(editNameField.getText());
            currentEmployee.setGender((String) editGenderBox.getSelectedItem());
            currentEmployee.setCNIC(editCnicField.getText());

            if (currentEmployee.getRole().equals("Pilot")) {
                Pilot p = (Pilot) currentEmployee;
                p.setPilotRank(editRankField.getText());
                p.setLicenseNumber(editLicenseField.getText());
            }

            employeeManager.saveAllEmployees();
            JOptionPane.showMessageDialog(frame, "Profile for " + currentEmployee.getEmail() + " saved successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    private JPanel createDeleteEmployeePanel() {
        JPanel panel = createStyledPanel("resources/images/pexels-anchoredcreativehub-1389783.jpg");
        panel.setLayout(new GridBagLayout());

        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        deletePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(178, 34, 34), 3),
                BorderFactory.createTitledBorder("Permanent Employee Deletion")));
        deletePanel.setBackground(new Color(255, 230, 230));

        JTextField emailToDeleteField = new JTextField(20);
        JButton deleteBtn = new JButton("Delete Employee Permanently");
        deleteBtn.setBackground(new Color(178, 34, 34));
        deleteBtn.setForeground(TEXT_COLOR);

        deletePanel.add(new JLabel("Employee Email to Delete:"));
        deletePanel.add(emailToDeleteField);
        deletePanel.add(deleteBtn);

        deleteBtn.addActionListener(e -> {
            String email = emailToDeleteField.getText();
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to DELETE " + email + "? This cannot be undone.",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (employeeManager.removeEmployee(email)) {
                    JOptionPane.showMessageDialog(frame, "Employee " + email + " deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    emailToDeleteField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: Employee not found.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(deletePanel);
        return panel;
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JPanel createStyledPanel(String imagePath) {
        if (imagePath != null) {
            return new ImagePanel(imagePath);
        } else {
            JPanel panel = new JPanel();
            panel.setBackground(new Color(245, 245, 255));
            return panel;
        }
    }
}