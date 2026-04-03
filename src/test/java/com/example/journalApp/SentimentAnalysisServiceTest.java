package com.example.journalApp;

import com.example.journalApp.schedulers.UserScheduler;
import com.example.journalApp.service.SentimentAnalysisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SentimentAnalysisServiceTest {
    @Autowired
    private UserScheduler userScheduler;
    @Test
    public void testfetchUsersAndSendSaMail(){
        userScheduler.fetchUsersAndSendSaMail();
    }
}
