import java.util.HashMap;
import java.util.Map;

// -------------------- Room Domain --------------------

// Abstract Room class
abstract class Room {

    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayDetails();
}

// Concrete Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }

    public void displayDetails() {
        System.out.println("Type: " + getRoomType() + ", Beds: " + getBeds() + ", Price: ₹" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }

    public void displayDetails() {
        System.out.println("Type: " + getRoomType() + ", Beds: " + getBeds() + ", Price: ₹" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }

    public void displayDetails() {
        System.out.println("Type: " + getRoomType() + ", Beds: " + getBeds() + ", Price: ₹" + getPrice());
    }
}

// -------------------- Inventory --------------------

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example: unavailable
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Expose all inventory (read-only usage expected)
    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}

// -------------------- Search Service --------------------

class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("---- Available Rooms ----\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: only show available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// -------------------- Main Application --------------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay Application ");
        System.out.println(" Hotel Booking System v4.1 ");
        System.out.println("=======================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room domain objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Perform search (READ-ONLY)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("=======================================");
        System.out.println(" End of Search ");
        System.out.println("=======================================");
    }
}