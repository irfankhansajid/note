package com.example.notebook.service.serviceImpl;

import com.example.notebook.dto.NoteRequestDto;
import com.example.notebook.dto.NoteResponseDto;
import com.example.notebook.exception.ResourceNotFoundException;
import com.example.notebook.model.Note;
import com.example.notebook.model.User;
import com.example.notebook.repository.NoteRepository;
import com.example.notebook.repository.UserRepository;
import com.example.notebook.security.AuthContext;
import com.example.notebook.service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final AuthContext authContext;

    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository, AuthContext authContext) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.authContext = authContext;
    }

    @Override
    public NoteResponseDto createNote(NoteRequestDto noteDto) {
        Long userId = authContext.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());

        note.setUser(user);

        Note savedNote = noteRepository.save(note);
        return mapToResponseDto(savedNote);
    }

    @Override
    public NoteResponseDto updateNote(NoteRequestDto noteDto, Long noteId) {
        Long userId = authContext.getUserId();

        Note existingNote = noteRepository.findByIdAndUserId(noteId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));



        existingNote.setTitle(noteDto.getTitle());
        existingNote.setContent(noteDto.getContent());

        Note updatedNote =  noteRepository.save(existingNote);
        return mapToResponseDto(updatedNote);

    }

    @Override
    public Page<NoteResponseDto> getAllNote(int page, int size) {
        Long userId = authContext.getUserId();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Note> notePage = noteRepository.findByUserId(userId,pageable);

        return notePage.map(this::mapToResponseDto);
    }

    @Override
    public NoteResponseDto getNoteById(Long noteId) {
        Long userId = authContext.getUserId();

        Note note = noteRepository.findByIdAndUserId(noteId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + noteId));


        return mapToResponseDto(note);
    }

    @Override
    public void deleteNote(Long noteId){
        Long userId = authContext.getUserId();

        Note existing = noteRepository.findByIdAndUserId(noteId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + noteId));

        noteRepository.delete(existing);
    }

    @Override
    public Page<NoteResponseDto> searchNote(String searchKeyword, int page, int size) {
        Long userId = authContext.getUserId();

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Note> notePage = noteRepository.findByUserIdAndTitleContainingIgnoreCaseOrUserIdAndContentContainingIgnoreCase(
                userId, searchKeyword, userId, searchKeyword, pageable);
        return notePage.map(this::mapToResponseDto);
    }



    private NoteResponseDto mapToResponseDto(Note note) {
        return NoteResponseDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .userId(note.getUser().getId())
                .createdAt(note.getCreatedAt())
                .build();
    }


}
