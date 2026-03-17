package com.example.journelApp.service;

import com.example.journelApp.entity.JournelEntry;
import com.example.journelApp.entity.User;
import com.example.journelApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){

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

}

