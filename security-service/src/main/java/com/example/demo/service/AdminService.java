package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.entity.User;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.UserNotFoundException;

public interface AdminService {
	
	Event createEvent(Event event);
	
	Event updateEvent(Integer eventId , Event event) throws EventNotFoundException;
	
	String deleteEvent(Integer eventId) throws EventNotFoundException;
	
	List<Event> getAllEvents();
	
	List<Booking> getAllBookings();

}
