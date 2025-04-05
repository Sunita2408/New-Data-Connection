package com.sunita.mynote.service;

import com.sunita.mynote.model.Note;
import com.sunita.mynote.model.User;
import com.sunita.mynote.repository.NoteRepository;
import com.sunita.mynote.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Note
    public Note createNote(Long userId, String title, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Note note = new Note(title, content, user);
        return noteRepository.save(note);
    }

    // Get All Notes by User ID
    public List<Note> getUserNotes(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    // Get Note by Note ID
    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    // Update Note
    public Note updateNote(Long noteId, String newTitle, String newContent) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        note.setTitle(newTitle);
        note.setContent(newContent);

        return noteRepository.save(note);
    }

    // Delete Note
    public void deleteNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        noteRepository.delete(note);
    }

    @Transactional
    public void deleteAllNotesOfUserByUserId(Long userId) {
        if (!noteRepository.existsByUserId(userId)) {
            throw new EntityNotFoundException("No notes found for user with ID: " + userId);
        }
        noteRepository.deleteByUserId(userId);
    }


}
