package com.example.notebook.controller;

import com.example.notebook.dto.NoteRequestDto;
import com.example.notebook.dto.NoteResponseDto;
import com.example.notebook.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {

        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<Page<NoteResponseDto>> getAllNotes(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                    @PathVariable Long userId)
    {
        return ResponseEntity.ok(noteService.getAllNote(page, size, userId ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<NoteResponseDto> getById(@PathVariable Long noteId, @PathVariable Long userId) {
        return ResponseEntity.ok(noteService.getNoteById(noteId, userId));
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody NoteRequestDto note, @PathVariable Long userId) {
        return new ResponseEntity<>(noteService.createNote(note, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNote(@Valid @RequestBody NoteRequestDto noteDto,
                                                      @PathVariable Long id,
                                                      @PathVariable Long userId) {
        return new ResponseEntity<>(noteService.updateNote(noteDto, id, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable Long noteId, @PathVariable Long userId) {
        noteService.deleteNote(noteId, userId);
        return ResponseEntity.ok("Note deleted successfully with id: " + noteId);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NoteResponseDto>> searchNote(@RequestParam(defaultValue = "") String searchKeyword,
                                                            @PathVariable Long userId,
                                                            @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size
                                                    )

    {
        return ResponseEntity.ok(noteService.searchNote( searchKeyword, page, size, userId));
    }
}
