package com.example.notebook.service.serviceImpl;

import com.example.notebook.dto.LoginRequestDto;
import com.example.notebook.dto.LoginResponseDto;
import com.example.notebook.dto.UserRegistrationRequestDto;
import com.example.notebook.dto.UserRegistrationResponseDto;
import com.example.notebook.exception.EmailAlreadyExistsException;
import com.example.notebook.model.User;
import com.example.notebook.repository.UserRepository;
import com.example.notebook.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmailAlreadyExistsException("User already exist" + requestDto.getEmail());
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        User savedUser = userRepository.save(user);
        return UserRegistrationResponseDto.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        return null;
    }
}
