package org.example.university_workshop_registration_system.controller;

import org.example.university_workshop_registration_system.Repository.UsersRepository;
import org.example.university_workshop_registration_system.api.ApiResponse;
import org.example.university_workshop_registration_system.dto.request.UserRequestDTO;
import org.example.university_workshop_registration_system.dto.response.UserResponseDTO;
import org.example.university_workshop_registration_system.exception.ResourceNotFoundException;
import org.example.university_workshop_registration_system.mapper.UserMapper;
import org.example.university_workshop_registration_system.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    public UserController(UsersRepository usersRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@RequestBody UserRequestDTO dto) {
        Users user = userMapper.toEntity(dto);
        Users saved = usersRepository.save(user);

        return ResponseEntity.status(201).body(
                new ApiResponse<>(true, "User created successfully", userMapper.toDTO(saved))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = usersRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Users fetched successfully", users)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User fetched successfully", userMapper.toDTO(user))
        );
    }
}