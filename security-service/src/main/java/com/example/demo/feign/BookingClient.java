package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("BOOKING-SERVICE")
public interface BookingClient {
	
	@DeleteMapping("/bookings/delete-booking-for-user/{userId}")
	public ResponseEntity<Void> deleteBookingByUserId(@PathVariable int userId);
}