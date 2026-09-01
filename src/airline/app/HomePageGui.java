package airline.app;

import javax.swing.*;
import java.awt.*;
import airline.customer.Customer;
import airline.customer.CustomerManager;
import airline.gui.customer.CustomerUI;
import airline.gui.customer.FlightGridViewer4;
import airline.gui.admin.AdminLoginGui;
import airline.admin.EmployeeManager;
import airline.customer.EditProfile;
import airline.util.ImagePanel;

public class HomePageGui extends JFrame {
    private JFrame f1;
    private JFrame loginFrame;
    private JFrame customerFrame;
    private CustomerManager customerManager;
    private Customer customerLoggedIn = null;
    private JButton homeBtn;
    private JButton customerPanelBtn;

    public HomePageGui() {
        customerManager = new CustomerManager();

        homeBtn = new JButton("Home");
        homeBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        homeBtn.setBackground(new Color(70, 130, 180));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setFocusPainted(false);
        homeBtn.addActionListener(e -> {
            if (loginFrame != null)
                loginFrame.dispose();
            if (customerFrame != null)
                customerFrame.dispose();
            f1.setVisible(true);
        });
        customerPanelBtn = new JButton("Customer Panel");
        customerPanelBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        customerPanelBtn.setBackground(new Color(0, 153, 76));
        customerPanelBtn.setForeground(Color.WHITE);
        customerPanelBtn.setFocusPainted(false);
        customerPanelBtn.setVisible(false); // default hidden
        customerPanelBtn.addActionListener(e -> {
            if (customerLoggedIn != null) {
                JPanel footerPanel = footer();
                CustomerUI customerUI = new CustomerUI(f1, customerLoggedIn, homeBtn, footerPanel, customerManager);
                customerUI.showCustomerPage();
            }
        });

        f1 = new JFrame("Airline Home Page");
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setSize(900, 600);
        showStartPage();
        f1.setVisible(true);
    }

    private void showStartPage() {
        JPanel backgroundPanel = new ImagePanel("resources/images/home.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        f1.add(backgroundPanel, BorderLayout.CENTER);

        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setBackground(new Color(230, 230, 250));
        JLabel welcomeLabel = new JLabel("SkyBlue");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 34));
        welcomeLabel.setForeground(new Color(0, 102, 204));

        JLabel sloganLabel = new JLabel("your journey, our commitment");
        sloganLabel.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 22));
        sloganLabel.setForeground(new Color(0, 51, 102));

        JLabel loginLabel = new JLabel("login");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        loginLabel.setForeground(new Color(70, 130, 180));
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 


        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f1.setVisible(false);
                showLoginPage();
            }
        });


        JLabel scheduleLabel = new JLabel("schedule");
        scheduleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        scheduleLabel.setForeground(new Color(70, 130, 180));
        scheduleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        scheduleLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                new FlightGridViewer4(false);
            }
        });

        JLabel codesLabel = new JLabel("promo codes");
        codesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        codesLabel.setForeground(new Color(70, 130, 180));
        codesLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click
        codesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPromoDeals();

            }
        });

        JButton customerPanelBtn = new JButton("Customer Panel");
        customerPanelBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        customerPanelBtn.setBackground(new Color(70, 130, 180));
        customerPanelBtn.setForeground(Color.WHITE);
        customerPanelBtn.setFocusPainted(false);
        customerPanelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener
        customerPanelBtn.addActionListener(e -> {
            if (customerLoggedIn != null) {
                f1.setVisible(false);
                JPanel footerPanel = footer();
                CustomerUI customerUI = new CustomerUI(f1, customerLoggedIn, homeBtn, footerPanel, customerManager);
                customerUI.showCustomerPage();
            } else {
                JOptionPane.showMessageDialog(f1,
                        "You are not logged in yet!\nPlease login first.",
                        "Login Required",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        ImageIcon original = new ImageIcon("resources/images/user.png");
        Image scaled = original.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon profileIcon = new ImageIcon(scaled);

        JLabel profileLabel = new JLabel(profileIcon);
        profileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileLabel.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showProfileDropdown(profileLabel);
            }
        });

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createHorizontalStrut(100));
        welcomePanel.add(sloganLabel);
        welcomePanel.add(Box.createHorizontalStrut(240)); 
        welcomePanel.add(loginLabel);
        welcomePanel.add(Box.createHorizontalStrut(30));
        welcomePanel.add(codesLabel);
        welcomePanel.add(Box.createHorizontalStrut(30));
        welcomePanel.add(scheduleLabel);
        welcomePanel.add(Box.createHorizontalStrut(50)); 
        welcomePanel.add(customerPanelBtn); 
        welcomePanel.add(Box.createHorizontalStrut(80)); 
        welcomePanel.add(profileLabel);

        f1.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f1.add(welcomePanel, BorderLayout.NORTH);
        f1.add(footer(), BorderLayout.SOUTH);

    }

    private JPanel footer() {
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.setBackground(new Color(100, 149, 237));

        JLabel information = new JLabel("© SkyBlue | Dastagir Travel & Tour | All Rights Reserved");
        information.setForeground(new Color(0, 0, 128));
        information.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel aboutUs = new JLabel("About Us");
        aboutUs.setFont(new Font("Arial", Font.PLAIN, 18));
        aboutUs.setForeground(new Color(0, 0, 128));
        aboutUs.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aboutUs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    // Read text content
                    String content = new String(java.nio.file.Files.readAllBytes(
                            java.nio.file.Paths
                                    .get("resources/txt_file/aboutus.txt")));

                    f1.setVisible(false); // hide main frame

                    JFrame aboutFrame = new JFrame("About Us");
                    aboutFrame.setSize(500, 400);
                    aboutFrame.setLocationRelativeTo(null);

                    // Main panel with light blue background
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.setBackground(new Color(173, 216, 230));

                    // Text area for content
                    JTextArea textArea = new JTextArea(content);
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setFont(new Font("Arial", Font.PLAIN, 19));
                    textArea.setForeground(new Color(0, 0, 0));
                    textArea.setBackground(new Color(173, 216, 230));

                    panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

                    // Back button at bottom-right
                    JButton backBtn = new JButton("Back");
                    backBtn.setBackground(new Color(0, 51, 102));
                    backBtn.setForeground(Color.WHITE);
                    backBtn.setFont(new Font("Arial", Font.BOLD, 16));
                    backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    backBtn.addActionListener(ev -> {
                        aboutFrame.dispose();
                        f1.setVisible(true);
                    });

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.setBackground(new Color(173, 216, 230));
                    buttonPanel.add(backBtn);

                    panel.add(buttonPanel, BorderLayout.SOUTH);

                    aboutFrame.add(panel);
                    aboutFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    aboutFrame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JLabel passengerRights = new JLabel("Passenger Rights");
        passengerRights.setFont(new Font("Arial", Font.PLAIN, 18));
        passengerRights.setForeground(new Color(0, 0, 128));
        passengerRights.setCursor(new Cursor(Cursor.HAND_CURSOR));
        passengerRights.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    // Read text content
                    String content = new String(java.nio.file.Files.readAllBytes(
                            java.nio.file.Paths
                                    .get("resources/txt_file/passengers.txt")));

                    f1.setVisible(false); // hide main frame

                    JFrame aboutFrame = new JFrame("Passenger Rights");
                    aboutFrame.setSize(500, 400);
                    aboutFrame.setLocationRelativeTo(null);

                    // Main panel with light blue background
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.setBackground(new Color(173, 216, 230));

                    // Text area for content
                    JTextArea textArea = new JTextArea(content);
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setFont(new Font("Arial", Font.PLAIN, 19));
                    textArea.setForeground(new Color(0, 0, 0));
                    textArea.setBackground(new Color(173, 216, 230));

                    panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

                    // Back button at bottom-right
                    JButton backBtn = new JButton("Back");
                    backBtn.setBackground(new Color(0, 51, 102));
                    backBtn.setForeground(Color.WHITE);
                    backBtn.setFont(new Font("Arial", Font.BOLD, 16));
                    backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    backBtn.addActionListener(ev -> {
                        aboutFrame.dispose();
                        f1.setVisible(true);
                    });

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.setBackground(new Color(173, 216, 230));
                    buttonPanel.add(backBtn);

                    panel.add(buttonPanel, BorderLayout.SOUTH);

                    aboutFrame.add(panel);
                    aboutFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    aboutFrame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JLabel contact = new JLabel("Contact Us");
        contact.setForeground(new Color(0, 0, 128));
        contact.setFont(new Font("Arial", Font.PLAIN, 18));
        contact.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    // Read text content
                    String content = new String(java.nio.file.Files.readAllBytes(
                            java.nio.file.Paths
                                    .get("resources/txt_file/contactus.txt")));

                    f1.setVisible(false); // hide main frame

                    JFrame aboutFrame = new JFrame("Contact Us");
                    aboutFrame.setSize(500, 400);
                    aboutFrame.setLocationRelativeTo(null);

                    // Main panel with light blue background
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.setBackground(new Color(173, 216, 230));

                    // Text area for content
                    JTextArea textArea = new JTextArea(content);
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setFont(new Font("Arial", Font.PLAIN, 19));
                    textArea.setForeground(new Color(0, 0, 0));
                    textArea.setBackground(new Color(173, 216, 230));

                    panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

                    // Back button at bottom-right
                    JButton backBtn = new JButton("Back");
                    backBtn.setBackground(new Color(0, 51, 102));
                    backBtn.setForeground(Color.WHITE);
                    backBtn.setFont(new Font("Arial", Font.BOLD, 16));
                    backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    backBtn.addActionListener(ev -> {
                        aboutFrame.dispose();
                        f1.setVisible(true);
                    });

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.setBackground(new Color(173, 216, 230));
                    buttonPanel.add(backBtn);

                    panel.add(buttonPanel, BorderLayout.SOUTH);

                    aboutFrame.add(panel);
                    aboutFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    aboutFrame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JLabel terms = new JLabel("Legal Terms & Conditions");
        terms.setForeground(new Color(0, 0, 128));
        terms.setFont(new Font("Arial", Font.PLAIN, 18));
        terms.setCursor(new Cursor(Cursor.HAND_CURSOR));
        terms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    // Read text content
                    String content = new String(java.nio.file.Files.readAllBytes(
                            java.nio.file.Paths
                                    .get("resources/txt_file/terms.txt")));

                    f1.setVisible(false); // hide main frame

                    JFrame aboutFrame = new JFrame("Terms & Conditions");
                    aboutFrame.setSize(500, 400);
                    aboutFrame.setLocationRelativeTo(null);

                    // Main panel with light blue background
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.setBackground(new Color(173, 216, 230));

                    // Text area for content
                    JTextArea textArea = new JTextArea(content);
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setFont(new Font("Arial", Font.PLAIN, 19));
                    textArea.setForeground(new Color(0, 0, 0));
                    textArea.setBackground(new Color(173, 216, 230));

                    panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

                    // Back button at bottom-right
                    JButton backBtn = new JButton("Back");
                    backBtn.setBackground(new Color(0, 51, 102));
                    backBtn.setForeground(Color.WHITE);
                    backBtn.setFont(new Font("Arial", Font.BOLD, 16));
                    backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    backBtn.addActionListener(ev -> {
                        aboutFrame.dispose();
                        f1.setVisible(true);
                    });

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.setBackground(new Color(173, 216, 230));
                    buttonPanel.add(backBtn);

                    panel.add(buttonPanel, BorderLayout.SOUTH);

                    aboutFrame.add(panel);
                    aboutFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    aboutFrame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        southPanel.add(information);
        southPanel.add(Box.createHorizontalStrut(350));
        southPanel.add(aboutUs);
        southPanel.add(Box.createHorizontalStrut(50));
        southPanel.add(passengerRights);
        southPanel.add(Box.createHorizontalStrut(50));
        southPanel.add(contact);
        southPanel.add(Box.createHorizontalStrut(50));
        southPanel.add(terms);

        return southPanel;
    }

    private void showLoginPage() {
        loginFrame = new JFrame("Login");
        loginFrame.setLayout(new BorderLayout());
        JPanel backgroundPanel = new ImagePanel("resources/images/login.png");
        backgroundPanel.setLayout(new BorderLayout());

        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setBackground(new Color(230, 230, 250));
        JLabel welcomeLabel = new JLabel("SkyBlue");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 34));
        welcomeLabel.setForeground(new Color(0, 102, 204));

        JLabel sloganLabel = new JLabel("your journey, our commitment");
        sloganLabel.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 22));
        sloganLabel.setForeground(new Color(0, 51, 102));
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createHorizontalStrut(90));
        welcomePanel.add(sloganLabel);

        // Login Panel
        JPanel loginChoicePanel = new JPanel();
        loginChoicePanel.setOpaque(false);
        loginChoicePanel.setBackground(new Color(230, 230, 250));

        JLabel title = new JLabel("login As");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(new Color(0, 0, 128));

        JButton userBtn = new JButton("User");
        JButton adminBtn = new JButton("Admin");

        // Smooth colors
        Color buttonBg = new Color(70, 130, 180); // smooth blue
        Color buttonFg = Color.WHITE;

        userBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        adminBtn.setFont(new Font("Arial", Font.PLAIN, 20));

        userBtn.setFocusPainted(false);
        adminBtn.setFocusPainted(false);

        userBtn.setBackground(buttonBg);
        userBtn.setForeground(buttonFg);
        adminBtn.setBackground(buttonBg);
        adminBtn.setForeground(buttonFg);
        homeBtn.setBackground(buttonBg);
        homeBtn.setForeground(buttonFg);
        homeBtn.setFont(new Font("Arial", Font.PLAIN, 20));

        loginChoicePanel.add(Box.createHorizontalStrut(210));
        loginChoicePanel.add(title);
        loginChoicePanel.add(Box.createHorizontalStrut(90));
        loginChoicePanel.add(userBtn);
        loginChoicePanel.add(adminBtn);
        loginChoicePanel.add(Box.createHorizontalStrut(800));
        loginChoicePanel.add(homeBtn);

        loginFrame.add(welcomePanel, BorderLayout.NORTH);
        loginFrame.add(backgroundPanel, BorderLayout.CENTER);
        loginFrame.add(loginChoicePanel, BorderLayout.SOUTH);
        loginFrame.setVisible(true);
        loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        userBtn.addActionListener(e -> {
            logging();
        });

        adminBtn.addActionListener(e -> {
            new AdminLoginGui(EmployeeManager.getInstance(), f1).showAdminVerificationDialog();
        });

    }

    private void showProfileDropdown(JLabel profileLabel) {
        JPopupMenu dropdown = new JPopupMenu();
        dropdown.setBackground(Color.WHITE);
        dropdown.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        if (customerLoggedIn == null) {
            showNotLoggedInBox(dropdown);
        } else {
            showLoggedInBox(dropdown);
        }

        dropdown.show(profileLabel, -180, profileLabel.getHeight() + 5);
    }

    private void showNotLoggedInBox(JPopupMenu dropdown) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(230, 50));

        JLabel loginLabel = new JLabel("Login to your account");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setOpaque(true);
        loginLabel.setBackground(Color.WHITE);
        loginLabel.setHorizontalAlignment(SwingConstants.LEFT);
        loginLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dropdown.setVisible(false);
                // parentFrame.setVisible(false);
                showLoginPage();
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginLabel.setBackground(new Color(245, 245, 245));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginLabel.setBackground(Color.WHITE);
            }
        });

        panel.add(loginLabel, BorderLayout.CENTER);
        dropdown.add(panel);
    }

    private void showLoggedInBox(JPopupMenu dropdown) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(new Color(244, 244, 244));
        infoPanel.setPreferredSize(new Dimension(230, 60));
        infoPanel.setMaximumSize(new Dimension(230, 60));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(244, 244, 244));
        textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Customer currentCustomer = customerManager.getCustomerByEmail(customerLoggedIn.getEmail());
        if (currentCustomer != null) {
            customerLoggedIn = currentCustomer;
        }

        JLabel nameLabel = new JLabel(customerLoggedIn.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(new Color(33, 33, 33));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel emailLabel = new JLabel(customerLoggedIn.getEmail());
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        emailLabel.setForeground(new Color(100, 100, 100));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setHorizontalAlignment(SwingConstants.LEFT);

        textPanel.add(nameLabel);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(emailLabel);

        infoPanel.add(textPanel, BorderLayout.WEST);

        // My Profile Section
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setPreferredSize(new Dimension(230, 40));
        profilePanel.setMaximumSize(new Dimension(230, 40));

        JLabel profileLabelText = new JLabel("  My Profile");
        profileLabelText.setFont(new Font("Arial", Font.PLAIN, 14));
        profileLabelText.setForeground(Color.BLUE);
        profileLabelText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        profileLabelText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dropdown.setVisible(false);
                EditProfile profilePage = new EditProfile(customerLoggedIn, customerManager, HomePageGui.this);
                profilePage.showProfile();
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profilePanel.setBackground(new Color(245, 245, 245));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                profilePanel.setBackground(Color.WHITE);
            }
        });

        profilePanel.add(profileLabelText, BorderLayout.CENTER);

        // Separators
        JSeparator separator1 = new JSeparator();
        separator1.setForeground(new Color(45, 45, 45));
        separator1.setMaximumSize(new Dimension(230, 1));

        JSeparator separator2 = new JSeparator();
        separator2.setForeground(new Color(45, 45, 45));
        separator2.setMaximumSize(new Dimension(230, 1));

        // Sign Out
        JPanel signoutPanel = new JPanel(new BorderLayout());
        signoutPanel.setBackground(Color.WHITE);
        signoutPanel.setPreferredSize(new Dimension(230, 40));
        signoutPanel.setMaximumSize(new Dimension(230, 40));

        JLabel signoutLabel = new JLabel("  Sign out");
        signoutLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        signoutLabel.setForeground(Color.RED);
        signoutLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        signoutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dropdown.setVisible(false);
                customerLoggedIn = null;
                customerPanelBtn.setVisible(false);
                customerManager.clearLoginSession();

                if (customerFrame != null)
                    customerFrame.dispose();

                JOptionPane.showMessageDialog(f1,
                        "You have been logged out successfully!",
                        "Logged Out",
                        JOptionPane.INFORMATION_MESSAGE);

                f1.getContentPane().removeAll();
                showStartPage();
                f1.revalidate();
                f1.repaint();
                f1.setVisible(true);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signoutPanel.setBackground(new Color(245, 245, 245));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                signoutPanel.setBackground(Color.WHITE);
            }
        });

        signoutPanel.add(signoutLabel, BorderLayout.CENTER);

        // Add components
        panel.add(infoPanel);
        panel.add(separator1);
        panel.add(profilePanel);
        panel.add(separator2);
        panel.add(signoutPanel);

        dropdown.add(panel);
    }

    // DropDwonIcon Ended

    private void logging() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setSize(400, 250); // small frame
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null); // center on screen

        // Main panel with GridLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // padding

        // Title
        JLabel titleLabel = new JLabel("Verification", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        mainPanel.add(titleLabel);

        // Email panel
        JPanel emailPanel = new JPanel(new BorderLayout(5, 5));
        JLabel emailLabel = new JLabel("Email ID:");
        JTextField emailField = new JTextField();
        emailPanel.add(emailLabel, BorderLayout.WEST);
        emailPanel.add(emailField, BorderLayout.CENTER);
        mainPanel.add(emailPanel);

        // Password panel
        JPanel passwordPanel = new JPanel(new BorderLayout(5, 5));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        mainPanel.add(passwordPanel);

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        mainPanel.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame,
                        "Please fill in all required fields.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (customerManager.customerPresent(email, password) != null) {
                customerLoggedIn = customerManager.customerPresent(email, password);
                customerPanelBtn.setVisible(true);
                loginFrame.dispose();
                JPanel footerPanel = footer();
                CustomerUI customerUI = new CustomerUI(f1, customerLoggedIn, homeBtn, footerPanel, customerManager);
                customerUI.showCustomerPage();
            } else {
                JOptionPane.showMessageDialog(loginFrame,
                        "No user exits!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        });

        // Sign up label
        JLabel signupLabel = new JLabel("Don't have an account? Sign up", SwingConstants.CENTER);
        signupLabel.setForeground(new Color(0, 102, 204));
        signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // action listener likhna hy
        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f1.setVisible(false);
                loginFrame.setVisible(false);
                showSignUpForm();
            }
        });

        mainPanel.add(signupLabel);
        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);
    }

    private void showSignUpForm() {
        JFrame frame = new JFrame("Customer Registration");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null); // center frame
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().setBackground(new Color(240, 246, 255));

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(70, 130, 180)); // steel blue
        JLabel titleLabel = new JLabel("Customer Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Name panel
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(new Color(240, 246, 255));
        JLabel nameLabel = new JLabel("Full Name: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField nameField = new JTextField(20);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // Gender panel
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(new Color(240, 246, 255));
        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        String[] genders = { "Male", "Female", "Other" };
        JComboBox<String> genderBox = new JComboBox<>(genders);
        genderBox.setFont(new Font("Arial", Font.PLAIN, 16));
        genderPanel.add(genderLabel);
        genderPanel.add(genderBox);

        // dob
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dobPanel.setBackground(new Color(240, 246, 255));
        JLabel dobLabel = new JLabel("Date of Birth: ");
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField dobField = new JTextField(20);
        dobField.setToolTipText("Format: DD-MM-YYYY (e.g., 05-09-2002)");
        dobPanel.add(dobLabel);
        dobPanel.add(dobField);

        // Email panel
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.setBackground(new Color(240, 246, 255));
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField emailField = new JTextField(15); // for user part (before @)
        String[] domains = { "@gmail.com", "@yahoo.com", "@outlook.com", "@hotmail.com" };
        JComboBox<String> domainBox = new JComboBox<>(domains);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        emailPanel.add(domainBox);

        // Password panel
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(new Color(240, 246, 255));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JPasswordField passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Phone panel
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        phonePanel.setBackground(new Color(240, 246, 255));
        JLabel phoneLabel = new JLabel("Phone: ");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        String[] codes = { "+1", "+92", "+44", "+61", "+91" };
        JComboBox<String> codeBox = new JComboBox<>(codes);
        JTextField phoneField = new JTextField(15);
        phonePanel.add(phoneLabel);
        phonePanel.add(codeBox);
        phonePanel.add(phoneField);

        // Postal address panel
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addressPanel.setBackground(new Color(240, 246, 255));
        JLabel addressLabel = new JLabel("Postal Address: ");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField addressField = new JTextField(20);
        addressPanel.add(addressLabel);
        addressPanel.add(addressField);

        // CNIC panel
        JPanel cnicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cnicPanel.setBackground(new Color(240, 246, 255));
        JLabel cnicLabel = new JLabel("CNIC: ");
        cnicLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField cnicField = new JTextField(20);
        cnicPanel.add(cnicLabel);
        cnicPanel.add(cnicField);

        // emergency Contact
        JPanel emergencyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emergencyPanel.setBackground(new Color(240, 246, 255));
        JLabel emergencyLabel = new JLabel("Emergency Contact: ");
        emergencyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<String> emergencyCodeBox = new JComboBox<>(codes); // Use same codes as phone
        JTextField emergencyField = new JTextField(15);
        emergencyPanel.add(emergencyLabel);
        emergencyPanel.add(emergencyCodeBox);
        emergencyPanel.add(emergencyField);

        // Submit panel
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setBackground(new Color(240, 246, 255));
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBackground(new Color(70, 130, 180));
        submitPanel.add(submitBtn);

        // Add all panels to frame
        frame.add(titlePanel);
        frame.add(namePanel);
        frame.add(genderPanel);
        frame.add(dobPanel);
        frame.add(emailPanel);
        frame.add(passwordPanel);
        frame.add(phonePanel);
        frame.add(addressPanel);
        frame.add(cnicPanel);
        frame.add(emergencyPanel);
        frame.add(submitPanel);

        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        homePanel.setBackground(new Color(240, 246, 255));
        homePanel.add(homeBtn);
        frame.add(homePanel);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        // Submit action using lambda
        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String gender = (String) genderBox.getSelectedItem();
            String emailUser = emailField.getText().trim();
            String domain = (String) domainBox.getSelectedItem();
            String email = emailUser + domain;
            String password = new String(passwordField.getPassword()).trim();
            String phone = phoneField.getText().trim();
            String code = (String) codeBox.getSelectedItem();
            String fullPhone = code + phone;
            String address = addressField.getText().trim();
            String cnic = cnicField.getText().trim();
            String dob = dobField.getText().trim();
            String emergencyPhone = emergencyField.getText().trim();
            String emergencyCode = (String) emergencyCodeBox.getSelectedItem();
            String fullEmergency = emergencyCode + "-" + emergencyPhone;

            // Validation using JOptionPane
            if (name.isEmpty() || emailUser.isEmpty() || password.isEmpty() ||
                    phone.isEmpty() || address.isEmpty() || cnic.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please fill in all required fields.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(frame,
                        "Phone number must be 10 digits.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!cnic.matches("\\d{13}")) {
                JOptionPane.showMessageDialog(frame,
                        "CNIC must be 13 digits.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (gender == null || gender.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please select your gender.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dob.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Date of birth cannot be empty!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isDateOfBirthValid(dob)) {
                JOptionPane.showMessageDialog(frame,
                        "Invalid Date Format!\n\nFormat: DD-MM-YYYY\nExample: 15-03-1995",
                        "Invalid Date", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (emergencyPhone.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Emergency contact cannot be empty!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!emergencyPhone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(frame,
                        "Emergency contact must be 10 digits.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (customerManager.customerPresent(
                    new Customer(name, gender, fullPhone, address, email, password, cnic, dob, fullEmergency))) {
                JOptionPane.showMessageDialog(frame,
                        "User already exits",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If valid, create customer using CustomerManager
            customerLoggedIn = new Customer(name, gender, fullPhone, address, email, password, cnic, dob,
                    fullEmergency);
            customerManager.addCustomer(customerLoggedIn);
            JOptionPane.showMessageDialog(frame,
                    "Customer registered successfully!\nWelcome, " + name,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            JPanel footerPanel = footer();
            CustomerUI customerUI = new CustomerUI(f1, customerLoggedIn, homeBtn, footerPanel, customerManager);
            customerUI.showCustomerPage();
        });
    }

    private boolean isDateOfBirthValid(String dob) {
        if (dob == null || dob.isEmpty())
            return false;
        return dob.matches("(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}");
    }

    private void showPromoDeals() {
        JDialog promoDialog = new JDialog(f1, "Flight Deals & Offers", true);
        promoDialog.setSize(650, 600);
        promoDialog.setLocationRelativeTo(f1);
        promoDialog.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        try {

            java.nio.file.Path path = java.nio.file.Paths.get(
                    "resources/txt_file/promodeals.txt");
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);

            for (String line : lines) {
                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    String leftText = parts[0].trim();
                    String title = parts[1].trim();
                    String description = parts[2].trim();
                    String promoCode = parts[3].trim();

                    mainPanel.add(createDealCard(leftText, title, description, promoCode));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(f1,
                    "Error reading promo deals file!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        promoDialog.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        promoDialog.setVisible(true);
    }

    private JPanel createDealCard(String leftText, String title, String description, String promoCode) {
        JPanel card = new JPanel(new BorderLayout());
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

        // Discount Box
        card.add(createLeftPanel(leftText), BorderLayout.WEST);

        // Title & Description Box
        card.add(createCenterPanel(title, description), BorderLayout.CENTER);

        // Button Box
        card.add(createRightPanel(promoCode), BorderLayout.EAST);

        return card;
    }

    private JPanel createLeftPanel(String text) {
        JPanel left = new JPanel(new GridBagLayout());
        left.setPreferredSize(new Dimension(120, 130));
        left.setBackground(new Color(255, 240, 220));
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        left.add(label);
        return left;
    }

    private JPanel createCenterPanel(String title, String description) {

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        center.add(Box.createVerticalStrut(10));
        center.add(titleLabel);
        center.add(Box.createVerticalStrut(5));
        center.add(descriptionLabel);

        return center;
    }

    private JPanel createRightPanel(String promoCode) {
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(Color.WHITE);

        JLabel dealLabel = new JLabel("Get the Deal");
        dealLabel.setFont(new Font("Arial", Font.BOLD, 12));
        dealLabel.setForeground(new Color(100, 100, 100));
        dealLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn = new JButton(promoCode);
        btn.setBackground(new Color(0, 120, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> handlePromoCode(promoCode));

        right.add(Box.createVerticalGlue());
        right.add(dealLabel);
        right.add(Box.createVerticalStrut(5));
        right.add(btn);
        right.add(Box.createVerticalGlue());

        return right;
    }

    private void handlePromoCode(String promoCode) {

        if (customerLoggedIn == null) {

            JOptionPane.showMessageDialog(f1,
                    "Login first to avail promo code!",
                    "Login Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String currentPromo = customerLoggedIn.getPromoCode();

        if (currentPromo != null && !currentPromo.isEmpty()) {

            JOptionPane.showMessageDialog(f1,
                    "You already have a promo code! ",
                    "Promo Code",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        customerLoggedIn.setPromoCode(promoCode);
        customerManager.updateCustomerPromoCode(customerLoggedIn.getEmail(), promoCode);

        JOptionPane.showMessageDialog(f1,
                "Promo code " + promoCode + " applied successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new HomePageGui();
    }
}