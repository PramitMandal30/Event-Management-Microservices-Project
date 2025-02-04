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
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.feign.BookingClient;
import com.example.demo.feign.EventClient;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	String message = "User not found with id: ";

	private UserService userService;

	private EventClient eventClient;

	private BookingClient bookingClient;

	public UserController(UserService userService, EventClient eventClient, BookingClient bookingClient) {
		this.userService = userService;
		this.eventClient = eventClient;
		this.bookingClient = bookingClient;
	}

	// get all
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAll());
	}

	// get all method for Admin service
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

	// get by Id
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) throws UserNotFoundException {
		User user = userService.getById(id);
		if (user == null) {
			throw new UserNotFoundException(message + id);
		}
		return ResponseEntity.ok(user);
	}

	// add
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
		userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	// update
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody @Valid User user)
			throws UserNotFoundException {
		User userOptional = userService.getById(id);
		if (userOptional == null) {
			throw new UserNotFoundException(message + id);
		}
		user.setId(id);
		userService.update(user);
		return ResponseEntity.ok(user);
	}

	// delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id) throws UserNotFoundException {
		User user = userService.getById(id);
		if (user == null) {
			throw new UserNotFoundException(message + id);
		}
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// search event by name
	@GetMapping("/search-name/{keyword}")
	public ResponseEntity<List<Event>> searchEventByName(@PathVariable String keyword) {
		ResponseEntity<List<Event>> response = eventClient.getAllEventsByName(keyword);
		return ResponseEntity.ok(response.getBody());

	}

	// search event by location
	@GetMapping("/search-location/{keyword}")
	public ResponseEntity<List<Event>> searchEventsByLocation(@PathVariable String keyword) {
		ResponseEntity<List<Event>> response = eventClient.getAllEventsByLocation(keyword);
		return ResponseEntity.ok(response.getBody());
	}

	// register user to event
	@PostMapping("/{userId}/register-event/{eventId}")
	public ResponseEntity<String> registerUserToEvent(@PathVariable int userId, @PathVariable int eventId) {
		User user = userService.getById(userId);
		Event event = eventClient.getEventById(eventId).getBody();

		if (user == null || event == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Event not found");
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

	// get bookings for a particular user
	@GetMapping("/user-id/{id}")
	public ResponseEntity<List<Booking>> getEventForUserId(@PathVariable int id) throws UserNotFoundException {
		User user = userService.getById(id);
		if (user == null) {
			throw new UserNotFoundException(message + id);
		}
		ResponseEntity<List<Booking>> response = bookingClient.getBookingByUserId(id);
		return ResponseEntity.ok(response.getBody());
	}

	// delete a particular booking for a user
	@DeleteMapping("/user/{userId}/event/{eventId}")
	public ResponseEntity<Void> deleteBookingForUser(@PathVariable int userId, @PathVariable int eventId) {
		bookingClient.deleteBookingByUserIdAndEventId(userId, eventId);
		return ResponseEntity.noContent().build();
	}

}
