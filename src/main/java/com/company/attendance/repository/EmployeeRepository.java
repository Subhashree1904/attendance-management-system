package com.company.attendance.repository;

import com.company.attendance.model.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class EmployeeRepository {
    private Map<Integer, Employee> employeeStorage = new HashMap<>();

    public void addEmployee(Employee employee) {
        employeeStorage.put(employee.getEmployeeId(), employee);
    }

    public Employee findEmployeeById(int employeeId) {
        return employeeStorage.get(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStorage.values());
    }
}
