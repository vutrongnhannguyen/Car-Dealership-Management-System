package UserOperation;

import Car.Car;
import Service.Service;
import Transaction.Transaction;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class EmployeeOperation {
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
        System.out.println("\n********************************\n");
        System.out.print("Total revenue in a "+ period+ " of "+ month+ ", "+ day + ", "+ year+ " is: "+ revenueFormatted);
        System.out.println("\n********************************\n");
    }

    public static int countCarsSoldInPeriod(List<Transaction> transactions, LocalDate startDate, LocalDate endDate) {
        int count = 0;
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate))
                    && (transactionDate.isEqual(endDate) || transactionDate.isBefore(endDate))) {
                count = countCarInItem(transaction.getPurchasedItems());
            }
        }
        return count;
    }

    public static int countCarInItem(List<Object> purchasedItems){
        int count = 0;
        for (Object item : purchasedItems) {
            if (item instanceof Car) {
                count++;
            }
        }
        return count;
    }

    // Function to count services done in a specific period (day/week/month)
    public static int countServicesInPeriod(List<Service> services, LocalDate startDate, LocalDate endDate) {
        int count = 0;
        for (Service service : services) {
            LocalDate serviceDate = service.getServiceDate();
            if ((serviceDate.isEqual(startDate) || serviceDate.isAfter(startDate))
                    && (serviceDate.isEqual(endDate) || serviceDate.isBefore(endDate))) {
                count++;
            }
        }
        return count;
    }

    public void listNumberCarAndService(List<Service> services, List<Transaction> transactions, String period, String month, String day, String year){
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        int dayInt = Integer.parseInt(day);
        int carNum = 0;
        int serviceNum = 0;
        LocalDate startDate = LocalDate.of(yearInt, dayInt, monthInt);
        LocalDate endDate = null;
        switch (period.toLowerCase()) {
            case "day":
                endDate = startDate;
                break;
            case "week":
                endDate = startDate.plusDays(6); // Define the week as 7 days
                break;
            case "month":
                startDate = startDate.with(TemporalAdjusters.firstDayOfMonth());
                endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
                break;

            default:
                throw new IllegalArgumentException("Invalid period type. Use 'day', 'week', or 'month'.");
        }
        carNum = countCarsSoldInPeriod(transactions,startDate,endDate);
        serviceNum = countServicesInPeriod(services,startDate,endDate);
        System.out.println("\n********************************\n");
        System.out.print("Number of Cars sold in the " + period + " is: " + carNum);
        System.out.println("\nNumber of Service sold in the " + period + " is: "+serviceNum);
        System.out.println("\n********************************\n");
    }
}
