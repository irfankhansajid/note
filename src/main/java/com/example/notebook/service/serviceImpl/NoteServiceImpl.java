package com.example.notebook.service.serviceImpl;

import com.example.notebook.exception.ResourceNotFoundException;
import com.example.notebook.model.Note;
import com.example.notebook.repository.NoteRepository;
import com.example.notebook.service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Note note, Long id) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        return noteRepository.save(existingNote);
    }

    @Override
    public List<Note> getAllNote() {
        return noteRepository.findAll();
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

    }

    @Override
    public void deleteNote(Long id) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        noteRepository.delete(existing);
    }

    @Override
    public Page<Note> searchNote(String searchKeyword, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        return noteRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(searchKeyword, searchKeyword, pageable);
    }
}
