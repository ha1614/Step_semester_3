package oop.class_problems;

public final class F4InstanceStaticBoundary {
    private F4InstanceStaticBoundary() {
    }

    private static final class BrokenSrmStudent {
        // Wrong: these values describe one student, but static stores one shared value for the class.
        static String name;
        static String regNo;
        static int attendance;

        BrokenSrmStudent(String name, String regNo, int attendance) {
            BrokenSrmStudent.name = name;
            BrokenSrmStudent.regNo = regNo;
            BrokenSrmStudent.attendance = attendance;
        }
    }

    private static final class SrmStudent {
        private final String name;
        private final String regNo;
        private final int attendance;
        private static final String university = "SRM Institute of Science and Technology";
        private static int admissionCount;

        private SrmStudent(String name, int attendance) {
            if (name == null || name.trim().isEmpty() || attendance < 0 || attendance > 100) {
                throw new IllegalArgumentException("Invalid student details");
            }
            admissionCount++;
            this.name = name;
            this.regNo = String.format("RA2311003010%02d", admissionCount);
            this.attendance = attendance;
        }

        private void printIdCard() {
            System.out.println(name + " | " + regNo + " | " + attendance + "% | " + university);
        }

        private static void printTotalAdmissions() {
            System.out.println("Students admitted so far: " + admissionCount);
        }
    }

    public static void main(String[] args) {
        System.out.println("Broken version:");
        new BrokenSrmStudent("Ravi", "RA231100301011", 82);
        new BrokenSrmStudent("Meera", "RA231100301012", 74);
        System.out.println(BrokenSrmStudent.name);
        System.out.println(BrokenSrmStudent.name + " (Ravi's data was overwritten)");

        System.out.println("Fixed version:");
        SrmStudent ravi = new SrmStudent("Ravi", 82);
        SrmStudent meera = new SrmStudent("Meera", 74);
        ravi.printIdCard();
        meera.printIdCard();
        SrmStudent.printTotalAdmissions();
    }
}
