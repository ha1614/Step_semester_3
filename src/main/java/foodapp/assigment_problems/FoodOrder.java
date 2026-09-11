package foodapp.assigment_problems;

public class FoodOrder {
    private String studentName;
    private String dishName;
    private boolean delivered;

    // Only a validating parameterized constructor exists - no usable no-arg constructor.
    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("studentName cannot be blank");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("dishName cannot be blank");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }

    public void markDelivered() {
        if (delivered) {
            System.out.println("Order for " + studentName + " (" + dishName + ") was already marked delivered!");
        } else {
            delivered = true;
            System.out.println("Order for " + studentName + " (" + dishName + ") marked delivered.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        int accepted = 0;
        int rejected = 0;
        for (String[] raw : rawOrders) {
            try {
                new FoodOrder(raw[0], raw[1]);
                accepted++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        System.out.println("Valid: " + accepted + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };
        processBatch(rawOrders); // Valid: 2 | Rejected: 2
    }
}
