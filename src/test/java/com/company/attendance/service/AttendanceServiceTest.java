package com.company.attendance.service;

import com.company.attendance.model.AttendanceRecord;
import com.company.attendance.repository.AttendanceRepository;
import com.company.attendance.service.AttendanceService;
import com.company.attendance.exception.InvalidAttendanceException;

import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;

@Test(groups = { "attendance-tests" })
public class AttendanceServiceTest {

    private AttendanceRepository attendanceRepository;
    private AttendanceService attendanceService;

    @BeforeClass
    public void setUpClass() {
        attendanceRepository = new AttendanceRepository();
        attendanceService = new AttendanceService(attendanceRepository);
    }

    @BeforeMethod
    public void setUp() {
        attendanceRepository = new AttendanceRepository();
        attendanceService = new AttendanceService(attendanceRepository);
    }

    @DataProvider(name = "employeeIds")
    public Object[][] employeeIds() {
        return new Object[][] { { 1 }, { 2 }, { 10 } };
    }

    @Test(dataProvider = "employeeIds", priority = 1, groups = { "smoke" })
    public void testClockInSuccess(int employeeId) throws InvalidAttendanceException {
        Assert.assertNull(attendanceRepository.findRecordByDateAndEmployee(employeeId, LocalDate.now()));
        attendanceService.clockIn(employeeId);

        AttendanceRecord record = attendanceRepository.findRecordByDateAndEmployee(employeeId, LocalDate.now());
        Assert.assertNotNull(record);
        Assert.assertEquals(record.getEmployeeId(), employeeId);
        Assert.assertNotNull(record.getClockInTime());
        Assert.assertNull(record.getClockOutTime());
    }

    @Test(dependsOnMethods = "testClockInSuccess", priority = 2,
          expectedExceptions = InvalidAttendanceException.class,
          groups = { "negative" })
    public void testDuplicateClockInThrows() throws InvalidAttendanceException {
        int employeeId = 1;
        attendanceService.clockIn(employeeId);
        attendanceService.clockIn(employeeId);
    }

    @Test(priority = 3, expectedExceptions = InvalidAttendanceException.class,
          groups = { "negative" })
    public void testClockOutWithoutClockInThrows() throws InvalidAttendanceException {
        attendanceService.clockOut(99);
    }

    @Test(priority = 4, groups = { "state" }, dependsOnMethods = "testClockInSuccess")
    public void testClockOutSuccess_calculatesHours() throws InvalidAttendanceException, InterruptedException {
        int employeeId = 5;
        attendanceService.clockIn(employeeId);
        Thread.sleep(50);

        attendanceService.clockOut(employeeId);
        AttendanceRecord record = attendanceRepository.findRecordByDateAndEmployee(employeeId, LocalDate.now());

        Assert.assertNotNull(record.getClockOutTime());
        Assert.assertTrue(record.getHoursWorked() >= 0.0);
    }

    @Test(priority = 5, groups = { "temporal" })
    public void testPreventDuplicateClockInsSameDay() throws InvalidAttendanceException {
        int employeeId = 77;
        attendanceService.clockIn(employeeId);
        try {
            attendanceService.clockIn(employeeId);
            Assert.fail();
        } catch (InvalidAttendanceException e) {
            Assert.assertTrue(e.getMessage().toLowerCase().contains("already clocked in"));
        }
    }
}
