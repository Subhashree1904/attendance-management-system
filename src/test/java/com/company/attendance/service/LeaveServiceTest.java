package com.company.attendance.service;

import com.company.attendance.model.LeaveRequest;
import com.company.attendance.model.LeaveRequest.LeaveStatus;
import com.company.attendance.service.LeaveService;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Test(groups = { "leave-tests" })
public class LeaveServiceTest {

    private LeaveService leaveService;

    @BeforeClass
    public void setUpClass() {
        leaveService = new LeaveService();
    }

    @BeforeMethod
    public void setUp() {
        leaveService = new LeaveService(); // reset state before each test
    }

    @Test(priority = 1, groups = { "smoke" })
    public void testApplyForLeave_success() {
        leaveService.applyForLeave(101, 1, LocalDate.of(2025, 9, 10),
                LocalDate.of(2025, 9, 12), "Vacation");

        List<LeaveRequest> requests = leaveService.getLeaveRequestsForEmployee(1);
        Assert.assertEquals(requests.size(), 1, "Employee should have 1 leave request");

        LeaveRequest request = requests.get(0);
        Assert.assertEquals(request.getStatus(), LeaveStatus.PENDING, "Status should be PENDING initially");
    }

    @Test(priority = 2, groups = { "workflow" })
    public void testApproveLeave_changesStatus() {
        leaveService.applyForLeave(102, 2, LocalDate.of(2025, 9, 15),
                LocalDate.of(2025, 9, 16), "Personal work");

        List<LeaveRequest> requests = leaveService.getLeaveRequestsForEmployee(2);
        LeaveRequest request = requests.get(0);

        // Approve the leave
        leaveService.approveLeave(request.getRequestId(), true);

        Assert.assertEquals(request.getStatus(), LeaveStatus.APPROVED, "Leave should be approved");
    }

    @Test(priority = 3, groups = { "temporal" })
    public void testApplyForLeave_invalidDateRange() {
        leaveService.applyForLeave(103, 3, LocalDate.of(2025, 9, 25),
                LocalDate.of(2025, 9, 24), "Invalid leave");

        List<LeaveRequest> requests = leaveService.getLeaveRequestsForEmployee(3);
        LeaveRequest request = requests.get(0);

        // Validate startDate is after endDate
        Assert.assertTrue(request.getStartDate().isAfter(request.getEndDate()),
                "Start date after end date should be detected");
    }

    @Test(priority = 4, groups = { "workflow" })
    public void testRejectLeave_changesStatus() {
        leaveService.applyForLeave(104, 4, LocalDate.of(2025, 10, 1),
                LocalDate.of(2025, 10, 3), "Other reason");

        List<LeaveRequest> requests = leaveService.getLeaveRequestsForEmployee(4);
        LeaveRequest request = requests.get(0);

        // Reject the leave
        leaveService.approveLeave(request.getRequestId(), false);

        Assert.assertEquals(request.getStatus(), LeaveStatus.REJECTED, "Leave should be rejected");
    }
}
