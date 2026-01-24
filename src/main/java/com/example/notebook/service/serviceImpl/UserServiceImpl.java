package com.example.notebook.service.serviceImpl;

import com.example.notebook.dto.LoginRequestDto;
import com.example.notebook.dto.LoginResponseDto;
import com.example.notebook.dto.UserRegistrationRequestDto;
import com.example.notebook.dto.UserRegistrationResponseDto;
import com.example.notebook.exception.ResourceAlreadyExistsException;
import com.example.notebook.model.User;
import com.example.notebook.model.UserPrinciple;
import com.example.notebook.repository.UserRepository;
import com.example.notebook.security.jwt.JwtService;
import com.example.notebook.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );


        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        String token = jwtService.generateToken(
                userPrinciple.getId(),
                userPrinciple.getUsername(),
                userPrinciple.getRole()
        );

        return LoginResponseDto.builder()
                .id(userPrinciple.getId())
                .email(userPrinciple.getUsername())
                .token(token)
                .build();
    }
}
