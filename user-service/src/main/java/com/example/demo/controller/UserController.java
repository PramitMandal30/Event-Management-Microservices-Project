package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.entity.UserWrapper;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.feign.BookingClient;
import com.example.demo.feign.EventClient;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

/**
 * REST controller for user-related operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	private EventClient eventClient;
	private BookingClient bookingClient;

	/**
	 * Constructs a {@code UserController} with the specified services and clients.
	 *
	 * @param userService   The user service for managing user data.
	 * @param eventClient   The Feign client for event-related operations.
	 * @param bookingClient The Feign client for booking-related operations.
	 */
	public UserController(UserService userService, EventClient eventClient, BookingClient bookingClient) {
		this.userService = userService;
		this.eventClient = eventClient;
		this.bookingClient = bookingClient;
	}

	/**
	 * Retrieves a list of all users.
	 *
	 * @return A {@code ResponseEntity} containing a list of {@code User} objects.
	 */
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAll());
	}

	/**
	 * Retrieves a list of all users for the admin service.
	 *
	 * @return A {@code ResponseEntity} containing a list of {@code UserWrapper}
	 *         objects.
	 */
	@GetMapping("/fetch")
	public ResponseEntity<List<UserWrapper>> getallUsersForAdmins() {
		List<User> users = userService.getAll();
		List<UserWrapper> userWrappers = new ArrayList<>();
		for (User user : users) {
			UserWrapper userWrapper = new UserWrapper(user.getId(), user.getName(), user.getEmail(), user.getPhNo());
			userWrappers.add(userWrapper);
		}
		return ResponseEntity.ok(userWrappers);
	}

	/**
	 * Retrieves a user by their ID.
	 *
	 * @param id The ID of the user to retrieve.
	 * @return A {@code ResponseEntity} containing the requested {@code User}.
	 * @throws UserNotFoundException If no user is found with the specified ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) throws UserNotFoundException {
		User user = userService.getById(id);
		return ResponseEntity.ok(user);
	}

	/**
	 * Saves a new user.
	 *
	 * @param user The {@code User} object to save.
	 * @return A {@code ResponseEntity} containing the saved {@code User}.
	 */
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
		userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	/**
	 * Updates an existing user.
	 *
	 * @param id   The ID of the user to update.
	 * @param user The updated {@code User} object.
	 * @return A {@code ResponseEntity} containing the updated {@code User}.
	 * @throws UserNotFoundException If no user is found with the specified ID.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody @Valid User user)
			throws UserNotFoundException {
		user.setId(id);
		userService.update(user);
		return ResponseEntity.ok(user);
	}

	/**
	 * Deletes a user by their ID.
	 *
	 * @param id The ID of the user to delete.
	 * @return A {@code ResponseEntity} with no content.
	 * @throws UserNotFoundException If no user is found with the specified ID.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id) throws UserNotFoundException {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Retrieves a list of all events.
	 *
	 * @return A {@code ResponseEntity} containing a list of {@code Event} objects.
	 */
	@GetMapping("/get-events")
	public ResponseEntity<List<Event>> getAllEvents() {
		ResponseEntity<List<Event>> response = eventClient.getAllEvents();
		return ResponseEntity.ok(response.getBody());
	}

	/**
	 * Searches for events by name.
	 *
	 * @param keyword The keyword to search for in event names.
	 * @return A {@code ResponseEntity} containing a list of matching {@code Event}
	 *         objects.
	 * @throws EventNotFoundException
	 */
	@GetMapping("/search-name/{keyword}")
	public ResponseEntity<List<Event>> searchEventByName(@PathVariable String keyword) throws EventNotFoundException {
		List<Event> event = null;
		try {
			event = eventClient.getAllEventsByName(keyword).getBody();
		} catch (Exception e) {

			throw new EventNotFoundException("Event not found with name " + keyword);
		}
		return ResponseEntity.ok(event);
	}

	/**
	 * Searches for events by location.
	 *
	 * @param keyword The keyword to search for in event locations.
	 * @return A {@code ResponseEntity} containing a list of matching {@code Event}
	 *         objects.
	 * @throws EventNotFoundException
	 */
	@GetMapping("/search-location/{keyword}")
	public ResponseEntity<List<Event>> searchEventsByLocation(@PathVariable String keyword)
			throws EventNotFoundException {
		List<Event> event = null;
		try {
			event = eventClient.getAllEventsByLocation(keyword).getBody();
		} catch (Exception e) {

			throw new EventNotFoundException("No events found in " + keyword);
		}
		return ResponseEntity.ok(event);
	}

	/**
	 * Registers a user to an event.
	 *
	 * @param userId  The ID of the user to register.
	 * @param eventId The ID of the event to register the user to.
	 * @return A {@code ResponseEntity} containing a success message.
	 * @throws UserNotFoundException  If no user is found with the specified ID.
	 * @throws EventNotFoundException If no event is found with the specified ID.
	 */
	@PostMapping("/{userId}/register-event/{eventId}")
	public ResponseEntity<String> registerUserToEvent(@PathVariable int userId, @PathVariable int eventId)
			throws UserNotFoundException, EventNotFoundException {
		User user = userService.getById(userId);
		Event event = null;
		try {
			event = eventClient.getEventById(eventId).getBody();
		} catch (Exception e) {

			throw new EventNotFoundException("Event not found with id " + eventId);
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

		return ResponseEntity.ok("User registered to event successfully");
	}

	/**
	 * Retrieves bookings for a particular user.
	 *
	 * @param id The ID of the user whose bookings are to be retrieved.
	 * @return A {@code ResponseEntity} containing a list of {@code Booking}
	 *         objects.
	 * @throws UserNotFoundException    If no user is found with the specified ID.
	 * @throws BookingNotFoundException If no booking is found with the specified
	 *                                  user ID.
	 */
	@GetMapping("/user-id/{id}")
	public ResponseEntity<List<Booking>> getEventForUserId(@PathVariable int id)
			throws UserNotFoundException, BookingNotFoundException {
		userService.getById(id);
		List<Booking> booking = null;
		try {
			booking = bookingClient.getBookingByUserId(id).getBody();
		} catch (Exception e) {
			throw new BookingNotFoundException("No bookings found for user id " + id);
		}
		return ResponseEntity.ok(booking);
	}

	/**
	 * Deletes a booking for a user for a specific event.
	 *
	 * @param userId  The ID of the user.
	 * @param eventId The ID of the event.
	 * @return A {@code ResponseEntity} with no content.
	 * @throws EventNotFoundException If no event is found with the specified ID.
	 * @throws UserNotFoundException  If no user is found with the specified ID.
	 */
	@DeleteMapping("/user/{userId}/event/{eventId}")
	public ResponseEntity<Void> deleteBookingForUser(@PathVariable int userId, @PathVariable int eventId)
			throws EventNotFoundException, UserNotFoundException {
		userService.getById(userId);
		try {
			eventClient.getEventById(eventId).getBody();
		} catch (Exception e) {

			throw new EventNotFoundException("Event not found with id " + eventId);
		}
		bookingClient.deleteBookingByUserIdAndEventId(userId, eventId);
		return ResponseEntity.noContent().build();
	}

}
