package User;

import AutoPart.AutoPart;
import Car.Car;
import AutoPart.PartManager;
import Service.Service;
import Transaction.Transaction;
import FileHandler.FileManager;
import Transaction.TransactionManager;
import Service.ServiceManager;

import java.util.List;
import java.util.Scanner;

public class User {
    protected String userID;
    protected String fullName;
    protected String userType;
    protected String username;
    protected String password;
    protected boolean isActive;

    public User(String userID, String fullName, String userType, String username, String password) {
        setUserID(userID);
        this.fullName = fullName;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.isActive = true; // Users are active by default
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password) && isActive;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        if (userID.matches("u-\\d+")) {
            this.userID = userID;
        } else {
            throw new IllegalArgumentException("Invalid User.User ID format");
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User ID: " + userID + "\nName: " + fullName + "\nUser Type: " + userType + "\nStatus: " + (isActive ? "Active" : "Inactive");
    }
}







