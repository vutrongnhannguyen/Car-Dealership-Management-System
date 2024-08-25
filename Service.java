import java.time.LocalDate;
import java.util.List;

public class Service {
    private String serviceID;
    private LocalDate serviceDate;
    private String clientID;
    private String mechanicID;
    private String serviceType;
    private List<AutoPart> replacedParts;
    private double cost;
    private String notes;
    private boolean isActive;

    public Service(String serviceID, LocalDate serviceDate, String clientID, String mechanicID, String serviceType, List<AutoPart> replacedParts, double cost, String notes) {
        this.serviceID = serviceID;
        this.serviceDate = serviceDate;
        this.clientID = clientID;
        this.mechanicID = mechanicID;
        this.serviceType = serviceType;
        this.replacedParts = replacedParts;
        this.cost = cost;
        this.notes = notes;
        this.isActive = true; // Default is active
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(String mechanicID) {
        this.mechanicID = mechanicID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<AutoPart> getReplacedParts() {
        return replacedParts;
    }

    public void setReplacedParts(List<AutoPart> replacedParts) {
        this.replacedParts = replacedParts;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isInPeriod(String period) {
        return false;
    }

    @Override
    public String toString() {
        return "Service ID: " + serviceID + ", Date: " + serviceDate + ", Type: " + serviceType;
    }
}