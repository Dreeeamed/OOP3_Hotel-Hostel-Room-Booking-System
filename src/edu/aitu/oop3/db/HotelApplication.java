package edu.aitu.oop3.db;

import Services.*;
import Entities.*;
import Exceptions.*;

import java.util.List;
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
                    case 1:
                        showRooms();
                        break;
                    case 2:
                        makeReservation();
                        break;
                    case 3:
                        showReservations();
                        break;
                    case 4:
                        makePayment();
                        break;
                    case 0:
                        { return; }
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void makeReservation() {
        System.out.print("Enter your Email to start: ");
        String email = scanner.next();
        Guest guest = guestService.getAndRegisterGuest(email, scanner);
        System.out.print("Enter the Room ID you want to book: ");
        int roomNumber = scanner.nextInt();
        Date date = new Date(System.currentTimeMillis());
        try {
            reservationService.createReservation(guest.getId(), roomNumber, date);
        } catch (ReservationException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }

    private void showRooms() {
        System.out.println("--- Available Rooms ---");
        for (Room room : roomService.getAllRooms()) {
            if (room.isAvailable()) {
                System.out.println(room); // This uses the toString() logic
            }
        }
    }

    private void showReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("--- Current Reservations ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
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