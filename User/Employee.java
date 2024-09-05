package User;

import UserOperation.EmployeeOperation;

public class Employee extends User {
    protected EmployeeOperation employeeOperation;

    public Employee(String userID, String fullName, String username, String password) {
        super(userID, fullName, "User.Manager", username, password);
        this.employeeOperation = new EmployeeOperation();
    }
    public EmployeeOperation getEmployeeOperation() {
        return employeeOperation;
    }
}
