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

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.dto.User;
import com.example.demo.entity.Admin;
import com.example.demo.exception.AdminNotFoundException;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

/**
 * REST controller for admin-related operations.
 */
@RestController
@RequestMapping("/admins")
public class AdminController {

	private AdminService adminService;

	/**
	 * Constructs an instance of {@code AdminController} with the specified services
	 * and clients.
	 *
	 * @param adminService  The admin service for managing admin data.
	 * @param eventClient   The Feign client for event-related operations.
	 * @param bookingClient The Feign client for booking-related operations.
	 * @param userClient    The Feign client for user-related operations.
	 */
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
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
	public ResponseEntity<String> deleteAdmin(@PathVariable int id) throws AdminNotFoundException {
		String response = adminService.delete(id);
		return ResponseEntity.ok(response);
	}

	/**
	 * Creates a new event.
	 *
	 * @param event The {@code Event} object to create.
	 * @return A {@code ResponseEntity} containing the created {@code Event}.
	 */
	@PostMapping("/create-event")
	public ResponseEntity<Event> createEvent(@RequestBody @Valid Event event) {
		Event response = adminService.createEvent(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
		Event response = adminService.updateEvent(id, event);
		return ResponseEntity.ok(response);
	}

	/**
	 * Deletes an event by its ID and removes associated bookings.
	 *
	 * @param id The ID of the event to delete.
	 * @return A {@code ResponseEntity} with no content.
	 * @throws EventNotFoundException If no event found with the specific ID
	 */
	@DeleteMapping("/delete-event/{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable int id) throws EventNotFoundException {
		String response = adminService.deleteEvent(id);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retrieves a list of all events.
	 *
	 * @return A {@code ResponseEntity} containing a list of {@code Event} objects.
	 */
	@GetMapping("/get-events")
	public ResponseEntity<List<Event>> getAllEvents() {
		List<Event> response = adminService.getAllEvents();
		return ResponseEntity.ok(response);
	}

	/**
	 * Retrieves a list of all bookings.
	 *
	 * @return A {@code ResponseEntity} containing a list of {@code Booking}
	 *         objects.
	 */
	@GetMapping("/get-bookings")
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> response = adminService.getAllBookings();
		return ResponseEntity.ok(response);
	}

	/**
	 * Retrieves a list of all registered users.
	 *
	 * @return A {@code ResponseEntity} containing a list of {@code User}
	 *         objects.
	 */
	@GetMapping("/get-all-users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = adminService.getAllUsers();
		return ResponseEntity.ok(users);
	}

}
