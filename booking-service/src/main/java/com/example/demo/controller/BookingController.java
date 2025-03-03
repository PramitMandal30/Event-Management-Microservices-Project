package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Booking;
import com.example.demo.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	private BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	// create a booking
	@PostMapping("/user/{userId}/event/{eventId}")
	public ResponseEntity<String> saveBooking(@PathVariable int userId , @PathVariable int eventId) {
		String response = bookingService.createBooking(userId,eventId);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// get all bookings
	@GetMapping
	public ResponseEntity<List<Booking>> getAllBookings() {
		return ResponseEntity.ok(bookingService.getAll());
	}

	// get a booking by id
	@GetMapping("/{id}")
	public ResponseEntity<Booking> getBookingById(@PathVariable int id) {
		Booking booking = bookingService.getById(id);
		if (booking == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(booking);
	}

	// get bookings for a particular user
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Booking>> getBookingByUserId(@PathVariable int id) {
		List<Booking> bookings = bookingService.getByUserId(id);
		if (bookings.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(bookings);
	}

	// delete booking
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable int id) {
		bookingService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// delete all bookings for a particular event
	@DeleteMapping("/event/{eventId}")
	public ResponseEntity<Void> deleteBookingByEventId(@PathVariable int eventId) {
		bookingService.deleteByEventId(eventId);
		return ResponseEntity.noContent().build();
	}
	
	// delete all bookings for a particular user
	@DeleteMapping("/delete-booking-for-user/{userId}")
	public ResponseEntity<Void> deleteBookingByUserId(@PathVariable int userId){
		bookingService.deleteByUserId(userId);
		return ResponseEntity.noContent().build();
	}

	// delete booking for a particular user and a particular event
	@DeleteMapping("/user/{userId}/event/{eventId}")
	public ResponseEntity<String> deleteBookingByUserIdAndEventId(@PathVariable int userId, @PathVariable int eventId) {
		String response = bookingService.deleteByUserIdAndEventId(userId, eventId);
		return ResponseEntity.ok(response);
	}
}
