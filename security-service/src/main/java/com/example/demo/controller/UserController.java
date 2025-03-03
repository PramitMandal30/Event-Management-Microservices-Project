package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserService;

/**
 * REST controller for user-related operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	/**
	 * Constructs a {@code UserController} with the specified services and clients.
	 *
	 * @param userService   The user service for managing user data.
	 * @param eventClient   The Feign client for event-related operations.
	 * @param bookingClient The Feign client for booking-related operations.
	 */
	public UserController(UserService userService) {
		this.userService = userService;
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
		String response = userService.registerUserToEvent(userId, eventId);
		return ResponseEntity.ok(response);
	}

}
