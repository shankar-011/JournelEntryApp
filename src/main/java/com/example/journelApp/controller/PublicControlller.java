package com.example.journelApp.controller;

import com.example.journelApp.entity.User;
import com.example.journelApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicControlller {
    @GetMapping("health-check")
    public String healthCheck(){
        return "Good !";
    }
    @Autowired
    UserService userService;
    @PostMapping
    public ResponseEntity<User> createUserEntry(@RequestBody User userEntryFromBody){
        try{
            userService.saveEntry(userEntryFromBody);
            return new ResponseEntity<>(userEntryFromBody, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
