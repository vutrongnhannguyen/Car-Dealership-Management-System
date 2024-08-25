import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Transaction {
    private String transactionID;
    private LocalDate transactionDate;
    private String clientID;
    private String salespersonID;
    private List<Object> purchasedItems;
    private double discount;
    private double totalAmount;
    private String notes;
    private boolean isActive;

    public Transaction(String transactionID, LocalDate transactionDate, String clientID, String salespersonID, List<Object> purchasedItems, double discount, double totalAmount, String notes) {
        this.transactionID = transactionID;
        this.transactionDate = transactionDate;
        this.clientID = clientID;
        this.salespersonID = salespersonID;
        this.purchasedItems = purchasedItems;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.notes = notes;
        this.isActive = true; // Default is active
    }

    public String getTransactionID() {
        return transactionID;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String getClientID() {
        return clientID;
    }

    public String getSalespersonID() {
        return salespersonID;
    }

    public double getDiscount() {
        return discount;
    }

    public String getNotes() {
        return notes;
    }

    public double getTotalAmount() {
        return totalAmount;
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



    public List<Object> getPurchasedItems() {
        return purchasedItems;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionID + ", Date: " + transactionDate + ", Total: " + totalAmount;
    }
}