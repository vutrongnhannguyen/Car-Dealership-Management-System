package User;


import UserOperation.ManagerOperation;


public class Manager extends User {
    ManagerOperation managerOperation = new ManagerOperation();
    public Manager(String userID, String fullName, String username, String password) {
        super(userID, fullName, "Manager", username, password);
        this.managerOperation = new ManagerOperation();
    }
    public ManagerOperation getManagerOperation() {
        return managerOperation;
    }
}
