package com.company.attendance;

import com.company.attendance.model.AttendanceRecord;
import com.company.attendance.model.Employee;
import com.company.attendance.model.LeaveRequest;
import com.company.attendance.service.AttendanceService;
import com.company.attendance.service.EmployeeService;
import com.company.attendance.service.LeaveService;
import com.company.attendance.service.ReportService;
import com.company.attendance.repository.EmployeeRepository;
import com.company.attendance.repository.AttendanceRepository;
import com.company.attendance.exception.DuplicateEmployeeException;
import com.company.attendance.exception.InvalidAttendanceException;

import java.time.LocalDate;
import java.util.List;

public class App {

    public static void main(String[] args) {

        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        AttendanceRepository attendanceRepository = new AttendanceRepository();
        AttendanceService attendanceService = new AttendanceService(attendanceRepository);
        LeaveService leaveService = new LeaveService();
        ReportService reportService = new ReportService();

        try {
            employeeService.addEmployee(new Employee(1, "Alice", "alice@example.com", "HR"));
            employeeService.addEmployee(new Employee(2, "Bob", "bob@example.com", "IT"));
            employeeService.addEmployee(new Employee(3, "Charlie", "charlie@example.com", "Finance"));
        } catch (DuplicateEmployeeException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }

        System.out.println("=== Employees Seeded ===");
        for (Employee e : employeeService.getAllEmployees()) {
            System.out.println(e.getEmployeeId() + " - " + e.getName() + " (" + e.getDepartment() + ")");
        }

        System.out.println("\n=== Clock-In Demo ===");
        try {
            attendanceService.clockIn(1);
            attendanceService.clockIn(2);

            try {
                attendanceService.clockIn(1);
            } catch (InvalidAttendanceException ex) {
                System.out.println("Duplicate clock-in prevented: " + ex.getMessage());
            }

        } catch (InvalidAttendanceException e) {
            System.out.println("Clock-in error: " + e.getMessage());
        }

        System.out.println("\n=== Clock-Out Demo ===");
        try {
            attendanceService.clockOut(1);
            attendanceService.clockOut(2);
        } catch (InvalidAttendanceException e) {
            System.out.println("Clock-out error: " + e.getMessage());
        }

        System.out.println("\n=== Leave Demo ===");
        leaveService.applyForLeave(1001, 1, LocalDate.of(2025, 9, 10), LocalDate.of(2025, 9, 12), "Vacation");
        leaveService.applyForLeave(1002, 2, LocalDate.of(2025, 9, 15), LocalDate.of(2025, 9, 16), "Personal work");

        leaveService.approveLeave(1001, true);
        leaveService.approveLeave(1002, false); 
        
        for (Employee e : employeeService.getAllEmployees()) {
            List<LeaveRequest> leaves = leaveService.getLeaveRequestsForEmployee(e.getEmployeeId());
            System.out.println("Leaves for " + e.getName() + ":");
            for (LeaveRequest l : leaves) {
                System.out.println("  RequestId: " + l.getRequestId() + ", Status: " + l.getStatus() +
                        ", From: " + l.getStartDate() + " To: " + l.getEndDate());
            }
        }

        System.out.println("\n=== Attendance Report Demo ===");
       
        for (Employee e : employeeService.getAllEmployees()) {
            List<AttendanceRecord> report = reportService.generateAttendanceReport(e.getEmployeeId(),
                    LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
            System.out.println("Attendance report for " + e.getName() + ":");
            if (report.isEmpty()) {
                System.out.println("  No records found.");
            }
            for (AttendanceRecord r : report) {
                System.out.println("  Date: " + r.getDate() + ", ClockIn: " + r.getClockInTime() +
                        ", ClockOut: " + r.getClockOutTime() + ", HoursWorked: " + r.getHoursWorked());
            }
        }
    }
}
