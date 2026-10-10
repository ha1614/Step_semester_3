package uml.assigment_problems.problem5;

import java.util.ArrayList;
import java.util.List;

/*
 * SmartCard 1 <>---- 0..* Transaction (composition) ; SmartCard 1 ---- 1 PricingPlan
 * PricingPlan <<interface>> <|.. DayScholarPlan, HostellerPlan, StaffPlan
 * Purchase transaction 1 ---- 0..1 Refund transaction (one-time refund)
 * The balance is DERIVED from the transaction list, so "balance == sum of transactions" always holds.
 * Card state: ACTIVE <-> BLOCKED.
 */
interface PricingPlan {
    String name();

    double priceFor(double listPrice);
}

class DayScholarPlan implements PricingPlan {
    public String name() {
        return "Day Scholar";
    }

    public double priceFor(double p) {
        return p;
    }
}

class HostellerPlan implements PricingPlan {
    public String name() {
        return "Hosteller";
    }

    public double priceFor(double p) {
        return p * 0.90;
    }
}

class StaffPlan implements PricingPlan {
    public String name() {
        return "Staff";
    }

    public double priceFor(double p) {
        return p * 0.80;
    }
}

enum TxnType {
    TOP_UP, PURCHASE, REFUND
}

final class Transaction {
    private final TxnType type;
    private final String description;
    private final double amount; // signed: + credit, - debit
    private boolean refunded;

    Transaction(TxnType type, String description, double amount) {
        this.type = type;
        this.description = description;
        this.amount = amount;
    }

    TxnType getType() {
        return type;
    }

    String getDescription() {
        return description;
    }

    double getAmount() {
        return amount;
    }

    boolean isRefunded() {
        return refunded;
    }

    void markRefunded() {
        refunded = true;
    }
}

class SmartCard {
    static final double MIN_TOP_UP = 100;
    static final double MAX_BALANCE = 5000;

    private final String cardId;
    private final PricingPlan plan;
    private final List<Transaction> transactions = new ArrayList<>();
    private boolean blocked;

    SmartCard(String cardId, PricingPlan plan) {
        this.cardId = cardId;
        this.plan = plan;
    }

    double getBalance() {
        double sum = 0;
        for (Transaction t : transactions) {
            sum += t.getAmount();
        }
        return sum;
    }

    private boolean checkActive(String action) {
        if (blocked) {
            System.out.println(action + " failed: Card " + cardId + " is blocked.");
            return false;
        }
        return true;
    }

    boolean topUp(double amount) {
        if (!checkActive("Top-up")) {
            return false;
        }
        if (amount < MIN_TOP_UP) {
            System.out.printf("Top-up failed: Minimum top-up is ₹%.2f.%n", MIN_TOP_UP);
            return false;
        }
        if (getBalance() + amount > MAX_BALANCE) {
            System.out.printf("Top-up failed: Balance cannot exceed ₹%.2f.%n", MAX_BALANCE);
            return false;
        }
        transactions.add(new Transaction(TxnType.TOP_UP, "Top-up", amount));
        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n", cardId, amount, getBalance());
        return true;
    }

    Transaction purchase(String item, double listPrice) {
        if (!checkActive("Purchase")) {
            return null;
        }
        double price = plan.priceFor(listPrice);
        if (price > getBalance()) {
            System.out.printf("Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    price, getBalance());
            return null;
        }
        Transaction t = new Transaction(TxnType.PURCHASE, item, -price);
        transactions.add(t);
        System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.%n", item, price, getBalance());
        return t;
    }

    boolean refund(Transaction purchase) {
        if (purchase == null || purchase.getType() != TxnType.PURCHASE || !transactions.contains(purchase)) {
            System.out.println("Refund rejected: Unknown purchase.");
            return false;
        }
        if (purchase.isRefunded()) {
            System.out.println("Refund rejected: " + purchase.getDescription() + " has already been refunded.");
            return false;
        }
        double amount = -purchase.getAmount();
        purchase.markRefunded();
        transactions.add(new Transaction(TxnType.REFUND, "Refund " + purchase.getDescription(), amount));
        System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
                amount, purchase.getDescription(), getBalance());
        return true;
    }

    void block() {
        blocked = true;
        System.out.println(cardId + " blocked.");
    }

    void unblock() {
        blocked = false;
        System.out.println(cardId + " unblocked.");
    }

    String miniStatement() {
        StringBuilder sb = new StringBuilder("Mini-statement for " + cardId + ": ");
        for (int i = 0; i < transactions.size(); i++) {
            double a = transactions.get(i).getAmount();
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(a >= 0 ? "+" : "-").append(String.format("%.2f", Math.abs(a)));
        }
        sb.append(String.format(" = ₹%.2f.", getBalance()));
        return sb.toString();
    }
}

public class CanteenSmartCard {
    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());
        card.topUp(500);
        Transaction thali = card.purchase("Veg Thali", 120);
        card.purchase("Cold Coffee", 60);
        card.purchase("Items", 400);
        card.refund(thali);
        card.refund(thali);
        System.out.println(card.miniStatement());

        card.block();
        card.topUp(200);  // rejected while blocked
        card.unblock();
    }
}
