package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Event;
import com.example.demo.dto.User;
import com.example.demo.entity.Booking;
import com.example.demo.feign.EventClient;
import com.example.demo.feign.UserClient;
import com.example.demo.repository.BookingRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

	private BookingRepository repository;
	private EventClient eventClient;
	private UserClient userClient;

	public BookingServiceImpl(BookingRepository repository,EventClient eventClient,UserClient userClient) {
		this.repository = repository;
		this.eventClient=eventClient;
		this.userClient=userClient;
	}

	@Override
	public List<Booking> getAll() {
		return repository.findAll();
	}

	@Override
	public Booking getById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public List<Booking> getByUserId(int id) {
		return repository.getByUserId(id);
	}

	@Override
	@Transactional
	public void deleteByEventId(int eventId) {
		repository.deleteByEventId(eventId);
	}

	@Override
	@Transactional
	public void deleteByUserId(int userId) {
		repository.deleteByUserId(userId);
	}

	@Override
	@Transactional
	public String deleteByUserIdAndEventId(int userId, int eventId) {
		repository.deleteByUserIdAndEventId(userId, eventId);
		return "Booking deleted successfully";
	}

	@Override
	public String createBooking(Integer userId, Integer eventId) {
		User user = userClient.getUserById(userId).getBody();
		Event event = eventClient.getEventById(eventId).getBody();
		Booking booking = new Booking();
        booking.setUserId(user.getId());
        booking.setUserName(user.getName());
        booking.setEventId(event.getId());
        booking.setEventName(event.getName());
        booking.setDate(event.getDate());
        booking.setLocation(event.getLocation());
        booking.setVenue(event.getVenue());
        repository.save(booking);
        return "User " + user.getName() + " registered to event " + event.getName() + " successfully";
	}
}
