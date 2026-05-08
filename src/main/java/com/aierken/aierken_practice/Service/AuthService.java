package com.aierken.aierken_practice.Service;

import com.aierken.aierken_practice.dto.AuthResponse;
import com.aierken.aierken_practice.dto.LoginRequest;
import com.aierken.aierken_practice.dto.RegisterRequest;
import com.aierken.aierken_practice.entity.User;
import com.aierken.aierken_practice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest registerRequest){
        if(userRepository.findByEmail(registerRequest.getEmail())!=null){
            throw new RuntimeException("Email already exists");
        }
        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPhone(registerRequest.getPhone());
        user.setPassword("123456");

        user.setRole("USER");
        user.setStatus("ACTIVE");
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);//加@AllArgsConstructor就行
    }

    public AuthResponse login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new RuntimeException("Invalid email"));//getEmail()返回值是Optional才能这样做

        if(!user.getPassword().equals(loginRequest.getPassword())){
            throw new RuntimeException("Passwords don't match");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
