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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUser(){
//        List<User> allUserData = userService.getAll();
//        if(allUserData!=null && !allUserData.isEmpty()){
//            return new ResponseEntity<>(allUserData, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping
    public ResponseEntity<?> changeEntry(@RequestBody User userFromBody ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameFromAuth = authentication.getName();
        User userInDb = userService.findByUserName(nameFromAuth);
        if(userInDb!=null){
            userInDb.setUserName(userFromBody.getUserName());
            userInDb.setPassword(userFromBody.getPassword());
            userService.saveEntry(userInDb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameFromAuth = authentication.getName();
        try{
            userService.deleteByUserName(nameFromAuth);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
