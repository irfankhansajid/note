package com.example.notebook.controller;

import com.example.notebook.dto.UserRegistrationRequestDto;
import com.example.notebook.dto.UserRegistrationResponseDto;
import com.example.notebook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/auth")
@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponseDto> register(@Valid @RequestBody UserRegistrationRequestDto requestDto) {
        UserRegistrationResponseDto response = userService.registerUser(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
