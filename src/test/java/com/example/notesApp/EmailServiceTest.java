package com.example.notesApp;

import com.example.notesApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class EmailServiceTest {
    @Autowired
    EmailService emailService;
    @Test
    public void testEmailService(){
        emailService.sendMail("naval.sawfish.ekxc@hidingmail.com","Testing JavaMailSender","Hii, How are you!");
    }
}
