package com.example.notesApp.service;

import com.example.notesApp.dto.UserSignUpDto;
import com.example.notesApp.entity.User;
import com.example.notesApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private OtpService otpService;
    @Transactional
    public boolean saveEntry(User user){
        try{
            String userNameFromBody = user.getUserName();
            String passwordFromBody = user.getPassword();
            String emailFromBody = user.getEmail();
            if (userNameFromBody==null||
                    userNameFromBody.isEmpty()||
                    emailFromBody==null||
                    emailFromBody.isEmpty()||
                    passwordFromBody==null||
                    passwordFromBody.isEmpty()||
                    !emailFromBody.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                return false;
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Exception while saving a user",e);
            return false;
        }
    }
    public boolean signUpEntry(UserSignUpDto user){
        try {
            otpService.sendOtp(user.getEmail());
            return true;
        } catch (Exception e) {
            log.error("Exception During sending otp",e);
            return false;
        }
    }
    public void saveAdminEntry(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
    public List<User> getAll(){

        return userRepository.findAll();
    }
    public Optional<User> getById(ObjectId id){

        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){

        userRepository.deleteById(id);
    }
    public User findByUserName(String userName){

        return userRepository.findByUserName(userName);
    }
    public void deleteByUserName(String userName){

        userRepository.deleteByUserName(userName);
    }

}

