package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;

public interface UserService {

	void save(User user);

	List<User> getAll();

	User getById(Integer id) throws UserNotFoundException;

	void update(User user) throws UserNotFoundException;

	void delete(Integer id) throws UserNotFoundException;

}
