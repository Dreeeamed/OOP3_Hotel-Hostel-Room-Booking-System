package Services;

import Entities.Reservation;
import Exceptions.ReservationException;
import Repositories.IReservationRepository;
import Repositories.IRoomRepository;

import java.sql.Date;
import java.util.List;

public class ReservationService {
    private final IReservationRepository reservationRepo;
    private final IRoomRepository roomRepo;

    // We inject BOTH repository interfaces
    public ReservationService(IReservationRepository reservationRepo, IRoomRepository roomRepo) {
        this.reservationRepo = reservationRepo;
        this.roomRepo = roomRepo;
    }

    public void createReservation(int guestId, int roomId, Date date) throws ReservationException {
        // 1. Business Logic: Check availability using the interface method
        boolean isAvailable = roomRepo.isRoomAvailable(roomId);

        if (!isAvailable) {
            throw new ReservationException("Room ID " + roomId + " is unavailable or does not exist.");
        }

        // 2. Create Reservation
        Reservation res = new Reservation(guestId, roomId, date);

        // 3. Save to DB via interface
        reservationRepo.createReservation(res);

        // 4. Update Room status via interface
        roomRepo.updateAvailability(roomId, false);

        System.out.println("Success! Reservation created for Guest " + guestId + " in Room " + roomId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.getAllReservations();
    }
}