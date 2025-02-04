package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;

/**
 * Implementation of the {@code UserService} interface, providing
 * CRUD operations for {@code User} entities.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepo repo;

    /**
     * Constructs an instance of {@code UserServiceImpl} with the specified repository.
     *
     * @param repo The {@code UserRepo} used for data access operations.
     */
    public UserServiceImpl(UserRepo repo) {
        this.repo = repo;
    }

    /**
     * Saves a new user entity to the repository.
     *
     * @param user The {@code User} entity to save.
     */
    public void save(User user) {
        repo.save(user);
    }

    /**
     * Retrieves all user entities from the repository.
     *
     * @return A list of {@code User} entities.
     */
    public List<User> getAll() {
        return repo.findAll();
    }

    /**
     * Retrieves a user entity by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@code User} entity if found, or {@code null} if not found.
     */
    public User getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Updates an existing user entity in the repository.
     *
     * @param user The {@code User} entity with updated information.
     */
    public void update(User user) {
        repo.save(user);
    }

    /**
     * Deletes a user entity from the repository by its ID.
     *
     * @param id The ID of the user to delete.
     */
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
