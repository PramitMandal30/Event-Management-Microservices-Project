package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.JWTResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
    private UserService service;
    private JwtService jwtService;
    private UserRepo repo;
    private AuthenticationManager authenticationManager;
    
    

    public AuthController(UserService service, JwtService jwtService, UserRepo repo,
			AuthenticationManager authenticationManager) {
		this.service = service;
		this.jwtService = jwtService;
		this.repo = repo;
		this.authenticationManager = authenticationManager;
	}



	@PostMapping("/signup")
    public String addNewUser(@RequestBody User user) {
        return service.save(user);
    }



    @PostMapping("/authenticate")
    public JWTResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
        	User obj = repo.findByName(authRequest.getUsername()).orElse(null);
            return new JWTResponse(obj.getId(),jwtService.generateToken(authRequest.getUsername(),obj.getRole()),obj.getRole());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    
    @GetMapping("/get")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(service.getAll());
	}
    
    @GetMapping("/get/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) throws UserNotFoundException {
		User user = service.getById(id);
		return ResponseEntity.ok(user);
	}
    
    @PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody @Valid User user)
			throws UserNotFoundException {
		user.setId(id);
		service.update(user);
		return ResponseEntity.ok(user);
	}
    
    @DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable int id) throws UserNotFoundException {
		String response = service.delete(id);
		return response;
	}
}
