import java.util.*;
import java.util.concurrent.*;

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
}

// -------------------- Thread-Safe Inventory --------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    // Synchronized to ensure thread-safe updates
    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void displayInventory() {
        System.out.println("\n---- Current Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- Booking Processor --------------------
class BookingProcessor implements Runnable {

    private Reservation reservation;
    private RoomInventory inventory;

    public BookingProcessor(Reservation reservation, RoomInventory inventory) {
        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        String guest = reservation.getGuestName();
        String type = reservation.getRoomType();

        // Synchronized allocation ensures no double-booking
        boolean success = inventory.allocateRoom(type);
        if (success) {
            System.out.println("Booking CONFIRMED for " + guest + " | Room Type: " + type);
        } else {
            System.out.println("Booking FAILED for " + guest + " | Room Type: " + type + " (No Availability)");
        }
    }
}

// -------------------- Main Application --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay Application ");
        System.out.println(" Hotel Booking System v11.1 ");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();

        // Simulate multiple guests
        List<Reservation> requests = Arrays.asList(
                new Reservation("Alice", "Single Room"),
                new Reservation("Bob", "Double Room"),
                new Reservation("Charlie", "Single Room"),
                new Reservation("David", "Suite Room"),
                new Reservation("Eva", "Double Room"),
                new Reservation("Frank", "Suite Room") // should fail
        );

        // Thread pool to simulate concurrent booking
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (Reservation r : requests) {
            executor.submit(new BookingProcessor(r, inventory));
        }

        // Shutdown executor and wait for all tasks to finish
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display final inventory state
        inventory.displayInventory();

        System.out.println("\n=======================================");
        System.out.println(" End of Concurrent Booking Simulation ");
        System.out.println("=======================================");
    }
}