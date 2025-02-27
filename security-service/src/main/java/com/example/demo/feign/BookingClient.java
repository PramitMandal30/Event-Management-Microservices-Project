package com.example.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.Booking;

@FeignClient("BOOKING-SERVICE")
public interface BookingClient {
	@PostMapping("/bookings")
	ResponseEntity<Booking> saveBooking(@RequestBody Booking booking);

	@GetMapping("/bookings/user/{id}")
	public ResponseEntity<List<Booking>> getBookingByUserId(@PathVariable int id);

	@DeleteMapping("/bookings/user/{userId}/event/{eventId}")
	public ResponseEntity<Void> deleteBookingByUserIdAndEventId(@PathVariable int userId, @PathVariable int eventId);
	
	@DeleteMapping("/bookings/delete-booking-for-user/{userId}")
	public ResponseEntity<Void> deleteBookingByUserId(@PathVariable int userId);
	
	@GetMapping("/bookings")
	public ResponseEntity<List<Booking>> getAllBookings();

	@DeleteMapping("/bookings/event/{eventId}")
	public ResponseEntity<Void> deleteBookingByEventId(@PathVariable int eventId);
}