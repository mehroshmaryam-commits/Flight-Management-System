package airline.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import airline.crew.Pilot;
import airline.crew.CabinCrew;

public class EmployeeManager {
    private ArrayList<Admin> employees;
    private static final String PILOT_FILE = "resources/txt_file/pilot_credentials.txt";
    private static final String CREW_FILE = "resources/txt_file/crew_credentials.txt";
    private static EmployeeManager instance;

    private EmployeeManager() {
        employees = new ArrayList<>();
        loadAllEmployees();
    }

    public static EmployeeManager getInstance() {
        if (instance == null) {
            instance = new EmployeeManager();
        }
        return instance;
    }

    private void loadAllEmployees() {
        employees.clear();
        loadEmployeesFromFile(PILOT_FILE, "Pilot");
        loadEmployeesFromFile(CREW_FILE, "CabinCrew");
        System.out.println("Total Employees loaded: " + employees.size());
    }

    private void loadEmployeesFromFile(String fileName, String role) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split(",");

                if (role.equals("Pilot") && parts.length >= 9) {
                    Pilot p = new Pilot(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                            parts[6].trim(), parts[7].trim(), parts[8].trim());
                    employees.add(p);
                } else if (role.equals("CabinCrew") && parts.length >= 7) {
                    CabinCrew cc = new CabinCrew(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                            parts[6].trim());
                    employees.add(cc);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file " + fileName + ": " + e.getMessage());
        }
    }

    public void saveAllEmployees() {
        saveEmployeesForRole("Pilot");
        saveEmployeesForRole("CabinCrew");
    }

    private void saveEmployeesForRole(String role) {
        String fileName = role.equals("Pilot") ? PILOT_FILE : CREW_FILE;
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            for (Admin emp : employees) {
                if (emp.getRole().equalsIgnoreCase(role)) {
                    out.println(emp.toFileString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving employees to file: " + e.getMessage());
        }
    }

    public Admin findEmployeeByEmail(String email) {
        for (Admin emp : employees) {
            if (emp.getEmail().equalsIgnoreCase(email.trim())) {
                return emp;
            }
        }
        return null;
    }


    public Admin verifyEmployee(String role, String email, String password) {

            Admin emp = findEmployeeByEmail(email);

            if (emp == null)
                return null;

            if (!emp.getRole().equalsIgnoreCase(role))
                return null;

            if (!emp.getPassword().equals(password))
                return null;

            return emp;
        }

        public boolean addEmployee(Admin newEmployee) {
            if (findEmployeeByEmail(newEmployee.getEmail()) != null) {
                return false;
            }
            employees.add(newEmployee);
            saveAllEmployees();
            return true;
        }

        public boolean removeEmployee(String email) {
            Admin empToRemove = findEmployeeByEmail(email);
            if (empToRemove != null) {
                employees.remove(empToRemove);
                saveAllEmployees();
                return true;
            }
            return false;
        }

        public ArrayList<Admin> getAllEmployees() {
            return employees;
        }

        public List<Pilot> getPilots() {
            List<Pilot> list = new ArrayList<>();
            for (Admin a : employees) {
                if (a instanceof Pilot)
                    list.add((Pilot) a);
            }
            return list;
        }

        public List<CabinCrew> getCabinCrew() {
            List<CabinCrew> list = new ArrayList<>();
            for (Admin a : employees) {
                if (a instanceof CabinCrew)
                    list.add((CabinCrew) a);
            }
            return list;
        }
    }