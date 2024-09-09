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

    // Ensure the part ID follows the p- format
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
        return "Part ID: " + partID +
                ", Name: " + name +
                ", Manufacturer: " + manufacturer +
                ", Part Number: " + partNumber +
                ", Condition: " + condition +
                ", Warranty: " + warranty +
                ", Cost: " + cost +
                ", Notes: " + notes;
    }

}