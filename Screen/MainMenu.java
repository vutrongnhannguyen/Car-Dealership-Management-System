package Screen;


import AutoPart.AutoPart;
import Car.Car;
import FileHandler.FileManager;
import Service.Service;
import Transaction.Transaction;
import User.User;
import User.Mechanic;
import User.Salesperson;
import User.Client;
import User.Manager;
import AutoPart.PartManager;
import User.UserManager;
import Service.ServiceManager;
import Transaction.TransactionManager;
import UserOperation.EmployeeOperation;
import Car.CarManager;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {
    public static void displayMenu(Scanner scanner, List<Car> cars, List<AutoPart> parts, List<Service> services, List<Transaction> transactions, List<User> users, User user) {
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. View Profile");
            System.out.println("2. Logout");
            switch (user.getUserType()) {
                case "Manager" -> {
                    System.out.println("3. Add Car");
                    System.out.println("4. Remove Car");
                    System.out.println("5. Add Part");
                    System.out.println("6. Remove Part");
                    System.out.println("7. Add Service");
                    System.out.println("8. Remove Service");
                    System.out.println("9. Add Transaction");
                    System.out.println("10. Remove Transaction");
                    System.out.println("11. Remove User");
                    System.out.println("12. View All Entities");
                    System.out.println("13. Calculate Cars Sold in Month");
                    System.out.println("14. Calculate Revenue");
                    System.out.println("15. Calculate Revenue for User");
                    System.out.println("16. Calculate Revenue for User");
                    System.out.println("17. List Cars Sold");
                    System.out.println("18. List Transactions");
                    System.out.println("19. List Services");
                    System.out.println("20. List Auto Parts Sold");
                    System.out.println("21. Update Entities");
                    System.out.println("22. Read Entities");
                }
                case "Mechanic","Salesperson" -> {
                    System.out.println("3. Calculate Revenue");
                    System.out.println("4. List Cars or Services");
                }
                case "Client" -> {
                    System.out.println("3. View Membership Status");
                    System.out.println("4. View Service History");
                    System.out.println("5. Update Full Name");
                    System.out.println("6. Update Username");
                    System.out.println("7. Update Password");
                }
            }

            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n--- PROFILE ---");
                    System.out.println(user);
                    break;
                case 2:
                    System.out.println("Logging out");
                    choice = 0; // Exit loop
                    break;
                case 3:
                    switch (user.getUserType()) {
                        case "Manager" -> CarManager.addCar(scanner, cars);
                        case "Mechanic","Salesperson" -> {
                            System.out.print("Enter the period (day/week/month): ");
                            String period = scanner.nextLine();
                            System.out.print("Enter the day: ");
                            String day = scanner.nextLine();
                            System.out.print("Enter the month: ");
                            String month = scanner.nextLine();
                            System.out.print("Enter the year: ");
                            String year = scanner.nextLine();
                            if(user instanceof Mechanic){
                                ((Mechanic) user).getEmployeeOperation().calculateRevenue(transactions,period, day, month, year);
                            } else if(user instanceof Salesperson){
                                ((Salesperson) user).getEmployeeOperation().calculateRevenue(transactions,period, day, month, year);
                            }
                        }
                        case "Client" -> ((Client) user).viewMembershipStatus();
                    }
                    break;
                case 4:
                    switch (user.getUserType()) {
                        case "Manager" -> CarManager.removeCar(scanner, cars);
                        case "Mechanic","Salesperson" -> {
                            System.out.print("Enter the period (day/week/month): ");
                            String period = scanner.nextLine();
                            System.out.print("Enter the day: ");
                            String day = scanner.nextLine();
                            System.out.print("Enter the month: ");
                            String month = scanner.nextLine();
                            System.out.print("Enter the year: ");
                            String year = scanner.nextLine();
                            if(user instanceof Mechanic){
                                ((Mechanic) user).getEmployeeOperation().listNumberCarAndService(services, transactions, period, day, month, year);
                            } else if(user instanceof Salesperson){
                                ((Salesperson) user).getEmployeeOperation().listNumberCarAndService(services, transactions, period, day, month, year);
                            }
                        }
                        case "Client" -> ((Client) user).viewServiceHistory(services, user.getUserID());
                    }
                    break;
                case 5:
                    if (user.getUserType().equals("Manager")) {
                        PartManager.addPart(scanner, parts);
                    } else if (user instanceof Client client) {
                        System.out.print("Enter new full name: ");
                        String newFullName = scanner.nextLine();
                        client.updateFullName(newFullName);
                        FileManager.writeUsers(users, "Database/users.csv"); // Save the updated user list to the CSV file
                    }
                    break;
                case 6:
                    if (user.getUserType().equals("Manager")) {
                        PartManager.removePart(scanner, parts);
                    } else if (user instanceof Client client) {
                        System.out.print("Enter new username: ");
                        String newUsername = scanner.nextLine();
                        client.updateUsername(newUsername);
                        FileManager.writeUsers(users, "Database/users.csv"); // Save the updated user list to the CSV file
                    }
                    break;
                case 7:
                    if (user.getUserType().equals("Manager")) {
                        ServiceManager.addService(scanner, services, parts, users);
                    } else if (user instanceof Client client) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        client.updatePassword(newPassword);
                        FileManager.writeUsers(users, "Database/users.csv"); // Save the updated user list to the CSV file
                    }
                    break;
                case 8:
                    if (user.getUserType().equals("Manager")) {
                        ServiceManager.removeService(scanner, services);
                    }
                    break;
                case 9:
                    if (user.getUserType().equals("Manager")) {
                        TransactionManager.addTransaction(scanner, transactions, cars, parts, users);
                    }
                    break;
                case 10:
                    if (user.getUserType().equals("Manager")) {
                        TransactionManager.removeTransaction(scanner, transactions);
                    }
                    break;
                case 11:
                    if (user.getUserType().equals("Manager")) {
                        UserManager.removeUser(scanner, users);
                    }
                    break;
                case 12:
                    if (user.getUserType().equals("Manager")) {
                        ((Manager) user).getManagerOperation().viewEntities(cars, parts, services, transactions);
                    }
                    break;
                case 13:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) user).getManagerOperation().calculateCarsSoldInMonth(transactions, month, year);
                    }
                    break;
                case 14:
                    if (user.getUserType().equals("Manager") || user.getUserType().equals("Mechanic") || user.getUserType().equals("Salesperson")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) user).getManagerOperation().calculateRevenue(transactions,period, day, month, year);

                    }
                    break;
                case 15:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter the mechanic ID: ");
                        String mechanicID = scanner.nextLine();
                        ((Manager) user).getManagerOperation().calculateRevenueForMechanic(services, mechanicID);
                    }
                    break;
                case 16:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter the salesperson ID: ");
                        String salespersonID = scanner.nextLine();
                        ((Manager) user).getManagerOperation().calculateRevenueForSalesperson(transactions, salespersonID);
                    }
                    break;
                case 17:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) user).getManagerOperation().listCarsSold(transactions,period, day, month, year);
                    }
                    break;
                case 18:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) user).getManagerOperation().listTransactions(transactions,period, day, month, year);
                    }
                    break;
                case 19:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) user).getManagerOperation().listServices(services,period, day, month, year);
                    }
                    break;
                case 20:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) user).getManagerOperation().listAutoPartsSold(transactions,period, day, month, year);
                    }
                    break;
                case 21:
                    if (user.getUserType().equals("Manager")) {
                        System.out.println("What would you like to update?");
                        System.out.println("1. Car");
                        System.out.println("2. Part");

                        // Let the manager choose by entering a number
                        System.out.print("Select an option: ");
                        int updateChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (updateChoice) {
                            case 1 -> CarManager.updateCar(scanner, cars);
                            case 2 -> PartManager.updatePart(scanner, parts);
                            default -> System.out.println("Invalid option");
                        }
                    }
                    break;
                case 22:
                    if (user.getUserType().equals("Manager")) {
                        boolean keepReading = true;

                        while (keepReading) {
                            System.out.println("What data would you like to access?");
                            System.out.println("1. Car");
                            System.out.println("2. Part");
                            System.out.println("3. Service");
                            System.out.println("4. Transaction");
                            System.out.println("5. User");
                            System.out.println("6. Exit");
                            System.out.println("Select an option: ");

                            int readChoice = scanner.nextInt();
                            scanner.nextLine(); // consume newline

                            switch (readChoice) {
                                case 1 -> CarManager.readCarByID(scanner, cars);
                                case 2 -> PartManager.readPartByID(scanner, parts);
                                case 3 -> ServiceManager.readServiceByID(scanner, services);
                                case 4 -> TransactionManager.readTransactionByID(scanner, transactions);
                                case 5 -> UserManager.readUserByID(scanner, users);
                                case 6 -> {
                                    keepReading = false;
                                    System.out.println("Returning to main menu...");
                                }
                                default -> System.out.println("Invalid option");
                            }
                        }
                    }
                    break;
                default:
                    System.out.println("We don't have that option");
            }
        }
    }

}
