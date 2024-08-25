import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Car {
    private String carID;
    private String make;
    private String model;
    private int year;
    private int mileage;
    private String color;
    private String status; // Available or Sold
    private double price;
    private LocalDate soldDate;
    private boolean isActive;
    private List<Service> serviceHistory;

    public Car(String carID, String make, String model, int year, int mileage, String color, String status, double price, List<Service> serviceHistory) {
        this.carID = carID;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.status = status;
        this.price = price;
        this.serviceHistory = serviceHistory;
        this.isActive = true; // Default is active
    }

    public String getCarID() {
        return carID;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(LocalDate soldDate) {
        this.soldDate = soldDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Service> getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(List<Service> serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

    public boolean isSoldInPeriod(String period) {

        return false;
    }

    @Override
    public String toString() {
        return "Car ID: " + carID + ", Make: " + make + ", Model: " + model + ", Year: " + year + ", Status: " + status;
    }


}