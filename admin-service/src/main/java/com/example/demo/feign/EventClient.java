package com.example.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.Event;

import jakarta.validation.Valid;

@FeignClient("EVENT-SERVICE")
public interface EventClient {

	@GetMapping("/events")
	public ResponseEntity<List<Event>> getAllEvents();
	
	@GetMapping("events/{id}")
	public ResponseEntity<Event> getEventById(@PathVariable int id);

	@PostMapping("/events")
	ResponseEntity<Event> saveEvent(@RequestBody Event event);

	@PutMapping("/events/{id}")
	public ResponseEntity<Event> updateEvent(@PathVariable int id, @RequestBody @Valid Event event);

	@DeleteMapping("/events/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable int id);

}