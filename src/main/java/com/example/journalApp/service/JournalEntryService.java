package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.JournalEntryRepository;
import com.example.journalApp.repository.JournalEntryRepository;
import com.example.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  UserService userService;
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = userRepository.findByUserName(userName);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userRepository.save(user);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Exception occured");
        }
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){

        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getById(ObjectId id){

        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String userName){
        User userInDb = userService.findByUserName(userName);
        userInDb.getJournalEntries().removeIf(x->x.getId().equals(id));
        userRepository.save(userInDb);
        journalEntryRepository.deleteById(id);
    }

}

