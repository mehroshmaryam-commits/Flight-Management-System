package airline.app;

import java.util.*;
import airline.flight.*;
import airline.util.FileHandler;
import airline.admin.EmployeeManager;

public class Runner {
        public static void main(String[] args) {

                // ===== CREATE 15 AIRCRAFTS =====
                Aircraft[] aircrafts = new Aircraft[15];

                aircrafts[0] = new Aircraft("Boeing 737", 180, "SkyBlue Airways");
                aircrafts[1] = new Aircraft("Airbus A320", 150, "SkyBlue Airways");
                aircrafts[2] = new Aircraft("Boeing 777", 300, "SkyBlue Airways");
                aircrafts[3] = new Aircraft("Airbus A350", 280, "SkyBlue Airways");
                aircrafts[4] = new Aircraft("Boeing 787", 250, "SkyBlue Airways");
                aircrafts[5] = new Aircraft("Airbus A330", 220, "SkyBlue Airways");
                aircrafts[6] = new Aircraft("Boeing 747", 400, "SkyBlue Airways");
                aircrafts[7] = new Aircraft("Airbus A380", 500, "SkyBlue Airways");
                aircrafts[8] = new Aircraft("Boeing 737 MAX", 190, "SkyBlue Airways");
                aircrafts[9] = new Aircraft("Airbus A321", 200, "SkyBlue Airways");
                aircrafts[10] = new Aircraft("Boeing 767", 240, "SkyBlue Airways");
                aircrafts[11] = new Aircraft("Airbus A340", 270, "SkyBlue Airways");
                aircrafts[12] = new Aircraft("Boeing 757", 200, "SkyBlue Airways");
                aircrafts[13] = new Aircraft("Airbus A319", 140, "SkyBlue Airways");
                aircrafts[14] = new Aircraft("Boeing 737-800", 175, "SkyBlue Airways");

                // Add all aircrafts to FlightManager
                for (Aircraft a : aircrafts) {
                        FlightManager.INSTANCE.addAircraft(a);
                }

                // ===== CREATE 30 FLIGHTS =====
                Calendar cal = Calendar.getInstance();

                // Flight 1 - Direct (Karachi to Islamabad)
                cal.set(2025, Calendar.DECEMBER, 15, 8, 0);
                Date dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 15, 10, 30);
                Date arrv1 = cal.getTime();
                DirectFlight df1 = new DirectFlight("SB101", "Karachi", "Islamabad", dept1, arrv1, 12000, 180, true);
                df1.setAircraft(aircrafts[0]);
                df1.setGateNumber("A1");
                FlightManager.INSTANCE.addFlight(df1);

                // Flight 2 - Direct (Lahore to Karachi)
                cal.set(2025, Calendar.DECEMBER, 16, 9, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 16, 11, 0);
                arrv1 = cal.getTime();
                DirectFlight df2 = new DirectFlight("SB102", "Lahore", "Karachi", dept1, arrv1, 11000, 150, true);
                df2.setAircraft(aircrafts[1]);
                df2.setGateNumber("A2");
                FlightManager.INSTANCE.addFlight(df2);

                // Flight 3 - Direct (Islamabad to Dubai)
                cal.set(2025, Calendar.DECEMBER, 17, 14, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 17, 18, 0);
                arrv1 = cal.getTime();
                DirectFlight df3 = new DirectFlight("SB103", "Islamabad", "Dubai", dept1, arrv1, 35000, 300, false);
                df3.setAircraft(aircrafts[2]);
                df3.setGateNumber("B1");
                FlightManager.INSTANCE.addFlight(df3);

                // Flight 4 - Direct (Karachi to Dubai)
                cal.set(2025, Calendar.DECEMBER, 18, 10, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 18, 14, 30);
                arrv1 = cal.getTime();
                DirectFlight df4 = new DirectFlight("SB104", "Karachi", "Dubai", dept1, arrv1, 38000, 280, false);
                df4.setAircraft(aircrafts[3]);
                df4.setGateNumber("B2");
                FlightManager.INSTANCE.addFlight(df4);

                // Flight 5 - Direct (Lahore to Dubai)
                cal.set(2025, Calendar.DECEMBER, 19, 11, 30);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 19, 16, 0);
                arrv1 = cal.getTime();
                DirectFlight df5 = new DirectFlight("SB105", "Lahore", "Dubai", dept1, arrv1, 36000, 250, false);
                df5.setAircraft(aircrafts[4]);
                df5.setGateNumber("B3");
                FlightManager.INSTANCE.addFlight(df5);

                // Flight 6 - Direct (Islamabad to Lahore)
                cal.set(2025, Calendar.DECEMBER, 20, 7, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 20, 8, 30);
                arrv1 = cal.getTime();
                DirectFlight df6 = new DirectFlight("SB106", "Islamabad", "Lahore", dept1, arrv1, 8000, 220, true);
                df6.setAircraft(aircrafts[5]);
                df6.setGateNumber("A3");
                FlightManager.INSTANCE.addFlight(df6);

                // Flight 7 - Direct (Karachi to London)
                cal.set(2025, Calendar.DECEMBER, 21, 22, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 22, 6, 0);
                arrv1 = cal.getTime();
                DirectFlight df7 = new DirectFlight("SB107", "Karachi", "London", dept1, arrv1, 85000, 400, false);
                df7.setAircraft(aircrafts[6]);
                df7.setGateNumber("C1");
                FlightManager.INSTANCE.addFlight(df7);

                // Flight 8 - Direct (Lahore to London)
                cal.set(2025, Calendar.DECEMBER, 22, 23, 30);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 23, 7, 30);
                arrv1 = cal.getTime();
                DirectFlight df8 = new DirectFlight("SB108", "Lahore", "London", dept1, arrv1, 88000, 500, false);
                df8.setAircraft(aircrafts[7]);
                df8.setGateNumber("C2");
                FlightManager.INSTANCE.addFlight(df8);

                // Flight 9 - Direct (Islamabad to Karachi)
                cal.set(2025, Calendar.DECEMBER, 23, 15, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 23, 17, 30);
                arrv1 = cal.getTime();
                DirectFlight df9 = new DirectFlight("SB109", "Islamabad", "Karachi", dept1, arrv1, 12500, 190, true);
                df9.setAircraft(aircrafts[8]);
                df9.setGateNumber("A4");
                FlightManager.INSTANCE.addFlight(df9);

                // Flight 10 - Direct (Karachi to Lahore)
                cal.set(2025, Calendar.DECEMBER, 24, 12, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 24, 14, 0);
                arrv1 = cal.getTime();
                DirectFlight df10 = new DirectFlight("SB110", "Karachi", "Lahore", dept1, arrv1, 11500, 200, true);
                df10.setAircraft(aircrafts[9]);
                df10.setGateNumber("A5");
                FlightManager.INSTANCE.addFlight(df10);

                // Flight 11 - Connecting (Karachi to London via Dubai)
                cal.set(2025, Calendar.DECEMBER, 25, 8, 0);
                Date dept11 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 25, 12, 0);
                Date arrv11 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 25, 14, 0);
                Date dept12 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 25, 20, 0);
                Date arrv12 = cal.getTime();
                Layover layover1 = new Layover("Dubai", 120);
                ConnectedFlight cf1 = new ConnectedFlight("SB201", "Karachi", "London", dept11, arrv12, 75000, 240,
                                layover1,
                                "SB201B", dept12, arrv11, false);
                cf1.setAircraft(aircrafts[10]);
                cf1.setGateNumber("D1");
                FlightManager.INSTANCE.addFlight(cf1);

                // Flight 12 - Connecting (Lahore to London via Dubai)
                cal.set(2025, Calendar.DECEMBER, 26, 9, 0);
                dept11 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 26, 13, 30);
                arrv11 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 26, 15, 30);
                dept12 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 26, 21, 30);
                arrv12 = cal.getTime();
                Layover layover2 = new Layover("Dubai", 120);
                ConnectedFlight cf2 = new ConnectedFlight("SB202", "Lahore", "London", dept11, arrv12, 78000, 270,
                                layover2,
                                "SB202B", dept12, arrv11, false);
                cf2.setAircraft(aircrafts[11]);
                cf2.setGateNumber("D2");
                FlightManager.INSTANCE.addFlight(cf2);

                // Flight 13 - Direct (Dubai to Karachi)
                cal.set(2025, Calendar.DECEMBER, 27, 16, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 27, 20, 30);
                arrv1 = cal.getTime();
                DirectFlight df13 = new DirectFlight("SB111", "Dubai", "Karachi", dept1, arrv1, 39000, 200, false);
                df13.setAircraft(aircrafts[12]);
                df13.setGateNumber("B4");
                FlightManager.INSTANCE.addFlight(df13);

                // Flight 14 - Direct (Dubai to Lahore)
                cal.set(2025, Calendar.DECEMBER, 28, 17, 30);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 28, 22, 0);
                arrv1 = cal.getTime();
                DirectFlight df14 = new DirectFlight("SB112", "Dubai", "Lahore", dept1, arrv1, 37000, 140, false);
                df14.setAircraft(aircrafts[13]);
                df14.setGateNumber("B5");
                FlightManager.INSTANCE.addFlight(df14);

                // Flight 15 - Direct (Dubai to Islamabad)
                cal.set(2025, Calendar.DECEMBER, 29, 18, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 29, 22, 30);
                arrv1 = cal.getTime();
                DirectFlight df15 = new DirectFlight("SB113", "Dubai", "Islamabad", dept1, arrv1, 36000, 175, false);
                df15.setAircraft(aircrafts[14]);
                df15.setGateNumber("B6");
                FlightManager.INSTANCE.addFlight(df15);

                // Flight 16 - Direct with Return (Karachi to Dubai - Round Trip)
                cal.set(2025, Calendar.DECEMBER, 30, 10, 0);
                dept1 = cal.getTime();
                cal.set(2025, Calendar.DECEMBER, 30, 14, 30);
                arrv1 = cal.getTime();
                DirectFlight df16 = new DirectFlight("SB114", "Karachi", "Dubai", dept1, arrv1, 38000, 180, false);
                df16.setAircraft(aircrafts[0]);
                df16.setGateNumber("B7");

                cal.set(2026, Calendar.JANUARY, 5, 16, 0);
                Date deptReturn = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 5, 20, 30);
                Date arrvReturn = cal.getTime();
                DirectFlight returnF16 = new DirectFlight("SB114R", "Dubai", "Karachi", deptReturn, arrvReturn, 39000,
                                180,
                                false);
                returnF16.setAircraft(aircrafts[0]);
                returnF16.setGateNumber("B8");
                df16.setReturnFlight(returnF16);
                FlightManager.INSTANCE.addFlight(df16);

                // Flight 17 - Direct (London to Karachi)
                cal.set(2025, Calendar.DECEMBER, 31, 22, 0);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 1, 10, 0);
                arrv1 = cal.getTime();
                DirectFlight df17 = new DirectFlight("SB115", "London", "Karachi", dept1, arrv1, 87000, 150, false);
                df17.setAircraft(aircrafts[1]);
                df17.setGateNumber("C3");
                FlightManager.INSTANCE.addFlight(df17);

                // Flight 18 - Direct (London to Lahore)
                cal.set(2026, Calendar.JANUARY, 1, 23, 30);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 2, 11, 30);
                arrv1 = cal.getTime();
                DirectFlight df18 = new DirectFlight("SB116", "London", "Lahore", dept1, arrv1, 89000, 300, false);
                df18.setAircraft(aircrafts[2]);
                df18.setGateNumber("C4");
                FlightManager.INSTANCE.addFlight(df18);

                // Flight 19 - Connecting (Islamabad to London via Dubai)
                cal.set(2026, Calendar.JANUARY, 3, 10, 0);
                dept11 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 3, 14, 0);
                arrv11 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 3, 16, 0);
                dept12 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 3, 22, 0);
                arrv12 = cal.getTime();
                Layover layover3 = new Layover("Dubai", 120);
                ConnectedFlight cf3 = new ConnectedFlight("SB203", "Islamabad", "London", dept11, arrv12, 76000, 280,
                                layover3,
                                "SB203B", dept12, arrv11, false);
                cf3.setAircraft(aircrafts[3]);
                cf3.setGateNumber("D3");
                FlightManager.INSTANCE.addFlight(cf3);

                // Flight 20 - Direct (Lahore to Islamabad)
                cal.set(2026, Calendar.JANUARY, 4, 8, 0);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 4, 9, 30);
                arrv1 = cal.getTime();
                DirectFlight df20 = new DirectFlight("SB117", "Lahore", "Islamabad", dept1, arrv1, 8500, 250, true);
                df20.setAircraft(aircrafts[4]);
                df20.setGateNumber("A6");
                FlightManager.INSTANCE.addFlight(df20);

                // Flight 21 - Direct with Return (Lahore to Dubai - Round Trip)
                cal.set(2026, Calendar.JANUARY, 5, 11, 30);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 5, 16, 0);
                arrv1 = cal.getTime();
                DirectFlight df21 = new DirectFlight("SB118", "Lahore", "Dubai", dept1, arrv1, 36000, 220, false);
                df21.setAircraft(aircrafts[5]);
                df21.setGateNumber("B9");

                cal.set(2026, Calendar.JANUARY, 10, 17, 30);
                deptReturn = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 10, 22, 0);
                arrvReturn = cal.getTime();
                DirectFlight returnF21 = new DirectFlight("SB118R", "Dubai", "Lahore", deptReturn, arrvReturn, 37000,
                                220,
                                false);
                returnF21.setAircraft(aircrafts[5]);
                returnF21.setGateNumber("B10");
                df21.setReturnFlight(returnF21);
                FlightManager.INSTANCE.addFlight(df21);

                // Flight 22 - Direct (Karachi to Islamabad)
                cal.set(2026, Calendar.JANUARY, 6, 13, 0);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 6, 15, 30);
                arrv1 = cal.getTime();
                DirectFlight df22 = new DirectFlight("SB119", "Karachi", "Islamabad", dept1, arrv1, 13000, 400, true);
                df22.setAircraft(aircrafts[6]);
                df22.setGateNumber("A7");
                FlightManager.INSTANCE.addFlight(df22);

                // Flight 23 - Direct (Islamabad to Dubai)
                cal.set(2026, Calendar.JANUARY, 7, 15, 0);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 7, 19, 0);
                arrv1 = cal.getTime();
                DirectFlight df23 = new DirectFlight("SB120", "Islamabad", "Dubai", dept1, arrv1, 35500, 500, false);
                df23.setAircraft(aircrafts[7]);
                df23.setGateNumber("B11");
                FlightManager.INSTANCE.addFlight(df23);

                // Flight 24 - Connecting (Karachi to London via Istanbul)
                cal.set(2026, Calendar.JANUARY, 8, 11, 0);
                dept11 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 8, 17, 0);
                arrv11 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 8, 19, 0);
                dept12 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 9, 1, 0);
                arrv12 = cal.getTime();
                Layover layover4 = new Layover("Istanbul", 120);
                ConnectedFlight cf4 = new ConnectedFlight("SB204", "Karachi", "London", dept11, arrv12, 79000, 190,
                                layover4,
                                "SB204B", dept12, arrv11, false);
                cf4.setAircraft(aircrafts[8]);
                cf4.setGateNumber("D4");
                FlightManager.INSTANCE.addFlight(cf4);

                // Flight 25 - Direct (Lahore to Karachi)
                cal.set(2026, Calendar.JANUARY, 9, 16, 0);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 9, 18, 0);
                arrv1 = cal.getTime();
                DirectFlight df25 = new DirectFlight("SB121", "Lahore", "Karachi", dept1, arrv1, 11800, 200, true);
                df25.setAircraft(aircrafts[9]);
                df25.setGateNumber("A8");
                FlightManager.INSTANCE.addFlight(df25);

                // Flight 26 - Direct with Return (Islamabad to Dubai - Round Trip)
                cal.set(2026, Calendar.JANUARY, 10, 14, 0);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 10, 18, 0);
                arrv1 = cal.getTime();
                DirectFlight df26 = new DirectFlight("SB122", "Islamabad", "Dubai", dept1, arrv1, 35000, 240, false);
                df26.setAircraft(aircrafts[10]);
                df26.setGateNumber("B12");

                cal.set(2026, Calendar.JANUARY, 15, 16, 0);
                deptReturn = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 15, 20, 0);
                arrvReturn = cal.getTime();
                DirectFlight returnF26 = new DirectFlight("SB122R", "Dubai", "Islamabad", deptReturn, arrvReturn, 36000,
                                240,
                                false);
                returnF26.setAircraft(aircrafts[10]);
                returnF26.setGateNumber("B13");
                df26.setReturnFlight(returnF26);
                FlightManager.INSTANCE.addFlight(df26);

                // Flight 27 - Direct (Dubai to London)
                cal.set(2026, Calendar.JANUARY, 11, 20, 0);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 12, 2, 0);
                arrv1 = cal.getTime();
                DirectFlight df27 = new DirectFlight("SB123", "Dubai", "London", dept1, arrv1, 55000, 270, false);
                df27.setAircraft(aircrafts[11]);
                df27.setGateNumber("C5");
                FlightManager.INSTANCE.addFlight(df27);

                // Flight 28 - Direct (London to Dubai)
                cal.set(2026, Calendar.JANUARY, 12, 22, 0);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 13, 8, 0);
                arrv1 = cal.getTime();
                DirectFlight df28 = new DirectFlight("SB124", "London", "Dubai", dept1, arrv1, 56000, 200, false);
                df28.setAircraft(aircrafts[12]);
                df28.setGateNumber("C6");
                FlightManager.INSTANCE.addFlight(df28);

                // Flight 29 - Connecting (Lahore to London via Dubai)
                cal.set(2026, Calendar.JANUARY, 13, 10, 0);
                dept11 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 13, 14, 30);
                arrv11 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 13, 16, 30);
                dept12 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 13, 22, 30);
                arrv12 = cal.getTime();
                Layover layover5 = new Layover("Dubai", 120);
                ConnectedFlight cf5 = new ConnectedFlight("SB205", "Lahore", "London", dept11, arrv12, 77000, 140,
                                layover5,
                                "SB205B", dept12, arrv11, false);
                cf5.setAircraft(aircrafts[13]);
                cf5.setGateNumber("D5");
                FlightManager.INSTANCE.addFlight(cf5);

                // Flight 30 - Direct (Islamabad to Karachi)
                cal.set(2026, Calendar.JANUARY, 14, 18, 0);
                dept1 = cal.getTime();
                cal.set(2026, Calendar.JANUARY, 14, 20, 30);
                arrv1 = cal.getTime();
                DirectFlight df30 = new DirectFlight("SB125", "Islamabad", "Karachi", dept1, arrv1, 12800, 175, true);
                df30.setAircraft(aircrafts[14]);
                df30.setGateNumber("A9");
                FlightManager.INSTANCE.addFlight(df30);

                // ===== SAVE & ASSIGN FLIGHTS =====
                FileHandler.saveFlights(FlightManager.INSTANCE.getFlights());
                EmployeeManager empManager = EmployeeManager.getInstance();
                
                FlightManager.INSTANCE.assignFlights(empManager);

                // Launch GUI
                new HomePageGui();
        }
}