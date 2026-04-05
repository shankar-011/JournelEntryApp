package com.example.journalApp.controller;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("journal")
@Tag(name="Journal APIs")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all Journel Entries")
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
    @Operation(summary = "Add Journel Entry")
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
    @Operation(summary = "Get Journel Entry based on ID")
    public ResponseEntity<JournalEntry> getById(@PathVariable String myId){
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> entriesOfTheUser = user.getJournalEntries();
        boolean foundIfIdMatchesWithUser = false;
        for(JournalEntry entries : entriesOfTheUser){
            if(entries.getId().equals(objectId)){
                foundIfIdMatchesWithUser = true;
            }
        }
        if(foundIfIdMatchesWithUser == true){
            JournalEntry myEntry = journalEntryService.getById(objectId).orElse(null);
            if (myEntry!=null){
                return new ResponseEntity<>(myEntry,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping("delete/{myId}")
    @Operation(summary = "Delete a Journal Entry")
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
    @Operation(summary = "Update Journal Entry")
    public ResponseEntity<JournalEntry> updateById(@PathVariable String myId, @RequestBody JournalEntry newEntry){
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> entriesOfTheUser = user.getJournalEntries();
        boolean foundIfIdMatchesWithUser = false;
        for(JournalEntry entries : entriesOfTheUser){
            if(entries.getId().equals(objectId)){
                foundIfIdMatchesWithUser = true;
            }
        }
        if(foundIfIdMatchesWithUser == true){
            JournalEntry oldEntry = journalEntryService.getById(objectId).orElse(null);
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
