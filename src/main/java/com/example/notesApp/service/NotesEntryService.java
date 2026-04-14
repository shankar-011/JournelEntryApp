package com.example.notesApp.service;

import com.example.notesApp.dto.NotesEntryDto;
import com.example.notesApp.entity.NotesEntry;
import com.example.notesApp.entity.User;
import com.example.notesApp.repository.NotesEntryRepository;
import com.example.notesApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class NotesEntryService {
    @Autowired
    private NotesEntryRepository notesEntryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  UserService userService;
    @Transactional
    public void saveEntry(NotesEntry notesEntry, String userName){
        try{
            User user = userRepository.findByUserName(userName);
            NotesEntry saved = notesEntryRepository.save(notesEntry);
            user.getNotesEntries().add(saved);
            userRepository.save(user);
        }
        catch (Exception e){
            log.error("During saving notes",e);
            throw new RuntimeException("Exception occured");
        }
    }
    public void saveEntry(NotesEntry notesEntry){
        notesEntryRepository.save(notesEntry);
    }
    public List<NotesEntry> getAll(){

        return notesEntryRepository.findAll();
    }
    public Optional<NotesEntry> getById(ObjectId id){

        return notesEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String userName){
        User userInDb = userService.findByUserName(userName);
        userInDb.getNotesEntries().removeIf(x->x.getId().equals(id));
        userRepository.save(userInDb);
        notesEntryRepository.deleteById(id);
    }

}

