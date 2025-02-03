package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Booking;
import com.example.demo.repository.BookingRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

	private BookingRepository repository;

	public BookingServiceImpl(BookingRepository repository) {
		this.repository = repository;
	}

	@Override
	public void save(Booking booking) {
		repository.save(booking);
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
	public void deleteByUserIdAndEventId(int userId, int eventId) {
		repository.deleteByUserIdAndEventId(userId, eventId);
	}
}
