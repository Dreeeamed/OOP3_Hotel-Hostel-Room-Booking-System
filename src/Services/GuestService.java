package Services;

import Entities.Guest;
import Exceptions.GuestNotFoundException;
import Repositories.IGuestRepository;

import java.util.List;

public class GuestService {
    // 1. Depend on the Interface, not the Class
    private final IGuestRepository guestRepository;

    // 2. Inject the Interface via Constructor
    public GuestService(IGuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public void registerGuest(String name, String email) {
        if (name == null || name.length() < 2) {
            System.out.println("Error: Name is too short.");
            return;
        }

        Guest newGuest = new Guest(name, email);
        guestRepository.addGuest(newGuest);
    }

    public List<Guest> getAllGuests() {
        return guestRepository.getAllGuests();
    }

    public Guest getGuestById(int id) throws GuestNotFoundException {
        Guest guest = guestRepository.getGuestById(id);
        if (guest == null) {
            throw new GuestNotFoundException("Guest with ID " + id + " was not found.");
        }
        return guest;
    }

    public Guest getAndRegisterGuest(String email, java.util.Scanner scanner) {
        // 1. Try to find existing guest
        Guest existingGuest = guestRepository.getGuestByEmail(email);

        if (existingGuest != null) {
            System.out.println("Welcome back, " + existingGuest.getName() + "!");
            return existingGuest;
        }

        // 2. If null, they are new. Ask for details.
        System.out.println("It looks like you are new here.");
        System.out.print("Please enter your Full Name to register: ");
        String name = scanner.next(); // Or scanner.nextLine() if you fixed the buffer

        Guest newGuest = new Guest(name, email);
        guestRepository.addGuest(newGuest); // This saves them and sets their ID

        System.out.println("Registration successful! Proceeding with booking...");
        return newGuest;
    }
}