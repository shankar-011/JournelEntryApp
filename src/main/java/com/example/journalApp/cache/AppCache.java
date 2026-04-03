package com.example.journalApp.cache;


import com.example.journalApp.entity.ConfigJournalAppEntry;
import com.example.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> appCache;
    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntry> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntry configjournalAppEntry:all){
            appCache.put(configjournalAppEntry.getKey(),configjournalAppEntry.getValue());
        }
    }
}
