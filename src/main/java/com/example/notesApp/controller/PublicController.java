package com.example.notesApp.controller;

import com.example.notesApp.dto.UserLoginDto;
import com.example.notesApp.dto.UserSignUpDto;
import com.example.notesApp.dto.UserSignUpWithOtpDto;
import com.example.notesApp.entity.User;
import com.example.notesApp.service.OtpService;
import com.example.notesApp.service.UserDetailsServiceImple;
import com.example.notesApp.service.UserService;
import com.example.notesApp.utilis.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
@Tag(name="Public APIs")
public class PublicController {
    @GetMapping("health-check")
    @Operation(summary = "Check Health of API")
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
    private OtpService otpService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    @Operation(summary = "Initial SignUp Using Email to receive otp")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSignUpDto userSignUpDto){
        try{
            boolean status = userService.signUpEntry(userSignUpDto);
            return (status==false)?new ResponseEntity<>(HttpStatus.BAD_REQUEST):new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/signup-with-otp")
    @Operation(summary = "Creating account using OTP Received")
    public ResponseEntity<User> signUpWithOtp(@Valid @RequestBody UserSignUpWithOtpDto userEntryFromBody){
        try{
            boolean verified = otpService.verifyOtp(userEntryFromBody.getEmail(), userEntryFromBody.getOtp());
            if (verified==false){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            User user = new User();
            user.setUserName(userEntryFromBody.getUserName());
            user.setPassword(userEntryFromBody.getPassword());
            user.setEmail(userEntryFromBody.getEmail());
            user.setSentimentAnalysis(userEntryFromBody.isSentimentAnalysis());
            user.setCity(userEntryFromBody.getCity());
            boolean status = userService.saveEntry(user);
            return (status==false)?new ResponseEntity<>(HttpStatus.BAD_REQUEST):new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    @Operation(summary = "Login using userName and password to generate JWT")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto userEntryFromBody){
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
