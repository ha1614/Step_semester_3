package oop.assigment_problems;

// F2: Extending Employee Without Touching It
// Employee is "already tested" - it is never edited. The two new types only ADD.

class Employee {
    private String empId;
    private String empName;
    private double salary;

    Employee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    String getEmpId() {
        return empId;
    }

    String getEmpName() {
        return empName;
    }

    double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    InternEmployee(String empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {
        if (getSalary() < stipendCap) {
            return getSalary();
        }
        return stipendCap;
    }
}

public class F2EmployeeHierarchy {
    public static void main(String[] args) {
        Employee plain = new Employee("E101", "Karan", 40000);
        Employee manager = new ManagerEmployee("E102", "Divya", 70000, 8000);
        Employee intern = new InternEmployee("E103", "Meera", 12000, 10000);

        Employee[] staff = { plain, manager, intern };

        for (int i = 0; i < staff.length; i++) {
            Employee e = staff[i];
            if (e instanceof ManagerEmployee) {
                ManagerEmployee m = (ManagerEmployee) e;
                System.out.println("Manager effective pay: Rs " + m.effectiveSalary());
            } else if (e instanceof InternEmployee) {
                InternEmployee in = (InternEmployee) e;
                System.out.println("Intern effective pay: Rs " + in.effectiveSalary());
            } else {
                System.out.println("Plain employee pay: Rs " + e.getSalary());
            }
        }
    }
}
