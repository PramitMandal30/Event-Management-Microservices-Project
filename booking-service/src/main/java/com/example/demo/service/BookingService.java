package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Booking;

public interface BookingService {
	String createBooking(Integer userId,Integer eventId);

	List<Booking> getAll();

	Booking getById(Integer id);

	void delete(Integer id);

	List<Booking> getByUserId(int id);

	void deleteByEventId(int eventId);

	void deleteByUserId(int userId);

	String deleteByUserIdAndEventId(int userId, int eventId);
}