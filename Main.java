import AutoPart.AutoPart;
import Car.Car;
import FileHandler.FileManager;
import Screen.MainMenu;
import Screen.WelcomeScreen;
import Service.Service;
import Transaction.Transaction;
import User.User;
import User.UserManager;
import java.util.*;

public class Main {
    private static List<User> users = new ArrayList<>();
    private static List<Car> cars = new ArrayList<>();
    private static List<Service> services = new ArrayList<>();
    private static List<AutoPart> parts = new ArrayList<>();
    private static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        WelcomeScreen.display();

        // Load data from CSV files
        cars = FileManager.readCars("Database/cars.csv");
        parts = FileManager.readParts("Database/parts.csv");
        services = FileManager.readServices("Database/services.csv", parts);
        transactions = FileManager.readTransactions("Database/transactions.csv", cars, parts);
        users = FileManager.readUsers("Database/users.csv");

        Scanner scanner = new Scanner(System.in);

        // User.User login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = UserManager.authenticateUser(username, password, users);

        if (loggedInUser != null) {
            if (!loggedInUser.isActive()) {
                System.out.println("The account has been deactivated, contact our hotline for support");
            } else {
                System.out.println("\nLogin successful! " + loggedInUser.getFullName() + " (" + loggedInUser.getUserType() + ")");
//                loggedInUser.displayMenu(scanner, cars, parts, services, transactions, users);
                MainMenu.displayMenu(scanner, cars, parts, services, transactions, users, loggedInUser);
                // Save data to CSV files only if the logged in
                FileManager.writeCars(cars, "Database/cars.csv");
                FileManager.writeParts(parts, "Database/parts.csv");
                FileManager.writeServices(services, "Database/services.csv");
                FileManager.writeTransactions(transactions, "Database/transactions.csv");
                FileManager.writeUsers(users, "Database/users.csv");
            }
        } else {
            System.out.println("Invalid username or password");
        }

        scanner.close();
    }
}