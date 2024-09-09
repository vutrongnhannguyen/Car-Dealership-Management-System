package User;

import java.util.List;
import java.util.Scanner;

public class UserManager {
    public static User authenticateUser(String username, String password, List<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static void removeUser(Scanner scanner, List<User> users) {
        System.out.print("Enter User ID to remove: ");
        String userID = scanner.nextLine();

        // Check if the User.User ID exists
        boolean userExists = false;
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                userExists = true;
                break;
            }
        }

        if (!userExists) {
            System.out.println("User ID " + userID + " does not exist");
            return;
        }

        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                user.setActive(false);
                System.out.println("User deactivated successfully.");
                return;
            }
        }
        System.out.println("User not found.");
    }

    public static void readUserByID(Scanner scanner, List<User> users) {
        System.out.print("Enter the User ID to access: ");
        String userID = scanner.nextLine();

        // Check if the User ID exists
        User userToRead = null;
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                userToRead = user;
                break;
            }
        }

        if (userToRead == null) {
            System.out.println("User ID " + userID + " does not exist.");
        } else {
            System.out.println("User Details:\n" + userToRead);
        }
    }
}