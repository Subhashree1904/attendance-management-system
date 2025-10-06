package com.company.attendance.repository;

import com.company.attendance.model.AttendanceRecord;
import com.company.attendance.service.ReportService;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Test(groups = { "reporting-tests" })
public class EmployeeRepositoryTest {

    private ReportService reportService;

    @BeforeClass
    public void setUpClass() {
        reportService = new ReportService();
    }

    @BeforeMethod
    public void setUp() {
        reportService = new ReportService();
    }

    @Test(priority = 1)
    public void testGenerateAttendanceReport_and_monthlyHours() {
        int employeeId = 200;

        reportService.addAttendanceRecord(new AttendanceRecord(1001, employeeId, LocalDate.of(2025, 9, 1),
                LocalTime.of(9, 0), LocalTime.of(17, 0), 8.0));
        reportService.addAttendanceRecord(new AttendanceRecord(1002, employeeId, LocalDate.of(2025, 9, 2),
                LocalTime.of(9, 30), LocalTime.of(17, 0), 7.5));
        reportService.addAttendanceRecord(new AttendanceRecord(1003, employeeId, LocalDate.of(2025, 10, 1),
                LocalTime.of(9, 0), LocalTime.of(12, 0), 3.0));

        List<AttendanceRecord> sepReport = reportService.generateAttendanceReport(employeeId,
                LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 30));
        Assert.assertEquals(sepReport.size(), 2);

        double totalSepHours = reportService.calculateMonthlyHours(employeeId, YearMonth.of(2025, 9));
        Assert.assertEquals(totalSepHours, 15.5, 0.0001);
    }
}
