package com.example.notesApp.cache;


import com.example.notesApp.entity.ConfigNotesAppEntry;
import com.example.notesApp.repository.ConfigNotesAppRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Component
public class AppCache {
    @Autowired
    private ConfigNotesAppRepository configNotesAppRepository;

    private Map<String,String> appCache;
    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigNotesAppEntry> all = configNotesAppRepository.findAll();
        for(ConfigNotesAppEntry confignotesAppEntry:all){
            appCache.put(confignotesAppEntry.getKey(),confignotesAppEntry.getValue());
        }
    }
}
