package com.example.notebook.security;

import org.springframework.stereotype.Component;

@Component
public class CurrentUserContext {

    public Long getUserId() {
        return 1L;
    }
}
