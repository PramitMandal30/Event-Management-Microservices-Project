package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Admin;
import com.example.demo.exception.AdminNotFoundException;

public interface AdminService {
	void save(Admin admin);

	List<Admin> getAll();

	Admin getById(Integer id) throws AdminNotFoundException;

	void update(Admin admin) throws AdminNotFoundException;

	void delete(Integer id) throws AdminNotFoundException;
}
