package com.example.journelApp.repository;

import com.example.journelApp.entity.JournelEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournelEntryRepository extends MongoRepository<JournelEntry , ObjectId> {
}
