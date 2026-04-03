package com.example.journalApp.repository;

import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

public class UserRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<User> getUserForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;

    }

    public List<User> isAdmin(){
        Query query = new Query();
        query.addCriteria(Criteria.where("roles").is("ADMIN"));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
