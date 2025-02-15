package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.dto.UserWrapper;
import com.example.demo.entity.User;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.UserNotFoundException;

public interface UserService {

	void save(User user);

	List<User> getAll();
	
	List<UserWrapper> getAllUsersForAdmins();

	User getById(Integer id) throws UserNotFoundException;

	void update(User user) throws UserNotFoundException;

	String delete(Integer id) throws UserNotFoundException;
	
	List<Event> getAllEvents();
	
	List<Event> getEventsByName(String keyword) throws EventNotFoundException;
	
	List<Event> getEventsByLocation(String keyword) throws EventNotFoundException;
	
	String registerUserToEvent(Integer userId , Integer eventId) throws UserNotFoundException, EventNotFoundException;
	
	List<Booking> getBookingsForUserId(Integer userId) throws BookingNotFoundException;
	
	String cancelBooking(Integer userId , Integer eventId) throws UserNotFoundException, EventNotFoundException;

}
