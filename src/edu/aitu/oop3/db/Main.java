package edu.aitu.oop3.db;

import Repositories.*;
import Services.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting application...");

        // 1. CONNECT TO DATABASE.
        try (Connection connection = DatabaseConnection.getConnection()) {

            System.out.println("Connected to Database!");

            // 2. PREPARE REPOSITORIES
            RoomRepository roomRepo = new RoomRepository(connection);
            GuestRepository guestRepo = new GuestRepository(connection);
            ReservationRepository resRepo = new ReservationRepository(connection);
            PaymentRepository payRepo = new PaymentRepository(connection);

            // 3. SETUP TABLES
            roomRepo.createTable();
            guestRepo.createTable();
            resRepo.createTable();
            payRepo.createTable();

            // 4. PREPARE SERVICES
            RoomService roomService = new RoomService(roomRepo);
            GuestService guestService = new GuestService(guestRepo);
            ReservationService reservationService = new ReservationService(resRepo, roomRepo);
            PaymentService paymentService = new PaymentService(payRepo);

            // 5. START THE APP
            HotelApplication app = new HotelApplication(roomService, guestService, reservationService, paymentService);
            app.start();

        } catch (SQLException e) {
            System.out.println("Error: Could not connect to database.");
            e.printStackTrace();
        }
    }
}