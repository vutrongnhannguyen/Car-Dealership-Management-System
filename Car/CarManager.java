package Car;

import FileHandler.FileManager;

import java.util.*;

public class CarManager {
    public static void addCar(Scanner scanner, List<Car> cars) {
        System.out.print("Enter car details (Car ID, Make, Model, Year, Mileage, Color, Status, Price, Notes): ");
        String carDetails = scanner.nextLine();
        String[] carData = carDetails.split(", ");

        // Check if the Car.Car ID already exists
        for (Car car : cars) {
            if (car.getCarID().equals(carData[0])) {
                System.out.println("Error: Car ID " + carData[0] + " already exists");
                return;
            }
        }

        System.out.print("Enter service history: ");
        String serviceHistoryInput = scanner.nextLine();
        // Remove the " " from the user input
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

        cars.add(newCar);

        // Sort the cars by their ID
        cars.sort(Comparator.comparing(Car::getCarID));

        System.out.println("Car added successfully");
    }

    public static void removeCar(Scanner scanner, List<Car> cars) {
        System.out.print("Enter Car ID to remove: ");
        String carID = scanner.nextLine();

        // Check if the Car.Car ID exists
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

        cars.removeIf(car -> car.getCarID().equals(carID));
        System.out.println("Car removed successfully");
    }

    public static void updateCar(Scanner scanner, List<Car> cars) {
        System.out.print("Enter the Car ID to update: ");
        String carID = scanner.nextLine();

        // Check if the Car ID exists
        Car carToUpdate = null;
        for (Car car : cars) {
            if (car.getCarID().equals(carID)) {
                carToUpdate = car;
                break;
            }
        }

        if (carToUpdate == null) {
            System.out.println("Car ID " + carID + " does not exist");
            return;
        }

        boolean keepUpdating = true;

        while (keepUpdating) {

            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Make");
            System.out.println("2. Model");
            System.out.println("3. Year");
            System.out.println("4. Mileage");
            System.out.println("5. Color");
            System.out.println("6. Service History");
            System.out.println("7. Notes");
            System.out.println("8. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new make: ");
                    String newMake = scanner.nextLine();
                    carToUpdate.setMake(newMake);
                    System.out.println("Make updated successfully");
                }
                case 2 -> {
                    System.out.print("Enter new model: ");
                    String newModel = scanner.nextLine();
                    carToUpdate.setModel(newModel);
                    System.out.println("Model updated successfully");
                }
                case 3 -> {
                    System.out.print("Enter new year: ");
                    int newYear = scanner.nextInt();
                    scanner.nextLine();
                    carToUpdate.setYear(newYear);
                    System.out.println("Year updated successfully");
                }
                case 4 -> {
                    System.out.print("Enter new mileage: ");
                    int newMileage = scanner.nextInt();
                    scanner.nextLine();
                    carToUpdate.setMileage(newMileage);
                    System.out.println("Mileage updated successfully");
                }
                case 5 -> {
                    System.out.print("Enter new color: ");
                    String newColor = scanner.nextLine();
                    carToUpdate.setColor(newColor);
                    System.out.println("Color updated successfully");
                }
                case 6 -> {
                    System.out.print("Enter new service history: ");
                    String serviceHistoryInput = scanner.nextLine();
                    List<String> serviceHistory = new ArrayList<>(Arrays.asList(serviceHistoryInput.split("\\s*,\\s*")));
                    carToUpdate.setServiceHistory(serviceHistory);
                    System.out.println("Service history updated successfully");
                }
                case 7 -> {
                    System.out.print("Enter new notes: ");
                    String newNotes = scanner.nextLine();
                    carToUpdate.setNotes(newNotes);
                    System.out.println("Notes updated successfully");
                }
                case 8 -> {
                    keepUpdating = false;
                    System.out.println("Exiting....");
                }
                default -> System.out.println("Invalid option");
            }
        }

        // Write the updated cars data to the CSV file
        FileManager.writeCars(cars, "Database/cars.csv");
        System.out.println("Car data saved successfully");
    }

    public static void readCarByID(Scanner scanner, List<Car> cars) {
        System.out.print("Enter the Car ID to access: ");
        String carID = scanner.nextLine();

        // Check for car ID
        Car carToRead = null;
        for (Car car : cars) {
            if (car.getCarID().equals(carID)) {
                carToRead = car;
                break;
            }
        }

        if (carToRead == null) {
            System.out.println("Car ID " + carID + " does not exist");
        } else {
            System.out.println(carToRead);
        }
    }
}