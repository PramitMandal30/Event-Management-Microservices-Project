package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Event;
import com.example.demo.exception.EventNotFoundException;

public interface EventService {

	void save(Event event);

	List<Event> getAll();

	Event getById(Integer id) throws EventNotFoundException;

	List<Event> getByName(String keyword) throws EventNotFoundException;

	List<Event> getByLocation(String keyword) throws EventNotFoundException;

	void update(Event event) throws EventNotFoundException;

	String delete(Integer id) throws EventNotFoundException;

}
