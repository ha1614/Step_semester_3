package uml.assigment_problems.problem4;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/*
 * Student <<abstract>> <|-- RegularStudent(24), HonorsStudent(28), ExchangeStudent(20)
 * Student 0..* ---- 0..* Elective (via Enrollment) ; Elective holds 0..capacity enrolled students
 * Elective 1 ---- 0..* waitlisted Student (private FIFO queue)
 * Invariants: enrolled.size() <= capacity ; a student is never both enrolled and waitlisted / twice.
 * EnrollmentService only calls student.getCreditLimit(), so new student types need no workflow change.
 */
abstract class Student {
    private final String name;
    private int currentCredits;

    Student(String name, int currentCredits) {
        this.name = name;
        this.currentCredits = currentCredits;
    }

    String getName() {
        return name;
    }

    int getCurrentCredits() {
        return currentCredits;
    }

    void addCredits(int c) {
        currentCredits += c;
    }

    abstract int getCreditLimit();

    abstract String typeName();
}

class RegularStudent extends Student {
    RegularStudent(String name, int credits) {
        super(name, credits);
    }

    int getCreditLimit() {
        return 24;
    }

    String typeName() {
        return "Regular";
    }
}

class HonorsStudent extends Student {
    HonorsStudent(String name, int credits) {
        super(name, credits);
    }

    int getCreditLimit() {
        return 28;
    }

    String typeName() {
        return "Honors";
    }
}

class ExchangeStudent extends Student {
    ExchangeStudent(String name, int credits) {
        super(name, credits);
    }

    int getCreditLimit() {
        return 20;
    }

    String typeName() {
        return "Exchange";
    }
}

class Elective {
    private final String name;
    private final int credits;
    private final int capacity;
    private final List<Student> enrolled = new ArrayList<>();
    private final Deque<Student> waitlist = new ArrayDeque<>();

    Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    String getName() {
        return name;
    }

    int getCredits() {
        return credits;
    }

    boolean isEnrolled(Student s) {
        return enrolled.contains(s);
    }

    boolean isWaitlisted(Student s) {
        return waitlist.contains(s);
    }

    boolean isFull() {
        return enrolled.size() >= capacity;
    }

    void enroll(Student s) {
        if (isFull()) {
            throw new IllegalStateException("capacity exceeded");
        }
        enrolled.add(s);
    }

    int addToWaitlist(Student s) {
        waitlist.addLast(s);
        return waitlist.size();
    }

    boolean remove(Student s) {
        return enrolled.remove(s);
    }

    Iterator<Student> waitlistIterator() {
        return waitlist.iterator();
    }
}

class EnrollmentService {

    private static String credits(Student s) {
        return "(credits: " + s.getCurrentCredits() + "/" + s.getCreditLimit() + ")";
    }

    private static boolean withinLimit(Student s, Elective e) {
        return s.getCurrentCredits() + e.getCredits() <= s.getCreditLimit();
    }

    void enroll(Student s, Elective e) {
        if (e.isEnrolled(s) || e.isWaitlisted(s)) {
            System.out.println("Enrollment failed: " + s.getName() + " is already enrolled or waitlisted for " + e.getName() + ".");
            return;
        }
        if (!withinLimit(s, e)) {
            System.out.println("Enrollment failed: " + s.getName() + " would exceed the " + s.typeName()
                    + " credit limit (" + (s.getCurrentCredits() + e.getCredits()) + "/" + s.getCreditLimit() + ").");
            return;
        }
        if (e.isFull()) {
            int pos = e.addToWaitlist(s);
            System.out.println(e.getName() + " is full. " + s.getName() + " added to waitlist (position " + pos + ").");
            return;
        }
        e.enroll(s);
        s.addCredits(e.getCredits());
        System.out.println(s.getName() + " enrolled in " + e.getName() + " " + credits(s) + ".");
    }

    // drop + promote run together as one consistent operation
    synchronized void drop(Student s, Elective e) {
        if (!e.remove(s)) {
            System.out.println("Drop failed: " + s.getName() + " is not enrolled in " + e.getName() + ".");
            return;
        }
        s.addCredits(-e.getCredits());
        System.out.println(s.getName() + " dropped " + e.getName() + " " + credits(s) + ".");

        Iterator<Student> it = e.waitlistIterator();
        while (it.hasNext() && !e.isFull()) {
            Student next = it.next();
            if (withinLimit(next, e)) {
                it.remove();
                e.enroll(next);
                next.addCredits(e.getCredits());
                System.out.println(next.getName() + " promoted from waitlist and enrolled in " + e.getName() + " " + credits(next) + ".");
            }
        }
    }
}

public class ElectiveSeatRush {
    public static void main(String[] args) {
        EnrollmentService service = new EnrollmentService();
        Elective cloud = new Elective("Cloud Computing", 4, 2);

        Student asha = new RegularStudent("Asha", 20);
        Student ravi = new HonorsStudent("Ravi", 22);
        Student neha = new ExchangeStudent("Neha", 12);
        Student kiran = new RegularStudent("Kiran", 22);

        service.enroll(asha, cloud);
        service.enroll(ravi, cloud);
        service.enroll(neha, cloud);
        service.enroll(kiran, cloud);
        service.drop(asha, cloud);
    }
}
