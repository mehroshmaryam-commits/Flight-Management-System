package airline.gui.admin;

import javax.swing.*;
import java.awt.*;
import airline.admin.EmployeeManager;
import airline.admin.Admin;
import airline.crew.Pilot;
import airline.gui.crew.PilotPanelGui;
import airline.gui.crew.CabinCrewPanelGui;

public class AdminLoginGui {
    private EmployeeManager employeeManager;
    private JFrame parentLoginFrame;
    private static final String HR_EMAIL = "hr@airline.com";
    private static final String HR_PASSWORD = "HRadminPass";

    public AdminLoginGui(EmployeeManager manager, JFrame parentFrame) {
        this.parentLoginFrame = parentFrame;
        this.employeeManager = EmployeeManager.getInstance();
    }

    public void showAdminVerificationDialog() {
        JFrame adminLoginFrame = new JFrame("Employee Verification");
        adminLoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminLoginFrame.setSize(400, 300);
        adminLoginFrame.setResizable(false);
        adminLoginFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        mainPanel.setBackground(new Color(240, 246, 255));

        JLabel titleLabel = new JLabel("Employee Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 51, 102));
        mainPanel.add(titleLabel);

        JPanel rolePanel = new JPanel(new BorderLayout(5, 5));
        JLabel roleLabel = new JLabel("Role:");
        String[] roles = { "Pilot", "CabinCrew", "HR Manager" };
        JComboBox<String> roleBox = new JComboBox<>(roles);
        rolePanel.add(roleLabel, BorderLayout.WEST);
        rolePanel.add(roleBox, BorderLayout.CENTER);
        mainPanel.add(rolePanel);

        JPanel emailPanel = new JPanel(new BorderLayout(5, 5));
        JLabel emailLabel = new JLabel("Email ID:");
        JTextField emailField = new JTextField();
        emailPanel.add(emailLabel, BorderLayout.WEST);
        emailPanel.add(emailField, BorderLayout.CENTER);
        mainPanel.add(emailPanel);

        JPanel passwordPanel = new JPanel(new BorderLayout(5, 5));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        mainPanel.add(passwordPanel);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(0, 102, 204));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setFocusPainted(false);
        mainPanel.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String role = (String) roleBox.getSelectedItem();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (role.equalsIgnoreCase("HRManager") || role.equalsIgnoreCase("HR Manager")) {
                if (email.equalsIgnoreCase(HR_EMAIL) && password.equals(HR_PASSWORD)) {
                    adminLoginFrame.dispose();
                    parentLoginFrame.setVisible(false);

                    HRManagerPanelGui hrPanel = new HRManagerPanelGui(
                            employeeManager,
                            parentLoginFrame,
                            HR_EMAIL);
                    hrPanel.showHRDashboard();
                    return;
                } else {
                    JOptionPane.showMessageDialog(adminLoginFrame,
                            "Verification Failed. Invalid HR Manager Credentials.",
                            "Verification Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Admin verifiedEmployee = employeeManager.verifyEmployee(role, email, password);

            if (verifiedEmployee != null) {
                JOptionPane.showMessageDialog(adminLoginFrame,
                        role + " Verified. Welcome!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                adminLoginFrame.dispose();
                parentLoginFrame.setVisible(false);

                if (role.equalsIgnoreCase("Pilot")) {
                    Pilot verifiedPilot = (Pilot) verifiedEmployee;
                    String pilotRank = verifiedPilot.getPilotRank();
                    String employeeEmail = verifiedEmployee.getEmail();
                    PilotPanelGui pilotPanel = new PilotPanelGui(employeeEmail, pilotRank, this.parentLoginFrame);
                    pilotPanel.showPilotDashboard();

                } else if (role.equalsIgnoreCase("CabinCrew")) {
                    String crewRole = verifiedEmployee.getRole();
                    String employeeEmail = verifiedEmployee.getEmail();
                    CabinCrewPanelGui crewPanel = new CabinCrewPanelGui(employeeEmail, crewRole, this.parentLoginFrame);
                    crewPanel.showCrewDashboard();
                }
            } else {
                JOptionPane.showMessageDialog(adminLoginFrame,
                        "Verification Failed. Credentials not found for " + role + ".",
                        "Verification Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JLabel newEmployeeLabel = new JLabel("New Employee? Contact HR for Credentials", SwingConstants.CENTER);
        newEmployeeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        newEmployeeLabel.setForeground(new Color(100, 100, 100));
        mainPanel.add(newEmployeeLabel);

        adminLoginFrame.add(mainPanel);
        adminLoginFrame.setVisible(true);
    }

}