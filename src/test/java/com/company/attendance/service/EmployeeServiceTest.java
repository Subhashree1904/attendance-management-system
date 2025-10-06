package com.company.attendance.service;

import com.company.attendance.exception.DuplicateEmployeeException;
import com.company.attendance.model.Employee;
import com.company.attendance.repository.EmployeeRepository;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

@Test(groups = {"employee-tests"})
public class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        employeeRepository = new EmployeeRepository();
        employeeService = new EmployeeService(employeeRepository);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        employeeRepository = new EmployeeRepository();
        employeeService = new EmployeeService(employeeRepository);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    @Test(priority = 1, groups = {"employee", "smoke"})
    public void testAddEmployeeSuccess() throws DuplicateEmployeeException {
        Employee emp = new Employee(1, "Alice", "alice@example.com", "HR");
        employeeService.addEmployee(emp);

        Employee found = employeeRepository.findEmployeeById(1);
        Assert.assertNotNull(found, "Employee should be added successfully");
        Assert.assertEquals(found.getName(), "Alice");
    }

    @Test(priority = 2, expectedExceptions = DuplicateEmployeeException.class,
          groups = {"employee", "negative"})
    public void testAddDuplicateEmployeeThrows() throws DuplicateEmployeeException {
        Employee emp = new Employee(2, "Bob", "bob@example.com", "IT");
        employeeService.addEmployee(emp);

        employeeService.addEmployee(emp);
    }

    @Test(priority = 3, groups = {"employee", "state"})
    public void testGetAllEmployees() throws DuplicateEmployeeException {
        Employee emp1 = new Employee(3, "Charlie", "charlie@example.com", "Finance");
        Employee emp2 = new Employee(4, "Diana", "diana@example.com", "Sales");

        employeeService.addEmployee(emp1);
        employeeService.addEmployee(emp2);

        List<Employee> employees = employeeService.getAllEmployees();
        Assert.assertEquals(employees.size(), 2, "Should return exactly 2 employees");
    }
}
