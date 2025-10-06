package com.company.attendance.service;

import com.company.attendance.model.AttendanceRecord;
import com.company.attendance.repository.AttendanceRepository;
import com.company.attendance.exception.InvalidAttendanceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceService {
    private AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public void clockIn(int employeeId) throws InvalidAttendanceException {
        LocalDate today = LocalDate.now();

        AttendanceRecord existingRecord = attendanceRepository.findRecordByDateAndEmployee(employeeId, today);
        if (existingRecord != null) {
            throw new InvalidAttendanceException("Employee " + employeeId + " has already clocked in today!");
        }

        AttendanceRecord newRecord = new AttendanceRecord();
        newRecord.setRecordId(generateRecordId());
        newRecord.setEmployeeId(employeeId);
        newRecord.setDate(today);
        newRecord.setClockInTime(LocalTime.now());
        newRecord.setHoursWorked(0);

        attendanceRepository.addRecord(newRecord);
    }
    
    public void clockOut(int employeeId) throws InvalidAttendanceException {
        LocalDate today = LocalDate.now();

        AttendanceRecord record = attendanceRepository.findRecordByDateAndEmployee(employeeId, today);
        if (record == null) {
            throw new InvalidAttendanceException("Employee " + employeeId + " cannot clock out without clocking in!");
        }

        if (record.getClockOutTime() != null) {
            throw new InvalidAttendanceException("Employee " + employeeId + " has already clocked out today!");
        }

        LocalTime clockOutTime = LocalTime.now();
        record.setClockOutTime(clockOutTime);

        double hoursWorked = java.time.Duration.between(record.getClockInTime(), clockOutTime).toHours();
        record.setHoursWorked(hoursWorked);
    }

    public List<AttendanceRecord> getAttendanceByEmployee(int employeeId) {
        return attendanceRepository.findRecordsByEmployeeId(employeeId);
    }

    private int generateRecordId() {
        return (int) (Math.random() * 10000);
        
    }
}
