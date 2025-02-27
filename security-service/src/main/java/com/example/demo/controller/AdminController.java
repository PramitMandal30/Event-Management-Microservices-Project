package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

/**
 * REST controller for admin-related operations.
 */
@RestController
@RequestMapping("/admins")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

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

}
