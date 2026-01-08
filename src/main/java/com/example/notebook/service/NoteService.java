package com.example.notebook.service;

import com.example.notebook.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    Note createNote(Note note);
    Note updateNote(Note note, Long id);
    List<Note> getAllNote();
    Note getNoteById(Long id);
    void deleteNote(Long id);
}
