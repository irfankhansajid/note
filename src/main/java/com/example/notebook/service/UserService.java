package com.example.notebook.service;

import com.example.notebook.dto.LoginRequestDto;
import com.example.notebook.dto.LoginResponseDto;
import com.example.notebook.dto.UserRegistrationRequestDto;
import com.example.notebook.dto.UserRegistrationResponseDto;

public interface UserService {

    UserRegistrationResponseDto registerUser(UserRegistrationRequestDto requestDto);

    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);

}
