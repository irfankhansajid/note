package com.example.notebook.controller;

import com.example.notebook.model.Note;
import com.example.notebook.service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {

        this.noteService = noteService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNote());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.createNote(note));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Note> updateNote(@RequestBody Note note,@PathVariable Long id) {
        return ResponseEntity.ok(noteService.updateNote(note, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok("Note deleted successfull with id: " + id);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Note>> searchNote(@RequestParam(defaultValue = "", name = "search") String searchKeyword,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size)
    {
        return ResponseEntity.ok(noteService.searchNote(searchKeyword, page, size));
    }
}
