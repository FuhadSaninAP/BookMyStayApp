import java.util.*;

// -------------------- Reservation --------------------
class Reservation {
    private String guestName;
    private String roomId;
    private String roomType;

    public Reservation(String guestName, String roomId, String roomType) {
        this.guestName = guestName;
        this.roomId = roomId;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType + " | Room ID: " + roomId);
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

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void displayInventory() {
        System.out.println("\n---- Current Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- Booking History --------------------
class BookingHistory {

    private List<Reservation> confirmedBookings = new ArrayList<>();

    public void addBooking(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    public void removeBooking(Reservation reservation) {
        confirmedBookings.remove(reservation);
    }

    public boolean containsBooking(Reservation reservation) {
        return confirmedBookings.contains(reservation);
    }

    public void displayAllBookings() {
        System.out.println("\n---- Booking History ----");
        if (confirmedBookings.isEmpty()) {
            System.out.println("No confirmed bookings.");
            return;
        }
        for (Reservation r : confirmedBookings) {
            r.displayReservation();
        }
    }
}

// -------------------- Cancellation Service --------------------
class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack = new Stack<>(); // stores released room IDs

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelBooking(Reservation reservation) {
        if (!history.containsBooking(reservation)) {
            System.out.println("\nCancellation FAILED for " + reservation.getGuestName() +
                    " | Reason: Reservation does not exist or already cancelled.");
            return;
        }

        // Rollback inventory
        inventory.increaseAvailability(reservation.getRoomType());

        // Record rollback in stack
        rollbackStack.push(reservation.getRoomId());

        // Remove from booking history
        history.removeBooking(reservation);

        System.out.println("\nBooking CANCELLED for " + reservation.getGuestName() +
                " | Room ID: " + reservation.getRoomId() +
                " | Room Type: " + reservation.getRoomType());
    }

    public void displayRollbackStack() {
        System.out.println("\n---- Recently Released Room IDs (LIFO) ----");
        if (rollbackStack.isEmpty()) {
            System.out.println("No recent cancellations.");
            return;
        }
        for (String roomId : rollbackStack) {
            System.out.println(roomId);
        }
    }
}

// -------------------- Main Application --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay Application ");
        System.out.println(" Hotel Booking System v10.1 ");
        System.out.println("=======================================\n");

        // Initialize inventory, booking history, and cancellation service
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService(inventory, history);

        // Sample confirmed bookings
        Reservation r1 = new Reservation("Alice", "SINGLE-1", "Single Room");
        Reservation r2 = new Reservation("Bob", "DOUBLE-1", "Double Room");
        Reservation r3 = new Reservation("Charlie", "SINGLE-2", "Single Room");

        // Add bookings to history
        history.addBooking(r1);
        history.addBooking(r2);
        history.addBooking(r3);

        System.out.println("Initial Booking History:");
        history.displayAllBookings();
        inventory.displayInventory();

        // Perform cancellations
        cancellationService.cancelBooking(r2); // valid
        cancellationService.cancelBooking(r2); // invalid, already cancelled
        cancellationService.cancelBooking(new Reservation("David", "SUITE-1", "Suite Room")); // invalid, non-existent

        // Display final state
        System.out.println("\nFinal Booking History:");
        history.displayAllBookings();
        inventory.displayInventory();
        cancellationService.displayRollbackStack();

        System.out.println("\n=======================================");
        System.out.println(" End of Booking Cancellation & Rollback ");
        System.out.println("=======================================");
    }
}