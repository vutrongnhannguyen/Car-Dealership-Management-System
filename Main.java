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
        System.out.println("--------------------------");
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("AUTO168 CAR DEALERSHIP MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Mr. Dung Nguyen");
        System.out.println("Group: Team 4");
        System.out.println("s, Chung Chi Vi");
        System.out.println("s3864111, Duong Hoang Anh Khoa");
        System.out.println("s, Nguyen Vu Trong Nhan");
        System.out.println("--------------------------");

        // Load data from CSV files
        cars = readCars("D:/programming1/asm3/cars.csv");
        parts = readParts("D:/programming1/asm3/parts.csv");
        services = readServices("D:/programming1/asm3/services.csv");
        transactions = readTransactions("D:/programming1/asm3/transactions.csv");
        users = readUsers("D:/programming1/asm3/users.csv");

        Scanner scanner = new Scanner(System.in);

        // User login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = authenticateUser(username, password);

        // Check username and password
        if (loggedInUser != null) {
            System.out.println("\nLogin successful! " + loggedInUser.getFullName() + " (" + loggedInUser.getUserType() + ")");
            displayMenu(loggedInUser, scanner);
        } else {
            System.out.println("Invalid username or password");
        }

        scanner.close();

        // Save data to CSV files
        writeCars(cars, "D:/programming1/asm3/cars.csv");
        writeParts(parts, "D:/programming1/asm3/parts.csv");
        writeServices(services, "D:/programming1/asm3/services.csv");
        writeTransactions(transactions, "D:/programming1/asm3/transactions.csv");
        writeUsers(users, "D:/programming1/asm3/users.csv");
    }

    private static User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.login(username, password)) {
                return user;
            }
        }
        return null;
    }

    private static void displayMenu(User user, Scanner scanner) {
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. View Profile");
            System.out.println("2. Logout");

            if (user.getUserType().equals("Manager")) {
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
            } else if (user.getUserType().equals("Employee")) {
                System.out.println("3. Calculate Revenue");
                System.out.println("4. List Cars or Services");

                /* Basically, do not need to handle Client type again because we already implemented the default
                interface in User.java*/
            } else if (user.getUserType().equals("Client")) {
                System.out.println("3. View Membership Status");
                System.out.println("4. View Service History");
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
                    System.out.println("Logging out...");
                    choice = 0; // Exit loop
                    break;
                case 3:
                    if (user.getUserType().equals("Manager")) {
                        addCar(scanner, (Manager) user);
                    } else if (user.getUserType().equals("Employee")) {
                        System.out.print("Enter day/week/month to calculate revenue: ");
                        String period = scanner.nextLine();
                        ((Employee) user).calculateRevenue(transactions, period);
                    } else if (user.getUserType().equals("Client")) {
                        ((Client) user).viewMembershipStatus();
                    }
                    break;
                case 4:
                    if (user.getUserType().equals("Manager")) {
                        removeCar(scanner, (Manager) user);
                    } else if (user.getUserType().equals("Employee")) {
                        System.out.print("Enter day/week/month to list cars or services: ");
                        String period = scanner.nextLine();
                        ((Employee) user).listCarsOrServices(cars, services, period);
                    } else if (user.getUserType().equals("Client")) {
                        viewServiceHistory(user.getUserID());
                    }
                    break;
                case 5:
                    if (user.getUserType().equals("Manager")) {
                        addPart(scanner, (Manager) user);
                    }
                    break;
                case 6:
                    if (user.getUserType().equals("Manager")) {
                        removePart(scanner, (Manager) user);
                    }
                    break;
                case 7:
                    if (user.getUserType().equals("Manager")) {
                        addService(scanner, (Manager) user);
                    }
                    break;
                case 8:
                    if (user.getUserType().equals("Manager")) {
                        removeService(scanner, (Manager) user);
                    }
                    break;
                case 9:
                    if (user.getUserType().equals("Manager")) {
                        addTransaction(scanner, (Manager) user);
                    }
                    break;
                case 10:
                    if (user.getUserType().equals("Manager")) {
                        removeTransaction(scanner, (Manager) user);
                    }
                    break;
                case 11:
                    if (user.getUserType().equals("Manager")) {
                        removeUser(scanner, (Manager) user);
                    }
                    break;
                case 12:
                    if (user.getUserType().equals("Manager")) {
                        ((Manager) user).viewEntities(cars, parts, services, transactions);
                    }
                    break;
                case 13:
                    System.out.print("Enter month and year (MM YYYY) to calculate cars sold in month: ");
                    String[] input = scanner.nextLine().split(" ");

                    break;
                case 14:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter day/week/month to calculate revenue: ");
                        String period = scanner.nextLine();
                        ((Manager) user).calculateRevenue(transactions, period);
                    }
                    break;
                case 15:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter mechanic ID and day/week/month to calculate revenue for Mechanic: ");
                    }
                    break;
                case 16:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter salesperson ID and day/week/month to calculate revenue for salesperson: ");
                    }
                    break;
                case 17:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter day/week/month to list car sold: ");
                    }
                    break;
                case 18:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter period day/week/month to list transaction: ");

                    }
                    break;
                case 19:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter period day/week/month to list services: ");
                    }
                    break;
                case 20:
                    if (user.getUserType().equals("Manager")) {
                        System.out.print("Enter period day/week/month to list Auto Parts sold: ");
                    }
                    break;
                default:
                    System.out.println("We don't have that option");
            }
        }
    }

    private static void addCar(Scanner scanner, Manager manager) {
        System.out.print("Enter car details (ID, Make, Model, Year, Mileage, Color, Status, Price): ");
        String carDetails = scanner.nextLine();
        String[] carData = carDetails.split(", ");

        Car newCar = new Car(
                carData[0],
                carData[1],
                carData[2],
                Integer.parseInt(carData[3]),
                Integer.parseInt(carData[4]),
                carData[5],
                carData[6],
                Double.parseDouble(carData[7]),
                new ArrayList<>());

        // Add the new car
        manager.addCar(cars, newCar);

        // Sort the cars by their ID
        cars.sort(Comparator.comparing(Car::getCarID));

        // Write the sorted cars back to the CSV
        writeCars(cars, "D:/programming1/asm3/cars.csv");
    }

    private static void removeCar(Scanner scanner, Manager manager) {
        System.out.print("Enter Car ID to remove: ");
        String carID = scanner.nextLine();
        manager.removeCar(cars, carID);
        writeCars(cars, "D:/programming1/asm3/cars.csv");
    }

    private static void addPart(Scanner scanner, Manager manager) {
        System.out.print("Enter part details (ID, Name, Manufacturer, Part Number, Condition, Warranty, Cost, Notes): ");
        String partDetails = scanner.nextLine();
        String[] partData = partDetails.split(", ");

        AutoPart newPart = new AutoPart(
                partData[0],
                partData[1],
                partData[2],
                partData[3],
                partData[4],
                partData[5],
                Double.parseDouble(partData[6]),
                partData[7]);

        // Add the new part
        manager.addPart(parts, newPart);

        // Sort the parts by their ID
        parts.sort(Comparator.comparing(AutoPart::getPartID));

        // Write the sorted parts back to the CSV
        writeParts(parts, "D:/programming1/asm3/parts.csv");
    }

    private static void removePart(Scanner scanner, Manager manager) {
        System.out.print("Enter Part ID to remove: ");
        String partID = scanner.nextLine();
        manager.removePart(parts, partID);
        writeParts(parts, "D:/programming1/asm3/parts.csv");
    }

    private static void addService(Scanner scanner, Manager manager) {
        System.out.print("Enter service details (ID, Date (yyyy-mm-dd), Client ID, Mechanic ID, Service Type, Cost, Notes): ");
        String serviceDetails = scanner.nextLine();
        String[] serviceData = serviceDetails.split(", ");

        // Use coma to separate each replaced parts in case there is more than 1 part
        System.out.print("Enter replaced parts: ");
        String partsInput = scanner.nextLine();

        List<AutoPart> replacedParts = new ArrayList<>();
        for (String partID : partsInput.replace("\"", "").split("\\s*,\\s*")) {
            for (AutoPart part : parts) {
                if (part.getPartID().equals(partID)) {
                    replacedParts.add(part);
                    break;
                }
            }
        }

        double serviceCost = Double.parseDouble(serviceData[5].trim());
        Service newService = new Service(
                serviceData[0],
                LocalDate.parse(serviceData[1]),
                serviceData[2],
                serviceData[3],
                serviceData[4],
                replacedParts,
                serviceCost,
                serviceData[6]);

        // Add the new service
        manager.addService(services, newService);

        // Sort the services by the ID
        services.sort(Comparator.comparing(Service::getServiceID));

        // Write the sorted services back to the CSV
        writeServices(services, "D:/programming1/asm3/services.csv");
    }

    private static void removeService(Scanner scanner, Manager manager) {
        System.out.print("Enter Service ID to remove: ");
        String serviceID = scanner.nextLine();
        manager.removeService(services, serviceID);
        writeServices(services, "D:/programming1/asm3/services.csv");
    }

    private static void addTransaction(Scanner scanner, Manager manager) {
        System.out.print("Enter transaction details (ID, Date (yyyy-mm-dd), Client ID, Salesperson ID, Discount, Total Amount, Notes): ");
        String transactionDetails = scanner.nextLine();
        String[] transactionData = transactionDetails.split(", ");

        // Use coma to separate each purchased items in case there is more than 1 item
        System.out.print("Enter purchased items: ");
        String purchasedItemsInput = scanner.nextLine();

        // Parse purchased items
        List<Object> purchasedItems = new ArrayList<>();
        for (String itemID : purchasedItemsInput.split("\\s*,\\s*")) {
            if (itemID.startsWith("c-")) {
                for (Car car : cars) {
                    if (car.getCarID().equals(itemID)) {
                        purchasedItems.add(car);
                        break;
                    }
                }
            } else if (itemID.startsWith("p-")) {
                for (AutoPart part : parts) {
                    if (part.getPartID().equals(itemID)) {
                        purchasedItems.add(part);
                        break;
                    }
                }
            }
        }

        for (Object item : purchasedItems) {
            if (item instanceof Car) {
                Car car = (Car) item;
                car.setStatus("Sold"); // Update the car status to "sold" if it is still "available"
            }
        }

        double discount = Double.parseDouble(transactionData[4].trim());
        double totalAmount = Double.parseDouble(transactionData[5].trim());

        Transaction newTransaction = new Transaction(
                transactionData[0],
                LocalDate.parse(transactionData[1]),
                transactionData[2],
                transactionData[3],
                purchasedItems,
                discount,
                totalAmount,
                transactionData[6]
        );

        // Add the new transaction
        manager.addTransaction(transactions, newTransaction);

        // Sort the transactions by  ID
        transactions.sort(Comparator.comparing(Transaction::getTransactionID));

        // Write the sorted transactions back to the CSV
        writeTransactions(transactions, "D:/programming1/asm3/transactions.csv");

        System.out.println("Transaction added successfully.");
    }

    private static void removeTransaction(Scanner scanner, Manager manager) {
        System.out.print("Enter Transaction ID to remove: ");
        String transactionID = scanner.nextLine();

        // Remove the transaction from the list
        manager.removeTransaction(transactions, transactionID);

        // Sort the transactions before writing back
        transactions.sort(Comparator.comparing(Transaction::getTransactionID));

        // Write the updated transactions back to the CSV
        writeTransactions(transactions, "D:/programming1/asm3/transactions.csv");
    }

    private static void removeUser(Scanner scanner, Manager manager) {
        System.out.print("Enter User ID to remove (soft delete): ");
        String userID = scanner.nextLine();
        manager.removeUser(users, userID);
        writeUsers(users, "D:/programming1/asm3/users.csv");
    }

    // Read CSV files
    private static List<Car> readCars(String fileName) {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Ensure that all data filled
                if (data.length < 8) {
                    System.out.println("Missing 1 data: " + line);
                    continue;
                }
                // Parse the car data
                Car car = new Car(data[0], data[1], data[2], Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]), data[5], data[6], Double.parseDouble(data[7]), new ArrayList<>());
                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    private static List<AutoPart> readParts(String fileName) {
        List<AutoPart> parts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Ensure that all data filled
                if (data.length < 8) {
                    System.out.println("Missing 1 data: " + line);
                    continue;
                }
                // Parse the part data
                AutoPart part = new AutoPart(data[0], data[1], data[2], data[3], data[4], data[5], Double.parseDouble(data[6]), data[7]);
                parts.add(part);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parts;
    }

    private static List<Service> readServices(String fileName) {
        List<Service> services = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // User can only use coma to separate the note, can not use other symbols
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Parse the list of replaced parts correctly
                List<AutoPart> replacedParts = new ArrayList<>();
                for (String partID : data[5].replace("\"", "").split("\\s*,\\s*")) { // Remove quotes around part IDs
                    for (AutoPart part : parts) {  // 'parts' should be your existing list of AutoPart objects
                        if (part.getPartID().equals(partID)) {
                            replacedParts.add(part);
                            break;
                        }
                    }
                }

                // Parse the service cost
                double serviceCost = Double.parseDouble(data[6].trim());

                // Parse the service data
                Service service = new Service(data[0], LocalDate.parse(data[1]), data[2], data[3], data[4], replacedParts, serviceCost, data[7]);
                services.add(service);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return services;
    }

    private static List<Transaction> readTransactions(String fileName) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Use regex to split on commas that are not within quotes
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Parse the purchased items correctly
                List<Object> purchasedItems = new ArrayList<>();
                for (String item : data[4].replace("\"", "").split("\\s*,\\s*")) { // Remove quotes around the list of items
                    if (item.startsWith("c-")) {
                        for (Car car : cars) {
                            if (car.getCarID().equals(item)) {
                                purchasedItems.add(car);
                                break;
                            }
                        }
                    } else if (item.startsWith("p-")) {
                        for (AutoPart part : parts) {
                            if (part.getPartID().equals(item)) {
                                purchasedItems.add(part);
                                break;
                            }
                        }
                    }
                }

                // Parse the discount and total amount as doubles
                double discount = Double.parseDouble(data[5].trim());
                double totalAmount = Double.parseDouble(data[6].trim());

                // Create the Transaction object
                Transaction transaction = new Transaction(data[0], LocalDate.parse(data[1]), data[2], data[3], purchasedItems, discount, totalAmount, data[7]);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private static List<User> readUsers(String fileName) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                User user;
                switch (data[2]) { // userType
                    case "Manager":
                        user = new Manager(data[0], data[1], data[3], data[4]);
                        break;
                    case "Employee":
                        user = new Employee(data[0], data[1], data[3], data[4]);
                        break;
                    case "Client":
                        user = new Client(data[0], data[1], data[3], data[4]);
                        break;
                    default:
                        continue;
                }
                user.setActive(Boolean.parseBoolean(data[5]));
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Write to CSV files
    private static void writeCars(List<Car> cars, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Car car : cars) {
                bw.write(car.getCarID() + "," + car.getMake() + "," + car.getModel() + "," + car.getYear() + ","
                        + car.getMileage() + "," + car.getColor() + "," + car.getStatus() + "," + car.getPrice() + ","
                        + (car.getSoldDate() != null ? car.getSoldDate() : "null") + "," + car.isActive());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeParts(List<AutoPart> parts, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (AutoPart part : parts) {
                bw.write(part.getPartID() + "," + part.getName() + "," + part.getManufacturer() + "," + part.getPartNumber() + ","
                        + part.getCondition() + "," + part.getWarranty() + "," + part.getCost() + "," + part.getNotes() + ","
                        + part.isActive());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeServices(List<Service> services, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Service service : services) {

                String replacedPartsString = "\"" + service.getReplacedParts().stream()
                        .map(AutoPart::getPartID)
                        .reduce((part1, part2) -> part1 + "," + part2)
                        .orElse("") + "\""; // Enclose the replaced parts list in quotes


                bw.write(service.getServiceID() + "," + service.getServiceDate() + "," + service.getClientID() + ","
                        + service.getMechanicID() + "," + service.getServiceType() + "," + replacedPartsString + ","
                        + service.getCost() + "," + service.getNotes() + "," + service.isActive());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeTransactions(List<Transaction> transactions, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Transaction transaction : transactions) {

                // Serialize the list of purchased items (IDs only)
                String purchasedItemsString = "\"" + transaction.getPurchasedItems().stream()
                        .map(item -> {
                            if (item instanceof Car) {
                                return ((Car) item).getCarID();
                            } else if (item instanceof AutoPart) {
                                return ((AutoPart) item).getPartID();
                            }
                            return "";
                        })
                        .reduce((item1, item2) -> item1 + "," + item2) // Join items with commas
                        .orElse("") + "\""; // Enclose the purchased items list in quotes

                // Prepare the line to be written to the CSV
                String line = transaction.getTransactionID() + ","
                        + transaction.getTransactionDate() + ","
                        + transaction.getClientID() + ","
                        + transaction.getSalespersonID() + ","
                        + purchasedItemsString + ","
                        + transaction.getDiscount() + ","
                        + transaction.getTotalAmount() + ","
                        + transaction.getNotes() + ","  // Note is not enclosed in quotes
                        + transaction.isActive();

                // Write the line to the CSV
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeUsers(List<User> users, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : users) {
                bw.write(user.getUserID() + "," + user.getFullName() + "," + user.getUserType() + "," + user.getUsername() + ","
                        + user.getPassword() + "," + user.isActive());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewServiceHistory(String clientID) {
        System.out.println("\n--- Service History ---");
        boolean hasServices = false;
        for (Service service : services) {
            if (service.getClientID().equals(clientID)) {
                System.out.println(service);
                hasServices = true;
            }
        }
        if (!hasServices) {
            System.out.println("No service history found for this client.");
        }
    }
}