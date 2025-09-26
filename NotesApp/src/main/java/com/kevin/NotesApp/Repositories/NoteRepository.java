package com.kevin.NotesApp.Repositories;

import java.util.List;
import com.kevin.NotesApp.Models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByUserId(String userId); //stores the returned note object in a list
}
