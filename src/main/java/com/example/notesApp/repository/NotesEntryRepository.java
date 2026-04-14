package com.example.notesApp.repository;

import com.example.notesApp.entity.NotesEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotesEntryRepository extends MongoRepository<NotesEntry, ObjectId> {
}
