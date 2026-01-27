package edu.aitu.oop3.db;

import Services.*;
import Entities.*;
import Exceptions.*;
import java.util.Scanner;
import java.sql.Date;

public class HotelApplication {
    private final RoomService roomService;
    private final GuestService guestService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final Scanner scanner;

    public HotelApplication(RoomService rs, GuestService gs, ReservationService resS, PaymentService ps) {
        this.roomService = rs;
        this.guestService = gs;
        this.reservationService = resS;
        this.paymentService = ps;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        roomService.initializeRoomsIfNeeded();

        while (true) {
            System.out.println("\n--- HOTEL BOOKING SYSTEM ---");
            System.out.println("1. Find Available Rooms");
            System.out.println("2. Make a Reservation (Auto-Register)");
            System.out.println("3. View My Reservations");
            System.out.println("4. Pay for Reservation");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1 -> showRooms();
                    case 2 -> makeReservationSmart(); // <--- NEW SMART FLOW
                    case 3 -> showReservations();
                    case 4 -> makePayment();
                    case 0 -> { return; }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void makeReservationSmart() {
        System.out.print("Enter your Email to start: ");
        String email = scanner.next();
        Guest guest = guestService.getAndRegisterGuest(email, scanner);
        System.out.print("Enter the Room ID you want to book: ");
        int roomId = scanner.nextInt();
        Date date = new Date(System.currentTimeMillis());
        try {
            reservationService.createReservation(guest.getId(), roomId, date);
        } catch (ReservationException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }

    private void showRooms() {
        roomService.getAllRooms().stream()
                .filter(Room::isAvailable) // Only show free rooms
                .forEach(System.out::println);
    }

    private void showReservations() {
        reservationService.getAllReservations().forEach(System.out::println);
    }

    private void makePayment() {
        System.out.println("--- Payment Counter ---");
        System.out.print("Enter Reservation ID: ");
        int resId = scanner.nextInt();

        System.out.print("Enter Amount to Pay: ");
        double amount = scanner.nextDouble();

        paymentService.makePayment(resId, amount);
        System.out.println("Payment accepted! Keep your Reservation ID #" + resId + " for check-in.");
    }
}