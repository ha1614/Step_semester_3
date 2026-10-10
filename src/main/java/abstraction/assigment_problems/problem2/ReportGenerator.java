package abstraction.assigment_problems.problem2;

public class ReportGenerator implements Exportable {
    private final String reportName;

    public ReportGenerator(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public String exportData() {
        ExportTracker.recordExport();
        return "Exported report: " + reportName;
    }
}
