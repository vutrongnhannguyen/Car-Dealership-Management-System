package User;

import UserOperation.EmployeeOperation;

public class Employee extends User {
    protected EmployeeOperation employeeOperation;

    public Employee(String userID, String fullName, String userType, String username, String password) {
        super(userID, fullName, userType , username, password);
        this.employeeOperation = new EmployeeOperation();
    }
    public EmployeeOperation getEmployeeOperation() {
        return employeeOperation;
    }
}
