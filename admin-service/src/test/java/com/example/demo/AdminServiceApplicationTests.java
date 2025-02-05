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

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepo;
import com.example.demo.service.AdminServiceImpl;

@SpringBootTest
class AdminServiceApplicationTests {

    @Mock
    private AdminRepo adminRepo;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin1;
    private Admin admin2;

    @BeforeEach
    void setUp() {
        admin1 = new Admin(1, "Admin One", "admin1@example.com", "password1");
        admin2 = new Admin(2, "Admin Two", "admin2@example.com", "password2");
    }

    @Test
    void testSave() {
        adminService.save(admin1);
        verify(adminRepo, times(1)).save(admin1);
    }

    @Test
    void testGetAll() {
        List<Admin> admins = Arrays.asList(admin1, admin2);
        when(adminRepo.findAll()).thenReturn(admins);

        List<Admin> result = adminService.getAll();

        assertEquals(2, result.size());
        assertEquals("Admin One", result.get(0).getName());
        verify(adminRepo, times(1)).findAll();
    }

    @Test
    void testGetById() {
        when(adminRepo.findById(admin1.getId())).thenReturn(Optional.of(admin1));

        Admin result = adminService.getById(admin1.getId());

        assertEquals("Admin One", result.getName());
        verify(adminRepo, times(1)).findById(admin1.getId());
    }

    @Test
    void testUpdate() {
        adminService.update(admin1);
        verify(adminRepo, times(1)).save(admin1);
    }

    @Test
    void testDelete() {
        adminService.delete(admin1.getId());
        verify(adminRepo, times(1)).deleteById(admin1.getId());
    }
}
