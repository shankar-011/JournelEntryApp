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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUserData = userService.getAll();
        if(allUserData!=null && !allUserData.isEmpty()){
            return new ResponseEntity<>(allUserData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<User> createEntry(@RequestBody User userEntryFromBody){
        try{
            userService.saveEntry(userEntryFromBody);
            return new ResponseEntity<>(userEntryFromBody,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{userName}")
    public ResponseEntity<?> changeEntry(@RequestBody User userFromBody,@PathVariable String userName ){
        User userInDb = userService.findByUserName(userName);
        if(userInDb!=null){
            userInDb.setUserName(userFromBody.getUserName());
            userInDb.setPassword(userFromBody.getPassword());
            userService.saveEntry(userInDb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
