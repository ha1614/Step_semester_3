package abstraction.class_problems.problem5;

public class DigitalPass implements Renewable {
    private final String resourceName;

    public DigitalPass(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String renew() {
        return resourceName + " renewed";
    }
}
