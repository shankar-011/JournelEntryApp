package com.example.notesApp.controller;

import com.example.notesApp.api.response.WeatherResponse;
import com.example.notesApp.dto.UserChangeDto;
import com.example.notesApp.entity.User;
import com.example.notesApp.service.UserService;
import com.example.notesApp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name="User APIs")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private WeatherService weatherService;
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUser(){
//        List<User> allUserData = userService.getAll();
//        if(allUserData!=null && !allUserData.isEmpty()){
//            return new ResponseEntity<>(allUserData, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping
    @Operation(summary = "Update a User")
    @Transactional
    public ResponseEntity<?> changeEntry(@Valid @RequestBody UserChangeDto userFromBody ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameFromAuth = authentication.getName();
        User userInDb = userService.findByUserName(nameFromAuth);
        User user = new User();
        if(userFromBody.getUserName()!=null){
            user.setUserName(userFromBody.getUserName());
        }
        user.setPassword(userFromBody.getPassword());

        if(userInDb!=null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            if (userFromBody.getPassword() != null) {
                userInDb.setPasswordChangedAt(System.currentTimeMillis());
            }
            userService.saveEntry(userInDb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    @Operation(summary = "Delete a User")
    @Transactional
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


    @GetMapping
    @Operation(summary = "Greet the user. Give feelslike weather if they have filled up city name")
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        if (user.getCity()!=null){
            WeatherResponse currWeather = weatherService.getWeather(user.getCity());
            String feelsLike = "";
            if (currWeather!=null){
                feelsLike = String.valueOf(currWeather.getCurrent().getFeelslike());

            }
            return new ResponseEntity<>("Hi "+username+" It feels like "+feelsLike+" in "+user.getCity(),HttpStatus.OK);
        }
        return new ResponseEntity<>("Hello "+username,HttpStatus.OK);

    }
}
