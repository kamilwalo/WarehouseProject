package warehouse.app;

import warehouse.Entity.Employee;

public class EmployeeLoginContainer {
    private static Employee loggedEmployee;

    public static Employee getLoggedEmployee(){
        return loggedEmployee;
    }

    public static void setLoggedEmployee(Employee employee){
        loggedEmployee=employee;
    }
}
