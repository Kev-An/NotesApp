package com.kevin.NotesApp.Services;

import com.kevin.NotesApp.Models.Note;
import com.kevin.NotesApp.Repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotesByUserId(String userId) {
        return noteRepository.findByUserId(userId);
    }

    public Note createNote(Note note, String userId) {
        note.setUserId(userId);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note updateNote(Note note, String userId) {
        if (!note.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this note");
        }
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public void deleteNote(Note note, String userId) {
        if (!note.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this note");
        }
        noteRepository.delete(note);
    }

    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
}
