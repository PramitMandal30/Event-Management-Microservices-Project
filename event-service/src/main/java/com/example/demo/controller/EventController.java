package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Event;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {

	String message = "Event not present with id: ";

	private EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	// get all
	@GetMapping
	public ResponseEntity<List<Event>> getAllEvents() {
		return ResponseEntity.ok(eventService.getAll());
	}

	// get by Id
	@GetMapping("/{id}")
	public ResponseEntity<Event> getEventById(@PathVariable int id) throws EventNotFoundException {
		Event event = eventService.getById(id);
		if (event == null) {
			throw new EventNotFoundException(message + id);
		}
		return ResponseEntity.ok(event);
	}

	// get by Name
	@GetMapping("/name/{keyword}")
	public ResponseEntity<List<Event>> getAllEventsByName(@PathVariable String keyword) throws EventNotFoundException {
		List<Event> event = eventService.getByName(keyword);
		if (event.isEmpty()) {
			throw new EventNotFoundException("No events found with name: " + keyword);
		}
		return ResponseEntity.ok(event);
	}

	// get by Location
	@GetMapping("/location/{keyword}")
	public ResponseEntity<List<Event>> getAllEventsByLocation(@PathVariable String keyword)
			throws EventNotFoundException {
		List<Event> event = eventService.getByLocation(keyword);
		if (event.isEmpty()) {
			throw new EventNotFoundException("No events found at: " + keyword);
		}
		return ResponseEntity.ok(event);
	}

	// add
	@PostMapping
	public ResponseEntity<Event> saveEvent(@RequestBody @Valid Event event) {
		eventService.save(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(event);
	}

	// update
	@PutMapping("/{id}")
	public ResponseEntity<Event> updateEvent(@PathVariable int id, @RequestBody @Valid Event event)
			throws EventNotFoundException {
		Event eventOptional = eventService.getById(id);
		if (eventOptional == null) {
			throw new EventNotFoundException(message + id);
		}
		event.setId(id);
		eventService.update(event);
		return ResponseEntity.ok(event);
	}

	// delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable int id) throws EventNotFoundException {
		Event event = eventService.getById(id);
		if (event == null) {
			throw new EventNotFoundException(message + id);
		}
		eventService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
