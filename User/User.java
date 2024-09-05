package User;

import AutoPart.AutoPart;
import Car.Car;
import AutoPart.PartManager;
import Service.Service;
import Transaction.Transaction;
import FileHandler.FileManager;
import Transaction.TransactionManager;
import Service.ServiceManager;

import java.util.List;
import java.util.Scanner;

public class User {
    protected String userID;
    protected String fullName;
    protected String userType;
    protected String username;
    protected String password;
    protected boolean isActive;

    public User(String userID, String fullName, String userType, String username, String password) {
        setUserID(userID);
        this.fullName = fullName;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.isActive = true; // Users are active by default
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password) && isActive;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        if (userID.matches("u-\\d+")) {
            this.userID = userID;
        } else {
            throw new IllegalArgumentException("Invalid User.User ID format");
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

//    public void displayMenu(Scanner scanner, List<Car> cars, List<AutoPart> parts, List<Service> services, List<Transaction> transactions, List<User> users) {
//        int choice = -1;
//
//        while (choice != 0) {
//            System.out.println("\n--- MENU ---");
//            System.out.println("1. View Profile");
//            System.out.println("2. Logout");
//
//            switch (getUserType()) {
//                case "User.Manager" -> {
//                    System.out.println("3. Add Car.Car");
//                    System.out.println("4. Remove Car.Car");
//                    System.out.println("5. Add Part");
//                    System.out.println("6. Remove Part");
//                    System.out.println("7. Add Service.Service");
//                    System.out.println("8. Remove Service.Service");
//                    System.out.println("9. Add Transaction.Transaction");
//                    System.out.println("10. Remove Transaction.Transaction");
//                    System.out.println("11. Remove User.User");
//                    System.out.println("12. View All Entities");
//                    System.out.println("13. Calculate Cars Sold in Month");
//                    System.out.println("14. Calculate Revenue");
//                    System.out.println("15. Calculate Revenue for User.Mechanic");
//                    System.out.println("16. Calculate Revenue for User.Salesperson");
//                    System.out.println("17. List Cars Sold");
//                    System.out.println("18. List Transactions");
//                    System.out.println("19. List Services");
//                    System.out.println("20. List Auto Parts Sold");
//                }
//                case "User.Salesperson", "User.Mechanic" -> {
//                    System.out.println("3. Calculate Revenue");
//                    System.out.println("4. List Cars or Services");
//                }
//                case "User.Client" -> {
//                    System.out.println("3. View Membership Status");
//                    System.out.println("4. View Service.Service History");
//                    System.out.println("5. Update Full Name");
//                    System.out.println("6. Update Username");
//                    System.out.println("7. Update Password");
//                }
//            }
//
//            System.out.print("Select an option: ");
//            choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    System.out.println("\n--- PROFILE ---");
//                    System.out.println(this);
//                    break;
//                case 2:
//                    System.out.println("Logging out");
//                    choice = 0; // Exit loop
//                    break;
//                case 3:
//                    switch (getUserType()) {
//                        case "User.Manager" -> AutoPart.CarManager.addCar(scanner, cars);
//                        case "User.Salesperson", "User.Mechanic" -> {
//                            System.out.print("Enter the period (day/week/month): ");
//                            String period = scanner.nextLine();
//                            System.out.print("Enter the day: ");
//                            String day = scanner.nextLine();
//                            System.out.print("Enter the month: ");
//                            String month = scanner.nextLine();
//                            System.out.print("Enter the year: ");
//                            String year = scanner.nextLine();
//                            ((Salesperson) this).calculateRevenue(transactions,period, day, month, year);
//                            ((Mechanic) this).calculateRevenue(transactions,period, day, month, year);
//                        }
//                        case "User.Client" -> ((Client) this).viewMembershipStatus();
//                    }
//                    break;
//                case 4:
//                    switch (getUserType()) {
//                        case "User.Manager" -> AutoPart.CarManager.removeCar(scanner, cars);
//                        case "User.Salesperson", "User.Mechanic" -> {
//                            System.out.print("Enter day/week/month to list cars or services: ");
//                            String period = scanner.nextLine();
//                            ((Salesperson) this).listCarsOrServices(cars, services, period);
//                        }
//                        case "User.Client" -> ((Client) this).viewServiceHistory(services, getUserID());
//                    }
//                    break;
//                case 5:
//                    if (getUserType().equals("User.Manager")) {
//                        PartManager.addPart(scanner, parts);
//                    } else if (this instanceof Client client) {
//                        System.out.print("Enter new full name: ");
//                        String newFullName = scanner.nextLine();
//                        client.updateFullName(newFullName);
//                        FileManager.writeUsers(users, "Database/users.csv"); // Save the updated user list to the CSV file
//                    }
//                    break;
//                case 6:
//                    if (getUserType().equals("User.Manager")) {
//                        PartManager.removePart(scanner, parts);
//                    } else if (this instanceof Client client) {
//                        System.out.print("Enter new username: ");
//                        String newUsername = scanner.nextLine();
//                        client.updateUsername(newUsername);
//                        FileManager.writeUsers(users, "Database/users.csv"); // Save the updated user list to the CSV file
//                    }
//                    break;
//                case 7:
//                    if (getUserType().equals("User.Manager")) {
//                        ServiceManager.addService(scanner, services, parts, users);
//                    } else if (this instanceof Client client) {
//                        System.out.print("Enter new password: ");
//                        String newPassword = scanner.nextLine();
//                        client.updatePassword(newPassword);
//                        FileManager.writeUsers(users, "Database/users.csv"); // Save the updated user list to the CSV file
//                    }
//                    break;
//                case 8:
//                    if (getUserType().equals("User.Manager")) {
//                        ServiceManager.removeService(scanner, services);
//                    }
//                    break;
//                case 9:
//                    if (getUserType().equals("User.Manager")) {
//                        TransactionManager.addTransaction(scanner, transactions, cars, parts, users);
//                    }
//                    break;
//                case 10:
//                    if (getUserType().equals("User.Manager")) {
//                        TransactionManager.removeTransaction(scanner, transactions);
//                    }
//                    break;
//                case 11:
//                    if (getUserType().equals("User.Manager")) {
//                        UserManager.removeUser(scanner, users);
//                    }
//                    break;
//                case 12:
//                    if (getUserType().equals("User.Manager")) {
//                        ((Manager) this).viewEntities(cars, parts, services, transactions);
//                    }
//                    break;
//                case 13:
//                    if (getUserType().equals("User.Manager")) {
//                       System.out.print("Enter the month: ");
//                       String month = scanner.nextLine();
//                       System.out.print("Enter the year: ");
//                       String year = scanner.nextLine();
//                        ((Manager) this).calculateCarsSoldInMonth(transactions, month, year);
//                    }
//                    break;
//                case 14:
//                    if (getUserType().equals("User.Manager") || getUserType().equals("User.Salesperson") || getUserType().equals("User.Mechanic")) {
//                        System.out.print("Enter the period (day/week/month): ");
//                        String period = scanner.nextLine();
//                        System.out.print("Enter the day: ");
//                        String day = scanner.nextLine();
//                        System.out.print("Enter the month: ");
//                        String month = scanner.nextLine();
//                        System.out.print("Enter the year: ");
//                        String year = scanner.nextLine();
//                        ((Manager) this).calculateRevenue(transactions,period, day, month, year);
//
//                    }
//                    break;
//                case 15:
//                    if (getUserType().equals("User.Manager")) {
//                        System.out.print("Enter the mechanic ID: ");
//                        String mechanicID = scanner.nextLine();
//                        ((Manager) this).calculateRevenueForMechanic(services, mechanicID);
//                    }
//                    break;
//                case 16:
//                    if (getUserType().equals("User.Manager")) {
//                        System.out.print("Enter the salesperson ID: ");
//                        String salespersonID = scanner.nextLine();
//                        ((Manager) this).calculateRevenueForSalesperson(transactions, salespersonID);
//                    }
//                    break;
//                case 17:
//                    if (getUserType().equals("User.Manager")) {
//                        System.out.print("Enter the period (day/week/month): ");
//                        String period = scanner.nextLine();
//                        System.out.print("Enter the day: ");
//                        String day = scanner.nextLine();
//                        System.out.print("Enter the month: ");
//                        String month = scanner.nextLine();
//                        System.out.print("Enter the year: ");
//                        String year = scanner.nextLine();
//                        ((Manager) this).listCarsSold(transactions,period, day, month, year);
//                    }
//                    break;
//                case 18:
//                    if (getUserType().equals("User.Manager")) {
//                        System.out.print("Enter the period (day/week/month): ");
//                        String period = scanner.nextLine();
//                        System.out.print("Enter the day: ");
//                        String day = scanner.nextLine();
//                        System.out.print("Enter the month: ");
//                        String month = scanner.nextLine();
//                        System.out.print("Enter the year: ");
//                        String year = scanner.nextLine();
//                        ((Manager) this).listTransactions(transactions,period, day, month, year);
//                    }
//                    break;
//                case 19:
//                    if (getUserType().equals("User.Manager")) {
//                        System.out.print("Enter the period (day/week/month): ");
//                        String period = scanner.nextLine();
//                        System.out.print("Enter the day: ");
//                        String day = scanner.nextLine();
//                        System.out.print("Enter the month: ");
//                        String month = scanner.nextLine();
//                        System.out.print("Enter the year: ");
//                        String year = scanner.nextLine();
//                        ((Manager) this).listServices(services,period, day, month, year);
//                    }
//                    break;
//                case 20:
//                    if (getUserType().equals("User.Manager")) {
//                        System.out.print("Enter the period (day/week/month): ");
//                        String period = scanner.nextLine();
//                        System.out.print("Enter the day: ");
//                        String day = scanner.nextLine();
//                        System.out.print("Enter the month: ");
//                        String month = scanner.nextLine();
//                        System.out.print("Enter the year: ");
//                        String year = scanner.nextLine();
//                        ((Manager) this).listAutoPartsSold(transactions,period, day, month, year);
//                    }
//                    break;
//                default:
//                    System.out.println("We don't have that option");
//            }
//        }
//    }

    @Override
    public String toString() {
        return "User.User ID: " + userID + "\nName: " + fullName + "\nUser.User Type: " + userType + "\nStatus: " + (isActive ? "Active" : "Inactive");
    }
}







