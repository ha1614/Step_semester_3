package abstraction.class_problems.problem5;

public abstract class LibraryItem {
    private static int itemCounter = 1000;

    private final String itemId;
    private final String title;

    protected LibraryItem(String title) {
        itemCounter++;
        this.itemId = "LIB-" + itemCounter;
        this.title = title;
    }

    public abstract int getLoanPeriodDays();

    String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }
}
