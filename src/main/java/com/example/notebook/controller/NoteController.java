package com.example.notebook.controller;

import com.example.notebook.dto.NoteRequestDto;
import com.example.notebook.dto.NoteResponseDto;
import com.example.notebook.model.UserPrinciple;
import com.example.notebook.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {

        this.noteService = noteService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<Page<NoteResponseDto>> getAllNotes(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size)
    {
        return ResponseEntity.ok(noteService.getAllNote(page, size, userPrinciple.getId()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> getById(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                   @PathVariable Long noteId) {
        return ResponseEntity.ok(noteService.getNoteById(noteId, userPrinciple.getId()));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                      @Valid @RequestBody NoteRequestDto note) {
        return new ResponseEntity<>(noteService.createNote(note, userPrinciple.getId()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> updateNote(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                      @Valid @RequestBody NoteRequestDto noteDto,
                                                      @PathVariable Long noteId) {
        return new ResponseEntity<>(noteService.updateNote(noteDto, noteId, userPrinciple.getId()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> deleteNote(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                             @PathVariable Long noteId) {
        noteService.deleteNote(noteId, userPrinciple.getId());
        return ResponseEntity.ok("Note deleted successfully with id: " + noteId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    public ResponseEntity<Page<NoteResponseDto>> searchNote(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                            @RequestParam(required = false) String searchKeyword,
                                                            @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size
                                                    )

    {
        return ResponseEntity.ok(noteService.searchNote(searchKeyword, page, size, userPrinciple.getId()));
    }
}
