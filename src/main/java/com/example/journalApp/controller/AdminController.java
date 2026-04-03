package com.example.journalApp.controller;

import com.example.journalApp.cache.AppCache;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    AppCache appCache;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> listOfAllUsers = userService.getAll();
        if (listOfAllUsers!=null && !listOfAllUsers.isEmpty()){
            return new ResponseEntity<>(listOfAllUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/add-admin")
    public ResponseEntity<?> addAdmin(@RequestBody User userFromBody){
        try{
            userService.saveAdminEntry(userFromBody);
            return new ResponseEntity<>(userFromBody, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }

}
