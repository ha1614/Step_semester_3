package uml.assigment_problems.problem3;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Device 1 <>---- 0..* Capability (composition, keyed by capability type)
 * Capability <<interface>> <|.. PowerCapability, BrightnessCapability, TemperatureCapability
 * Scene 1 ---- 1..* SceneStep ; SceneStep ---> one Capability type + value
 * LabControlPanel 1 ---- 0..* Device, 0..* Scene
 *
 * Why it scales: a new capability (e.g. Timer) is ONE new class implementing Capability. Devices
 * gain it at runtime with addCapability(); no subclass per combination (which would be 2^n classes).
 * Each capability validates its own range, so a device is always valid after every command.
 */
interface Capability {
    String name();

    // returns a description of the change; throws IllegalArgumentException if value is invalid
    String apply(String deviceName, int value);
}

class PowerCapability implements Capability {
    private boolean on;

    public String name() {
        return "Power";
    }

    public String apply(String deviceName, int value) {
        if (value != 0 && value != 1) {
            throw new IllegalArgumentException(deviceName + " power must be ON (1) or OFF (0)");
        }
        on = value == 1;
        return deviceName + ": " + (on ? "ON" : "OFF");
    }
}

class BrightnessCapability implements Capability {
    private int level;

    public String name() {
        return "Brightness";
    }

    public String apply(String deviceName, int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException(deviceName + " brightness must be between 0% and 100%");
        }
        level = value;
        return deviceName + ": brightness set to " + level + "%";
    }
}

class TemperatureCapability implements Capability {
    private int celsius = 24;

    public String name() {
        return "Temperature";
    }

    public String apply(String deviceName, int value) {
        if (value < 16 || value > 30) {
            throw new IllegalArgumentException(deviceName + " temperature must be between 16°C and 30°C");
        }
        celsius = value;
        return deviceName + ": temperature set to " + celsius + "°C";
    }
}

class Device {
    private final String name;
    private final Map<Class<? extends Capability>, Capability> capabilities = new LinkedHashMap<>();

    Device(String name, Capability... caps) {
        this.name = name;
        for (Capability c : caps) {
            capabilities.put(c.getClass(), c);
        }
    }

    String getName() {
        return name;
    }

    boolean has(Class<? extends Capability> type) {
        return capabilities.containsKey(type);
    }

    void addCapability(Capability c) {
        capabilities.put(c.getClass(), c);
        System.out.println(name + ": " + c.name() + " capability added.");
    }

    boolean command(Class<? extends Capability> type, int value) {
        Capability c = capabilities.get(type);
        if (c == null) {
            System.out.println("Rejected: " + name + " has no such capability.");
            return false;
        }
        try {
            System.out.println(c.apply(name, value) + ".");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage() + ".");
            return false;
        }
    }
}

class SceneStep {
    final Class<? extends Capability> capability;
    final int value;

    SceneStep(Class<? extends Capability> capability, int value) {
        this.capability = capability;
        this.value = value;
    }
}

class Scene {
    private final String name;
    private final List<SceneStep> steps = new ArrayList<>();

    Scene(String name) {
        this.name = name;
    }

    Scene step(Class<? extends Capability> capability, int value) {
        steps.add(new SceneStep(capability, value));
        return this;
    }

    void run(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");
        int applied = 0;
        for (SceneStep s : steps) {
            for (Device d : devices) {
                if (d.has(s.capability) && d.command(s.capability, s.value)) {
                    applied++;
                }
            }
        }
        System.out.println("Scene '" + name + "' completed: " + applied + " actions applied.");
    }
}

public class SmartLabControlPanel {
    public static void main(String[] args) {
        Device ac = new Device("Lab AC", new PowerCapability(), new TemperatureCapability());
        Device lights = new Device("Ceiling Lights", new PowerCapability(), new BrightnessCapability());
        Device projector = new Device("Projector", new PowerCapability());
        List<Device> lab = new ArrayList<>();
        lab.add(ac);
        lab.add(lights);
        lab.add(projector);

        new Scene("Lecture Mode")
                .step(PowerCapability.class, 1)
                .step(BrightnessCapability.class, 40)
                .step(TemperatureCapability.class, 24)
                .run(lab);

        ac.command(TemperatureCapability.class, 12);

        projector.addCapability(new BrightnessCapability());
        projector.command(BrightnessCapability.class, 70);
    }
}
