import java.io.*;
import java.util.*;

// -------------------- Reservation --------------------
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType + " | Room ID: " + roomId);
    }
}

// -------------------- Inventory --------------------
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
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
class BookingHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Reservation> confirmedBookings = new ArrayList<>();

    public void addBooking(Reservation r) { confirmedBookings.add(r); }
    public void removeBooking(Reservation r) { confirmedBookings.remove(r); }
    public List<Reservation> getAllBookings() { return confirmedBookings; }

    public void displayAllBookings() {
        System.out.println("\n---- Booking History ----");
        if (confirmedBookings.isEmpty()) {
            System.out.println("No confirmed bookings.");
            return;
        }
        for (Reservation r : confirmedBookings) { r.displayReservation(); }
    }
}

// -------------------- Persistence Service --------------------
class PersistenceService {

    private static final String FILE_NAME = "booking_data.ser";

    public static void saveData(RoomInventory inventory, BookingHistory history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("\nSystem state successfully saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("\nError saving data: " + e.getMessage());
        }
    }

    public static Object[] loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("\nNo saved data found. Starting fresh.");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            RoomInventory inventory = (RoomInventory) ois.readObject();
            BookingHistory history = (BookingHistory) ois.readObject();
            System.out.println("\nSystem state successfully restored from " + FILE_NAME);
            return new Object[]{inventory, history};
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nError loading data: " + e.getMessage());
            return null;
        }
    }
}

// -------------------- Main Application --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay Application ");
        System.out.println(" Hotel Booking System v12.1 ");
        System.out.println("=======================================\n");

        RoomInventory inventory;
        BookingHistory history;

        // Attempt to restore persisted data
        Object[] data = PersistenceService.loadData();
        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (BookingHistory) data[1];
        } else {
            inventory = new RoomInventory();
            history = new BookingHistory();
        }

        // Display current state
        System.out.println("Current Booking History:");
        history.displayAllBookings();
        inventory.displayInventory();

        // Simulate new bookings
        Reservation r1 = new Reservation("Alice", "Single Room", "SINGLE-1");
        Reservation r2 = new Reservation("Bob", "Double Room", "DOUBLE-1");

        if (inventory.getAvailability(r1.getRoomType()) > 0) {
            inventory.decreaseAvailability(r1.getRoomType());
            history.addBooking(r1);
            System.out.println("\nBooking CONFIRMED: " + r1.getGuestName());
        }

        if (inventory.getAvailability(r2.getRoomType()) > 0) {
            inventory.decreaseAvailability(r2.getRoomType());
            history.addBooking(r2);
            System.out.println("Booking CONFIRMED: " + r2.getGuestName());
        }

        // Display updated state
        System.out.println("\nUpdated Booking History:");
        history.displayAllBookings();
        inventory.displayInventory();

        // Persist state before exit
        PersistenceService.saveData(inventory, history);

        System.out.println("\n=======================================");
        System.out.println(" End of Persistence & Recovery Simulation ");
        System.out.println("=======================================");
    }
}