package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserServiceImpl;

@SpringBootTest
class UserServiceApplicationTests {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1, "User One", "user1@example.com", "password1","9090909090");
        user2 = new User(2, "User Two", "user2@example.com", "password2","8080808080");
    }

    @Test
    void testSave() {
        userService.save(user1);
        verify(userRepo, times(1)).save(user1);
    }

    @Test
    void testGetAll() {
        List<User> users = Arrays.asList(user1, user2);
        when(userRepo.findAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertEquals(2, result.size());
        assertEquals("User One", result.get(0).getName());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void testGetById() {
        when(userRepo.findById(user1.getId())).thenReturn(Optional.of(user1));

        User result = userService.getById(user1.getId());

        assertEquals("User One", result.getName());
        verify(userRepo, times(1)).findById(user1.getId());
    }

    @Test
    void testUpdate() {
        userService.update(user1);
        verify(userRepo, times(1)).save(user1);
    }

    @Test
    void testDelete() {
        userService.delete(user1.getId());
        verify(userRepo, times(1)).deleteById(user1.getId());
    }
}
