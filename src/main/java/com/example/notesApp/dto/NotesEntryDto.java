package com.example.notesApp.dto;

import com.example.notesApp.enums.Sentiment;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotesEntryDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Sentiment sentiment;
}