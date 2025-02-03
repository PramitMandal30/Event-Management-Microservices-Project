package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	private UserRepo repo;

	public UserServiceImpl(UserRepo repo) {
		this.repo = repo;
	}

	public void save(User user) {
		repo.save(user);
	}

	public List<User> getAll() {
		return repo.findAll();
	}

	public User getById(Integer id) {
		return repo.findById(id).orElse(null);
	}

	public void update(User user) {
		repo.save(user);
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
