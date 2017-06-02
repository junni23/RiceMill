package Application.Listener;


import Application.Model.Employee;

/**
 * Created by Sohel on 5/16/2017.
 */
public interface EmployeeListener {
    public void addEmployee(Employee employee);
    public void updateEmployee(Employee old, Employee current);
}
