package com.example.notesApp.repository;

import com.example.notesApp.entity.ConfigNotesAppEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigNotesAppRepository extends MongoRepository<ConfigNotesAppEntry, ObjectId> {
}
