package accessmodifiers.assigment_problems.problem5;

import java.util.regex.Pattern;

public final class LoanReceipt {
    private static final Pattern BOOK_ID_PATTERN = Pattern.compile("^BK-\\d{3}$");

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        if (bookIds == null) {
            throw new IllegalArgumentException("bookIds cannot be null");
        }
        for (String id : bookIds) {
            if (id == null || !BOOK_ID_PATTERN.matcher(id).matches()) {
                throw new IllegalArgumentException("Invalid book id: " + id);
            }
        }
        this.memberId = memberId;
        this.bookIds = bookIds.clone(); // defensive copy on the way in
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone(); // defensive copy on the way out
    }

    // "Wither" pattern: correcting a receipt returns a brand-new immutable object.
    public LoanReceipt withCorrectedBookId(int index, String newId) {
        String[] updated = bookIds.clone();
        updated[index] = newId;
        return new LoanReceipt(memberId, updated);
    }

    public static void main(String[] args) {
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println(r.getBookIds()[0]); // BK-100 - internal state untouched
    }
}
