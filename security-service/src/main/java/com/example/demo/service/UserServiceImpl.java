package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.entity.User;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.feign.BookingClient;
import com.example.demo.feign.EventClient;
import com.example.demo.repository.UserRepo;

/**
 * Implementation of the {@code UserService} interface, providing CRUD
 * operations for {@code User} entities.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Message template for user not found exceptions.
     */
    private String message = "User not found with id : ";

    private UserRepo repo;
    private EventClient eventClient;
    private BookingClient bookingClient;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

    /**
     * Constructs an instance of {@code UserServiceImpl} with the specified
     * repository.
     *
     * @param repo The {@code UserRepo} used for data access operations.
     */
    public UserServiceImpl(UserRepo repo, EventClient eventClient, BookingClient bookingClient) {
        this.repo = repo;
        this.eventClient = eventClient;
        this.bookingClient = bookingClient;
    }

    /**
     * Saves a new user entity to the repository.
     *
     * @param user The {@code User} entity to save.
     */
    public String save(User user) {
    	String name = user.getName();
		User obj1 = repo.findByName(name).orElse(null);
		System.out.println(obj1);
		if (obj1 == null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			repo.save(user);
			return "Registration Successfully ";
		} else {
			return "This UserName is Already Registered.";
		}
    }

    /**
     * Retrieves all user entities from the repository.
     *
     * @return A list of {@code User} entities.
     */
    public List<User> getAll() {
        return repo.findAll();
    }

    /**
     * Retrieves a user entity by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@code User} entity if found, or {@code null} if not found.
     * @throws UserNotFoundException if the user entity is not found.
     */
    public User getById(Integer id) throws UserNotFoundException {
        return repo.findById(id).orElseThrow(() -> new UserNotFoundException(message + id));
    }

    /**
     * Updates an existing user entity in the repository.
     *
     * @param user The {@code User} entity with updated information.
     * @throws UserNotFoundException if the user entity is not found.
     */
    public void update(User user) throws UserNotFoundException {
        if (!repo.existsById(user.getId())) {
            throw new UserNotFoundException(message + user.getId());
        }
        User existingUser = repo.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(message + user.getId()));
        if (!user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        repo.save(user);
    }

    /**
     * Deletes a user entity from the repository by its ID.
     *
     * @param id The ID of the user to delete.
     * @throws UserNotFoundException if the user entity is not found.
     * @return A message indicating successful deletion.
     */
    public String delete(Integer id) throws UserNotFoundException {
        if (!repo.existsById(id)) {
            throw new UserNotFoundException(message + id);
        }
        repo.deleteById(id);
        bookingClient.deleteBookingByUserId(id);
        return "User deleted successfully";
    }

    /**
     * Registers a user to an event.
     *
     * @param userId  The ID of the user.
     * @param eventId The ID of the event.
     * @return A message indicating successful registration.
     * @throws UserNotFoundException  if the user entity is not found.
     * @throws EventNotFoundException if the event entity is not found.
     */
    @Override
    public String registerUserToEvent(Integer userId, Integer eventId)
            throws UserNotFoundException, EventNotFoundException {
        User user = getById(userId);
        Event event;
        try {
            event = eventClient.getEventById(eventId).getBody();
        } catch (Exception e) {
            throw new EventNotFoundException("Event not found with id : " + eventId);
        }
        Booking booking = new Booking();
        booking.setUserId(user.getId());
        booking.setUserName(user.getName());
        booking.setEventId(event.getId());
        booking.setEventName(event.getName());
        booking.setDate(event.getDate());
        booking.setLocation(event.getLocation());
        booking.setVenue(event.getVenue());

        bookingClient.saveBooking(booking);
        return "User " + user.getName() + " registered to event " + event.getName() + " successfully";
    }
}
