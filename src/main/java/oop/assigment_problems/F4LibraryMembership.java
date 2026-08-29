package oop.assigment_problems;

// F4: Designing the Instance/Static Boundary for a Library Membership System

// ---------- THE BROKEN VERSION (everything static) ----------
class BrokenLibraryMember {
    static String name;        // WRONG: each member has their own name. One shared
                               // slot means member 2 overwrites member 1.
    static String memberId;    // WRONG: an ID must be unique per member. Shared =>
                               // every member ends up with the last ID assigned.
    static int booksIssued;    // WRONG: books borrowed belong to one person. Shared
                               // => everyone's counts pile into a single number.

    BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }
}

// ---------- THE CORRECTED VERSION ----------
class LibraryMember {
    private String name;               // instance: differs per member
    private String memberId;           // instance: unique per member
    private int booksIssued;           // instance: differs per member

    static String libraryName = "City Central Library";  // static: same for all
    static int memberCount = 1000;                       // static: one running counter

    LibraryMember(String name, int booksIssued) {
        memberCount++;
        this.name = name;
        this.memberId = "LM-" + memberCount;   // ID derived from the shared counter
        this.booksIssued = booksIssued;
    }

    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    static void printTotalMembers() {
        System.out.println("Total members: " + (memberCount - 1000));
    }
}

public class F4LibraryMembership {
    public static void main(String[] args) {
        System.out.println("Broken version:");
        BrokenLibraryMember m1 = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember m2 = new BrokenLibraryMember("Rohan", "LM-1002", 1);
        System.out.println(BrokenLibraryMember.name);   // prints Rohan
        System.out.println(BrokenLibraryMember.name);   // Aditi's data is gone

        System.out.println();
        System.out.println("Fixed version:");
        LibraryMember a = new LibraryMember("Aditi", 2);
        LibraryMember b = new LibraryMember("Rohan", 1);
        a.printMemberCard();
        b.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}
