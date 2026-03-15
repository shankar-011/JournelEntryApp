package com.example.journelApp.service;

import com.example.journelApp.entity.JournelEntry;
import com.example.journelApp.repository.JournelEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournelEntryService {
    @Autowired
    private JournelEntryRepository journelEntryRepository;

    public void saveEntry(JournelEntry journelEntry){
        journelEntryRepository.save(journelEntry);
    }
    public List<JournelEntry> getAll(){
        return journelEntryRepository.findAll();
    }
    public Optional<JournelEntry> getById(ObjectId id){
        return journelEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        journelEntryRepository.deleteById(id);
    }

}

