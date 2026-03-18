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

// -------------------- Booking History --------------------
class BookingHistory {

    private List<Reservation> confirmedBookings = new ArrayList<>();

    // Add confirmed reservation
    public void addBooking(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    // Retrieve all bookings (read-only)
    public List<Reservation> getAllBookings() {
        return Collections.unmodifiableList(confirmedBookings);
    }

    // Display all bookings
    public void displayAllBookings() {
        System.out.println("\n---- Booking History ----");
        if (confirmedBookings.isEmpty()) {
            System.out.println("No confirmed bookings yet.");
            return;
        }
        for (Reservation r : confirmedBookings) {
            r.displayReservation();
        }
    }
}

// -------------------- Booking Report Service --------------------
class BookingReportService {

    // Generate a summary report: bookings count by room type
    public void generateReport(BookingHistory history) {
        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : history.getAllBookings()) {
            summary.put(r.getRoomType(), summary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\n---- Booking Summary Report ----");
        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " bookings");
        }
    }
}

// -------------------- Main Application --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay Application ");
        System.out.println(" Hotel Booking System v8.1 ");
        System.out.println("=======================================\n");

        // Initialize booking history and report service
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("Alice", "SINGLE-1", "Single Room");
        Reservation r2 = new Reservation("Bob", "DOUBLE-1", "Double Room");
        Reservation r3 = new Reservation("Charlie", "SINGLE-2", "Single Room");

        // Add to booking history
        history.addBooking(r1);
        history.addBooking(r2);
        history.addBooking(r3);

        // Display all confirmed bookings
        history.displayAllBookings();

        // Generate summary report
        reportService.generateReport(history);

        System.out.println("\n=======================================");
        System.out.println(" End of Booking History & Report ");
        System.out.println("=======================================");
    }
}