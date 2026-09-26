package oop.class_problems;

public final class F5FeeHostelMiniSystem {
    private F5FeeHostelMiniSystem() {
    }

    private static final class SrmStudentRecord {
        private static int totalStudents;
        private final String name;
        private final String regNo;
        private final F2ScholarshipFeeAccounts.HostelFeeAccount feeAccount;
        private final HostelRoom room;

        private SrmStudentRecord(String name, String regNo,
                                 F2ScholarshipFeeAccounts.HostelFeeAccount feeAccount, HostelRoom room) {
            this.name = name;
            this.regNo = regNo;
            this.feeAccount = feeAccount;
            this.room = room;
            totalStudents++;
        }

        private String fullStatus() {
            String roomText = room == null ? "unallotted" : room.roomNo;
            return name + " | Due: Rs " + feeAccount.getDue() + " | Room: " + roomText;
        }
    }

    private static HostelRoom allotNext(HostelRoom[] rooms, String studentName) {
        HostelRoom room = F3HostelRoomAllocation.findAvailableRoom(rooms);
        return room != null && room.allot(studentName) ? room : null;
    }

    public static void main(String[] args) {
        HostelRoom[] rooms = {new HostelRoom("C-214", 1, 0), new HostelRoom("C-507", 1, 0)};
        F2ScholarshipFeeAccounts.HostelFeeAccount raviFees =
            new F2ScholarshipFeeAccounts.HostelFeeAccount("RA011", 200000);
        F2ScholarshipFeeAccounts.HostelFeeAccount anithaFees =
            new F2ScholarshipFeeAccounts.HostelFeeAccount("RA012", 200000);
        F2ScholarshipFeeAccounts.HostelFeeAccount karthikFees =
            new F2ScholarshipFeeAccounts.HostelFeeAccount("RA013", 200000);
        raviFees.payInTwoInstallments(30000);
        anithaFees.pay(20000);
        karthikFees.pay(-1000); // Invalid payment is rejected and leaves the due unchanged.

        SrmStudentRecord[] students = {
            new SrmStudentRecord("Ravi", "RA011", raviFees, allotNext(rooms, "Ravi")),
            new SrmStudentRecord("Anitha", "RA012", anithaFees, allotNext(rooms, "Anitha")),
            new SrmStudentRecord("Karthik", "RA013", karthikFees, null)
        };
        for (SrmStudentRecord student : students) System.out.println(student.fullStatus());
        System.out.println("Total students: " + SrmStudentRecord.totalStudents);
    }
}
