package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.dto.User;
import com.example.demo.entity.Admin;
import com.example.demo.exception.AdminNotFoundException;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.feign.BookingClient;
import com.example.demo.feign.EventClient;
import com.example.demo.feign.UserClient;
import com.example.demo.repository.AdminRepo;

/**
 * Implementation of the {@code AdminService} interface, providing CRUD
 * operations for {@code Admin} entities.
 */
@Service
public class AdminServiceImpl implements AdminService {

    /**
     * Message template for admin not found exceptions.
     */
    String message = "Admin not found with id : ";

    private AdminRepo repo;
    private EventClient eventClient;
    private BookingClient bookingClient;
    private UserClient userClient;

    /**
     * Constructs an instance of {@code AdminServiceImpl} with the specified
     * repository.
     *
     * @param repo The {@code AdminRepo} used for data access operations.
     */
    public AdminServiceImpl(AdminRepo repo, EventClient eventClient, BookingClient bookingClient,
            UserClient userClient) {
        this.repo = repo;
        this.eventClient = eventClient;
        this.bookingClient = bookingClient;
        this.userClient = userClient;
    }

    /**
     * Saves a new admin entity to the repository.
     *
     * @param admin The {@code Admin} entity to save.
     */
    public void save(Admin admin) {
        repo.save(admin);
    }

    /**
     * Retrieves all admin entities from the repository.
     *
     * @return A list of {@code Admin} entities.
     */
    public List<Admin> getAll() {
        return repo.findAll();
    }

    /**
     * Retrieves an admin entity by its ID.
     *
     * @param id The ID of the admin to retrieve.
     * @return The {@code Admin} entity if found, or {@code null} if not found.
     * @throws AdminNotFoundException if the admin entity is not found.
     */
    public Admin getById(Integer id) throws AdminNotFoundException {
        return repo.findById(id).orElseThrow(() -> new AdminNotFoundException(message + id));
    }

    /**
     * Updates an existing admin entity in the repository.
     *
     * @param admin The {@code Admin} entity with updated information.
     * @throws AdminNotFoundException if the admin entity is not found.
     */
    public void update(Admin admin) throws AdminNotFoundException {
        if (!repo.existsById(admin.getId())) {
            throw new AdminNotFoundException(message + admin.getId());
        }
        repo.save(admin);
    }

    /**
     * Deletes an admin entity from the repository by its ID.
     *
     * @param id The ID of the admin to delete.
     * @throws AdminNotFoundException if the admin entity is not found.
     * @return A message indicating successful deletion.
     */
    public String delete(Integer id) throws AdminNotFoundException {
        if (!repo.existsById(id)) {
            throw new AdminNotFoundException(message + id);
        }
        repo.deleteById(id);
        return "Admin deleted successfully";
    }

    /**
     * Creates a new event using the EventClient.
     *
     * @param event The {@code Event} entity to create.
     * @return The created {@code Event} entity.
     */
    public Event createEvent(Event event) {
        return eventClient.saveEvent(event).getBody();
    }

    /**
     * Updates an existing event using the EventClient.
     *
     * @param eventId The ID of the event to update.
     * @param event The {@code Event} entity with updated information.
     * @return The updated {@code Event} entity.
     * @throws EventNotFoundException if the event entity is not found.
     */
    public Event updateEvent(Integer eventId, Event event) throws EventNotFoundException {
        try {
            eventClient.getEventById(eventId);
        } catch (Exception e) {
            throw new EventNotFoundException("No event found with id : " + eventId);
        }
        return eventClient.updateEvent(eventId, event).getBody();
    }

    /**
     * Deletes an event using the EventClient.
     *
     * @param eventId The ID of the event to delete.
     * @return A message indicating successful deletion.
     * @throws EventNotFoundException if the event entity is not found.
     */
    public String deleteEvent(Integer eventId) throws EventNotFoundException {
        try {
            eventClient.getEventById(eventId);
        } catch (Exception e) {
            throw new EventNotFoundException("No event found with id : " + eventId);
        }
        eventClient.deleteEvent(eventId).getBody();
        bookingClient.deleteBookingByEventId(eventId);
        return "Event deleted successfully";
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
     * Retrieves all bookings using the BookingClient.
     *
     * @return A list of {@code Booking} entities.
     */
    public List<Booking> getAllBookings() {
        return bookingClient.getAllBookings().getBody();
    }

    /**
     * Retrieves all users using the UserClient.
     *
     * @return A list of {@code User} entities.
     */
    public List<User> getAllUsers() {
        return userClient.getallUsersForAdmins().getBody();
    }
}
