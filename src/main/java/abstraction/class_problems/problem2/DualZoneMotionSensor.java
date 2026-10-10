package abstraction.class_problems.problem2;

public class DualZoneMotionSensor extends MotionSensor {
    private final String secondZoneName;

    public DualZoneMotionSensor(String zoneName, String secondZoneName) {
        super(zoneName);
        this.secondZoneName = secondZoneName;
    }

    public String getSecondZoneName() {
        return secondZoneName;
    }

    @Override
    public String sendAlert(String message) {
        return super.sendAlert(message) + " [also covering " + secondZoneName + "]";
    }
}
