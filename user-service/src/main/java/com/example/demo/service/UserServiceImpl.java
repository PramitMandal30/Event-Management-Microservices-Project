package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.dto.UserWrapper;
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
    public void save(User user) {
        repo.save(user);
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
     * Retrieves all user entities and maps them to {@code UserWrapper} objects.
     *
     * @return A list of {@code UserWrapper} objects.
     */
    public List<UserWrapper> getAllUsersForAdmins() {
        List<User> users = repo.findAll();
        List<UserWrapper> userWrappers = new ArrayList<>();
        for (User user : users) {
            UserWrapper userWrapper = new UserWrapper(user.getId(), user.getName(), user.getEmail(), user.getPhNo());
            userWrappers.add(userWrapper);
        }
        return userWrappers;
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
     * Retrieves all events using the EventClient.
     *
     * @return A list of {@code Event} entities.
     */
    public List<Event> getAllEvents() {
        return eventClient.getAllEvents().getBody();
    }

    /**
     * Retrieves events by their name using the EventClient.
     *
     * @param keyword The name keyword to search for.
     * @return A list of {@code Event} entities matching the keyword.
     * @throws EventNotFoundException if no events are found with the specified name.
     */
    @Override
    public List<Event> getEventsByName(String keyword) throws EventNotFoundException {
        List<Event> event;
        try {
            event = eventClient.getAllEventsByName(keyword).getBody();
        } catch (Exception e) {
            throw new EventNotFoundException("No events with name : " + keyword);
        }
        return event;
    }

    /**
     * Retrieves events by their location using the EventClient.
     *
     * @param keyword The location keyword to search for.
     * @return A list of {@code Event} entities matching the location.
     * @throws EventNotFoundException if no events are found with the specified location.
     */
    @Override
    public List<Event> getEventsByLocation(String keyword) throws EventNotFoundException {
        List<Event> event;
        try {
            event = eventClient.getAllEventsByLocation(keyword).getBody();
        } catch (Exception e) {
            throw new EventNotFoundException("No events found at : " + keyword);
        }
        return event;
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

    /**
     * Retrieves bookings for a user by their ID.
     *
     * @param userId The ID of the user.
     * @return A list of {@code Booking} entities for the user.
     * @throws BookingNotFoundException if no bookings are found for the user.
     */
    @Override
    public List<Booking> getBookingsForUserId(Integer userId) throws BookingNotFoundException {
        List<Booking> booking;
        try {
            booking = bookingClient.getBookingByUserId(userId).getBody();
        }catch(Exception e) {
            throw new BookingNotFoundException("No bookings found for user " + userId);
        }
        return booking;
    }

    /**
     * Cancels a booking for a user and event.
     *
     * @param userId  The ID of the user.
     * @param eventId The ID of the event.
     * @return A message indicating successful cancellation.
     * @throws UserNotFoundException  if the user entity is not found.
     * @throws EventNotFoundException if the event entity is not found.
     */
    @Override
    public String cancelBooking(Integer userId, Integer eventId) throws UserNotFoundException, EventNotFoundException {
        User user = getById(userId);
        Event event;
        try {
            event = eventClient.getEventById(eventId).getBody();
        }catch(Exception e) {
            throw new EventNotFoundException("No events found with id : " + eventId);
        }
        bookingClient.deleteBookingByUserIdAndEventId(userId, eventId);
        return "User " + user.getName() + " successfully cancelled booking for event " + event.getName();
    }
}
