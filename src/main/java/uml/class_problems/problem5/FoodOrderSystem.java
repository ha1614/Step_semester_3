package uml.class_problems.problem5;

import java.util.ArrayList;
import java.util.List;

/*
 * Customer 1 ---- 0..* Order ; Order 1 <>---- 1..* LineItem (composition) ; LineItem * ---- 1 FoodItem
 * Restaurant 1 ---- 1..* FoodItem
 * Order ---> PaymentMethod <<interface>> (CreditCardPayment, DigitalWalletPayment, CashOnDelivery)
 * Order ---> OrderListener <<interface>> (Customer gets notified of events)
 * New payment methods only implement PaymentMethod; Order.placeAndPay never changes.
 */
interface PaymentMethod {
    String name();

    boolean pay(double amount);
}

class CreditCardPayment implements PaymentMethod {
    private final boolean simulateSuccess;

    CreditCardPayment(boolean simulateSuccess) {
        this.simulateSuccess = simulateSuccess;
    }

    public String name() {
        return "Credit Card";
    }

    public boolean pay(double amount) {
        return simulateSuccess;
    }
}

class DigitalWalletPayment implements PaymentMethod {
    private final boolean simulateSuccess;

    DigitalWalletPayment(boolean simulateSuccess) {
        this.simulateSuccess = simulateSuccess;
    }

    public String name() {
        return "Digital Wallet";
    }

    public boolean pay(double amount) {
        return simulateSuccess;
    }
}

class CashOnDelivery implements PaymentMethod {
    public String name() {
        return "Cash on Delivery";
    }

    public boolean pay(double amount) {
        return true;
    }
}

interface OrderListener {
    void onEvent(String message);
}

class FoodItem {
    private final String name;
    private final double price;

    FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    String getName() {
        return name;
    }

    double getPrice() {
        return price;
    }
}

class Restaurant {
    private final String name;
    private final List<FoodItem> menu = new ArrayList<>();

    Restaurant(String name) {
        this.name = name;
    }

    FoodItem addToMenu(String item, double price) {
        FoodItem f = new FoodItem(item, price);
        menu.add(f);
        return f;
    }
}

class LineItem {
    private final FoodItem item;
    private final int quantity;

    LineItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    double subtotal() {
        return item.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return item.getName() + " (Qty " + quantity + ")";
    }
}

enum OrderStatus {
    CREATED("Created"), PENDING_PAYMENT("Pending Payment"), PAID("Paid");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    String label() {
        return label;
    }
}

class Order {
    private static int nextId = 123;

    private int id; // order number is issued only when the order is actually placed
    private final List<LineItem> items = new ArrayList<>();
    private final OrderListener listener;
    private OrderStatus status = OrderStatus.CREATED;

    Order(OrderListener listener) {
        this.listener = listener;
    }

    int getId() {
        return id;
    }

    boolean isEmpty() {
        return items.isEmpty();
    }

    void addItem(FoodItem item, int quantity) {
        items.add(new LineItem(item, quantity)); // LineItems are created and owned by the Order
    }

    String describeItems() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(items.get(i));
        }
        return sb.toString();
    }

    double total() {
        double t = 0;
        for (LineItem li : items) {
            t += li.subtotal();
        }
        return t;
    }

    boolean placeAndPay(PaymentMethod payment) {
        if (items.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return false;
        }
        id = nextId++;
        System.out.println("Order placed successfully.");
        if (payment.pay(total())) {
            status = OrderStatus.PAID;
            System.out.println("Payment via " + payment.name() + " successful. Order status: " + status.label() + ".");
            listener.onEvent("Order #" + id + " placed and paid.");
            return true;
        }
        status = OrderStatus.PENDING_PAYMENT;
        System.out.println("Payment via " + payment.name() + " failed. Order status: " + status.label() + ".");
        listener.onEvent("Order #" + id + " placed, awaiting payment.");
        return false;
    }
}

class Customer implements OrderListener {
    private final String name;
    private final List<Order> orders = new ArrayList<>();

    Customer(String name) {
        this.name = name;
    }

    Order createOrder() {
        Order o = new Order(this);
        orders.add(o);
        System.out.println("Order created.");
        return o;
    }

    @Override
    public void onEvent(String message) {
        System.out.println("Notification: " + message);
    }
}

public class FoodOrderSystem {
    public static void main(String[] args) {
        Restaurant r = new Restaurant("Spice Hub");
        FoodItem pizza = r.addToMenu("Pizza", 250);
        FoodItem soda = r.addToMenu("Soda", 40);
        FoodItem burger = r.addToMenu("Burger", 150);

        Customer customer = new Customer("Kiran");

        Order empty = customer.createOrder();
        empty.placeAndPay(new CreditCardPayment(true)); // fails: empty cart

        Order order = customer.createOrder();
        order.addItem(pizza, 2);
        order.addItem(soda, 1);
        System.out.println("Added " + order.describeItems() + ".");
        order.placeAndPay(new CreditCardPayment(true));

        Order second = customer.createOrder();
        second.addItem(burger, 1);
        second.placeAndPay(new DigitalWalletPayment(false));
    }
}
