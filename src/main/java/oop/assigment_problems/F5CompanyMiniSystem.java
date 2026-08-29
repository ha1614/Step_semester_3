package oop.assigment_problems;

// F5 Capstone: HR + Parking mini-system.
// Reuses Employee / ManagerEmployee / InternEmployee (F2) and ParkingSlot (F3),
// which live in this same package.

class CompanyEmployeeRecord {
    String name;
    String empId;
    Employee employee;    // a field that is itself an object -> composition
    ParkingSlot slot;     // may stay null -> must be handled safely

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    private double pay() {
        if (employee instanceof ManagerEmployee) {
            return ((ManagerEmployee) employee).effectiveSalary();
        }
        if (employee instanceof InternEmployee) {
            return ((InternEmployee) employee).effectiveSalary();
        }
        return employee.getSalary();
    }

    String fullProfile() {
        String slotText = (slot == null) ? "no parking assigned" : slot.slotNo;
        return name + " | Pay: Rs " + pay() + " | Slot: " + slotText;
    }
}

public class F5CompanyMiniSystem {
    public static void main(String[] args) {
        ParkingSlot[] slots = { new ParkingSlot("A1", 1, 0), new ParkingSlot("A2", 1, 0) };

        Employee divya = new ManagerEmployee("E102", "Divya", 70000, 8000);
        Employee karan = new Employee("E101", "Karan", 40000);
        Employee meera = new InternEmployee("E103", "Meera", 12000, 10000);

        // allot parking to only the first two, on purpose
        ParkingSlot s1 = ParkingSlot.findAvailableSlot(slots);
        if (s1 != null) {
            s1.occupiedCount++;
        }
        ParkingSlot s2 = ParkingSlot.findAvailableSlot(slots);
        if (s2 != null) {
            s2.occupiedCount++;
        }

        CompanyEmployeeRecord[] records = {
            new CompanyEmployeeRecord("Divya", "E102", divya, s1),
            new CompanyEmployeeRecord("Karan", "E101", karan, s2),
            new CompanyEmployeeRecord("Meera", "E103", meera, null)
        };

        for (int i = 0; i < records.length; i++) {
            System.out.println(records[i].fullProfile());
        }
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
