package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.Booking;
import com.example.demo.dto.Event;
import com.example.demo.entity.User;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.UserNotFoundException;

public interface UserService {
	
	String save(User user);

	List<User> getAll();

	User getById(Integer id) throws UserNotFoundException;

	void update(User user) throws UserNotFoundException;

	String delete(Integer id) throws UserNotFoundException;
	
	String registerUserToEvent(Integer userId , Integer eventId) throws UserNotFoundException, EventNotFoundException;

}
