import java.time.LocalDate;
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

    public Transaction(String transactionID, LocalDate transactionDate, String clientID, String salespersonID, List<Object> purchasedItems, double discount, double totalAmount, String notes) {
        setTransactionID(transactionID);
        this.transactionDate = transactionDate;
        this.clientID = clientID;
        setSalespersonID(salespersonID);
        this.purchasedItems = purchasedItems;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.notes = notes;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        if (transactionID.matches("t-\\d+")) {
            this.transactionID = transactionID;
        } else {
            throw new IllegalArgumentException("Invalid transaction ID format");
        }
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getSalespersonID() {
        return salespersonID;
    }

    public void setSalespersonID(String salespersonID) {
        if (salespersonID.matches("u-\\d+")) {
            this.salespersonID = salespersonID;
        } else {
            throw new IllegalArgumentException("Invalid Sales person ID format");
        }
    }

    public List<Object> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<Object> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isInPeriod(String period) {
        return false;
    }
    @Override
    public String toString() {
        return "Transaction ID: " + transactionID + ", Date: " + transactionDate + ", Total: " + totalAmount;
    }
}
