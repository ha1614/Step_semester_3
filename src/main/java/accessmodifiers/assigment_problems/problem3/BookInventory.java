package accessmodifiers.assigment_problems.problem3;

public class BookInventory {
    // Private with no direct external setter - only reachable via checkOut()/checkIn().
    private int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {
        if (copiesTotal <= 0) {
            throw new IllegalArgumentException("copiesTotal must be positive");
        }
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    // Invariant maintained at every step: 0 <= copiesAvailable <= copiesTotal.
    public void checkOut() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
        // else: silently rejected - nothing left to take out
    }

    public void checkIn() {
        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
        // else: silently rejected - already at full capacity
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public static void main(String[] args) {
        try {
            new BookInventory(0);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        BookInventory b = new BookInventory(3);
        b.checkOut(); b.checkOut(); b.checkOut(); b.checkOut();
        System.out.println(b.getCopiesAvailable()); // 0

        b.checkIn(); b.checkIn(); b.checkIn(); b.checkIn();
        System.out.println(b.getCopiesAvailable()); // 3
    }
}
