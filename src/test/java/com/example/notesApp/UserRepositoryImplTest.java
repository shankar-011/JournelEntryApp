package com.example.notesApp;

import com.example.notesApp.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRepositoryImplTest {
    @Autowired
    UserRepositoryImpl userRepository;
    @Test
    public void testGetUserForSA(){
        assertNotNull(userRepository.getUserForSA());
    }
    @Test
    public void testIsAdmin(){
        assertNotNull(userRepository.isAdmin());
    }
}
