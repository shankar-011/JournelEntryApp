package com.example.journalApp.schedulers;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.enums.Sentiment;
import com.example.journalApp.repository.UserRepositoryImpl;
import com.example.journalApp.service.EmailService;
import com.example.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private EmailService emailService;
    @Scheduled(cron = "0 * * * * *")
    public void fetchUsersAndSendSaMail(){
        List<User> userForSA = userRepository.getUserForSA();
        for(User user: userForSA){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDateTime().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment: sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment,Integer> entry:sentimentCounts.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment!=null){
                emailService.sendMail(user.getEmail(),"Sentiment Analysis of Last 7 Days",mostFrequentSentiment.toString());
            }
        }


    }

}
