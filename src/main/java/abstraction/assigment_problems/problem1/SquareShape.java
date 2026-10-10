package abstraction.assigment_problems.problem1;

public class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        super();
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    protected void applyScale(double xFactor, double yFactor) {
        // A square must stay square, so it grows by the average of both factors
        side *= (xFactor + yFactor) / 2;
    }
}
