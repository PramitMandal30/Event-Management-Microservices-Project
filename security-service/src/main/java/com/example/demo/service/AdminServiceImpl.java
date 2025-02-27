package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.feign.BookingClient;
import com.example.demo.feign.EventClient;

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
    private EventClient eventClient;
    private BookingClient bookingClient;

    /**
     * Constructs an instance of {@code AdminServiceImpl} with the specified
     * repository.
     *
     * @param repo The {@code AdminRepo} used for data access operations.
     */
    public AdminServiceImpl(EventClient eventClient, BookingClient bookingClient) {
        this.eventClient = eventClient;
        this.bookingClient = bookingClient;
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
}
