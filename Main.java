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
        cars = readCars();
        parts = readParts();
        services = readServices();
        transactions = readTransactions();
        users = readUsers();

        Scanner scanner = new Scanner(System.in);

        // User login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = authenticateUser(username, password);

        if (loggedInUser != null) {
            if (!loggedInUser.isActive()) {
                System.out.println("The account has been deactivated, contact our hotline for support");
            } else {
                System.out.println("\nLogin successful! " + loggedInUser.getFullName() + " (" + loggedInUser.getUserType() + ")");
                displayMenu(loggedInUser, scanner);

                // Save data to CSV files only if the logged in
                writeCars(cars);
                writeParts(parts);
                writeServices(services);
                writeTransactions(transactions);
                writeUsers(users);
            }
        } else {
            System.out.println("Invalid username or password");
        }

        scanner.close();
    }

    private static User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
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
                    System.out.println(user);
                    break;
                case 2:
                    System.out.println("Logging out");
                    choice = 0; // Exit loop
                    break;
                case 3:
                    switch (user.getUserType()) {
                        case "Manager" -> addCar(scanner, (Manager) user);
                        case "Salesperson", "Mechanic" -> {
                            System.out.print("Enter day/week/month to calculate revenue: ");
                            String period = scanner.nextLine();
                            ((Salesperson) user).calculateRevenue(transactions, period);
                        }
                        case "Client" -> ((Client) user).viewMembershipStatus();
                    }
                    break;
                case 4:
                    switch (user.getUserType()) {
                        case "Manager" -> removeCar(scanner, (Manager) user);
                        case "Salesperson", "Mechanic" -> {
                            System.out.print("Enter day/week/month to list cars or services: ");
                            String period = scanner.nextLine();
                            ((Salesperson) user).listCarsOrServices(cars, services, period);
                        }
                        case "Client" -> viewServiceHistory(user.getUserID());
                    }
                    break;
                case 5:
                    if (user.getUserType().equals("Manager")) {
                        addPart(scanner, (Manager) user);
                    } else if (user instanceof Client client) {
                        System.out.print("Enter new full name: ");
                        String newFullName = scanner.nextLine();
                        client.updateFullName(newFullName);
                        writeUsers(users); // Save the updated user list to the CSV file
                    }
                    break;
                case 6:
                    if (user.getUserType().equals("Manager")) {
                        removePart(scanner, (Manager) user);
                    } else if (user instanceof Client client) {
                        System.out.print("Enter new username: ");
                        String newUsername = scanner.nextLine();
                        client.updateUsername(newUsername);
                        writeUsers(users); // Save the updated user list to the CSV file
                    }
                    break;
                case 7:
                    if (user.getUserType().equals("Manager")) {
                        addService(scanner, (Manager) user);
                    } else if (user instanceof Client client) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        client.updatePassword(newPassword);
                        writeUsers(users); // Save the updated user list to the CSV file
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
        System.out.print("Enter car details (Car ID, Make, Model, Year, Mileage, Color, Status, Price, Notes): ");
        String carDetails = scanner.nextLine();
        String[] carData = carDetails.split(", ");

        // Check if the Car ID already exists
        for (Car car : cars) {
            if (car.getCarID().equals(carData[0])) {
                System.out.println("Error: Car ID " + carData[0] + " already exists");
                return;
            }
        }

        System.out.print("Enter service history: ");
        String serviceHistoryInput = scanner.nextLine();

        List<String> serviceHistory = new ArrayList<>(Arrays.asList(serviceHistoryInput.replace("\"", "").split("\\s*,\\s*")));

        Car newCar = new Car(
                carData[0], // carID
                carData[1], // make
                carData[2], // model
                Integer.parseInt(carData[3]), // year
                Integer.parseInt(carData[4]), // mileage
                carData[5], // color
                carData[6], // status
                Double.parseDouble(carData[7].trim()), // price
                serviceHistory, // List of service history
                carData[8] // notes
        );

        // Use the manager to add the car
        manager.addCar(cars, newCar);

        // Sort the cars by their ID
        cars.sort(Comparator.comparing(Car::getCarID));

        // Write the updated cars list back to the cars CSV file
        writeCars(cars);

        System.out.println("Car added successfully");
    }

    private static void removeCar(Scanner scanner, Manager manager) {
        System.out.print("Enter Car ID to remove: ");
        String carID = scanner.nextLine();

        // Check if the Car ID exists
        boolean carExists = false;
        for (Car car : cars) {
            if (car.getCarID().equals(carID)) {
                carExists = true;
                break;
            }
        }

        if (!carExists) {
            System.out.println("Car ID " + carID + " does not exist");
            return;
        }

        // If the Car ID exists continue to remove
        manager.removeCar(cars, carID);
        writeCars(cars);
        System.out.println("Car removed successfully");
    }

    private static void addPart(Scanner scanner, Manager manager) {
        System.out.print("Enter part details (Part ID, Name, Manufacturer, Part Number, Condition, Warranty, Cost, Notes): ");
        String partDetails = scanner.nextLine();
        String[] partData = partDetails.split(", ");

        // Check if the Part ID already exists
        for (AutoPart part : parts) {
            if (part.getPartID().equals(partData[0])) {
                System.out.println("Part ID " + partData[0] + " already exists. Please use a unique Part ID");
                return;
            }
        }

        AutoPart newPart = new AutoPart(
                partData[0], // partID
                partData[1], // name
                partData[2], // manufacturer
                partData[3], // partNumber
                partData[4], // condition
                partData[5], // warranty
                Double.parseDouble(partData[6]), // cost
                partData[7] // notes
        );

        // Add the new part
        manager.addPart(parts, newPart);

        // Sort the parts by their ID
        parts.sort(Comparator.comparing(AutoPart::getPartID));

        // Write the sorted parts back to the CSV
        writeParts(parts);

        System.out.println("Part added successfully");
    }

    private static void removePart(Scanner scanner, Manager manager) {
        System.out.print("Enter Part ID to remove: ");
        String partID = scanner.nextLine();

        // Check if the Part ID exists
        boolean partExists = false;
        for (AutoPart part : parts) {
            if (part.getPartID().equals(partID)) {
                partExists = true;
                break;
            }
        }

        if (!partExists) {
            System.out.println("Error: Part ID " + partID + " does not exist");
            return;
        }

        manager.removePart(parts, partID);

        parts.sort(Comparator.comparing(AutoPart::getPartID));

        writeParts(parts);

        System.out.println("Part removed successfully!");
    }

    private static void addService(Scanner scanner, Manager manager) {
        System.out.print("Enter service details (ID, Date (yyyy-mm-dd), Client ID, Mechanic ID, Service Type, Cost, Notes): ");
        String serviceDetails = scanner.nextLine();
        String[] serviceData = serviceDetails.split(", ");

        // Check if the Service ID already exists
        for (Service service : services) {
            if (service.getServiceID().equals(serviceData[0])) {
                System.out.println("Service ID " + serviceData[0] + " already exists");
                return;
            }
        }

        System.out.print("Enter replaced parts: ");
        String partsInput = scanner.nextLine();

        List<AutoPart> replacedParts = new ArrayList<>();
        boolean invalidPartFound = false;

        // Handle replaced parts stored as a list
        for (String partID : partsInput.split("\\s*,\\s*")) {
            boolean partFound = false;
            for (AutoPart part : parts) {
                if (part.getPartID().equals(partID)) {
                    replacedParts.add(part);
                    partFound = true;
                    break;
                }
            }
            if (!partFound) {
                System.out.println("Part ID " + partID + " does not exist");
                invalidPartFound = true;
            }
        }

        // If any invalid parts were found, stop the process
        if (invalidPartFound) {
            System.out.println("Invalid part IDs");
            return;
        }

        // Check if the user's account is still active or existing
        Client client = (Client) users.stream()
                .filter(user -> user.getUserID().equals(serviceData[2]) && user instanceof Client)
                .findFirst()
                .orElse(null);

        if (client == null) {
            System.out.println("Client not found");
            return;
        }

        if (!client.isActive()) {
            System.out.println("Client is currently set to inactive and can not request any services");
            return;
        }

        // Check if employee's account is still active or existing
        Mechanic mechanic = (Mechanic) users.stream()
                .filter(user -> user.getUserID().equals(serviceData[3]) && user instanceof Mechanic)
                .findFirst()
                .orElse(null);

        if (mechanic == null) {
            System.out.println("There is no Mechanic has this user ID");
            return;
        }

        if (!mechanic.isActive()) {
            System.out.println("Mechanic is currently set to inactive");
            return;
        }

        double serviceCost = Double.parseDouble(serviceData[5]); // Original service cost

        double discount = 0.0;
        if (client != null) {
            double discountRate = client.getDiscountRate();
            discount = serviceCost * discountRate; // Calculate discount
            serviceCost = serviceCost - discount; // Apply the discount
            client.updateTotalSpending(serviceCost); // Update the client’s total spending
        }

        // Create the service object
        Service newService = new Service(
                serviceData[0], // serviceID
                LocalDate.parse(serviceData[1]), // serviceDate
                serviceData[2], // clientID
                serviceData[3], // mechanicID
                serviceData[4], // serviceType
                replacedParts, // List of replaced parts
                serviceCost, // serviceCost after applying membership discount
                serviceData[6] // notes
        );

        manager.addService(services, newService);

        services.sort(Comparator.comparing(Service::getServiceID));

        writeUsers(users);

        System.out.println("Applied discount: " + discount);

        System.out.println("Service added successfully");
    }

    private static void removeService(Scanner scanner, Manager manager) {
        System.out.print("Enter Service ID to remove: ");
        String serviceID = scanner.nextLine();

        // Check if the Service ID exists
        boolean serviceExists = false;
        for (Service service : services) {
            if (service.getServiceID().equals(serviceID)) {
                serviceExists = true;
                break;
            }
        }

        if (!serviceExists) {
            System.out.println("Service ID " + serviceID + " does not exist");
            return;
        }


        manager.removeService(services, serviceID);
        services.sort(Comparator.comparing(Service::getServiceID));
        writeServices(services);
        System.out.println("Service removed successfully");
    }

    private static void addTransaction(Scanner scanner, Manager manager) {
        System.out.print("Enter transaction details (Transaction ID, Date (yyyy-mm-dd), Client ID, Salesperson ID, Total Amount, Notes): ");
        String transactionDetails = scanner.nextLine();
        String[] transactionData = transactionDetails.split(", ");

        // Check if the Transaction ID already exists
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
                    System.out.println("Car ID " + itemID + " does not exist.");
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
                    System.out.println("Part ID " + itemID + " does not exist.");
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
            System.out.println("Client not found.");
            return;
        }

        if (!client.isActive()) {
            System.out.println("Client is currently set to inactive and cannot make purchases.");
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

        // Add the new transaction
        manager.addTransaction(transactions, newTransaction);

        // Sort the transactions before writing back
        transactions.sort(Comparator.comparing(Transaction::getTransactionID));

        // Save updated users list to the CSV
        writeUsers(users);

        // Update car status
        boolean carStatusChanged = false;
        for (Object item : purchasedItems) {
            if (item instanceof Car) {
                Car car = (Car) item;
                if (car.getStatus().equalsIgnoreCase("available")) {
                    car.setStatus("Sold");
                    carStatusChanged = true;
                }
            }
        }

        // If any car status was changed, write the updated cars list to the CSV
        if (carStatusChanged) {
            writeCars(cars);
        }

        System.out.println("Applied discount: " + discount);
        System.out.println("Total amount after discount: " + totalAmount);
        System.out.println("Transaction added successfully");
    }

    private static void removeTransaction(Scanner scanner, Manager manager) {
        System.out.print("Enter Transaction ID to remove: ");
        String transactionID = scanner.nextLine();

        // Check if the Transaction ID exists
        boolean transactionExists = false;
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().equals(transactionID)) {
                transactionExists = true;
                break;
            }
        }

        if (!transactionExists) {
            System.out.println("Transaction ID " + transactionID + " does not exist");
            return; // Stop the process if the Transaction ID does not exist
        }

        // Remove the transaction from the list
        manager.removeTransaction(transactions, transactionID);

        // Sort the transactions before writing back
        transactions.sort(Comparator.comparing(Transaction::getTransactionID));

        // Write the updated transactions back to the CSV
        writeTransactions(transactions);

        System.out.println("Transaction with ID " + transactionID + " has been successfully removed");
    }

    private static void removeUser(Scanner scanner, Manager manager) {
        System.out.print("Enter User ID to remove: ");
        String userID = scanner.nextLine();

        // Check if the User ID exists
        boolean userExists = false;
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                userExists = true;
                break;
            }
        }

        if (!userExists) {
            System.out.println("User ID " + userID + " does not exist");
            return;
        }


        manager.removeUser(users, userID);

        users.sort(Comparator.comparing(User::getUserID));

        writeUsers(users);

        System.out.println("User with ID " + userID + " has been deactivated successfully");
    }

    // Read CSV files
    private static List<Car> readCars() {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:/programming1/asm3/cars.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                List<String> serviceHistory = new ArrayList<>(Arrays.asList(data[8].replace("\"", "").split("\\s*,\\s*")));

                // Parse the car data
                Car car = new Car(
                        data[0],
                        data[1],
                        data[2],
                        Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]),
                        data[5],
                        data[6],
                        Double.parseDouble(data[7]),
                        serviceHistory,
                        data[9]
                );
                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    private static List<AutoPart> readParts() {
        List<AutoPart> parts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:/programming1/asm3/parts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                AutoPart part = new AutoPart(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5],
                        Double.parseDouble(data[6]),
                        data[7]);
                parts.add(part);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parts;
    }

    private static List<Service> readServices() {
        List<Service> services = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:/programming1/asm3/services.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                List<AutoPart> replacedParts = new ArrayList<>();
                for (String partID : data[5].replace("\"", "").split("\\s*,\\s*")) {
                    for (AutoPart part : parts) {
                        if (part.getPartID().equals(partID)) {
                            replacedParts.add(part);
                            break;
                        }
                    }
                }

                Service service = new Service(
                        data[0],
                        LocalDate.parse(data[1]),
                        data[2],
                        data[3],
                        data[4],
                        replacedParts,
                        Double.parseDouble(data[6]),
                        data[7]);
                services.add(service);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return services;
    }

    private static List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:/programming1/asm3/transactions.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                List<Object> purchasedItems = new ArrayList<>();
                for (String item : data[4].replace("\"", "").split("\\s*,\\s*")) {
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

                Transaction transaction = new Transaction(
                        data[0],
                        LocalDate.parse(data[1]),
                        data[2],
                        data[3],
                        purchasedItems,
                        Double.parseDouble(data[5]),
                        Double.parseDouble(data[6]),
                        data[7]);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:/programming1/asm3/users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                User user;
                switch (data[2]) { // userType
                    case "Manager":
                        user = new Manager(data[0], data[1], data[3], data[4]);
                        break;
                    case "Salesperson":
                        user = new Salesperson(data[0], data[1], data[3], data[4]);
                        break;
                    case "Mechanic":
                        user = new Mechanic(data[0], data[1], data[3], data[4]);
                        break;
                    case "Client":
                        user = new Client(data[0], data[1], data[3], data[4]);
                        ((Client) user).updateTotalSpending(Double.parseDouble(data[7])); // Load total spent
                        ((Client) user).updateMembership(); // Update membership based on total spent
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
    private static void writeCars(List<Car> cars) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/programming1/asm3/cars.csv"))) {
            for (Car car : cars) {

                String serviceHistoryString = String.join(",", car.getServiceHistory());

                bw.write(car.getCarID() + ","
                        + car.getMake() + ","
                        + car.getModel() + ","
                        + car.getYear() + ","
                        + car.getMileage() + ","
                        + car.getColor() + ","
                        + car.getStatus() + ","
                        + car.getPrice() + ","
                        + "\"" + serviceHistoryString + "\"," // Have to add double quotes to this, ran into an issue that cause the list disappear when performing another feature, problem is somehow solved after adding the quote
                        + car.getNotes());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeParts(List<AutoPart> parts) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/programming1/asm3/parts.csv"))) {
            for (AutoPart part : parts) {
                bw.write(part.getPartID() + ","
                        + part.getName() + ","
                        + part.getManufacturer() + ","
                        + part.getPartNumber() + ","
                        + part.getCondition() + ","
                        + part.getWarranty() + ","
                        + part.getCost() + ","
                        + part.getNotes());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeServices(List<Service> services) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/programming1/asm3/services.csv"))) {
            for (Service service : services) {

                String replacedPartsString = service.getReplacedParts().stream()
                        .map(AutoPart::getPartID)
                        .reduce((part1, part2) -> part1 + "," + part2)
                        .orElse("");


                bw.write(service.getServiceID() + ","
                        + service.getServiceDate() + ","
                        + service.getClientID() + ","
                        + service.getMechanicID() + ","
                        + service.getServiceType() + ","
                        + "\"" + replacedPartsString + "\","
                        + service.getCost() + ","
                        + service.getNotes());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeTransactions(List<Transaction> transactions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/programming1/asm3/transactions.csv"))) {
            for (Transaction transaction : transactions) {

                // Serialize the list of purchased items (ID only)
                String purchasedItemsString = transaction.getPurchasedItems().stream()
                        .map(item -> {
                            if (item instanceof Car) {
                                return ((Car) item).getCarID();
                            } else if (item instanceof AutoPart) {
                                return ((AutoPart) item).getPartID();
                            }
                            return "";
                        })
                        .reduce((item1, item2) -> item1 + "," + item2) // Join items with commas
                        .orElse(""); // Default to an empty string if no items

                // Prepare the line to be written to the CSV
                String line = transaction.getTransactionID() + ","
                        + transaction.getTransactionDate() + ","
                        + transaction.getClientID() + ","
                        + transaction.getSalespersonID() + ","
                        + "\"" + purchasedItemsString + "\","
                        + transaction.getDiscount() + ","
                        + transaction.getTotalAmount() + ","
                        + transaction.getNotes();

                // Write the line to the CSV
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/programming1/asm3/users.csv"))) {
            for (User user : users) {
                if (user instanceof Client client) {
                    bw.write(client.getUserID() + ","
                            + client.getFullName() + ","
                            + client.getUserType() + ","
                            + client.getUsername() + ","
                            + client.getPassword() + ","
                            + client.isActive() + ","
                            + client.getMembership() + ","
                            + client.getTotalSpending());
                } else {
                    bw.write(user.getUserID() + ","
                            + user.getFullName() + ","
                            + user.getUserType() + ","
                            + user.getUsername() + ","
                            + user.getPassword() + ","
                            + user.isActive());
                }
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
