package com.example.journalApp.controller;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAlljournalEntryOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> allData = user.getJournalEntries();
        if(allData!=null && !allData.isEmpty()){
            return new ResponseEntity<>(allData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            myEntry.setDateTime(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("get/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> entriesOfTheUser = user.getJournalEntries();
        boolean foundIfIdMatchesWithUser = false;
        for(JournalEntry entries : entriesOfTheUser){
            if(entries.getId().equals(myId)){
                foundIfIdMatchesWithUser = true;
            }
        }
        if(foundIfIdMatchesWithUser == true){
            JournalEntry myEntry = journalEntryService.getById(myId).orElse(null);
            if (myEntry!=null){
                return new ResponseEntity<>(myEntry,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping("delete/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> entriesOfTheUser = user.getJournalEntries();
        boolean foundIfIdMatchesWithUser = false;
        for(JournalEntry entries : entriesOfTheUser){
            if(entries.getId().equals(myId)){
                foundIfIdMatchesWithUser = true;
            }
        }
        if(foundIfIdMatchesWithUser == true){
            JournalEntry myEntry = journalEntryService.getById(myId).orElse(null);
            if (myEntry!=null){
                journalEntryService.deleteById(myId,username);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    @PutMapping("update/{myId}")
    public ResponseEntity<JournalEntry> updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> entriesOfTheUser = user.getJournalEntries();
        boolean foundIfIdMatchesWithUser = false;
        for(JournalEntry entries : entriesOfTheUser){
            if(entries.getId().equals(myId)){
                foundIfIdMatchesWithUser = true;
            }
        }
        if(foundIfIdMatchesWithUser == true){
            JournalEntry oldEntry = journalEntryService.getById(myId).orElse(null);
            if(oldEntry != null ){
                oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
