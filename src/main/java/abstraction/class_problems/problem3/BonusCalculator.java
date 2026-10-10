package abstraction.class_problems.problem3;

public class BonusCalculator {

    static String getAuditIfApplicable(StaffMember s) {
        if (s instanceof Auditable) {
            return ((Auditable) s).auditRecord();
        }
        return "No audit required";
    }

    public static void main(String[] args) {
        TeamLead t = new TeamLead(60000, 5);
        System.out.println(t.calculateBonus()); // 6000.0

        TeamLead t2 = new TeamLead(60000, 0.20, 5);
        System.out.println(t2.calculateBonus()); // 12000.0

        t.setSalary(-5000);              // rejected, salary unchanged
        System.out.println(t.getSalary()); // 60000.0

        StaffMember ref = t; // upcasting: TeamLead stored as its parent type
        System.out.println(getAuditIfApplicable(ref));
    }
}
