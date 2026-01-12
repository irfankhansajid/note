package com.example.notebook.service.serviceImpl;

import com.example.notebook.dto.LoginRequestDto;
import com.example.notebook.dto.LoginResponseDto;
import com.example.notebook.dto.UserRegistrationRequestDto;
import com.example.notebook.dto.UserRegistrationResponseDto;
import com.example.notebook.exception.InvalidCredentialsException;
import com.example.notebook.exception.ResourceAlreadyExistsException;
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
            throw new ResourceAlreadyExistsException("User already exist" + requestDto.getEmail());
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
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        boolean isPasswordMatch = passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        String fakeToken = java.util.UUID.randomUUID().toString();
        return LoginResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(fakeToken)
                .build();
    }
}
