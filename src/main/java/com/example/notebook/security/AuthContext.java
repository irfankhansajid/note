package com.example.notebook.security;

import com.example.notebook.exception.UnauthorizedAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {

    public Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new UnauthorizedAccessException("No security context found");
        }

        if (!auth.isAuthenticated() || auth.getPrincipal() == null) {
            throw new UnauthorizedAccessException("User is not authenticated");
        }

        try {
            return (Long) auth.getPrincipal();
        } catch (ClassCastException e) {
            throw new UnauthorizedAccessException("Principle is not of type Long: Type mismatch");
        }
    }
}
