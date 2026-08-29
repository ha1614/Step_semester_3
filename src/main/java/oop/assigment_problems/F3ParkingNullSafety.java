package oop.assigment_problems;

// F3: Object References, Null Safety, and a Mutating Method

class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    boolean allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
            System.out.println(vehicleNo + " allotted to slot " + slotNo);
            return true;
        }
        return false;
    }

    // WHY THE ARRAY IS NOT COPIED:
    // Passing slots into a method copies only the REFERENCE (the address), not the
    // objects. Both the caller and the method point at the same ParkingSlot objects,
    // so occupiedCount++ inside allot() is visible back in main.
    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) {
            return null;
        }
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].occupiedCount < slots[i].capacity) {
                return slots[i];
            }
        }
        return null;
    }

    static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot free = findAvailableSlot(slots);
        if (free == null) {                       // the null check BEFORE any use
            System.out.println("No slots available for " + vehicleNo);
            return;
        }
        free.allot(vehicleNo);
    }
}

public class F3ParkingNullSafety {
    public static void main(String[] args) {
        ParkingSlot[] hasSpace = { new ParkingSlot("A1", 4, 3), new ParkingSlot("A2", 5, 5) };
        ParkingSlot.safeAllot(hasSpace, "TN09AB1234");

        ParkingSlot[] allFull = { new ParkingSlot("A1", 4, 4), new ParkingSlot("A2", 5, 5) };
        ParkingSlot.safeAllot(allFull, "TN09AB1234");
    }
}
