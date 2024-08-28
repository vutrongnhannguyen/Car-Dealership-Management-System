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
        cars = FileManager.readCars("D:/programming1/asm3/cars.csv");
        parts = FileManager.readParts("D:/programming1/asm3/parts.csv");
        services = FileManager.readServices("D:/programming1/asm3/services.csv", parts);
        transactions = FileManager.readTransactions("D:/programming1/asm3/transactions.csv", cars, parts);
        users = FileManager.readUsers("D:/programming1/asm3/users.csv");

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
                FileManager.writeCars(cars, "D:/programming1/asm3/cars.csv");
                FileManager.writeParts(parts, "D:/programming1/asm3/parts.csv");
                FileManager.writeServices(services, "D:/programming1/asm3/services.csv");
                FileManager.writeTransactions(transactions, "D:/programming1/asm3/transactions.csv");
                FileManager.writeUsers(users, "D:/programming1/asm3/users.csv");
            }
        } else {
            System.out.println("Invalid username or password");
        }

        scanner.close();
    }
}