package com.example.journelApp.service;

import com.example.journelApp.entity.JournelEntry;
import com.example.journelApp.entity.User;
import com.example.journelApp.repository.JournelEntryRepository;
import com.example.journelApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournelEntryService {
    @Autowired
    private JournelEntryRepository journelEntryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  UserService userService;
    @Transactional
    public void saveEntry(JournelEntry journelEntry,String userName){
        try{
            User user = userRepository.findByUserName(userName);
            JournelEntry saved = journelEntryRepository.save(journelEntry);
            user.getJournelEntries().add(saved);
            userRepository.save(user);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Exception occured");
        }
    }
    public void saveEntry(JournelEntry journelEntry){
        journelEntryRepository.save(journelEntry);
    }
    public List<JournelEntry> getAll(){

        return journelEntryRepository.findAll();
    }
    public Optional<JournelEntry> getById(ObjectId id){

        return journelEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String userName){
        User userInDb = userService.findByUserName(userName);
        userInDb.getJournelEntries().removeIf(x->x.getId().equals(id));
        userRepository.save(userInDb);
        journelEntryRepository.deleteById(id);
    }

}

