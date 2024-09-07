package Car;

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
}