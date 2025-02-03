package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepo;

@Service
public class EventServiceImpl implements EventService {

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

	public Event getById(Integer id) {
		return repo.findById(id).orElse(null);
	}

	public List<Event> getByName(String keyword) {
		return repo.findByNameContainingOrDescriptionContaining(keyword, keyword);
	}

	public List<Event> getByLocation(String keyword) {
		return repo.findByLocationContaining(keyword);
	}

	public void update(Event event) {
		repo.save(event);
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
