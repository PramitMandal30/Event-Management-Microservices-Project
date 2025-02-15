package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.dto.User;
import com.example.demo.entity.Admin;
import com.example.demo.exception.AdminNotFoundException;
import com.example.demo.exception.EventNotFoundException;

public interface AdminService {
	void save(Admin admin);

	List<Admin> getAll();

	Admin getById(Integer id) throws AdminNotFoundException;

	void update(Admin admin) throws AdminNotFoundException;

	String delete(Integer id) throws AdminNotFoundException;
	
	Event createEvent(Event event);
	
	Event updateEvent(Integer eventId , Event event) throws EventNotFoundException;
	
	String deleteEvent(Integer eventId) throws EventNotFoundException;
	
	List<Event> getAllEvents();
	
	List<Booking> getAllBookings();
	
	List<User> getAllUsers();
}
