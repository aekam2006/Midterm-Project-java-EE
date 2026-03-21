package org.example.university_workshop_registration_system.controller;

import org.example.university_workshop_registration_system.Repository.WorkshopRepository;
import org.example.university_workshop_registration_system.api.ApiResponse;
import org.example.university_workshop_registration_system.dto.request.WorkshopRequestDTO;
import org.example.university_workshop_registration_system.dto.response.WorkshopResponseDTO;
import org.example.university_workshop_registration_system.exception.ResourceNotFoundException;
import org.example.university_workshop_registration_system.mapper.WorkshopMapper;
import org.example.university_workshop_registration_system.model.Workshop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/workshops")
public class WorkshopController {

    private final WorkshopRepository workshopRepository;
    private final WorkshopMapper workshopMapper;

    public WorkshopController(WorkshopRepository workshopRepository,
                              WorkshopMapper workshopMapper) {
        this.workshopRepository = workshopRepository;
        this.workshopMapper = workshopMapper;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<WorkshopResponseDTO>> createWorkshop(@RequestBody WorkshopRequestDTO dto) {
        Workshop workshop = workshopMapper.toEntity(dto);
        Workshop saved = workshopRepository.save(workshop);

        return ResponseEntity.status(201).body(
                new ApiResponse<>(true, "Workshop created successfully", workshopMapper.toDTO(saved))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkshopResponseDTO>>> getAllWorkshops() {
        List<WorkshopResponseDTO> list = workshopRepository.findAll()
                .stream()
                .map(workshopMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Workshops fetched successfully", list)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkshopResponseDTO>> getWorkshopById(@PathVariable Long id) {

        Workshop workshop = workshopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workshop not found"));

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Workshop fetched successfully", workshopMapper.toDTO(workshop))
        );
    }
}