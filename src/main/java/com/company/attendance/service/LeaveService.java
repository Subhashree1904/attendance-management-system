package com.company.attendance.service;


import com.company.attendance.model.LeaveRequest;
import com.company.attendance.model.LeaveRequest.LeaveStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaveService {
    private List<LeaveRequest> leaveRequests = new ArrayList<>();

    public void applyForLeave(int requestId, int employeeId, LocalDate startDate, LocalDate endDate, String reason) {
        LeaveRequest request = new LeaveRequest(requestId, employeeId, startDate, endDate, LeaveStatus.PENDING, reason);
        leaveRequests.add(request);
    }

    public void approveLeave(int requestId, boolean isApproved) {
        for (LeaveRequest request : leaveRequests) {
            if (request.getRequestId() == requestId) {
                request.setStatus(isApproved ? LeaveStatus.APPROVED : LeaveStatus.REJECTED);
                break;
            }
        }
    }

    public List<LeaveRequest> getLeaveRequestsForEmployee(int employeeId) {
        List<LeaveRequest> result = new ArrayList<>();
        for (LeaveRequest request : leaveRequests) {
            if (request.getEmployeeId() == employeeId) {
                result.add(request);
            }
        }
        return result;
    }
}
