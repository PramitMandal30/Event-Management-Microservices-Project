package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.Booking;

@FeignClient("BOOKING-SERVICE")
public interface BookingClient {
	@PostMapping("/bookings")
	ResponseEntity<Booking> saveBooking(@RequestBody Booking booking);
	
	@DeleteMapping("/bookings/delete-booking-for-user/{userId}")
	public ResponseEntity<Void> deleteBookingByUserId(@PathVariable int userId);
}