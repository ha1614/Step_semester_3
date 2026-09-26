package oop.class_problems;

class HostelRoom {
    final String roomNo;
    final int beds;
    int occupied;

    HostelRoom(String roomNo, int beds, int occupied) {
        if (roomNo == null || beds < 0 || occupied < 0 || occupied > beds) {
            throw new IllegalArgumentException("Invalid room details");
        }
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    boolean allot(String name) {
        if (name == null || name.trim().isEmpty() || occupied >= beds) return false;
        occupied++;
        System.out.println(name + " allotted to room " + roomNo);
        return true;
    }
}

public final class F3HostelRoomAllocation {
    private F3HostelRoomAllocation() {
    }

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) return null;
        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) return room;
        }
        return null;
    }

    static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom room = findAvailableRoom(rooms);
        if (room == null) {
            System.out.println("No rooms available for " + studentName);
            return;
        }
        room.allot(studentName);
    }

    public static void main(String[] args) {
        HostelRoom[] rooms = {new HostelRoom("C-214", 3, 2), new HostelRoom("C-507", 2, 2)};
        safeAllot(rooms, "Divya");
        safeAllot(new HostelRoom[] {new HostelRoom("C-214", 1, 1)}, "Ravi");
    }
}
