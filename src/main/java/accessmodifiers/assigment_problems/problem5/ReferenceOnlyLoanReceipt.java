package accessmodifiers.assigment_problems.problem5;

import java.util.regex.Pattern;

// NOTE: LoanReceipt is required to be final for true immutability, so it cannot be
// extended. ReferenceOnlyLoanReceipt is therefore its own independent, equally
// immutable type (same validation rules) rather than a LoanReceipt subclass -
// see NightlyProcessor for how the two are told apart with instanceof.
public final class ReferenceOnlyLoanReceipt {
    private static final Pattern BOOK_ID_PATTERN = Pattern.compile("^BK-\\d{3}$");

    private final String memberId;
    private final String[] bookIds;
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        if (bookIds == null) {
            throw new IllegalArgumentException("bookIds cannot be null");
        }
        for (String id : bookIds) {
            if (id == null || !BOOK_ID_PATTERN.matcher(id).matches()) {
                throw new IllegalArgumentException("Invalid book id: " + id);
            }
        }
        this.memberId = memberId;
        this.bookIds = bookIds.clone();
        this.roomNumber = roomNumber;
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone();
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
