package com.example.notebook.service.serviceImpl;

import com.example.notebook.dto.NoteRequestDto;
import com.example.notebook.dto.NoteResponseDto;
import com.example.notebook.exception.ResourceNotFoundException;
import com.example.notebook.model.Note;
import com.example.notebook.model.User;
import com.example.notebook.repository.NoteRepository;
import com.example.notebook.repository.UserRepository;
import com.example.notebook.service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NoteResponseDto createNote(NoteRequestDto noteDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());

        note.setUser(user);

        Note savedNote = noteRepository.save(note);
        return NoteResponseDto.builder()
                .id(savedNote.getId())
                .title(savedNote.getTitle())
                .content(savedNote.getContent())
                .userId(savedNote.getUser().getId())
                .createdAt(savedNote.getCreatedAt())
                .build();
    }

    @Override
    public NoteResponseDto updateNote(NoteRequestDto noteDto, Long id, Long userId) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        if (existingNote.getUser().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to update this note!");
        }
        existingNote.setTitle(noteDto.getTitle());
        existingNote.setContent(noteDto.getContent());

        Note updatedNote =  noteRepository.save(existingNote);
        return NoteResponseDto.builder()
                .id(updatedNote.getId())
                .title(updatedNote.getTitle())
                .content(updatedNote.getContent())
                .userId(updatedNote.getUser().getId())
                .createdAt(updatedNote.getCreatedAt())
                .build();

    }

    @Override
    public Page<NoteResponseDto> getAllNote(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Note> notePage = noteRepository.findAll(pageable);
        return notePage.map(note -> NoteResponseDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .userId(note.getUser().getId())
                .createdAt(note.getCreatedAt())
                .build());
    }

    @Override
    public NoteResponseDto getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

        return NoteResponseDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .userId(note.getUser().getId())
                .createdAt(note.getCreatedAt())
                .build();
    }

    @Override
    public void deleteNote(Long id) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        noteRepository.delete(existing);
    }

    @Override
    public Page<NoteResponseDto> searchNote(String searchKeyword, int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Note> notePage = noteRepository.findByUserIdAndTitleContainingIgnoreCaseOrUserIdAndContentContainingIgnoreCase(
                userId, searchKeyword, userId, searchKeyword, pageable);
        return notePage.map(note ->
                NoteResponseDto.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .userId(note.getUser().getId())
                        .createdAt(note.getCreatedAt())
                        .build());
    }
}
