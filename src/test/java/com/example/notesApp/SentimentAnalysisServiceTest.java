package com.example.notesApp;

import com.example.notesApp.schedulers.UserScheduler;
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
