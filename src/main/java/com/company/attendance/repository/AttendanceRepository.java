package com.company.attendance.repository;

import com.company.attendance.model.AttendanceRecord;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {
    private List<AttendanceRecord> attendanceStorage = new ArrayList<>();

    public void addRecord(AttendanceRecord record) {
        attendanceStorage.add(record);
    }
    
    public List<AttendanceRecord> findRecordsByEmployeeId(int employeeId) {
        List<AttendanceRecord> results = new ArrayList<>();
        for (AttendanceRecord record : attendanceStorage) {
            if (record.getEmployeeId() == employeeId) {
                results.add(record);
            }
        }
        return results;
    }

    public AttendanceRecord findRecordByDateAndEmployee(int employeeId, LocalDate date) {
        for (AttendanceRecord record : attendanceStorage) {
            if (record.getEmployeeId() == employeeId && record.getDate().equals(date)) {
                return record;
            }
        }
        return null;
    }
}
