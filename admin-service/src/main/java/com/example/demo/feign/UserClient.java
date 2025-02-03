package com.example.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.entity.UserWrapper;

@FeignClient("USER-SERVICE")
public interface UserClient {

	@GetMapping("users/fetch")
	public ResponseEntity<List<UserWrapper>> getallUsersForAdmins();

}
