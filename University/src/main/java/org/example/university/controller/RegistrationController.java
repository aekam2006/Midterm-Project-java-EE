package org.example.university.controller;

import org.example.university.Service.RegistrationService;
import org.example.university.api.ApiResponse;
import org.example.university.dto.request.RegistrationRequestDTO;
import org.example.university.dto.response.RegistrationResponseDTO;
import org.example.university.mapper.RegistrationMapper;
import org.example.university.model.Registration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RegistrationMapper registrationMapper;

    // Removed RegistrationRepository - never inject repository in controller
    public RegistrationController(RegistrationService registrationService,
                                  RegistrationMapper registrationMapper) {
        this.registrationService = registrationService;
        this.registrationMapper = registrationMapper;
    }

    // POST /api/v1/workshops/{id}/registrations
    @PostMapping("/workshops/{id}/registrations")
    public ResponseEntity<ApiResponse<String>> register(@PathVariable Long id,
                                                        @RequestBody RegistrationRequestDTO dto) {
        registrationService.register(dto.getUserId(), id);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Registration successful", "User registered successfully")
        );
    }

    // DELETE /api/v1/registrations/{registrationId}
    @DeleteMapping("/registrations/{registrationId}")
    public ResponseEntity<ApiResponse<String>> cancel(@PathVariable Long registrationId) {
        registrationService.cancelRegistration(registrationId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cancelled", "Registration cancelled successfully")
        );
    }

    // GET /api/v1/me/registrations
    @GetMapping("/me/registrations")
    public ResponseEntity<ApiResponse<List<RegistrationResponseDTO>>> getMyRegistrations(
            @RequestParam Long userId) {

        List<Registration> list = registrationService.getUserRegistrations(userId);

        List<RegistrationResponseDTO> dtoList = list.stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Registrations fetched successfully", dtoList)
        );
    }
}