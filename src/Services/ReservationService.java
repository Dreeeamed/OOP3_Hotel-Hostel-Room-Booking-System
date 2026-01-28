package Services;

import Entities.Reservation;
import Entities.Room;
import Exceptions.ReservationException;
import Repositories.IReservationRepository;
import Repositories.IRoomRepository;


import java.sql.Date;
import java.util.List;

public class ReservationService {
    private final IReservationRepository reservationRepo;
    private final IRoomRepository roomRepo;

    public ReservationService(IReservationRepository reservationRepo, IRoomRepository roomRepo) {
        this.reservationRepo = reservationRepo;
        this.roomRepo = roomRepo;
    }

    public void createReservation(int guestId, int roomNumber, Date date) throws ReservationException {
        Room room = roomRepo.getRoomByNumber(roomNumber);

        if (room==null) {
            throw new ReservationException("Room #" + roomNumber + " does not exist.");
        }

        if (!room.isAvailable()) {
            throw new ReservationException("Room #" + roomNumber + " ain't available.");
        }
        Reservation res = new Reservation(guestId, room.getId(), date);

        reservationRepo.createReservation(res);

        roomRepo.updateAvailability(room.getId(), false);

        System.out.println("Success! Reservation created for Guest " + guestId + " in Room " + room.getRoomNumber());
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.getAllReservations();
    }
}