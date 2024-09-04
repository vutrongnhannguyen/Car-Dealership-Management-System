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
            throw new IllegalArgumentException("Invalid User ID format");
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

    public void displayMenu(Scanner scanner, List<Car> cars, List<AutoPart> parts, List<Service> services, List<Transaction> transactions, List<User> users) {
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. View Profile");
            System.out.println("2. Logout");

            switch (getUserType()) {
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
                    System.out.println("15. Calculate Revenue for Mechanic");
                    System.out.println("16. Calculate Revenue for Salesperson");
                    System.out.println("17. List Cars Sold");
                    System.out.println("18. List Transactions");
                    System.out.println("19. List Services");
                    System.out.println("20. List Auto Parts Sold");
                }
                case "Salesperson", "Mechanic" -> {
                    System.out.println("3. Calculate Revenue");
                    System.out.println("4. List Cars or Services");
                    System.out.println("5. Activity Histories");
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
                    System.out.println(this);
                    break;
                case 2:
                    System.out.println("Logging out");
                    choice = 0; // Exit loop
                    break;
                case 3:
                    switch (getUserType()) {
                        case "Manager" -> CarManager.addCar(scanner, cars);
                        case "Salesperson", "Mechanic" -> {
                            System.out.print("Enter day/week/month to calculate revenue: ");
                            String period = scanner.nextLine();
                            ((Salesperson) this).calculateRevenue(transactions, period);
                        }
                        case "Client" -> ((Client) this).viewMembershipStatus();
                    }
                    break;
                case 4:
                    switch (getUserType()) {
                        case "Manager" -> CarManager.removeCar(scanner, cars);
                        case "Salesperson", "Mechanic" -> {
                            System.out.print("Enter day/week/month to list cars or services: ");
                            String period = scanner.nextLine();
                            ((Salesperson) this).listCarsOrServices(cars, services, period);
                        }
                        case "Client" -> ((Client) this).viewServiceHistory(services, getUserID());
                    }
                    break;
                case 5:
                    if (getUserType().equals("Manager")) {
                        // Manager adds a new part
                        PartManager.addPart(scanner, parts);
                    } else if (this instanceof Client client) {
                        // Client updates their full name
                        System.out.print("Enter new full name: ");
                        String newFullName = scanner.nextLine();
                        client.updateFullName(newFullName);
                        // Save the updated user list to the CSV file
                        FileManager.writeUsers(users, "D:/programming1/asm3/users.csv");
                    } else if (this instanceof Salesperson salesperson) {
                        // Salesperson views their transaction history
                        salesperson.viewTransactionHistory(transactions, getUserID());
                    } else if (this instanceof Mechanic mechanic) {
                        // Mechanic views their service history
                        mechanic.viewServiceHistory(services, getUserID());
                    }
                    break;
                case 6:
                    if (getUserType().equals("Manager")) {
                        PartManager.removePart(scanner, parts);
                    } else if (this instanceof Client client) {
                        System.out.print("Enter new username: ");
                        String newUsername = scanner.nextLine();
                        client.updateUsername(newUsername);
                        FileManager.writeUsers(users, "D:/programming1/asm3/users.csv"); // Save the updated user list to the CSV file
                    }
                    break;
                case 7:
                    if (getUserType().equals("Manager")) {
                        ServiceManager.addService(scanner, services, parts, users);
                    } else if (this instanceof Client client) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        client.updatePassword(newPassword);
                        FileManager.writeUsers(users, "D:/programming1/asm3/users.csv"); // Save the updated user list to the CSV file
                    }
                    break;
                case 8:
                    if (getUserType().equals("Manager")) {
                        ServiceManager.removeService(scanner, services);
                    }
                    break;
                case 9:
                    if (getUserType().equals("Manager")) {
                        TransactionManager.addTransaction(scanner, transactions, cars, parts, users);
                    }
                    break;
                case 10:
                    if (getUserType().equals("Manager")) {
                        TransactionManager.removeTransaction(scanner, transactions);
                    }
                    break;
                case 11:
                    if (getUserType().equals("Manager")) {
                        UserManager.removeUser(scanner, users);
                    }
                    break;
                case 12:
                    if (getUserType().equals("Manager")) {
                        ((Manager) this).viewEntities(cars, parts, services, transactions);
                    }
                    break;
                default:
                    System.out.println("We don't have that option");
            }
        }
    }

    @Override
    public String toString() {
        return "User ID: " + userID + "\nName: " + fullName + "\nUser Type: " + userType + "\nStatus: " + (isActive ? "Active" : "Inactive");
    }
}

class Manager extends User {
    public Manager(String userID, String fullName, String username, String password) {
        super(userID, fullName, "Manager", username, password);
    }

    // View/Search Operations
    public void viewEntities(List<Car> cars, List<AutoPart> parts, List<Service> services, List<Transaction> transactions) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Loop until the manager chooses to exit
        while (choice != 0) {
            System.out.println("\n--- Entities Menu ---");
            System.out.println("1. View Cars");
            System.out.println("2. View Auto Parts");
            System.out.println("3. View Services");
            System.out.println("4. View Transactions");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\n--- Cars ---");
                    if (cars.isEmpty()) {
                        System.out.println("No cars available.");
                    } else {
                        cars.forEach(System.out::println);
                    }
                    break;

                case 2:
                    System.out.println("\n--- Auto Parts ---");
                    if (parts.isEmpty()) {
                        System.out.println("No auto parts available.");
                    } else {
                        parts.forEach(System.out::println);
                    }
                    break;

                case 3:
                    System.out.println("\n--- Services ---");
                    if (services.isEmpty()) {
                        System.out.println("No services available.");
                    } else {
                        services.forEach(System.out::println);
                    }
                    break;

                case 4:
                    System.out.println("\n--- Transactions ---");
                    if (transactions.isEmpty()) {
                        System.out.println("No transactions available.");
                    } else {
                        transactions.forEach(System.out::println);
                    }
                    break;

                case 0:
                    System.out.println("Exiting entity view.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from 0 to 4.");
            }
        }
    }

    // Statistics Operations
    public void calculateCarsSoldInMonth(List<Car> cars, int month, int year) {
    }

    public void calculateRevenue(List<Transaction> transactions, String period) {

    }

    public void calculateRevenueForMechanic(List<Service> services, String mechanicID, String period) {

    }

    public void calculateRevenueForSalesperson(List<Transaction> transactions, String salespersonID, String period) {

    }

    public void listCarsSold(List<Car> cars, String period) {

    }

    public void listTransactions(List<Transaction> transactions, String period) {

    }

    public void listServices(List<Service> services, String period) {

    }

    public void listAutoPartsSold(List<Transaction> transactions, String period) {

    }
}

class Salesperson extends User {

    public Salesperson(String userID, String fullName, String username, String password) {
        super(userID, fullName, "Salesperson", username, password);
    }

    public void calculateRevenue(List<Transaction> transactions, String period) {

    }

    public void listCarsOrServices(List<Car> cars, List<Service> services, String period) {

    }

    public void viewTransactionHistory(List<Transaction> transactions, String salespersonID) {
        System.out.println("\nTransaction History: ");
        boolean hasTransactions = false;
        for (Transaction transaction : transactions) {
            if (transaction.getSalespersonID().equals(salespersonID)) {
                System.out.println(transaction);
                hasTransactions = true;
            }
        }
        if (!hasTransactions) {
            System.out.println("No transaction history found for this salesperson.");
        }
    }
}

class Mechanic extends User {

    public Mechanic(String userID, String fullName, String username, String password) {
        super(userID, fullName, "Mechanic", username, password);
    }

    public void calculateRevenue(List<Transaction> transactions, String period) {

    }

    public void listCarsOrServices(List<Car> cars, List<Service> services, String period) {

    }

    public void viewServiceHistory(List<Service> services, String mechanicID) {
        System.out.println("\nService History: ");
        boolean hasServices = false;
        for (Service service : services) {
            if (service.getMechanicID().equals(mechanicID)) {
                System.out.println(service);
                hasServices = true;
            }
        }
        if (!hasServices) {
            System.out.println("No service history found for this mechanic.");
        }
    }
}

class Client extends User {
    private String membership;
    private double totalSpending;

    public Client(String userID, String fullName, String username, String password) {
        super(userID, fullName, "Client", username, password);
        this.membership = "None"; // Default membership level
        this.totalSpending = 0.0;
    }

    public String getMembership() {
        return membership;
    }

    public double getTotalSpending() {
        return totalSpending;
    }

    public void updateTotalSpending(double amount) {
        this.totalSpending += amount;
        updateMembership(); // Update membership based on the new total spending
    }

    public void updateMembership() {
        if (totalSpending > 250_000_000) {
            membership = "Platinum";
        } else if (totalSpending > 100_000_000) {
            membership = "Gold";
        } else if (totalSpending > 30_000_000) {
            membership = "Silver";
        } else {
            membership = "None";
        }
    }

    public double getDiscountRate() {
        switch (membership) {
            case "Silver":
                return 0.05;
            case "Gold":
                return 0.10;
            case "Platinum":
                return 0.15;
            default:
                return 0.0;
        }
    }

    public void viewMembershipStatus() {
        System.out.println("Your membership is: " + membership);
    }

    public void viewServiceHistory(List<Service> services, String clientID) {
        System.out.println("\nService History: ");
        boolean hasServices = false;
        for (Service service : services) {
            if (service.getClientID().equals(clientID)) {
                System.out.println(service);
                hasServices = true;
            }
        }
        if (!hasServices) {
            System.out.println("No service history found for this client");
        }
    }

    // Methods to modify profile information
    public void updateFullName(String newFullName) {
        this.fullName = newFullName;
        System.out.println("Full name updated successfully");
    }

    public void updateUsername(String newUsername) {
        this.username = newUsername;
        System.out.println("Username updated successfully");
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password updated successfully");
    }
}