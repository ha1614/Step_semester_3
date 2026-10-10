package abstraction.assigment_problems.problem5;

public abstract class HomeDevice {
    private static int serialCounter = 1000;

    private final String serialNumber;

    protected HomeDevice() {
        serialCounter++;
        this.serialNumber = "HD-" + serialCounter;
    }

    public abstract String activate();

    String getSerialNumber() {
        return serialNumber;
    }
}
