package com.example.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Booking;

@FeignClient("BOOKING-SERVICE")
public interface BookingClient {
	@PostMapping("/bookings")
	ResponseEntity<Booking> saveBooking(@RequestBody Booking booking);

	@GetMapping("/bookings/user/{id}")
	public ResponseEntity<List<Booking>> getBookingByUserId(@PathVariable int id);

	@DeleteMapping("/bookings/user/{userId}/event/{eventId}")
	public ResponseEntity<Void> deleteBookingByUserIdAndEventId(@PathVariable int userId, @PathVariable int eventId);
}