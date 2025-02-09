package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Event;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {

	List<Event> findByNameContaining(String name);

	List<Event> findByLocationContaining(String location);

}
