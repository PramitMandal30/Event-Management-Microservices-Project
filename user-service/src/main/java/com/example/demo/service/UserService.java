package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;

public interface UserService {

	void save(User user);

	List<User> getAll();

	User getById(Integer id);

	void update(User user);

	void delete(Integer id);

}
