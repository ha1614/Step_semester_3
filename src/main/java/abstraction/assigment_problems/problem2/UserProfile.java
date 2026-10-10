package abstraction.assigment_problems.problem2;

public class UserProfile implements Exportable {
    private final String username;

    public UserProfile(String username) {
        this.username = username;
    }

    @Override
    public String exportData() {
        ExportTracker.recordExport();
        return "Exported profile: " + username;
    }
}
