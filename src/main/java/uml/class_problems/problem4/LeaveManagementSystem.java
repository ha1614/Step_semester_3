package uml.class_problems.problem4;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/*
 * Employee <<abstract>> <|-- FullTimeEmployee, PartTimeEmployee, ContractEmployee (each defines its leave policy)
 * Employee 1 ---- 0..* LeaveRequest ; LeaveManager reviews LeaveRequests.
 * State machine: PENDING -> APPROVED | REJECTED ; APPROVED/REJECTED are final.
 */
enum LeaveStatus {
    PENDING("Pending"), APPROVED("Approved"), REJECTED("Rejected");

    private final String label;

    LeaveStatus(String label) {
        this.label = label;
    }

    String label() {
        return label;
    }
}

abstract class Employee {
    private final String name;

    Employee(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    abstract int maxLeaveDaysPerRequest();
}

class FullTimeEmployee extends Employee {
    FullTimeEmployee(String name) {
        super(name);
    }

    @Override
    int maxLeaveDaysPerRequest() {
        return 20;
    }
}

class PartTimeEmployee extends Employee {
    PartTimeEmployee(String name) {
        super(name);
    }

    @Override
    int maxLeaveDaysPerRequest() {
        return 10;
    }
}

class ContractEmployee extends Employee {
    ContractEmployee(String name) {
        super(name);
    }

    @Override
    int maxLeaveDaysPerRequest() {
        return 5;
    }
}

class LeaveRequest {
    private final Employee employee;
    private final LocalDate from;
    private final LocalDate to;
    private LeaveStatus status = LeaveStatus.PENDING;

    LeaveRequest(Employee employee, LocalDate from, LocalDate to) {
        this.employee = employee;
        this.from = from;
        this.to = to;
    }

    Employee getEmployee() {
        return employee;
    }

    LocalDate getFrom() {
        return from;
    }

    LocalDate getTo() {
        return to;
    }

    LeaveStatus getStatus() {
        return status;
    }

    long days() {
        return ChronoUnit.DAYS.between(from, to) + 1;
    }

    void changeStatus(LeaveStatus newStatus) {
        if (status != LeaveStatus.PENDING) {
            throw new IllegalStateException(status.label() + " request cannot revert to " + newStatus.label());
        }
        if (newStatus == LeaveStatus.PENDING) {
            throw new IllegalStateException("Request is already Pending");
        }
        status = newStatus;
    }
}

class LeaveManager {

    LeaveRequest submit(Employee e, LocalDate from, LocalDate to) {
        LeaveRequest r = new LeaveRequest(e, from, to);
        System.out.println("Leave request submitted by " + e.getName() + " for " + from + " to " + to
                + ". Status: " + r.getStatus().label() + ".");
        return r;
    }

    void review(LeaveRequest r) {
        boolean withinPolicy = r.days() <= r.getEmployee().maxLeaveDaysPerRequest();
        setStatus(r, withinPolicy ? LeaveStatus.APPROVED : LeaveStatus.REJECTED);
    }

    void setStatus(LeaveRequest r, LeaveStatus status) {
        try {
            r.changeStatus(status);
            System.out.println("Leave request for " + r.getEmployee().getName() + " " + status.label().toLowerCase()
                    + ". Status: " + status.label() + ".");
        } catch (IllegalStateException ex) {
            System.out.println("Cannot change status: " + ex.getMessage() + ".");
        }
    }
}

public class LeaveManagementSystem {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();
        Employee john = new FullTimeEmployee("John Doe");
        Employee jane = new PartTimeEmployee("Jane Smith");

        LeaveRequest johnReq = manager.submit(john, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        manager.review(johnReq);
        manager.submit(jane, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));
        manager.setStatus(johnReq, LeaveStatus.PENDING);
    }
}
