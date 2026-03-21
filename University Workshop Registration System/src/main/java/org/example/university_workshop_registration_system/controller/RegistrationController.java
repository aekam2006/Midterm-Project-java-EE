package org.example.university_workshop_registration_system.controller;

import org.example.university_workshop_registration_system.Repository.RegistrationRepository;
import org.example.university_workshop_registration_system.Service.RegistrationService;
import org.example.university_workshop_registration_system.api.ApiResponse;
import org.example.university_workshop_registration_system.dto.request.RegistrationRequestDTO;
import org.example.university_workshop_registration_system.dto.response.RegistrationResponseDTO;
import org.example.university_workshop_registration_system.mapper.RegistrationMapper;
import org.example.university_workshop_registration_system.model.Registration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;

    public RegistrationController(RegistrationService registrationService,
                                  RegistrationRepository registrationRepository,
                                  RegistrationMapper registrationMapper) {
        this.registrationService = registrationService;
        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegistrationRequestDTO dto) {

        registrationService.register(dto.getUserId(), dto.getWorkshopId());

        return ResponseEntity.status(201).body(
                new ApiResponse<>(true, "Registration successful", "User registered successfully")
        );
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<String>> cancel(@PathVariable Long id) {

        registrationService.cancelRegistration(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cancelled", "Registration cancelled successfully")
        );
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<RegistrationResponseDTO>>> getByUser(@PathVariable Long userId) {

        List<Registration> list = registrationService.getUserRegistrations(userId);

        List<RegistrationResponseDTO> dtoList = list.stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Registrations fetched successfully", dtoList)
        );
    }
}