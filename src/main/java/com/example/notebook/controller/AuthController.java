package com.example.notebook.controller;

import com.example.notebook.dto.*;
import com.example.notebook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegistrationResponseDto>> register(@Valid @RequestBody UserRegistrationRequestDto requestDto) {
        UserRegistrationResponseDto response = userService.registerUser(requestDto);

        ApiResponse<UserRegistrationResponseDto> apiResponse = ApiResponse.<UserRegistrationResponseDto>builder()
                .success(true)
                .message("Registration Successful")
                .data(response)
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = userService.loginUser(requestDto);

        ApiResponse<LoginResponseDto> apiResponse = ApiResponse.<LoginResponseDto>builder()
                .success(true)
                .message("Login Successful")
                .data(response)
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
