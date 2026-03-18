import java.util.*;

// -------------------- Custom Exception --------------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// -------------------- Reservation --------------------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// -------------------- Inventory --------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // Validate room type exists
    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    // Check availability
    public void checkAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    // Reduce availability
    public void reduceAvailability(String roomType) throws InvalidBookingException {
        checkAvailability(roomType);
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n---- Current Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- Booking Service --------------------
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Process reservation with validation
    public void processReservation(Reservation reservation) {
        try {
            // Validate room type
            inventory.validateRoomType(reservation.getRoomType());

            // Check availability
            inventory.checkAvailability(reservation.getRoomType());

            // Reduce inventory atomically
            inventory.reduceAvailability(reservation.getRoomType());

            // Confirm booking
            System.out.println("\nBooking CONFIRMED for " + reservation.getGuestName() +
                    " | Room Type: " + reservation.getRoomType());

        } catch (InvalidBookingException e) {
            // Fail-fast with meaningful message
            System.out.println("\nBooking FAILED for " + reservation.getGuestName() +
                    " | Reason: " + e.getMessage());
        }
    }
}

// -------------------- Main Application --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay Application ");
        System.out.println(" Hotel Booking System v9.1 ");
        System.out.println("=======================================\n");

        // Initialize inventory and booking service
        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Test reservations (including invalid scenarios)
        Reservation r1 = new Reservation("Alice", "Single Room");   // valid
        Reservation r2 = new Reservation("Bob", "Penthouse");       // invalid type
        Reservation r3 = new Reservation("Charlie", "Suite Room");  // valid
        Reservation r4 = new Reservation("David", "Suite Room");    // no availability (should fail)

        // Process all bookings
        bookingService.processReservation(r1);
        bookingService.processReservation(r2);
        bookingService.processReservation(r3);
        bookingService.processReservation(r4);

        // Display remaining inventory
        inventory.displayInventory();

        System.out.println("\n=======================================");
        System.out.println(" End of Error Handling & Validation ");
        System.out.println("=======================================");
    }
}