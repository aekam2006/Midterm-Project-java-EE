package org.example.university.controller;

import org.example.university.Service.UserService;
import org.example.university.api.ApiResponse;
import org.example.university.dto.response.UserResponseDTO;
import org.example.university.mapper.UserMapper;
import org.example.university.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // GET /api/v1/users  (admin only - secured via SecurityConfig)
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Users fetched successfully", users)
        );
    }

    // GET /api/v1/users/{id}  (admin only - secured via SecurityConfig)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User fetched successfully", userMapper.toDTO(user))
        );
    }
}