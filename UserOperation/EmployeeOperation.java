package UserOperation;

import Car.Car;
import Service.Service;
import Transaction.Transaction;

import java.time.LocalDate;
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
        System.out.println("Total revenue in a "+ period+ " of "+ month+ ", "+ day + ", "+ year+ " is: "+ revenueFormatted);
    }

    public void listCarsOrServices(List<Car> cars, List<Service> services, String period) {
    }
}
