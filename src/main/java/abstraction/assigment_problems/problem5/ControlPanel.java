package abstraction.assigment_problems.problem5;

public class ControlPanel {

    static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable item : items) {
            System.out.println(item.connect(appId));
        }
    }

    static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            return ((EnergyTrackable) d).getConsumptionWatts();
        }
        System.out.println("Device is not energy-trackable");
        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));

        Refrigerator fridge = new Refrigerator(150.0);
        System.out.println(getConsumptionIfTrackable(fridge)); // 150.0

        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println(app.connect("HomeConnect"));

        HomeDevice ref = wm; // upcasting: WashingMachine stored as its parent type
        System.out.println(getConsumptionIfTrackable(ref)); // 500.0

        connectAll(new RemoteControllable[]{wm, app}, "HomeConnect");
    }
}
