package com.aierken.aierken_practice.Service;

import com.aierken.aierken_practice.dto.UserRequest;
import com.aierken.aierken_practice.dto.UserResponse;
import com.aierken.aierken_practice.entity.User;
import com.aierken.aierken_practice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {
        User user = User.builder().name(request.getName()).email(request.getEmail()).phone(request.getPhone()).address(request.getAddress())
                .role("USER")
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }
    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole(),
                user.getStatus()
        );
    }
}
