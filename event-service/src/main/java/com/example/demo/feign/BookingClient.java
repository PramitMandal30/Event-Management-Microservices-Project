package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("BOOKING-SERVICE")
public interface BookingClient {
	
	@DeleteMapping("/bookings/event/{eventId}")
	public ResponseEntity<Void> deleteBookingByEventId(@PathVariable int eventId);

}
