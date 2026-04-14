package com.example.notesApp.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="config_notes_app")
@Getter
@Setter
public class ConfigNotesAppEntry {
    private String key;
    private String value;
}
