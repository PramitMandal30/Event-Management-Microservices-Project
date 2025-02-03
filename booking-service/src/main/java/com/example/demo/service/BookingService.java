package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Booking;

public interface BookingService {
	void save(Booking booking);

	List<Booking> getAll();

	Booking getById(Integer id);

	void delete(Integer id);

	List<Booking> getByUserId(int id);

	void deleteByEventId(int eventId);

	void deleteByUserId(int userId);

	void deleteByUserIdAndEventId(int userId, int eventId);
}