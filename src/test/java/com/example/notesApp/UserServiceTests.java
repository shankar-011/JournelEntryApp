package com.example.notesApp;

import com.example.notesApp.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@BeforeAll,@BeforeEach,@AfterAll,@AfterEach
@SpringBootTest
public class UserServiceTests {
    @Autowired
    UserService userService;
    @Disabled
    @ParameterizedTest
    @CsvSource({
        "shankar",
        "test",
        "new"
    })
    public void testsFindByUserName(String name){
        assertNotNull(userService.findByUserName(name));
    }
    @Disabled
    @Test
    public void testFindByUserName(){
        assertNotNull(userService.findByUserName("shankar"));
    }
}
