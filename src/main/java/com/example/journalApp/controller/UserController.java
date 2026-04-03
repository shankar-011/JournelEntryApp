package com.example.journalApp.controller;

import com.example.journalApp.api.response.WeatherResponse;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;
import com.example.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
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


    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WeatherResponse currWeather = weatherService.getWeather("Kochi");
        String feelsLike = "";
        if (currWeather!=null){
            feelsLike = String.valueOf(currWeather.getCurrent().getFeelslike());

        }
        return new ResponseEntity<>("Hi "+username+" It feels like "+feelsLike,HttpStatus.OK);

    }
}
