package inheritance.assigment_problems.problem2;

public class EliteRunnerEntry extends RunnerEntry {
    private final double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    public double getSponsorBonus() {
        return sponsorBonus;
    }

    @Override
    public String announce() {
        return "Elite Runner | Bib: " + getBibNumber() + " | Category: " + getCategory()
                + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
    }
}
