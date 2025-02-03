package com.example.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Event;

@FeignClient("EVENT-SERVICE")
public interface EventClient {
	
	@GetMapping("events/name/{keyword}")
	public ResponseEntity<List<Event>> getAllEventsByName(@PathVariable String keyword);

	@GetMapping("events/location/{keyword}")
	public ResponseEntity<List<Event>> getAllEventsByLocation(@PathVariable String keyword);
	
	@GetMapping("events/{id}")
	public ResponseEntity<Event> getEventById(@PathVariable int id);

}
