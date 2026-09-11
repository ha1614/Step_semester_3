package foodapp.assigment_problems;

public class Canteen implements Comparable<Canteen> {
    private String canteenCode;
    private String canteenName;
    private int trustScore;

    // "this" resolves the field/parameter naming clash below.
    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    // Chains to the full constructor with a sensible default trust score.
    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    // Tie-break order: higher trustScore first, then canteenCode (case-insensitive),
    // then shorter canteenName. Deterministic every time - never depends on input order.
    @Override
    public int compareTo(Canteen other) {
        if (this.trustScore != other.trustScore) {
            return other.trustScore - this.trustScore;
        }
        int codeCompare = this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeCompare != 0) {
            return codeCompare;
        }
        return this.canteenName.length() - other.canteenName.length();
    }

    // Manual bubble sort - no built-in sort utility used.
    public static Canteen[] rankCanteens(Canteen[] canteens) {
        Canteen[] result = canteens.clone();
        for (int i = 0; i < result.length - 1; i++) {
            for (int j = 0; j < result.length - 1 - i; j++) {
                if (result[j].compareTo(result[j + 1]) > 0) {
                    Canteen temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };
        Canteen[] ranked = rankCanteens(canteens);
        for (Canteen c : ranked) {
            System.out.print(c.getCanteenCode() + " "); // hb1-c HB2-C HB3-C
        }
    }
}
