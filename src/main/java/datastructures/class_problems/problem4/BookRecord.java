package datastructures.class_problems.problem4;

public class BookRecord {
    private final String isbn;
    private final String title;

    public BookRecord(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }
}
