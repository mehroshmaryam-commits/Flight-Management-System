package airline.customer;

import java.io.*;
import java.util.ArrayList;

public class CustomerManager {
    private ArrayList<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
        loadCustomers();
        processMissedCheckIns();

    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomer();
    }

    private void saveCustomer() {
        File file = new File("resources/bin_file/customers.dat");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(customers);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCustomers() {
        File file = new File("resources/bin_file/customers.dat");
        if (!file.exists())
            return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.customers = (ArrayList<Customer>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean customerPresent(Customer customer) {
        for (Customer c : customers) {
            if (c.getCNIC().equals(customer.getCNIC()))
                return true;
        }
        return false;
    }

    public Customer customerPresent(String email, String password) {
        for (Customer c : customers) {
            if (c.getEmail().equals(email) && c.getPassword().equals(password)) {
                c.reloadBookings();
                return c;
            }
        }
        return null;
    }

    public Customer searchByCNIC(String CNIC) {
        for (Customer c : customers) {
            if (c.getCNIC().equals(CNIC)) {
                c.reloadBookings();
                return c;
            }
        }
        return null;
    }

    public void updateCustomerPromoCode(String email, String promoCode) {
        for (Customer c : customers) {
            if (c.getEmail().equals(email)) {
                c.setPromoCode(promoCode);
                saveCustomer();
                break;
            }
        }
    }

    public Customer getCustomerByEmail(String email) {
        for (Customer c : customers) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    public void saveLoginSession(Customer customer) {
        File file = new File(
                "resources/bin_file/login_session.dat");
        try {
            // Ensure directory exists
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(customer);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving login session: " + e.getMessage());
        }
    }

    public Customer loadLoginSession() {
        File file = new File(
                "resources/bin_file/login_session.dat");
        if (!file.exists())
            return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Customer sessionCustomer = (Customer) ois.readObject();
            // Critical: Return the object from our live list, not the stale one from the
            // file
            // This ensures that if we edit the profile, the session object in memory is the
            // same reference
            return getCustomerByEmail(sessionCustomer.getEmail());
        } catch (Exception e) {
            System.out.println("Error loading login session: " + e.getMessage());
            return null;
        }
    }

    public void clearLoginSession() {
        File file = new File("resources/bin_file/login_session.dat");
        if (file.exists()) {
            file.delete(); // Delete the session file
        }
    }

    private void processMissedCheckIns() {
        boolean globalChangesMade = false;

        for (Customer customer : this.customers) {
            boolean customerBookingsChanged = false;
            customer.reloadBookings();
            if (customer.getBookings() == null)
                continue;
            for (Booking booking : customer.getBookings()) {
                if (booking.getStatus().equals("Confirmed")) {
                    if (booking.isCheckInDeadlinePassed()) {
                        if (!booking.isCheckedIn()) {
                            booking.setStatus("Failed");
                            System.out.println("Booking ID " + booking.getBookingId() + " for customer " +
                                    customer.getCNIC() + " changed to 'Failed'.");
                            customerBookingsChanged = true;
                            globalChangesMade = true;
                        }
                    }
                }
            }

            if (customerBookingsChanged)
                customer.saveBooking();
        }
        if (globalChangesMade) {
            saveCustomer();
        }
    }

    public void updateCustomerProfile(String oldCNIC, String name, String gender,
            String phone, String address, String email,
            String password, String newCNIC, String dob, String emergency) {
        Customer customer = searchByCNIC(oldCNIC);
        if (customer != null) {
            // Update all fields in the existing object
            customer.setName(name);
            customer.setGender(gender);
            customer.setPhoneNumber(phone);
            customer.setPostalAddress(address);
            customer.setEmail(email);
            if (password != null && !password.isEmpty()) {
                customer.setPassword(password);
            }
            customer.setCNIC(newCNIC);
            customer.setDateOfBirth(dob);
            customer.setEmergencyContact(emergency);

            saveCustomer(); // Save to file
        }
    }
}

// search customer ka code krna hy
