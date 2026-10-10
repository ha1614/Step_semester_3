# Week 8 – Practice: Quiz & Concept Answers

## Quiz

| # | Answer | Why |
|---|--------|-----|
| 1 | **C** Abstraction and Runtime Polymorphism | Engine depends on a common abstraction; new transaction types plug in and are dispatched at runtime. |
| 2 | **B, C, D** Runtime Polymorphism, Inheritance, Dynamic Dispatch | Car/Motorcycle inherit from Vehicle; the overridden method is chosen at runtime. |
| 3 | **D** State Diagram | Models states (Available/Borrowed) and guarded transitions. |
| 4 | **C** Composition | Orders cannot exist without their Customer and are deleted with it. |
| 5 | **D** Composition | LineItems are created, owned and destroyed by the FoodOrder. |
| 6 | **C** Abstraction | Processor depends only on the `IPaymentMethod` abstraction (Open/Closed). |
| 7 | **C** Sequence Diagram | Shows ordered messages between Student, Examination and Submission. |
| 8 | **A, B, D** Data Hiding, Encapsulation, Maintaining Valid Object State | Private field + validating setter keeps the object valid. |
| 9 | **D** Realization | A class implementing an interface is a realization relationship. |
| 10 | **C** Course 1 --- 0..50 Student, Student 1 --- 0..* Course | A course holds at most 50 students; a student may take many courses. |

## Concept Questions

**1. Invariants in a ShoppingCart.** An invariant is a rule that must be true whenever the object is observable – here, `totalPrice == sum(lineItem prices)`. If `totalPrice` were public, outside code could change it without touching the items and break the rule. Encapsulation keeps `totalPrice` and the item list private and exposes only `addItem()`, `removeItem()` and `getTotalPrice()`. Each method updates the items and the total together (or derives the total on demand), so every state change goes through code that re-establishes the invariant. Invalid states can no longer be produced from outside.

**2. Composition over inheritance for SmartDevice capabilities.** With inheritance, every combination needs its own subclass (`WithCamera`, `WithGps`, `WithCameraAndGps`, …), so the class count grows exponentially (2ⁿ for n features). The hierarchy is also fixed at compile time. With composition, `SmartDevice` holds a collection of `Capability` objects (`Camera`, `Gps`, `Bluetooth`, each implementing a common interface). Any mix is just a different set of parts, and capabilities can be added or removed at runtime. A new feature is a single new class, and `SmartDevice` stays untouched.

**3. Sequence diagram for checkout.** Objects (`CheckoutManager`, `PaymentGateway`, `BankService`) sit across the top, each with a dashed vertical lifeline. Time flows downward, so message order is read top to bottom. A solid arrow `processPayment()` goes from CheckoutManager to PaymentGateway, then `authorize()` from PaymentGateway to BankService. Activation bars show when each object is busy. Dashed return arrows carry `authResult` back to the gateway and then `paymentStatus` back to the manager. The diagram makes the nested call chain, who waits on whom, and where responses flow back immediately visible, which a static class diagram cannot show.

**4. Abstraction in NotificationService.** Define `interface Notifier { void send(String to, String msg); }` with `EmailNotifier`, `SmsNotifier` and `PushNotifier` implementations. The client and `NotificationService` depend only on `Notifier`, and the concrete class is injected (constructor or config). To add a new channel (e.g. a `WhatsAppNotifier`), you write one new class that implements the interface and register it. No client code changes, because the client never referenced a concrete class. This is the Dependency Inversion and Open/Closed principles at work.

**5. Aggregation vs Composition (University / Department / Professor).** *Composition* is strong ownership: the part's lifecycle is bound to the whole. University ◆— Department: departments are created by and exist only within the university, and when it is dissolved they disappear too. *Aggregation* is a weak "has-a": the part can exist independently and be shared. Department ◇— Professor: a department groups professors, but professors outlive the department and can join another institution. Ownership and lifecycle dependency are what decide between the two.
