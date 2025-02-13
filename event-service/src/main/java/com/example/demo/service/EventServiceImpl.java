package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.repository.EventRepo;

@Service
public class EventServiceImpl implements EventService {
	
	String message = "Event not present with id: ";

	private EventRepo repo;

	public EventServiceImpl(EventRepo repo) {
		this.repo = repo;
	}

	public void save(Event event) {
		repo.save(event);
	}

	public List<Event> getAll() {
		return repo.findAll();
	}

	public Event getById(Integer id) throws EventNotFoundException {
		return repo.findById(id).orElseThrow(()->new EventNotFoundException(message + id));
	}

	public List<Event> getByName(String keyword) throws EventNotFoundException {
		List<Event> events = repo.findByNameContaining(keyword);
		if(events.isEmpty()) {
			throw new EventNotFoundException("No events found with name " + keyword);
		}
		return events;
	}

	public List<Event> getByLocation(String keyword) throws EventNotFoundException {
		List<Event> events = repo.findByLocation(keyword);
		if(events.isEmpty()) {
			throw new EventNotFoundException("No events found at " + keyword);
		}
		return events;
	}

	public void update(Event event) throws EventNotFoundException {
		if(!repo.existsById(event.getId())) {
			throw new EventNotFoundException(message + event.getId());
		}
		repo.save(event);
	}

	public void delete(Integer id) throws EventNotFoundException {
		if(!repo.existsById(id)) {
			throw new EventNotFoundException(message + id);
		}
		repo.deleteById(id);
	}
}
