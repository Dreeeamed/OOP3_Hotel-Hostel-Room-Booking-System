package Entities;

import java.sql.Date;

public class Reservation {
    private int id;
    private int guestId;
    private int roomId;
    private Date checkInDate;

    public Reservation() {} //A che v default pisat'?

    public Reservation(int guestId, int roomId, Date checkInDate) {
        setGuestId(guestId);
        setRoomId(roomId);
        setCheckInDate(checkInDate);
    }

    // ENCAPSULATION OPYAT'
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }

    @Override
    public String toString() {
        return "Res ID: " + id + " | Guest ID: " + guestId + " | Room ID: " + roomId + " | Date: " + checkInDate;
    }
}