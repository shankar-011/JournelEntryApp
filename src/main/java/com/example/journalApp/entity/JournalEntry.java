package com.example.journalApp.entity;

import com.example.journalApp.enums.Sentiment;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="journal_entries")
@Getter
@Setter
public class JournalEntry {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    private Sentiment sentiment;
}
