import java.time.LocalDate;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static List<User> users = new ArrayList<>();
    private static List<Car> cars = new ArrayList<>();
    private static List<Service> services = new ArrayList<>();
    private static List<AutoPart> parts = new ArrayList<>();
    private static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        WelcomeScreen.display();

        // Load data from CSV files
        cars = FileManager.readCars("cars.csv");
        parts = FileManager.readParts("parts.csv");
        services = FileManager.readServices("services.csv", parts);
        transactions = FileManager.readTransactions("transactions.csv", cars, parts);
        users = FileManager.readUsers("users.csv");

        Scanner scanner = new Scanner(System.in);

        // User login
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
                loggedInUser.displayMenu(scanner, cars, parts, services, transactions, users);

                // Save data to CSV files only if the logged in
                FileManager.writeCars(cars, "cars.csv");
                FileManager.writeParts(parts, "parts.csv");
                FileManager.writeServices(services, "services.csv");
                FileManager.writeTransactions(transactions, "transactions.csv");
                FileManager.writeUsers(users, "users.csv");
            }
        } else {
            System.out.println("Invalid username or password");
        }

        scanner.close();
    }
}