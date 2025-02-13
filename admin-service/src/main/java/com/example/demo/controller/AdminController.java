package com.example.demo.controller;

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

import com.example.demo.entity.Admin;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Event;
import com.example.demo.entity.UserWrapper;
import com.example.demo.exception.AdminNotFoundException;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.feign.BookingClient;
import com.example.demo.feign.EventClient;
import com.example.demo.feign.UserClient;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

/**
 * REST controller for admin-related operations.
 */
@RestController
@RequestMapping("/admins")
public class AdminController {

	private AdminService adminService;
	private EventClient eventClient;
	private BookingClient bookingClient;
	private UserClient userClient;

	/**
	 * Constructs an instance of {@code AdminController} with the specified services
	 * and clients.
	 *
	 * @param adminService  The admin service for managing admin data.
	 * @param eventClient   The Feign client for event-related operations.
	 * @param bookingClient The Feign client for booking-related operations.
	 * @param userClient    The Feign client for user-related operations.
	 */
	public AdminController(AdminService adminService, EventClient eventClient, BookingClient bookingClient,
			UserClient userClient) {
		this.adminService = adminService;
		this.eventClient = eventClient;
		this.bookingClient = bookingClient;
		this.userClient = userClient;
	}

	/**
	 * Retrieves a list of all admins.
	 *
	 * @return A {@code ResponseEntity} containing a list of {@code Admin} objects.
	 */
	@GetMapping
	public ResponseEntity<List<Admin>> getAllAdmins() {
		return ResponseEntity.ok(adminService.getAll());
	}

	/**
	 * Retrieves an admin by their ID.
	 *
	 * @param id The ID of the admin to retrieve.
	 * @return A {@code ResponseEntity} containing the requested {@code Admin}.
	 * @throws AdminNotFoundException If no admin is found with the specified ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Admin> getAdminById(@PathVariable int id) throws AdminNotFoundException {
		Admin admin = adminService.getById(id);
		return ResponseEntity.ok(admin);
	}

	/**
	 * Saves a new admin.
	 *
	 * @param admin The {@code Admin} object to save.
	 * @return A {@code ResponseEntity} containing the saved {@code Admin}.
	 */
	@PostMapping
	public ResponseEntity<Admin> saveAdmin(@RequestBody @Valid Admin admin) {
		adminService.save(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body(admin);
	}

	/**
	 * Updates an existing admin.
	 *
	 * @param id    The ID of the admin to update.
	 * @param admin The updated {@code Admin} object.
	 * @return A {@code ResponseEntity} containing the updated {@code Admin}.
	 * @throws AdminNotFoundException If no admin is found with the specified ID.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody @Valid Admin admin)
			throws AdminNotFoundException {
		admin.setId(id);
		adminService.update(admin);
		return ResponseEntity.ok(admin);
	}

	/**
	 * Deletes an admin by their ID.
	 *
	 * @param id The ID of the admin to delete.
	 * @return A {@code ResponseEntity} with no content.
	 * @throws AdminNotFoundException If no admin is found with the specified ID.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable int id) throws AdminNotFoundException {
		adminService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Creates a new event.
	 *
	 * @param event The {@code Event} object to create.
	 * @return A {@code ResponseEntity} containing the created {@code Event}.
	 */
	@PostMapping("/create-event")
	public ResponseEntity<Event> createEvent(@RequestBody @Valid Event event) {
		ResponseEntity<Event> response = eventClient.saveEvent(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
	}

	/**
	 * Updates an existing event.
	 *
	 * @param id    The ID of the event to update.
	 * @param event The updated {@code Event} object.
	 * @return A {@code ResponseEntity} containing the updated {@code Event}.
	 * @throws EventNotFoundException If no event found with the specific ID
	 */
	@PutMapping("/update-event/{id}")
	public ResponseEntity<Event> updateEvent(@PathVariable int id, @RequestBody @Valid Event event)
			throws EventNotFoundException {
		try {
			eventClient.getEventById(id).getBody();
		} catch (Exception e) {
			throw new EventNotFoundException("Event not found with id : " + id);
		}
		ResponseEntity<Event> response = eventClient.updateEvent(id, event);
		return ResponseEntity.ok(response.getBody());
	}

	/**
	 * Deletes an event by its ID and removes associated bookings.
	 *
	 * @param id The ID of the event to delete.
	 * @return A {@code ResponseEntity} with no content.
	 * @throws EventNotFoundException If no event found with the specific ID
	 */
	@DeleteMapping("/delete-event/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable int id) throws EventNotFoundException {
		try {
			eventClient.getEventById(id).getBody();
		} catch (Exception e) {
			throw new EventNotFoundException("Event not found with id : " + id);
		}
		eventClient.deleteEvent(id);
		bookingClient.deleteBookingByEventId(id);
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
	 * Retrieves a list of all bookings.
	 *
	 * @return A {@code ResponseEntity} containing a list of {@code Booking}
	 *         objects.
	 */
	@GetMapping("/get-bookings")
	public ResponseEntity<List<Booking>> getAllBookings() {
		ResponseEntity<List<Booking>> response = bookingClient.getAllBookings();
		return ResponseEntity.ok(response.getBody());
	}

	/**
	 * Retrieves a list of all registered users.
	 *
	 * @return A {@code ResponseEntity} containing a list of {@code UserWrapper}
	 *         objects.
	 */
	@GetMapping("/get-all-users")
	public ResponseEntity<List<UserWrapper>> getAllUsers() {
		List<UserWrapper> users = userClient.getallUsersForAdmins().getBody();
		return ResponseEntity.ok(users);
	}

}
