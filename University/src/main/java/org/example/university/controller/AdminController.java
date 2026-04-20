package org.example.university.controller;

import jakarta.validation.Valid;
import org.example.university.Service.RegistrationService;
import org.example.university.Service.WorkshopService;
import org.example.university.api.ApiResponse;
import org.example.university.dto.request.WorkshopRequestDTO;
import org.example.university.dto.response.RegistrationResponseDTO;
import org.example.university.dto.response.WorkshopResponseDTO;
import org.example.university.mapper.RegistrationMapper;
import org.example.university.mapper.WorkshopMapper;
import org.example.university.model.Registration;
import org.example.university.model.Workshop;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final WorkshopService workshopService;
    private final RegistrationService registrationService;
    private final WorkshopMapper workshopMapper;
    private final RegistrationMapper registrationMapper;

    public AdminController(WorkshopService workshopService,
                           RegistrationService registrationService,
                           WorkshopMapper workshopMapper,
                           RegistrationMapper registrationMapper) {
        this.workshopService = workshopService;
        this.registrationService = registrationService;
        this.workshopMapper = workshopMapper;
        this.registrationMapper = registrationMapper;
    }

    // POST /api/v1/admin/workshops
    @PostMapping("/workshops")
    public ResponseEntity<ApiResponse<WorkshopResponseDTO>> createWorkshop(
            @Valid @RequestBody WorkshopRequestDTO dto) {

        Workshop workshop = workshopMapper.toEntity(dto);
        Workshop saved = workshopService.createWorkshop(workshop);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Workshop created successfully", workshopMapper.toDTO(saved))
        );
    }

    // PUT /api/v1/admin/workshops/{id}
    @PutMapping("/workshops/{id}")
    public ResponseEntity<ApiResponse<WorkshopResponseDTO>> updateWorkshop(
            @PathVariable Long id,
            @Valid @RequestBody WorkshopRequestDTO dto) {

        Workshop workshop = workshopMapper.toEntity(dto);
        Workshop updated = workshopService.updateWorkshop(id, workshop);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Workshop updated successfully", workshopMapper.toDTO(updated))
        );
    }

    // PATCH /api/v1/admin/workshops/{id}/cancel
    @PatchMapping("/workshops/{id}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelWorkshop(@PathVariable Long id) {

        workshopService.cancelWorkshop(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Workshop cancelled", "Workshop has been cancelled")
        );
    }

    // GET /api/v1/admin/workshops/{id}/registrations
    @GetMapping("/workshops/{id}/registrations")
    public ResponseEntity<ApiResponse<List<RegistrationResponseDTO>>> getWorkshopRegistrations(
            @PathVariable Long id) {

        List<Registration> registrations = registrationService.getWorkshopRegistrations(id);

        List<RegistrationResponseDTO> dtoList = registrations.stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Registrations fetched successfully", dtoList)
        );
    }
}