package accessmodifiers.assigment_problems.problem1;

public class LibraryMember {
    // Fields kept private - only reachable through the constructor / accessors below.
    private String membershipId;
    private String branchCode;
    private double finesOwed;
    private String displayName;

    // Only a validating parameterized constructor exists - no usable no-arg constructor.
    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        if (membershipId == null || membershipId.trim().isEmpty() || membershipId.trim().length() < 4) {
            throw new IllegalArgumentException("membershipId must be non-blank and at least 4 characters");
        }
        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public static void main(String[] args) {
        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair"); // "LB9" is 3 chars - too short
            System.out.println("construction succeeded (unexpected)");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        LibraryMember m = new LibraryMember("LB94", "BR1", 0, "Priya Nair"); // succeeds
        System.out.println("construction succeeded: " + m.getMembershipId());
    }
}
