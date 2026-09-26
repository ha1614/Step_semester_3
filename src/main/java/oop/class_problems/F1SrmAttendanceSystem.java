package oop.class_problems;

public final class F1SrmAttendanceSystem {
    private F1SrmAttendanceSystem() {
    }

    private static final class SrmStudent {
        private final String name;
        private final String regNo;
        private int attendance;

        private SrmStudent(String name, String regNo, int attendance) {
            this.name = name;
            this.regNo = regNo;
            addAttendanceUpdate(attendance);
        }

        private void addAttendanceUpdate(int newAttendance) {
            if (newAttendance < 0 || newAttendance > 100) {
                throw new IllegalArgumentException("Attendance must be from 0 to 100");
            }
            attendance = newAttendance;
        }

        private boolean isEligible() {
            return attendance >= 75;
        }
    }

    // Static because it aggregates an array; eligibility belongs to each individual student.
    private static double classAverage(SrmStudent[] students) {
        if (students == null || students.length == 0) return 0.0;
        int total = 0;
        int count = 0;
        for (SrmStudent student : students) {
            if (student != null) {
                total += student.attendance;
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) total / count;
    }

    public static void main(String[] args) {
        SrmStudent[] students = {
            new SrmStudent("Ravi", "RA231100301011", 82),
            new SrmStudent("Anitha", "RA231100301012", 68),
            new SrmStudent("Karthik", "RA231100301013", 91),
            new SrmStudent("Meera", "RA231100301014", 74),
            new SrmStudent("Suresh", "RA231100301015", 60)
        };
        for (SrmStudent student : students) {
            System.out.println(student.name + " - " + student.attendance + "% - "
                + (student.isEligible() ? "Eligible" : "Detained") + " (" + student.regNo + ")");
        }
        System.out.printf("Class average: %.1f%%%n", classAverage(students));
    }
}
