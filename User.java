import java.time.LocalDate;
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
                        case "Manager" -> AutoPart.CarManager.addCar(scanner, cars);
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
                        case "Manager" -> AutoPart.CarManager.removeCar(scanner, cars);
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
                        PartManager.addPart(scanner, parts);
                    } else if (this instanceof Client client) {
                        System.out.print("Enter new full name: ");
                        String newFullName = scanner.nextLine();
                        client.updateFullName(newFullName);
                        FileManager.writeUsers(users, "users.csv"); // Save the updated user list to the CSV file
                    }
                    break;
                case 6:
                    if (getUserType().equals("Manager")) {
                        PartManager.removePart(scanner, parts);
                    } else if (this instanceof Client client) {
                        System.out.print("Enter new username: ");
                        String newUsername = scanner.nextLine();
                        client.updateUsername(newUsername);
                        FileManager.writeUsers(users, "users.csv"); // Save the updated user list to the CSV file
                    }
                    break;
                case 7:
                    if (getUserType().equals("Manager")) {
                        ServiceManager.addService(scanner, services, parts, users);
                    } else if (this instanceof Client client) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        client.updatePassword(newPassword);
                        FileManager.writeUsers(users, "users.csv"); // Save the updated user list to the CSV file
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
                case 13:
                    if (getUserType().equals("Manager")) {
                       System.out.print("Enter the month: ");
                       String month = scanner.nextLine();
                       System.out.print("Enter the year: ");
                       String year = scanner.nextLine();
                        ((Manager) this).calculateCarsSoldInMonth(transactions, month, year);
                    }
                    break;
                case 14:
                    if (getUserType().equals("Manager") || getUserType().equals("Salesperson") || getUserType().equals("Mechanic")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) this).calculateRevenue(transactions,period, day, month, year);

                    }
                    break;
                case 15:
                    if (getUserType().equals("Manager")) {
                        System.out.print("Enter the mechanic ID: ");
                        String mechanicID = scanner.nextLine();
                        ((Manager) this).calculateRevenueForMechanic(services, mechanicID);
                    }
                    break;
                case 16:
                    if (getUserType().equals("Manager")) {
                        System.out.print("Enter the salesperson ID: ");
                        String salespersonID = scanner.nextLine();
                        ((Manager) this).calculateRevenueForSalesperson(transactions, salespersonID);
                    }
                    break;
                case 17:
                    if (getUserType().equals("Manager")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) this).listCarsSold(transactions,period, day, month, year);
                    }
                    break;
                case 18:
                    if (getUserType().equals("Manager")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) this).listTransactions(transactions,period, day, month, year);
                    }
                    break;
                case 19:
                    if (getUserType().equals("Manager")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) this).listServices(services,period, day, month, year);
                    }
                    break;
                case 20:
                    if (getUserType().equals("Manager")) {
                        System.out.print("Enter the period (day/week/month): ");
                        String period = scanner.nextLine();
                        System.out.print("Enter the day: ");
                        String day = scanner.nextLine();
                        System.out.print("Enter the month: ");
                        String month = scanner.nextLine();
                        System.out.print("Enter the year: ");
                        String year = scanner.nextLine();
                        ((Manager) this).listAutoPartsSold(transactions,period, day, month, year);
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
        System.out.println("\n--- Cars ---");
        cars.forEach(System.out::println);
        System.out.println("\n--- Auto Parts ---");
        parts.forEach(System.out::println);
        System.out.println("\n--- Services ---");
        services.forEach(System.out::println);
        System.out.println("\n--- Transactions ---");
        transactions.forEach(System.out::println);
    }

    public int calculateCarInOneTransaction(List<Object> purchasedItems){
        int count = 0;

        for (Object item : purchasedItems) {
            if (item instanceof Car) {
                count++;
            }
        }
        return count;
    }

    // Statistics Operations
    public void calculateCarsSoldInMonth(List<Transaction> transactions, String month, String year) {
        int numberOfCar = 0;

        // Convert month and year strings to integers
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);

        // Iterate through the transactions and calculate the number of cars sold
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().getMonthValue() == monthInt && transaction.getTransactionDate().getYear() == yearInt) {
                numberOfCar += calculateCarInOneTransaction(transaction.getPurchasedItems());
            }
        }

        System.out.println("Number of cars sold in " + month + "-" + year + ": " + numberOfCar);
    }

    public void calculateRevenue(List<Transaction> transactions, String period, String month, String day, String year) {
        double totalRevenue = 0;
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (transactionDate.isEqual(startDate)) {
                        totalRevenue += transaction.getTotalAmount();
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                            (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                        totalRevenue += transaction.getTotalAmount();
                    }
                    break;

                case "month":
                    if (transactionDate.getYear() == startDate.getYear() &&
                            transactionDate.getMonthValue() == startDate.getMonthValue()) {
                        totalRevenue += transaction.getTotalAmount();
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        String revenueFormatted = String.format("%.0f", totalRevenue);
        System.out.println("Total revenue in a "+ period+ " of "+ day+ ", "+ month + ", "+ year+ " is: "+ revenueFormatted);
    }

    public void calculateRevenueForMechanic(List<Service> services, String mechanicID) {
        double revenue = 0;
        for(Service service : services) {
            if(service.getMechanicID().equals(mechanicID)) {
                revenue += service.getCost();
            }
        }
        String revenueFormatted = String.format("%.0f", revenue);
        System.out.print("Revenue for "+mechanicID+" is: " + revenueFormatted);
    }

    public void calculateRevenueForSalesperson(List<Transaction> transactions, String salespersonID) {
        double revenue = 0;
        for (Transaction transaction : transactions) {
            if(transaction.getSalespersonID().equals(salespersonID)) {
                revenue += transaction.getTotalAmount();
            }
        }
        String revenueFormatted = String.format("%.0f", revenue);
        System.out.print("Revenue for "+salespersonID+" is: " + revenueFormatted);
    }

    public void printCar(List<Object> purchasedItems){
        for (Object item : purchasedItems) {
            if (item instanceof Car) {
                System.out.println(item);
            }
        }
    }

    public void listCarsSold(List<Transaction> transactions, String period, String month, String day, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue
        System.out.println("\n--- List Cars Sold ---");
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (transactionDate.isEqual(startDate)) {
                        printCar(transaction.getPurchasedItems());
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                            (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                        printCar(transaction.getPurchasedItems());
                    }
                    break;

                case "month":
                    if (transactionDate.getYear() == startDate.getYear() &&
                            transactionDate.getMonthValue() == startDate.getMonthValue()) {
                        printCar(transaction.getPurchasedItems());
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        System.out.println("----------------------");
    }

    public void listTransactions(List<Transaction> transactions, String period, String month, String day, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue
        System.out.println("\n--- List Of Transaction ---");
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (transactionDate.isEqual(startDate)) {
                        System.out.println(transaction);
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                            (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                        System.out.println(transaction);
                    }
                    break;

                case "month":
                    if (transactionDate.getYear() == startDate.getYear() &&
                            transactionDate.getMonthValue() == startDate.getMonthValue()) {
                        System.out.println(transaction);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        System.out.println("--------------------------");
    }

    public void listServices(List<Service> services, String period, String month, String day, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue
        System.out.println("\n--- List Of Services ---");
        for (Service service : services) {
            LocalDate serviceDate = service.getServiceDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (serviceDate.isEqual(startDate)) {
                        System.out.println(service);
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((serviceDate.isEqual(startDate) || serviceDate.isAfter(startDate)) &&
                            (serviceDate.isEqual(endOfWeek) || serviceDate.isBefore(endOfWeek))) {
                        System.out.println(service);
                    }
                    break;

                case "month":
                    if (serviceDate.getYear() == startDate.getYear() &&
                            serviceDate.getMonthValue() == startDate.getMonthValue()) {
                        System.out.println(service);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        System.out.println("-----------------------");
    }

    public void printAutoPart(List<Object> purchasedItems){
        for (Object item : purchasedItems) {
            if (item instanceof AutoPart) {
                System.out.println(item);
            }
        }
    }

    public void listAutoPartsSold(List<Transaction> transactions, String period, String month, String day, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue
        System.out.println("\n--- List Auto Parts Sold ---");
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (transactionDate.isEqual(startDate)) {
                        printAutoPart(transaction.getPurchasedItems());
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                            (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                        printAutoPart(transaction.getPurchasedItems());
                    }
                    break;

                case "month":
                    if (transactionDate.getYear() == startDate.getYear() &&
                            transactionDate.getMonthValue() == startDate.getMonthValue()) {
                        printAutoPart(transaction.getPurchasedItems());
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        System.out.println("----------------------------");
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
}

class Mechanic extends User {

    public Mechanic(String userID, String fullName, String username, String password) {
        super(userID, fullName, "Mechanic", username, password);
    }

    public void calculateRevenue(List<Transaction> transactions, String period, String month, String day, String year) {
        double totalRevenue = 0;
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (transactionDate.isEqual(startDate)) {
                        totalRevenue += transaction.getTotalAmount();
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                            (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                        totalRevenue += transaction.getTotalAmount();
                    }
                    break;

                case "month":
                    if (transactionDate.getYear() == startDate.getYear() &&
                            transactionDate.getMonthValue() == startDate.getMonthValue()) {
                        totalRevenue += transaction.getTotalAmount();
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        String revenueFormatted = String.format("%.0f", totalRevenue);
        System.out.println("Total revenue in a "+ period+ " of "+ day+ ", "+ month + ", "+ year+ " is: "+ revenueFormatted);
    }

    public void listCarsOrServices(List<Car> cars, List<Service> services, String period) {

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