package abstraction.assigment_problems.problem1;

public abstract class Shape {
    private static int shapeCounter = 1000;

    private final String shapeId;

    protected Shape() {
        shapeCounter++;
        this.shapeId = "SHP-" + shapeCounter;
    }

    public abstract double calculateArea();

    // Hooks each subclass uses to resize its own dimensions
    protected abstract void applyScale(double xFactor, double yFactor);

    void scale(double factor) {
        applyScale(factor, factor);
    }

    void scale(double xFactor, double yFactor) {
        applyScale(xFactor, yFactor);
    }

    String getShapeId() {
        return shapeId;
    }
}
