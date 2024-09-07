package UserOperation;

import AutoPart.AutoPart;
import Car.Car;
import Service.Service;
import Transaction.Transaction;

import java.time.LocalDate;
import java.util.List;

public class ManagerOperation {
    // View/Search Operations
    public void viewEntities(List<Car> cars, List<AutoPart> parts, List<Service> services, List<Transaction> transactions) {
        System.out.println("\n--- Cars ---");
        cars.forEach(System.out::println);
        System.out.println("\n--- Auto Parts ---");
        parts.forEach(System.out::println);
        System.out.println("\n--- Services ---");
        services.forEach(System.out::println);
        System.out.println("\n--- Transactions ---");
        transactions.forEach(System.out::println);
    }

    public int calculateCarInOneTransaction(List<Object> purchasedItems){
        int count = 0;

        for (Object item : purchasedItems) {
            if (item instanceof Car) {
                count++;
            }
        }
        return count;
    }

    // Statistics Operations
    public void calculateCarsSoldInMonth(List<Transaction> transactions, String month, String year) {
        int numberOfCar = 0;

        // Convert month and year strings to integers
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);

        // Iterate through the transactions and calculate the number of cars sold
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().getMonthValue() == monthInt && transaction.getTransactionDate().getYear() == yearInt) {
                numberOfCar += calculateCarInOneTransaction(transaction.getPurchasedItems());
            }
        }

        System.out.println("Number of cars sold in " + month + "-" + year + ": " + numberOfCar);
    }

    public void calculateRevenue(List<Transaction> transactions, String period, String month, String day, String year) {
        double totalRevenue = 0;
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (transactionDate.isEqual(startDate)) {
                        totalRevenue += transaction.getTotalAmount();
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                            (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                        totalRevenue += transaction.getTotalAmount();
                    }
                    break;

                case "month":
                    if (transactionDate.getYear() == startDate.getYear() &&
                            transactionDate.getMonthValue() == startDate.getMonthValue()) {
                        totalRevenue += transaction.getTotalAmount();
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        String revenueFormatted = String.format("%.0f", totalRevenue);
        System.out.println("Total revenue in a "+ period+ " of "+ month+ ", "+ day + ", "+ year+ " is: "+ revenueFormatted);
    }

    public void calculateRevenueForMechanic(List<Service> services, String mechanicID) {
        double revenue = 0;
        for(Service service : services) {
            if(service.getMechanicID().equals(mechanicID)) {
                revenue += service.getCost();
            }
        }
        String revenueFormatted = String.format("%.0f", revenue);
        System.out.print("Revenue for "+mechanicID+" is: " + revenueFormatted);
    }

    public void calculateRevenueForSalesperson(List<Transaction> transactions, String salespersonID) {
        double revenue = 0;
        for (Transaction transaction : transactions) {
            if(transaction.getSalespersonID().equals(salespersonID)) {
                revenue += transaction.getTotalAmount();
            }
        }
        String revenueFormatted = String.format("%.0f", revenue);
        System.out.print("Revenue for "+salespersonID+" is: " + revenueFormatted);
    }

    public void printCar(List<Object> purchasedItems){
        for (Object item : purchasedItems) {
            if (item instanceof Car) {
                System.out.println(item);
            }
        }
    }

    public void listCarsSold(List<Transaction> transactions, String period, String month, String day, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue
        System.out.println("\n--- List Cars Sold ---");
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (transactionDate.isEqual(startDate)) {
                        printCar(transaction.getPurchasedItems());
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                            (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                        printCar(transaction.getPurchasedItems());
                    }
                    break;

                case "month":
                    if (transactionDate.getYear() == startDate.getYear() &&
                            transactionDate.getMonthValue() == startDate.getMonthValue()) {
                        printCar(transaction.getPurchasedItems());
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        System.out.println("----------------------");
    }

    public void listTransactions(List<Transaction> transactions, String period, String month, String day, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue
        System.out.println("\n--- List Of Transaction.Transaction ---");
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (transactionDate.isEqual(startDate)) {
                        System.out.println(transaction);
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                            (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                        System.out.println(transaction);
                    }
                    break;

                case "month":
                    if (transactionDate.getYear() == startDate.getYear() &&
                            transactionDate.getMonthValue() == startDate.getMonthValue()) {
                        System.out.println(transaction);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        System.out.println("--------------------------");
    }

    public void listServices(List<Service> services, String period, String month, String day, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue
        System.out.println("\n--- List Of Services ---");
        for (Service service : services) {
            LocalDate serviceDate = service.getServiceDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (serviceDate.isEqual(startDate)) {
                        System.out.println(service);
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((serviceDate.isEqual(startDate) || serviceDate.isAfter(startDate)) &&
                            (serviceDate.isEqual(endOfWeek) || serviceDate.isBefore(endOfWeek))) {
                        System.out.println(service);
                    }
                    break;

                case "month":
                    if (serviceDate.getYear() == startDate.getYear() &&
                            serviceDate.getMonthValue() == startDate.getMonthValue()) {
                        System.out.println(service);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        System.out.println("-----------------------");
    }

    public void printAutoPart(List<Object> purchasedItems){
        for (Object item : purchasedItems) {
            if (item instanceof AutoPart) {
                System.out.println(item);
            }
        }
    }

    public void listAutoPartsSold(List<Transaction> transactions, String period, String month, String day, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        // Loop through all transactions to calculate the revenue
        System.out.println("\n--- List Auto Parts Sold ---");
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            switch (period.toLowerCase()) {
                case "day":
                    if (transactionDate.isEqual(startDate)) {
                        printAutoPart(transaction.getPurchasedItems());
                    }
                    break;

                case "week":
                    LocalDate endOfWeek = startDate.plusDays(6); // Define the week as 7 days
                    if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                            (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                        printAutoPart(transaction.getPurchasedItems());
                    }
                    break;

                case "month":
                    if (transactionDate.getYear() == startDate.getYear() &&
                            transactionDate.getMonthValue() == startDate.getMonthValue()) {
                        printAutoPart(transaction.getPurchasedItems());
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
            }
        }
        System.out.println("----------------------------");
    }
}
