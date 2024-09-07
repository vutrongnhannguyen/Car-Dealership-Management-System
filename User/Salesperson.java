package User;

import Car.Car;
import Service.Service;
import Transaction.Transaction;

import java.time.LocalDate;
import java.util.List;

public class Salesperson extends Employee {

    public Salesperson(String userID, String fullName, String userType, String username, String password) {
        super(userID, fullName, userType, username, password);
    }
}
