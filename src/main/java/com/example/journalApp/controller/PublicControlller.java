package com.example.journalApp.controller;

import com.example.journalApp.dto.UserDto;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserDetailsServiceImple;
import com.example.journalApp.service.UserService;
import com.example.journalApp.utilis.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Tag(name="Public APIs")
public class PublicControlller {
    @GetMapping("health-check")
    public String healthCheck(){

        return "Good !";
    }
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImple userDetailsServiceImple;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody UserDto userEntryFromBody){
        try{
            User user = new User();
            user.setUserName(userEntryFromBody.getUserName());
            user.setPassword(userEntryFromBody.getPassword());
            user.setEmail(userEntryFromBody.getEmail());
            user.setSentimentAnalysis(userEntryFromBody.isSentimentAnalysis());
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userEntryFromBody){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntryFromBody.getUserName(),userEntryFromBody.getPassword()));
            UserDetails userDetails = userDetailsServiceImple.loadUserByUsername(userEntryFromBody.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
