package com.example.journalApp.repository;

import com.example.journalApp.entity.ConfigJournalAppEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntry, ObjectId> {
}
