package com.example.notebook.service;

import com.example.notebook.dto.NoteRequestDto;
import com.example.notebook.dto.NoteResponseDto;

import org.springframework.data.domain.Page;


public interface NoteService {

    NoteResponseDto createNote(NoteRequestDto noteDto, Long userId);
    NoteResponseDto updateNote(NoteRequestDto noteDto, Long noteId, Long userId);
    Page<NoteResponseDto> getAllNote(int page, int size);
    NoteResponseDto getNoteById(Long id);
    void deleteNote(Long id);
    Page<NoteResponseDto> searchNote(String searchKeyword, int page, int size, Long userId);
}
