package com.example.journelApp.controller;

import com.example.journelApp.entity.JournelEntry;
import com.example.journelApp.entity.User;
import com.example.journelApp.service.JournelEntryService;
import com.example.journelApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journel")
public class JournelEntryControllerV2 {
    @Autowired
    private JournelEntryService journelEntryService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<JournelEntry>> getAllJournelEntryOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournelEntry> allData = user.getJournelEntries();
        if(allData!=null && !allData.isEmpty()){
            return new ResponseEntity<>(allData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping()
    public ResponseEntity<JournelEntry> createEntry(@RequestBody JournelEntry myEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            myEntry.setDateTime(LocalDateTime.now());
            journelEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("get/{myId}")
    public ResponseEntity<JournelEntry> getById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournelEntry> entriesOfTheUser = user.getJournelEntries();
        boolean foundIfIdMatchesWithUser = false;
        for(JournelEntry entries : entriesOfTheUser){
            if(entries.getId().equals(myId)){
                foundIfIdMatchesWithUser = true;
            }
        }
        if(foundIfIdMatchesWithUser == true){
            JournelEntry myEntry = journelEntryService.getById(myId).orElse(null);
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
        List<JournelEntry> entriesOfTheUser = user.getJournelEntries();
        boolean foundIfIdMatchesWithUser = false;
        for(JournelEntry entries : entriesOfTheUser){
            if(entries.getId().equals(myId)){
                foundIfIdMatchesWithUser = true;
            }
        }
        if(foundIfIdMatchesWithUser == true){
            JournelEntry myEntry = journelEntryService.getById(myId).orElse(null);
            if (myEntry!=null){
                journelEntryService.deleteById(myId,username);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    @PutMapping("update/{myId}")
    public ResponseEntity<JournelEntry> updateById(@PathVariable ObjectId myId, @RequestBody JournelEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournelEntry> entriesOfTheUser = user.getJournelEntries();
        boolean foundIfIdMatchesWithUser = false;
        for(JournelEntry entries : entriesOfTheUser){
            if(entries.getId().equals(myId)){
                foundIfIdMatchesWithUser = true;
            }
        }
        if(foundIfIdMatchesWithUser == true){
            JournelEntry oldEntry = journelEntryService.getById(myId).orElse(null);
            if(oldEntry != null ){
                oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : oldEntry.getContent());
                journelEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
