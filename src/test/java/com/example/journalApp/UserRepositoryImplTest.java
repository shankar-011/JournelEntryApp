package com.example.journalApp;

import com.example.journalApp.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.notNull;

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
