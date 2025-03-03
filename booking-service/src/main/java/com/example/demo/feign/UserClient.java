package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.User;

@FeignClient("SECURITY-SERVICE")
public interface UserClient {
	
	@GetMapping("auth/get/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id);

}
