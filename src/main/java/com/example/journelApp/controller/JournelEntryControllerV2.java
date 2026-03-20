package com.example.journelApp.controller;

import com.example.journelApp.entity.JournelEntry;
import com.example.journelApp.entity.User;
import com.example.journelApp.service.JournelEntryService;
import com.example.journelApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{userName}")
    public ResponseEntity<List<JournelEntry>> getAllJournelEntryOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournelEntry> allData = user.getJournelEntries();
        if(allData!=null && !allData.isEmpty()){
            return new ResponseEntity<>(allData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/{userName}")
    public ResponseEntity<JournelEntry> createEntry(@RequestBody JournelEntry myEntry,@PathVariable String userName){
        try{
            myEntry.setDateTime(LocalDateTime.now());
            journelEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("get/{myId}")
    public ResponseEntity<JournelEntry> getById(@PathVariable ObjectId myId){
        JournelEntry myEntry = journelEntryService.getById(myId).orElse(null);
        if (myEntry!=null){
            return new ResponseEntity<>(myEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("delete/{userName}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId,@PathVariable String userName){
        JournelEntry myEntry = journelEntryService.getById(myId).orElse(null);
        if (myEntry!=null){
            journelEntryService.deleteById(myId,userName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("update/{userName}/{myId}")
    public ResponseEntity<JournelEntry> updateById(@PathVariable ObjectId myId,@PathVariable String userName, @RequestBody JournelEntry newEntry){
        JournelEntry oldEntry = journelEntryService.getById(myId).orElse(null);
        if(oldEntry != null ){
           oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : oldEntry.getTitle());
           oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : oldEntry.getContent());
           journelEntryService.saveEntry(oldEntry);
           return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
