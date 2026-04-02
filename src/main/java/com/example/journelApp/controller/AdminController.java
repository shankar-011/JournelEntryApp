package com.example.journelApp.controller;

import com.example.journelApp.entity.User;
import com.example.journelApp.service.UserService;
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

}
