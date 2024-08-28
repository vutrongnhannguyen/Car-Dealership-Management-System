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
    private List<String> serviceHistory;
    private String notes;

    public Car(String carID, String make, String model, int year, int mileage, String color, String status, double price, List<String> serviceHistory, String notes) {
        setCarID(carID);
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.status = status;
        this.price = price;
        this.serviceHistory = serviceHistory;
        this.notes = notes;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        if (carID.matches("c-\\d+")) {
            this.carID = carID;
        } else {
            throw new IllegalArgumentException("Invalid car ID format.");
        }
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

    public List<String> getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(List<String> serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isSoldInPeriod(String period) {
        return false;
    }

    @Override
    public String toString() {
        return "Car ID: " + carID + ", Make: " + make + ", Model: " + model + ", Year: " + year + ", Status: " + status;
    }
}