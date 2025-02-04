package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepo;

/**
 * Implementation of the {@code AdminService} interface, providing
 * CRUD operations for {@code Admin} entities.
 */
@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepo repo;

    /**
     * Constructs an instance of {@code AdminServiceImpl} with the specified repository.
     *
     * @param repo The {@code AdminRepo} used for data access operations.
     */
    public AdminServiceImpl(AdminRepo repo) {
        this.repo = repo;
    }

    /**
     * Saves a new admin entity to the repository.
     *
     * @param admin The {@code Admin} entity to save.
     */
    public void save(Admin admin) {
        repo.save(admin);
    }

    /**
     * Retrieves all admin entities from the repository.
     *
     * @return A list of {@code Admin} entities.
     */
    public List<Admin> getAll() {
        return repo.findAll();
    }

    /**
     * Retrieves an admin entity by its ID.
     *
     * @param id The ID of the admin to retrieve.
     * @return The {@code Admin} entity if found, or {@code null} if not found.
     */
    public Admin getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Updates an existing admin entity in the repository.
     *
     * @param admin The {@code Admin} entity with updated information.
     */
    public void update(Admin admin) {
        repo.save(admin);
    }

    /**
     * Deletes an admin entity from the repository by its ID.
     *
     * @param id The ID of the admin to delete.
     */
    public void delete(Integer id) {
        repo.deleteById(id);
    }

}
