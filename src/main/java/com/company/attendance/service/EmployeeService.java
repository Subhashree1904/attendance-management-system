package com.company.attendance.service;

import com.company.attendance.model.Employee;
import com.company.attendance.repository.EmployeeRepository;
import com.company.attendance.exception.DuplicateEmployeeException;
import com.company.attendance.exception.EmployeeNotFoundException;

import java.util.List;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(Employee employee) throws DuplicateEmployeeException {
        for (Employee existing : employeeRepository.getAllEmployees()) {
            if (existing.getEmail().equalsIgnoreCase(employee.getEmail())) {
                throw new DuplicateEmployeeException(
                        "Employee with email " + employee.getEmail() + " already exists!"
                );
            }
        }
        employeeRepository.addEmployee(employee);
    }
    
    public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
        Employee emp = employeeRepository.findEmployeeById(employeeId);
        if (emp == null) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found!");
        }
        return emp;
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }
}
