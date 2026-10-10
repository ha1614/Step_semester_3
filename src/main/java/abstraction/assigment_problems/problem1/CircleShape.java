package abstraction.assigment_problems.problem1;

public class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        super();
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    protected void applyScale(double xFactor, double yFactor) {
        // A circle cannot stretch unevenly, so it grows by the average of both factors
        radius *= (xFactor + yFactor) / 2;
    }
}
