package com.company.attendance.service;

import com.company.attendance.model.AttendanceRecord;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ReportService {
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public void addAttendanceRecord(AttendanceRecord record) {
        attendanceRecords.add(record);
    }

    public List<AttendanceRecord> generateAttendanceReport(int employeeId, LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> report = new ArrayList<>();
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeId() == employeeId &&
                (record.getDate().isEqual(startDate) || record.getDate().isAfter(startDate)) &&
                (record.getDate().isEqual(endDate) || record.getDate().isBefore(endDate))) {
                report.add(record);
            }
        }
        return report;
    }

    public double calculateMonthlyHours(int employeeId, YearMonth month) {
        double totalHours = 0;
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeId() == employeeId && YearMonth.from(record.getDate()).equals(month)) {
                totalHours += record.getHoursWorked();
            }
        }
        return totalHours;
    }
}
