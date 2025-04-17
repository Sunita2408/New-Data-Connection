package com.sunita.mynote.controller;

import com.sunita.mynote.dto.notes.CreateNoteRequestBody;
import com.sunita.mynote.dto.notes.UpdateNoteRequestBody;
import com.sunita.mynote.model.Note;
import com.sunita.mynote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    // Create Note
    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/create")
    public ResponseEntity<Note> createNote(@RequestBody CreateNoteRequestBody noteRequestBody) {
        Note note = noteService.createNote(noteRequestBody.getUserId(), noteRequestBody.getTitle(), noteRequestBody.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }

    // Get All Notes by User ID
    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Note>> getUserNotes(@PathVariable Long userId) {
        List<Note> notes = noteService.getUserNotes(userId);
        return ResponseEntity.ok(notes);
    }

    // Get Note by Note ID
    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long noteId) {
        Note note = noteService.getNoteById(noteId);
        return ResponseEntity.ok(note);
    }

    // Update Note
    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PutMapping("/update")
    public ResponseEntity<Note> updateNote(@RequestBody UpdateNoteRequestBody updateNoteRequestBody) {
        Note updatedNote = noteService.updateNote(updateNoteRequestBody.getNoteId(), updateNoteRequestBody.getTitle(), updateNoteRequestBody.getContent());
        return ResponseEntity.ok(updatedNote);
    }

    // Delete Note
    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @DeleteMapping("/delete/{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable Long noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.ok("Note deleted successfully");
    }

    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @DeleteMapping("/deleteAllNotesOfUser")
    public ResponseEntity<String> deleteAllNotesOfUser(@RequestParam Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body("User ID cannot be null");
        }
        noteService.deleteAllNotesOfUserByUserId(userId);
        return ResponseEntity.ok("Notes deleted successfully");
    }
}

