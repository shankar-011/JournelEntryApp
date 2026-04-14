package com.example.notesApp.schedulers;

import com.example.notesApp.cache.AppCache;
import com.example.notesApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class appCacheScheduler {
    @Autowired
    private AppCache appCache;
    @Autowired
    private EmailService emailService;
    @Value("${my.email}")
    private String mailId;
    //@Scheduled(cron = "0 * * * * *")
    public void refreshAppCache(){
        appCache.init();
        emailService.sendMail(mailId,"Cache Refreshed","");
    }
}
