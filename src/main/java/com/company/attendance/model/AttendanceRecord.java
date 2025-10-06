package com.company.attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRecord {
    
    private int recordId;
    private int employeeId;
    private LocalDate date;
    private LocalTime clockInTime;
    private LocalTime clockOutTime;
    private double hoursWorked;

  
    public AttendanceRecord(int recordId, int employeeId, LocalDate date, LocalTime clockInTime, LocalTime clockOutTime, double hoursWorked) {
        this.recordId = recordId;
        this.employeeId = employeeId;
        this.date = date;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.hoursWorked = hoursWorked;
    }

    public AttendanceRecord() {
    }

  
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(LocalTime clockInTime) {
        this.clockInTime = clockInTime;
    }

    public LocalTime getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(LocalTime clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String toString() {
        return "AttendanceRecord {" + "RecordID=" + recordId + ", EmployeeID=" + employeeId + ", Date=" + date + ", ClockIn=" + clockInTime + ", ClockOut=" + clockOutTime + ", HoursWorked=" + hoursWorked + '}';
    }
}
