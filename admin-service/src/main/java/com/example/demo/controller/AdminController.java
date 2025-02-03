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
import com.example.demo.feign.BookingClient;
import com.example.demo.feign.EventClient;
import com.example.demo.feign.UserClient;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
public class AdminController {

	String message = "Admin not found with id: ";

	private AdminService adminService;

	private EventClient eventClient;

	private BookingClient bookingClient;

	private UserClient userClient;

	public AdminController(AdminService adminService, EventClient eventClient, BookingClient bookingClient,
			UserClient userClient) {
		this.adminService = adminService;
		this.eventClient = eventClient;
		this.bookingClient = bookingClient;
		this.userClient = userClient;
	}

	// get all
	@GetMapping
	public ResponseEntity<List<Admin>> getAllAdmins() {
		return ResponseEntity.ok(adminService.getAll());
	}

	// get by Id
	@GetMapping("/{id}")
	public ResponseEntity<Admin> getAdminById(@PathVariable int id) throws AdminNotFoundException {
		Admin admin = adminService.getById(id);
		if (admin == null) {
			throw new AdminNotFoundException(message + id);
		}
		return ResponseEntity.ok(admin);
	}

	// add
	@PostMapping
	public ResponseEntity<Admin> saveAdmin(@RequestBody @Valid Admin admin) {
		adminService.save(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body(admin);
	}

	// update
	@PutMapping("/{id}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody @Valid Admin admin)
			throws AdminNotFoundException {
		Admin adminOptional = adminService.getById(id);
		if (adminOptional == null) {
			throw new AdminNotFoundException(message + id);
		}
		admin.setId(id);
		adminService.update(admin);
		return ResponseEntity.ok(admin);
	}

	// delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable int id) throws AdminNotFoundException {
		Admin admin = adminService.getById(id);
		if (admin == null) {
			throw new AdminNotFoundException(message + id);
		}
		adminService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// create an event
	@PostMapping("/create-event")
	public ResponseEntity<Event> createEvent(@RequestBody @Valid Event event) {
		ResponseEntity<Event> response = eventClient.saveEvent(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
	}

	// update an event
	@PutMapping("/update-event/{id}")
	public ResponseEntity<Event> updateEvent(@PathVariable int id, @RequestBody @Valid Event event) {
		ResponseEntity<Event> response = eventClient.updateEvent(id, event);
		return ResponseEntity.ok(response.getBody());
	}

	// delete an event
	@DeleteMapping("/delete-event/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable int id) {
		eventClient.deleteEvent(id);
		bookingClient.deleteBookingByEventId(id);
		return ResponseEntity.noContent().build();
	}

	// get all events
	@GetMapping("/get-events")
	public ResponseEntity<List<Event>> getAllEvents() {
		ResponseEntity<List<Event>> response = eventClient.getAllEvents();
		return ResponseEntity.ok(response.getBody());
	}

	// get all bookings
	@GetMapping("/get-bookings")
	public ResponseEntity<List<Booking>> getAllBookings() {
		ResponseEntity<List<Booking>> response = bookingClient.getAllBookings();
		return ResponseEntity.ok(response.getBody());
	}

	// get all registered users
	@GetMapping("/get-all-users")
	public ResponseEntity<List<UserWrapper>> getAllUsers() {
		List<UserWrapper> user = userClient.getallUsersForAdmins().getBody();
		return ResponseEntity.ok(user);

	}

}
