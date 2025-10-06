# Attendance Management System (Java + TestNG)

## Overview:

The **Attendance Management System (AMS)** is a **Java-based backend console (CLI)** application that manages **employee attendance, leave requests, and reporting**.  
It demonstrates **Core Java**, **OOP principles**, and **TestNG-based automated testing** for a complete quality assurance workflow — ideal for backend validation and QAE (Quality Assurance Engineer) preparation.

## Architecture Overview:

This project follows a **layered architecture** ensuring clean separation of concerns:

| Layer | Description | Key Classes |
| --- | --- | --- |
| **Model Layer** | Represents core entities and encapsulates data. | `Employee`, `AttendanceRecord`, `LeaveRequest` |
| **Repository Layer** | Manages in-memory data storage using Java Collections. | `EmployeeRepository`, `AttendanceRepository` |
| **Service Layer** | Contains core business logic, validations, and exception handling. | `EmployeeService`, `AttendanceService`, `LeaveService`, `ReportService` |
| **Exception Layer** | Custom exceptions enforcing business rules and error handling. | `DuplicateEmployeeException`, `EmployeeNotFoundException`, `InvalidAttendanceException` |
| **Test Layer** | TestNG-based suite covering all major service methods and edge cases. | `AttendanceServiceTest`, `LeaveServiceTest`, `EmployeeServiceTest` |

## Project Structure:

```
attendance-management-system/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/company/attendance/
    │   │       ├── model/
    │   │       │   ├── Employee.java
    │   │       │   ├── AttendanceRecord.java
    │   │       │   └── LeaveRequest.java
    │   │       ├── repository/
    │   │       │   ├── EmployeeRepository.java
    │   │       │   └── AttendanceRepository.java
    │   │       ├── service/
    │   │       │   ├── EmployeeService.java
    │   │       │   ├── AttendanceService.java
    │   │       │   ├── LeaveService.java
    │   │       │   └── ReportService.java
    │   │       ├── exception/
    │   │       │   ├── DuplicateEmployeeException.java
    │   │       │   ├── EmployeeNotFoundException.java
    │   │       │   └── InvalidAttendanceException.java
    │   │       └── App.java   <-- CLI Demo Runner
    │   └── resources/
    └── test/
        └── java/
            └── com/company/attendance/service/
                ├── AttendanceServiceTest.java
                ├── LeaveServiceTest.java
                └── EmployeeServiceTest.java
```

## Core Concepts Demonstrated

| Concept | Description |
| --- | --- |
| **Encapsulation** | Private fields, getters/setters for controlled access. |
| **Abstraction** | Business logic isolated in service classes. |
| **Inheritance** | Custom exceptions extending `Exception`. |
| **Polymorphism** | Service methods overridden or reused dynamically. |
| **Collections API** | `HashMap`, `ArrayList` for in-memory data management. |
| **Date & Time API** | Uses `java.time.LocalDate` and `java.time.LocalDateTime`. |
| **Exception Handling** | Custom exceptions, `try-catch` blocks, and business rule enforcement. |
| **TestNG Framework** | Data-driven, exception-based, and dependency-controlled test execution. |

## Features

1.  `Employee Management` :
    *   Add new employees with unique IDs and emails.
    *   Prevent duplicate entries (`DuplicateEmployeeException`).
    *   Fetch and list all employees.
2.  `Attendance Tracking` :
    *   **Clock-in** and **Clock-out** employees.
    *   Prevent **duplicate clock-ins** on the same day.
    *   Automatically calculate **hours worked** using `java.time.Duration`.
    *   Handle invalid operations (e.g., clock-out without clock-in).
3.  `Leave Management` :
    *   Apply for leave with **start/end dates and reason**.
    *   Approve or reject leave requests.
    *   Validate date ranges and prevent overlaps.
4.  `Reporting` :
    *   Generate attendance reports for a **date range** or **month**.
    *   Summarize total working hours.
    *   Display all attendance records for a given employee.

## Testing Strategy (TestNG)

The **TestNG suite** ensures that all critical business rules are tested thoroughly.

> `Test Coverage` :
> 
> | Test Category | Description |
> | --- | --- |
> | **EmployeeService Tests** | Validates adding employees, duplicate prevention, and record retrieval. |
> | **AttendanceService Tests** | Validates clock-in/out, duplicate checks, time calculations, and attendance reports. |
> | **LeaveService Tests** | Validates leave application, approval/rejection, and date range correctness. |
> | **ReportService Tests** | Ensures accuracy of generated attendance summaries. |
> | **Exception Testing** | Tests for duplicate employees, invalid attendance, and missing employee records. |
> | **Data-Driven Testing** | Uses `@DataProvider` for testing multiple employee IDs and attendance scenarios. |
> | **State-Based Testing** | Verifies correct state changes (e.g., hours worked after valid clock-in/out). |
> | **Temporal Logic Testing** | Ensures duplicate clock-ins are prevented and leave date ranges are valid. |
> | **Organized Tests** | Tests are grouped into `attendance`, `employee`, `leave`, and `report` categories. |
> | **Execution Control** | Uses `priority` and `dependsOnMethods` to maintain logical test sequences. |

---

## Test Features Used:

| Feature | Description |
| --- | --- |
| **@DataProvider** | Data-driven tests for multiple scenarios (e.g., multiple employees). |
| **expectedExceptions** | Validates proper throwing of custom exceptions. |
| **dependsOnMethods** | Maintains test sequence (e.g., clock-out depends on clock-in). |
| **@BeforeMethod / @AfterMethod** | Ensures clean setup/teardown for every test. |
| **groups** | Groups tests into categories: `attendance`, `leave`, `employee`, `report`. |
| **priority** | Controls logical execution order of test cases. |

## Example

### Scenarios Tested:

> Clock-out without clock-in → `InvalidAttendanceException`
> 
> Duplicate employee addition → `DuplicateEmployeeException`
> 
> Multiple clock-ins in one day → Validation error
> 
> Correct hour calculation between clock-in/out times
> 
> Leave date range validation
> 
> State verification after successful attendance updates

### Example CLI Output (App.java)

> \=== Employees Seeded ===  
> 1 - Alice (HR)  
> 2 - Bob (IT)  
> 3 - Charlie (Finance)
> 
> \=== Clock-In Demo ===  
> Duplicate clock-in prevented: Employee 1 has already clocked in today!
> 
> \=== Clock-Out Demo ===
> 
> \=== Leave Demo ===  
> Leaves for Alice:  
>  RequestId: 1001, Status: APPROVED, From: 2025-09-10 To: 2025-09-12  
> Leaves for Bob:  
>  RequestId: 1002, Status: REJECTED, From: 2025-09-15 To: 2025-09-16  
> Leaves for Charlie:
> 
> \=== Attendance Report Demo ===  
> Attendance report for Alice:  
>  No records found.  
> Attendance report for Bob:  
>  No records found.  
> Attendance report for Charlie:  
>  No records found.

## Setup Instructions

### Prerequisites

Before running the framework, ensure you have the following setup on your system:

| Requirement | Description |
| --- | --- |
| **Java JDK 11+** | Install and configure the latest JDK (version **11 or higher**) in your system environment variables. |
| **Maven** | Install Maven and add it to your **PATH** for dependency management and build execution. |
| **IDE** | Use an IDE like **IntelliJ IDEA** or **Eclipse** configured for Java and Maven projects. |

> **Tips:** 
> 
> Run `java -version` in your terminal to verify proper installation of java.
> 
> ```
> C:\Users\subhn>java --version
> java 21.0.6 2025-01-21 LTS
> Java(TM) SE Runtime Environment (build 21.0.6+8-LTS-188)
> Java HotSpot(TM) 64-Bit Server VM (build 21.0.6+8-LTS-188, mixed mode, sharing)
> ```
> 
> Run `mvn -version` in your terminal to verify proper installation of maven.
> 
> ```
> C:\Users\subhn>mvn --version
> Apache Maven 3.8.9 (e26b057cc3a17459358ef53e4d0e2e381bf08a1c)
> Maven home: C:\Users\subhn\Downloads\apache-maven-3.8.9-bin\apache-maven-3.8.9
> Java version: 21.0.6, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-21
> Default locale: en_US, platform encoding: UTF-8
> OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
> ```

### Setup

*   Clone the Repository

```
C:\Users\subhn>git clone [Your Repository URL]
C:\Users\subhn>cd attendance-management-system
```

---

*   Compile the project:

```
C:\Users\subhn>mvn clean compile
```

## Running Tests

*   Run the Automated Test Suite

```
C:\Users\subhn>mvn test
```

---

*   Run the CLI Demo:

```
C:\Users\subhn>mvn exec:java -Dexec.mainClass="com.company.attendance.App"
```

## Key Learning Outcomes

> `Applied **OOP design principles** in a real-world scenario.`
> 
> `Designed a **multi-layered architecture** separating data, logic, and presentation.`
> 
> `Implemented **custom exception handling** for business rules.`
> 
> `Built a **TestNG-driven QA suite** covering data, state, and temporal validation.`
> 
> `Developed a **robust backend logic** mimicking enterprise-level systems.`