package abstraction.assigment_problems.problem2;

public class ExportTracker {
    private static int totalExports = 0;

    private ExportTracker() {
    }

    static void recordExport() {
        totalExports++;
    }

    static int getTotalExports() {
        return totalExports;
    }

    static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }

    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        UserProfile u = new UserProfile("jane_doe");

        Exportable ref = r; // upcasting: ReportGenerator stored as the Exportable interface type
        exportAll(new Exportable[]{ref, u});
        System.out.println(getTotalExports()); // 2
    }
}
