package accessmodifiers.assigment_problems.problem4;

public class LibraryMemberBean {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private int securityAnswerHash;
    private boolean membershipIdSet;

    // No-arg constructor required by the JavaBean-scanning framework.
    public LibraryMemberBean() {
        this.membershipIdSet = false;
    }

    // Chains through this() so there is one real initialization path.
    public LibraryMemberBean(String name) {
        this();
        this.name = name;
    }

    public LibraryMemberBean(String membershipId, String name) {
        this(name);
        setMembershipId(membershipId);
    }

    public String getMembershipId() {
        return membershipId;
    }

    // Write-once: the first call sets it for good, every later call is silently ignored.
    public void setMembershipId(String id) {
        if (!membershipIdSet) {
            this.membershipId = id;
            this.membershipIdSet = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // True write-only property: only a one-way hash is retained, and there is no
    // getSecurityAnswer()/isSecurityAnswer() anywhere that could hand the value back.
    public void setSecurityAnswer(String answer) {
        this.securityAnswerHash = (answer == null) ? 0 : answer.hashCode();
    }

    public static void main(String[] args) {
        System.out.println(new LibraryMemberBean("Priya Nair").getMembershipId()); // null
        System.out.println(new LibraryMemberBean("LIB-8841", "Priya Nair").getMembershipId()); // LIB-8841

        LibraryMemberBean m = new LibraryMemberBean();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000"); // silently ignored
        System.out.println(m.getMembershipId()); // LIB-8841
    }
}
