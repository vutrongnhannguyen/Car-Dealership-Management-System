import java.util.List;
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

    @Override
    public String toString() {
        return "User ID: " + userID + "\nName: " + fullName + "\nUser Type: " + userType + "\nStatus: " + (isActive ? "Active" : "Inactive");
    }
}

class Manager extends User {
    public Manager(String userID, String fullName, String username, String password) {
        super(userID, fullName, "Manager", username, password);
    }

    // CRUD Operations for Cars
    public void addCar(List<Car> cars, Car car) {
        cars.add(car);
        System.out.println("Car added successfully.");
    }

    public void removeCar(List<Car> cars, String carID) {
        cars.removeIf(car -> car.getCarID().equals(carID));
        System.out.println("Car removed successfully.");
    }

    // CRUD Operations for Auto Parts
    public void addPart(List<AutoPart> parts, AutoPart part) {
        parts.add(part);
        System.out.println("Part added successfully.");
    }

    public void removePart(List<AutoPart> parts, String partID) {
        parts.removeIf(part -> part.getPartID().equals(partID));
        System.out.println("Part removed successfully.");
    }

    // CRUD Operations for Services
    public void addService(List<Service> services, Service service) {
        services.add(service);
        System.out.println("Service added successfully.");
    }

    public void removeService(List<Service> services, String serviceID) {
        services.removeIf(service -> service.getServiceID().equals(serviceID));
        System.out.println("Service removed successfully.");
    }

    // CRUD Operations for Transactions
    public void addTransaction(List<Transaction> transactions, Transaction transaction) {
        transactions.add(transaction);
        System.out.println("Transaction added successfully.");
    }

    public void removeTransaction(List<Transaction> transactions, String transactionID) {
        transactions.removeIf(transaction -> transaction.getTransactionID().equals(transactionID));
        System.out.println("Transaction removed successfully.");
    }

    // CRUD Operations for Users
    public void removeUser(List<User> users, String userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                user.setActive(false);
                System.out.println("User deactivated successfully.");
                return;
            }
        }
        System.out.println("User not found.");
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
}

class Mechanic extends User {

    public Mechanic(String userID, String fullName, String username, String password) {
        super(userID, fullName, "Mechanic", username, password);
    }

    public void calculateRevenue(List<Transaction> transactions, String period) {

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
