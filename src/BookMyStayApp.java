import java.util.LinkedList;
import java.util.Queue;

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
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// -------------------- Booking Queue --------------------

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void displayAllRequests() {
        System.out.println("\n---- Booking Request Queue ----");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }

    // Peek next request (FIFO head)
    public Reservation peekNextRequest() {
        return queue.peek();
    }
}

// -------------------- Main Application --------------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay Application ");
        System.out.println(" Hotel Booking System v5.1 ");
        System.out.println("=======================================\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate guest booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display all requests (FIFO order)
        bookingQueue.displayAllRequests();

        // Show next request to be processed
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.peekNextRequest();

        if (next != null) {
            next.displayReservation();
        }

        System.out.println("\n=======================================");
        System.out.println(" Requests queued successfully ");
        System.out.println("=======================================");
    }
}