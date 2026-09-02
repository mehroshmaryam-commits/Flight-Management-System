# ✈️ Airline Reservation & Management System

A desktop **airline management system** built in **Java (Swing)**, providing dedicated dashboards for administrators, HR managers, pilots, cabin crew, and customers. The system supports flight booking, employee management, crew flight assignments, and role-based authentication — all through a clean, modern GUI.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Project Structure](#project-structure)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

This project simulates a full airline operations platform, split into four main user roles:

| Role | Capabilities |
|------|---------------|
| **Admin / HR Manager** | Register, view, edit, and delete employee (pilot/crew) profiles |
| **Pilot** | View assigned flights and flight history |
| **Cabin Crew** | View assigned flights, team details, and aircraft information |
| **Customer** | Search flights, book tickets, manage profile, and view fare breakdowns |

Each role has its own login flow and dashboard, backed by a shared domain model for flights, bookings, employees, and payments.

---

## Features

- 🔐 **Role-based login** for Admin, Pilots, Cabin Crew, and Customers
- 👥 **Employee management** — add, edit, and remove pilots/crew with role-specific fields (rank, license number, etc.)
- 🛫 **Flight management** — supports both **direct** and **connected flights** (with layovers)
- 🪑 **Seat & aircraft management**
- 🧳 **Customer booking flow** with a detailed booking summary (passenger info, flight info, fare breakdown)
- 💳 **Payment handling**
- 📅 **Crew/pilot flight assignment** and history tracking
- 🖼️ Custom GUI panels with background imagery and airline-branded styling
- 📁 File-based persistence via a dedicated utility layer


## Project Structure

```
src/airline/
│
├── admin/
│   ├── Admin.java
│   └── EmployeeManager.java
│
├── app/
│   ├── HomePageGui.java
│   ├── Runner.java              # Application entry point
│   └── User.java
│
├── crew/
│   ├── Assignment.java
│   ├── CabinCrew.java
│   └── Pilot.java
│
├── customer/
│   ├── Booking.java
│   ├── Customer.java
│   ├── CustomerManager.java
│   ├── EditProfile.java
│   └── Payment.java
│
├── flight/
│   ├── Aircraft.java
│   ├── ConnectedFlight.java
│   ├── DirectFlight.java
│   ├── Flight.java
│   ├── FlightManager.java
│   ├── Layover.java
│   └── Seat.java
│
├── gui/
│   ├── admin/
│   │   ├── AdminLoginGui.java
│   │   └── HRManagerPanelGui.java
│   ├── crew/
│   │   ├── CabinCrewPanelGui.java
│   │   └── PilotPanelGui.java
│   └── customer/
│       ├── CustomerUI.java
│       ├── FlightGridViewer4.java
│       └── FlightSearchViewer.java
│
└── util/
    ├── FileHandler.java
    └── ImagePanel.java

resources/                        # Images, icons, and other static assets
.gitignore
```

---

## Tech Stack

- **Language:** Java
- **GUI Framework:** Java Swing
- **Architecture:** Layered — domain model (`admin`, `crew`, `customer`, `flight`) separated from presentation (`gui`) and utilities (`util`)
- **Persistence:** File-based storage via `FileHandler`

---

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- An IDE such as IntelliJ IDEA, Eclipse, or VS Code (optional but recommended)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/<repo-name>.git
   cd <repo-name>
   ```

2. Compile the project:
   ```bash
   javac -d bin $(find src -name "*.java")
   ```

3. Run the application:
   ```bash
   java -cp bin airline.app.Runner
   ```

> **Note:** Adjust the classpath and main class (`airline.app.Runner`) if your entry point differs.

---

## Usage

1. Launch the app — the **Home Page** lets you choose a login type (Admin, Pilot, Cabin Crew, or Customer).
2. **Admins/HR Managers** can add, view/edit, and delete employee profiles from the HR Administration Hub.
3. **Pilots and Cabin Crew** log in to view their assigned flights, team details, and flight history.
4. **Customers** can search for flights, view direct/connected routing options, and complete bookings — receiving a full fare breakdown before confirming.

---

## Roadmap

- [ ] Migrate file-based storage to a relational database (e.g., MySQL/SQLite)
- [ ] Add flight status tracking (delayed, boarding, departed)
- [ ] Email/SMS booking confirmations
- [ ] Unit tests for manager and booking logic
- [ ] Package as an executable `.jar`

---

## Contributing

Contributions are welcome! Please fork the repository, create a feature branch, and submit a pull request describing your changes.

---

## License

This project is available for personal and educational use. Add your preferred license (e.g., MIT) here.
