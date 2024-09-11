package Transaction;

import AutoPart.AutoPart;
import Car.Car;
import FileHandler.FileManager;
import User.Client;
import User.Salesperson;
import User.User;

import java.time.LocalDate;
import java.util.*;

public class TransactionManager {
    public static void addTransaction(Scanner scanner, List<Transaction> transactions, List<Car> cars, List<AutoPart> parts, List<User> users) {
        System.out.print("Enter transaction details (Transaction ID, Date (yyyy-mm-dd), Client ID, Salesperson ID, Total Amount, Notes): ");
        String transactionDetails = scanner.nextLine();
        String[] transactionData = transactionDetails.split(", ");

        // Check if the Transaction.Transaction ID already exists
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().equals(transactionData[0])) {
                System.out.println("Transaction ID " + transactionData[0] + " already exists");
                return;
            }
        }

        System.out.print("Enter purchased items (Cars/Parts): ");
        String purchasedItemsInput = scanner.nextLine();

        List<Object> purchasedItems = new ArrayList<>();
        boolean invalidItemFound = false;

        // Handle purchased items
        for (String itemID : purchasedItemsInput.split("\\s*,\\s*")) {
            if (itemID.startsWith("c-")) {
                boolean carFound = false;
                for (Car car : cars) {
                    if (car.getCarID().equals(itemID)) {
                        if (car.getStatus().equalsIgnoreCase("Sold")) {
                            System.out.println("Car " + car.getCarID() + " is already sold");
                            return;
                        }
                        purchasedItems.add(car);
                        carFound = true;
                        break;
                    }
                }
                if (!carFound) {
                    System.out.println("Car ID " + itemID + " does not exist");
                    invalidItemFound = true;
                }
            } else if (itemID.startsWith("p-")) {
                boolean partFound = false;
                for (AutoPart part : parts) {
                    if (part.getPartID().equals(itemID)) {
                        purchasedItems.add(part);
                        partFound = true;
                        break;
                    }
                }
                if (!partFound) {
                    System.out.println("Part ID " + itemID + " does not exist");
                    invalidItemFound = true;
                }
            } else {
                System.out.println("Invalid item ID format: " + itemID);
                invalidItemFound = true;
            }
        }

        // If any invalid items were found, stop the process
        if (invalidItemFound) {
            System.out.println("Invalid item ID");
            return;
        }

        // Check if the user's account is still active or existing
        Client client = (Client) users.stream()
                .filter(user -> user.getUserID().equals(transactionData[2]) && user instanceof Client)
                .findFirst()
                .orElse(null);

        if (client == null) {
            System.out.println("Client not found");
            return;
        }

        if (!client.isActive()) {
            System.out.println("Client is currently set to inactive and cannot make purchases");
            return;
        }

        // Check if employee's account is still active or existing
        Salesperson salesperson = (Salesperson) users.stream()
                .filter(user -> user.getUserID().equals(transactionData[3]) && user instanceof Salesperson)
                .findFirst()
                .orElse(null);

        if (salesperson == null) {
            System.out.println("There is no Salesperson with this user ID");
            return;
        }

        if (!salesperson.isActive()) {
            System.out.println("Salesperson is currently set to inactive");
            return;
        }

        double totalAmount = Double.parseDouble(transactionData[4]); // Original total amount

        double discount = 0.0;
        if (client != null) {
            double discountRate = client.getDiscountRate();
            discount = totalAmount * discountRate; // Calculate discount
            totalAmount = totalAmount - discount; // Apply the discount
            client.updateTotalSpending(totalAmount); // Update the client’s total spending
        }

        // Create the transaction object
        Transaction newTransaction = new Transaction(
                transactionData[0], // transactionID
                LocalDate.parse(transactionData[1]), // transactionDate
                transactionData[2], // clientID
                transactionData[3], // salespersonID
                purchasedItems, // List of purchased item ID
                discount, // Automatically calculated discount
                totalAmount, // Total amount after discount
                transactionData[5] // notes
        );

        transactions.add(newTransaction);

        // Sort the transactions before writing back
        transactions.sort(Comparator.comparing(Transaction::getTransactionID));

        System.out.println("Applied discount: " + discount);
        System.out.println("Total amount after discount: " + totalAmount);
        System.out.println("Transaction.Transaction added successfully");

        // Update car status
        for (Object item : purchasedItems) {
            if (item instanceof Car) {
                Car car = (Car) item;
                if (car.getStatus().equalsIgnoreCase("available")) {
                    car.setStatus("Sold");
                }
            }
        }
    }

    public static void removeTransaction(Scanner scanner, List<Transaction> transactions) {
        System.out.print("Enter Transaction.Transaction ID to remove: ");
        String transactionID = scanner.nextLine();

        // Check if the Transaction.Transaction ID exists
        boolean transactionExists = false;
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().equals(transactionID)) {
                transactionExists = true;
                break;
            }
        }

        if (!transactionExists) {
            System.out.println("Transaction.Transaction ID " + transactionID + " does not exist");
            return;
        }

        transactions.removeIf(transaction -> transaction.getTransactionID().equals(transactionID));

        System.out.println("Transaction.Transaction with ID " + transactionID + " has been successfully removed");
    }

    public static void readTransactionByID(Scanner scanner, List<Transaction> transactions) {
        System.out.print("Enter the Transaction ID to access: ");
        String transactionID = scanner.nextLine();

        // Check Transaction ID
        Transaction transactionToRead = null;
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().equals(transactionID)) {
                transactionToRead = transaction;
                break;
            }
        }

        if (transactionToRead == null) {
            System.out.println("Transaction ID " + transactionID + " does not exist");
        } else {
            System.out.println(transactionToRead);
        }
    }
}