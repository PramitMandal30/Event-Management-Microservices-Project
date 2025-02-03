package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Admin;

public interface AdminService {
	void save(Admin admin);

	List<Admin> getAll();

	Admin getById(Integer id);

	void update(Admin admin);

	void delete(Integer id);
}
