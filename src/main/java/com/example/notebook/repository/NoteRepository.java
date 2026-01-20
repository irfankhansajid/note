package com.example.notebook.repository;

import com.example.notebook.model.Note;
import com.example.notebook.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByIdAndUserId(Long noteId, Long userId);

    Page<Note> findByUserIdAndTitleContainingIgnoreCaseOrUserIdAndContentContainingIgnoreCase(
            Long userId, String title, Long userId2, String content, Pageable pageable);

    Page<Note> findByUserId(Long userId, Pageable pageable);
}
