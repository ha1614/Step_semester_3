package abstraction.class_problems.problem2;

public class AlertNetwork {

    static void broadcastAll(Alertable[] devices, String message) {
        for (Alertable device : devices) {
            System.out.println(device.sendAlert(message));
        }
    }

    static String getZoneIfMotionSensor(Alertable a) {
        if (a instanceof MotionSensor) {
            MotionSensor m = (MotionSensor) a;
            return m.getZoneName();
        }
        return "Not a motion sensor";
    }

    public static void main(String[] args) {
        MotionSensor m = new MotionSensor("Living Room");
        System.out.println(m.sendAlert("Motion detected"));

        DualZoneMotionSensor d = new DualZoneMotionSensor("Hallway", "Stairwell");
        System.out.println(d.sendAlert("Motion detected"));

        SmokeDetector s = new SmokeDetector("SD-01");
        System.out.println(s.sendAlert("Smoke detected"));

        System.out.println(getZoneIfMotionSensor(m));
        System.out.println(getZoneIfMotionSensor(s));

        broadcastAll(new Alertable[]{m, d, s}, "System test");
    }
}
