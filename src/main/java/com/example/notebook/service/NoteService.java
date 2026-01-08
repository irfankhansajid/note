package com.example.notebook.service;

import com.example.notebook.model.Note;
import org.springframework.data.domain.Page;

import java.util.List;


public interface NoteService {

    Note createNote(Note note);
    Note updateNote(Note note, Long id);
    List<Note> getAllNote();
    Note getNoteById(Long id);
    void deleteNote(Long id);
    Page<Note> searchNote(String searchKeyword, int page, int size);
}
