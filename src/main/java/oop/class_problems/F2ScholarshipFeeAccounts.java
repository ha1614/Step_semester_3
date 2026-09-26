package oop.class_problems;

public final class F2ScholarshipFeeAccounts {
    private F2ScholarshipFeeAccounts() {
    }

    static class FeeAccount {
        private final String regNo;
        private final double totalFee;
        private double amountPaid;

        FeeAccount(String regNo, double totalFee) {
            if (regNo == null || regNo.trim().isEmpty() || !Double.isFinite(totalFee) || totalFee < 0) {
                throw new IllegalArgumentException("A registration number and non-negative fee are required");
            }
            this.regNo = regNo;
            this.totalFee = totalFee;
        }

        boolean pay(double amount) {
            if (!Double.isFinite(amount) || amount <= 0) return false;
            amountPaid = Math.min(totalFee, amountPaid + amount);
            return true;
        }

        double getDue() {
            return Math.max(0.0, totalFee - amountPaid);
        }

        String getRegNo() {
            return regNo;
        }
    }

    static class HostelFeeAccount extends FeeAccount {
        HostelFeeAccount(String regNo, double totalFee) {
            super(regNo, totalFee);
        }

        void payInTwoInstallments(double amount) {
            if (!Double.isFinite(amount) || amount <= 0) return;
            pay(amount);
            pay(amount);
        }
    }

    static class ScholarshipFeeAccount extends FeeAccount {
        private final double scholarshipPercent;

        ScholarshipFeeAccount(String regNo, double totalFee, double scholarshipPercent) {
            super(regNo, totalFee);
            if (!Double.isFinite(scholarshipPercent) || scholarshipPercent < 0 || scholarshipPercent > 100) {
                throw new IllegalArgumentException("Scholarship must be between 0 and 100 percent");
            }
            this.scholarshipPercent = scholarshipPercent;
        }

        double effectiveDue() {
            return getDue() * (1.0 - scholarshipPercent / 100.0);
        }
    }

    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("RA001", 150000);
        HostelFeeAccount hostel = new HostelFeeAccount("RA002", 200000);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA003", 180000, 20);
        plain.pay(150000);
        hostel.payInTwoInstallments(30000);

        FeeAccount[] accounts = {plain, hostel, scholarship};
        for (FeeAccount account : accounts) {
            if (account instanceof ScholarshipFeeAccount) {
                System.out.println("Scholarship effective due: Rs "
                    + ((ScholarshipFeeAccount) account).effectiveDue());
            } else if (account instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + account.getDue());
            } else {
                System.out.println("Plain account due: Rs " + account.getDue());
            }
        }
    }
}
