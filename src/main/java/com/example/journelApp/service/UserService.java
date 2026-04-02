package com.example.journelApp.service;

import com.example.journelApp.entity.JournelEntry;
import com.example.journelApp.entity.User;
import com.example.journelApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Transactional
    public void saveEntry(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            log.trace("ha",e);
            log.debug("ha",e);
            log.info("ha");
            log.warn("ha");
            log.error("ha");
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

