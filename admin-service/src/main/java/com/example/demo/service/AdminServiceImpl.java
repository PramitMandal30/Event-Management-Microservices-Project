package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepo;

@Service
public class AdminServiceImpl implements AdminService {

	private AdminRepo repo;

	public AdminServiceImpl(AdminRepo repo) {
		this.repo = repo;
	}

	public void save(Admin admin) {
		repo.save(admin);
	}

	public List<Admin> getAll() {
		return repo.findAll();
	}

	public Admin getById(Integer id) {
		return repo.findById(id).orElse(null);
	}

	public void update(Admin admin) {
		repo.save(admin);
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
