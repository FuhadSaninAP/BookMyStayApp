import java.util.HashMap;
import java.util.Map;

// Inventory Class
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int countChange) {
        int current = inventory.getOrDefault(roomType, 0);
        int updated = current + countChange;

        if (updated >= 0) {
            inventory.put(roomType, updated);
        } else {
            System.out.println("Invalid operation: Not enough rooms available for " + roomType);
        }
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("---- Current Room Inventory ----");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main Class (UPDATED NAME)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay Application ");
        System.out.println(" Hotel Booking System v3.1 ");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        System.out.println("\n--- Updating Inventory ---");

        // Booking simulation
        inventory.updateAvailability("Single Room", -2);

        // Cancellation simulation
        inventory.updateAvailability("Double Room", +1);

        // Invalid case
        inventory.updateAvailability("Suite Room", -5);

        // Final inventory
        System.out.println();
        inventory.displayInventory();

        System.out.println("\n=======================================");
        System.out.println(" End of Inventory Management ");
        System.out.println("=======================================");
    }
}