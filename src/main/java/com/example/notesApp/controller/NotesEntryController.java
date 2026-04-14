package com.example.notesApp.controller;

import com.example.notesApp.dto.NotesEntryDto;
import com.example.notesApp.entity.NotesEntry;
import com.example.notesApp.entity.User;
import com.example.notesApp.service.NotesEntryService;
import com.example.notesApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("notes")
@Tag(name="Notes APIs")
public class NotesEntryController {
    @Autowired
    private NotesEntryService notesEntryService;
    @Autowired
    private UserService userService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private boolean noteBelongsToUser(User user, ObjectId noteId) {
        return user.getNotesEntries()
                .stream()
                .anyMatch(entry -> entry.getId().equals(noteId));
    }


    @GetMapping
    @Operation(summary = "Get all Notes Entries")
    public ResponseEntity<List<NotesEntry>> getAllNotesEntryOfUser(){
        User user = userService.findByUserName(getAuthenticatedUsername());
        List<NotesEntry> notes = user.getNotesEntries();
        if (notes == null || notes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
    @PostMapping
    @Operation(summary = "Add Notes Entry")
    public ResponseEntity<NotesEntry> createEntry(@Valid @RequestBody NotesEntryDto dto) {
        try {
            NotesEntry notesEntry = new NotesEntry();
            notesEntry.setTitle(dto.getTitle());
            notesEntry.setContent(dto.getContent());
            notesEntry.setSentiment(dto.getSentiment());
            notesEntry.setDateTime(LocalDateTime.now());
            notesEntryService.saveEntry(notesEntry, getAuthenticatedUsername());
            return new ResponseEntity<>(notesEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get/{id}")
    @Operation(summary = "Get Notes Entry based on ID")
    public ResponseEntity<NotesEntry> getById(@PathVariable String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByUserName(getAuthenticatedUsername());

        if (!noteBelongsToUser(user, objectId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<NotesEntry> entry = notesEntryService.getById(objectId);
        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Delete a Notes Entry")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByUserName(getAuthenticatedUsername());

        if (!noteBelongsToUser(user, objectId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (notesEntryService.getById(objectId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        notesEntryService.deleteById(objectId, getAuthenticatedUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    @Operation(summary = "Update Notes Entry")
    public ResponseEntity<NotesEntry> updateById(@PathVariable String id,
                                                 @Valid @RequestBody NotesEntryDto dto) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByUserName(getAuthenticatedUsername());

        if (!noteBelongsToUser(user, objectId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        NotesEntry existing = notesEntryService.getById(objectId).orElse(null);
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            existing.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null && !dto.getContent().isBlank()) {
            existing.setContent(dto.getContent());
        }
        if (dto.getSentiment() != null) {
            existing.setSentiment(dto.getSentiment());
        }

        notesEntryService.saveEntry(existing);
        return new ResponseEntity<>(existing, HttpStatus.OK);
    }
}
