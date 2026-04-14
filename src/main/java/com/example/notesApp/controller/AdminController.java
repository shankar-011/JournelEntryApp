package com.example.notesApp.controller;

import com.example.notesApp.cache.AppCache;
import com.example.notesApp.dto.AddAdminDto;
import com.example.notesApp.entity.User;
import com.example.notesApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/admin")
@Tag(name="Admin APIs")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    AppCache appCache;
    @GetMapping("/all-users")
    @Operation(summary = "Display all users")
    public ResponseEntity<?> getAllUsers(){
        List<User> listOfAllUsers = userService.getAll();
        if (listOfAllUsers!=null && !listOfAllUsers.isEmpty()){
            return new ResponseEntity<>(listOfAllUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/add-admin")
    @Operation(summary = "Create another admin account")
    public ResponseEntity<?> addAdmin(@Valid @RequestBody AddAdminDto userFromBody){
        try{
            User user = new User();
            user.setUserName(userFromBody.getUserName());
            user.setPassword(userFromBody.getPassword());
            user.setEmail(userFromBody.getEmail());
            userService.saveAdminEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception During add-admin",e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("clear-app-cache")
    @Operation(summary = "Reload Config DB")
    public void clearAppCache(){
        appCache.init();
    }

}
