package com.kevin.NotesApp.Controllers;

import com.kevin.NotesApp.Models.Note;
import com.kevin.NotesApp.Repositories.UserRepository;
import com.kevin.NotesApp.Services.NoteService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        String userId = getCurrentUserId();
        return noteService.getNotesByUserId(userId);
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable String id) {
        String userId = getCurrentUserId();
        Note note = noteService.getNoteById(id);
        if (!note.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to view this note");
        }
        return note;
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        String userId = getCurrentUserId();
        return noteService.createNote(note, userId);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable String id, @RequestBody Note noteDetails) {
        String userId = getCurrentUserId();
        Note note = noteService.getNoteById(id);
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        return noteService.updateNote(note, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable String id) {
        String userId = getCurrentUserId();
        Note note = noteService.getNoteById(id);
        noteService.deleteNote(note, userId);
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
