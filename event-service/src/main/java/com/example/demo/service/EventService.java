package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Event;

public interface EventService {

	void save(Event event);

	List<Event> getAll();

	Event getById(Integer id);

	List<Event> getByName(String keyword);

	List<Event> getByLocation(String keyword);

	void update(Event event);

	void delete(Integer id);

}
