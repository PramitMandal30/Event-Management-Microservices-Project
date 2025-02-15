package com.example.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.Booking;

@FeignClient("BOOKING-SERVICE")
public interface BookingClient {
	@GetMapping("/bookings")
	public ResponseEntity<List<Booking>> getAllBookings();

	@DeleteMapping("/bookings/event/{eventId}")
	public ResponseEntity<Void> deleteBookingByEventId(@PathVariable int eventId);

}
