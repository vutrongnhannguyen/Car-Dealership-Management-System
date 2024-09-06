package User;

import Service.Service;

import java.util.List;

public class Client extends User {
    private String membership;
    private double totalSpending;

    public Client(String userID, String fullName, String username, String password) {
        super(userID, fullName, "User.Client", username, password);
        this.membership = "None"; // Default membership level
        this.totalSpending = 0.0;
    }

    public String getMembership() {
        return membership;
    }

    public double getTotalSpending() {
        return totalSpending;
    }

    public void updateTotalSpending(double amount) {
        this.totalSpending += amount;
        updateMembership(); // Update membership based on the new total spending
    }

    // Set amount for each membership
    public void updateMembership() {
        if (totalSpending > 250_000_000) {
            membership = "Platinum";
        } else if (totalSpending > 100_000_000) {
            membership = "Gold";
        } else if (totalSpending > 30_000_000) {
            membership = "Silver";
        } else {
            membership = "None";
        }
    }


    // Set discount rate based on membership
    public double getDiscountRate() {
        switch (membership) {
            case "Silver":
                return 0.05;
            case "Gold":
                return 0.10;
            case "Platinum":
                return 0.15;
            default:
                return 0.0;
        }
    }

    public void viewMembershipStatus() {
        System.out.println("Your membership is: " + membership);
    }

    public void viewServiceHistory(List<Service> services, String clientID) {
        System.out.println("\nService.Service History: ");
        boolean hasServices = false;
        for (Service service : services) { // Loop through services object
            if (service.getClientID().equals(clientID)) { // Check for user ID
                System.out.println(service);
                hasServices = true;
            }
        }
        if (!hasServices) {
            System.out.println("No service history found for this client");
        }
    }

    // Methods to modify profile information
    public void updateFullName(String newFullName) {
        this.fullName = newFullName;
        System.out.println("Full name updated successfully");
    }

    public void updateUsername(String newUsername) {
        this.username = newUsername;
        System.out.println("Username updated successfully");
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password updated successfully");
    }
}
