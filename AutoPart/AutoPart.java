package AutoPart;

import Car.Car;

import java.util.*;

public class AutoPart {
    private String partID;
    private String name;
    private String manufacturer;
    private String partNumber;
    private String condition; // New, Used, Refurbished
    private String warranty;
    private double cost;
    private String notes;

    public AutoPart(String partID, String name, String manufacturer, String partNumber, String condition, String warranty, double cost, String notes) {
        setPartID(partID);
        this.name = name;
        this.manufacturer = manufacturer;
        this.partNumber = partNumber;
        this.condition = condition;
        this.warranty = warranty;
        this.cost = cost;
        this.notes = notes;
    }

    public String getPartID() {
        return partID;
    }

    public void setPartID(String partID) {
        if (partID.matches("p-\\d+")) {
            this.partID = partID;
        } else {
            throw new IllegalArgumentException("Invalid part ID format");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Part ID: " + partID + ", Name: " + name + ", Manufacturer: " + manufacturer;
    }

    public static class CarManager {
        public static void addCar(Scanner scanner, List<Car> cars) {
            System.out.print("Enter car details (Car.Car ID, Make, Model, Year, Mileage, Color, Status, Price, Notes): ");
            String carDetails = scanner.nextLine();
            String[] carData = carDetails.split(", ");

            // Check if the Car.Car ID already exists
            for (Car car : cars) {
                if (car.getCarID().equals(carData[0])) {
                    System.out.println("Error: Car.Car ID " + carData[0] + " already exists");
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

            cars.add(newCar);

            // Sort the cars by their ID
            cars.sort(Comparator.comparing(Car::getCarID));

            System.out.println("Car.Car added successfully");
        }

        public static void removeCar(Scanner scanner, List<Car> cars) {
            System.out.print("Enter Car.Car ID to remove: ");
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
                System.out.println("Car.Car ID " + carID + " does not exist");
                return;
            }

            cars.removeIf(car -> car.getCarID().equals(carID));
            System.out.println("Car.Car removed successfully");
        }
    }
}