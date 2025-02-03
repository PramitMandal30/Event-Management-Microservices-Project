package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

	List<Booking> getByUserId(int id);

	void deleteByEventId(int id);

	void deleteByUserId(int userId);

	@Modifying
	@Query("DELETE FROM Booking b WHERE b.userId = :userId AND b.eventId = :eventId")
	void deleteByUserIdAndEventId(@Param("userId") int userId, @Param("eventId") int eventId);
}
