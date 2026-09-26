package constructors.class_problems;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class BusTicket {
    private final String passengerName;
    private final String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {
        if (!isMeaningful(passengerName) || !isMeaningful(destination)) {
            throw new IllegalArgumentException("Passenger and destination must contain meaningful letters");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
    }

    private static boolean isMeaningful(String value) {
        if (value == null || value.trim().isEmpty()) return false;
        boolean hasLetter = false;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (Character.isLetter(ch)) hasLetter = true;
            else if (!Character.isWhitespace(ch) && ch != '-' && ch != '\'') return false;
        }
        return hasLetter;
    }

    public void markCheckedIn() {
        if (checkedIn) {
            System.out.println(passengerName + " is already checked in for " + destination);
        } else {
            checkedIn = true;
            System.out.println(passengerName + " checked in for " + destination);
        }
    }

    public static void processBatch(String[][] rawBookings) {
        if (rawBookings == null) throw new IllegalArgumentException("Bookings cannot be null");
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        Set<String> acceptedPairs = new HashSet<>();
        for (String[] raw : rawBookings) {
            if (raw == null || raw.length != 2) {
                rejected++;
                continue;
            }
            try {
                BusTicket ticket = new BusTicket(raw[0], raw[1]);
                String key = ticket.passengerName.toLowerCase(Locale.ROOT) + "\u0000"
                    + ticket.destination.toLowerCase(Locale.ROOT);
                if (acceptedPairs.add(key)) valid++;
                else duplicates++;
            } catch (IllegalArgumentException exception) {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected
            + " | Duplicates skipped: " + duplicates);
    }

    public static void main(String[] args) {
        String[][] attempts = {
            {"Divya", "Chennai"}, {"", "Bangalore"}, {"Ravi123", "Pune"},
            {"Divya", "Chennai"}, {"   ", "  "}
        };
        processBatch(attempts);
        new BusTicket("Divya", "Chennai").markCheckedIn();
    }
}
