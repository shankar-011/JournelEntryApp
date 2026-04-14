package com.example.notesApp.entity;

import com.example.notesApp.enums.Sentiment;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="notes_entries")
@Getter
@Setter
public class NotesEntry {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    private Sentiment sentiment;
}
