package com.example.journalApp;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import com.example.journalApp.service.UserDetailsServiceImple;
import com.example.journalApp.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImpleTests {
    @InjectMocks
    private UserDetailsServiceImple userDetailsService;
    @Mock
    private UserRepository userRepository;
    @Disabled
    @Test
    public void testLoadUserByUsername(){
        when(userRepository.findByUserName(anyString())).thenReturn(User.builder()
                .userName("shankar")
                .password("afafa")
                .roles(List.of("USER"))
                .build());
        UserDetails user = userDetailsService.loadUserByUsername("shankar");
        assertNotNull(user);
        assertEquals("shankar", user.getUsername());

    }

}
