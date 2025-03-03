package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.Event;

@FeignClient("EVENT-SERVICE")
public interface EventClient {
	
	@GetMapping("events/{id}")
	public ResponseEntity<Event> getEventById(@PathVariable int id);

}
