import java.util.*;

// -------------------- Add-On Service --------------------
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(name + " (₹" + cost + ")");
    }
}

// -------------------- Reservation --------------------
class Reservation {
    private String guestName;
    private String roomId;

    public Reservation(String guestName, String roomId) {
        this.guestName = guestName;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void displayReservation() {
        System.out.println("Reservation: " + guestName + " | Room ID: " + roomId);
    }
}

// -------------------- Add-On Service Manager --------------------
class AddOnServiceManager {

    // Map reservation ID → list of services
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Attach service(s) to reservation
    public void addServices(Reservation reservation, Service... services) {
        reservationServices
                .computeIfAbsent(reservation.getRoomId(), k -> new ArrayList<>())
                .addAll(Arrays.asList(services));
    }

    // Calculate total additional cost
    public double calculateTotalCost(Reservation reservation) {
        List<Service> services = reservationServices.getOrDefault(reservation.getRoomId(), new ArrayList<>());
        return services.stream().mapToDouble(Service::getCost).sum();
    }

    // Display attached services
    public void displayServices(Reservation reservation) {
        System.out.println("\nAdd-On Services for " + reservation.getGuestName() + ":");

        List<Service> services = reservationServices.getOrDefault(reservation.getRoomId(), new ArrayList<>());

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            for (Service s : services) {
                s.displayService();
            }
            System.out.println("Total Additional Cost: ₹" + calculateTotalCost(reservation));
        }
    }
}

// -------------------- Main Application --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay Application ");
        System.out.println(" Hotel Booking System v7.1 ");
        System.out.println("=======================================\n");

        // Example reservations (assume already allocated rooms)
        Reservation r1 = new Reservation("Alice", "SINGLE-1");
        Reservation r2 = new Reservation("Bob", "DOUBLE-1");

        // Define some add-on services
        Service breakfast = new Service("Breakfast", 300);
        Service airportPickup = new Service("Airport Pickup", 500);
        Service spa = new Service("Spa Access", 700);

        // Initialize Add-On Service Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Attach services to reservations
        serviceManager.addServices(r1, breakfast, spa);
        serviceManager.addServices(r2, breakfast, airportPickup);

        // Display reservations and their services
        r1.displayReservation();
        serviceManager.displayServices(r1);

        r2.displayReservation();
        serviceManager.displayServices(r2);

        System.out.println("\n=======================================");
        System.out.println(" End of Add-On Service Selection ");
        System.out.println("=======================================");
    }
}