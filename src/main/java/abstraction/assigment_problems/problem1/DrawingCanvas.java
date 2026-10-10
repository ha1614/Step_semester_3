package abstraction.assigment_problems.problem1;

public class DrawingCanvas {

    static void printArea(Shape s) {
        System.out.println(s.getShapeId() + " area: " + s.calculateArea());
    }

    public static void main(String[] args) {
        // new Shape(); // does not compile: Shape is abstract
        CircleShape c = new CircleShape(5.0);
        System.out.printf("%.2f%n", c.calculateArea()); // ~78.54

        SquareShape sq = new SquareShape(4.0);
        System.out.println(sq.calculateArea()); // 16.0

        sq.scale(2.0); // uses the one-argument overload
        System.out.println(sq.calculateArea()); // 64.0

        printArea(c);
    }
}
