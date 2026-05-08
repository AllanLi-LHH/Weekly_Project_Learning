package com.aierken.aierken_practice.Service;

import com.aierken.aierken_practice.Exception.UnauthorizedAccessException;
import com.aierken.aierken_practice.dto.AuthResponse;
import com.aierken.aierken_practice.dto.LoginRequest;
import com.aierken.aierken_practice.dto.RegisterRequest;
import com.aierken.aierken_practice.entity.User;
import com.aierken.aierken_practice.repository.UserRepository;
import com.aierken.aierken_practice.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        User existing = userRepository.findByEmail(request.getEmail());
        if (existing != null) {
            throw new UnauthorizedAccessException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .address(null)
                .role("CUSTOMER")
                .status("ACTIVE")
                .build();

        User saved = userRepository.save(user);

        String token = jwtUtil.generateToken(saved.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new UnauthorizedAccessException("Invalid email or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedAccessException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
