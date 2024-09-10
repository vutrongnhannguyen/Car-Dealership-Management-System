package FileHandler;

import AutoPart.AutoPart;
import Car.Car;
import Service.Service;
import Transaction.Transaction;
import User.User;
import User.Manager;
import User.Salesperson;
import User.Client;
import User.Mechanic;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static List<Car> readCars(String filePath) {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                List<String> serviceHistory = new ArrayList<>(List.of(data[8].replace("\"", "").split("\\s*,\\s*")));

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

    public static List<AutoPart> readParts(String filePath) {
        List<AutoPart> parts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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

    public static List<Service> readServices(String filePath, List<AutoPart> parts) {
        List<Service> services = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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

    public static List<Transaction> readTransactions(String filePath, List<Car> cars, List<AutoPart> parts) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                List<Object> purchasedItems = new ArrayList<>();
                // In testing process, data in the array duplicate the quote everytime a new transaction is added
                // data[4].replace("\"", "") to ensure that there is no duplicate
                for (String item : data[4].replace("\"", "").split("\\s*,\\s*")) {
                    if (item.startsWith("c-")) { // If the item checked using c- format
                        for (Car car : cars) {
                            if (car.getCarID().equals(item)) {
                                purchasedItems.add(car);
                                break;
                            }
                        }
                    } else if (item.startsWith("p-")) { // If the item checked using p- format
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

    public static List<User> readUsers(String filePath) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                User user;
                switch (data[2]) { // userType
                    case "Manager":
                        user = new Manager(data[0], data[1], data[3], data[4]);
                        break;
                    case "Salesperson":
                        user = new Salesperson(data[0], data[1], data[2], data[3], data[4]);
                        break;
                    case "Mechanic":
                        user = new Mechanic(data[0], data[1], data[2], data[3], data[4]);
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

    public static void writeCars(List<Car> cars, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
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
                        + "\"" + serviceHistoryString + "\"," // Have to add double quotes to this, ran into an issue that cause the list disappear when performing another function, problem is somehow solved after adding the quote
                        + car.getNotes());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeParts(List<AutoPart> parts, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
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

    public static void writeServices(List<Service> services, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
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

    public static void writeTransactions(List<Transaction> transactions, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
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

    public static void writeUsers(List<User> users, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
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
}