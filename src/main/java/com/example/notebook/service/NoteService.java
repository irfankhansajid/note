package com.example.notebook.service;

import com.example.notebook.dto.NoteRequestDto;
import com.example.notebook.dto.NoteResponseDto;

import org.springframework.data.domain.Page;


public interface NoteService {

    NoteResponseDto createNote(NoteRequestDto noteDto);
    NoteResponseDto updateNote(NoteRequestDto noteDto, Long noteId);
    Page<NoteResponseDto> getAllNote(int page, int size);
    NoteResponseDto getNoteById(Long noteId);
    void deleteNote(Long noteId);
    Page<NoteResponseDto> searchNote(String searchKeyword, int page, int size);
}
