package org.example.university.controller;

import org.example.university.Service.WorkshopService;
import org.example.university.api.ApiResponse;
import org.example.university.dto.response.WorkshopResponseDTO;
import org.example.university.mapper.WorkshopMapper;
import org.example.university.model.Workshop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/workshops")
public class WorkshopController {

    private final WorkshopService workshopService;
    private final WorkshopMapper workshopMapper;

    public WorkshopController(WorkshopService workshopService,
                              WorkshopMapper workshopMapper) {
        this.workshopService = workshopService;
        this.workshopMapper = workshopMapper;
    }

    // GET /api/v1/workshops  (public)
    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkshopResponseDTO>>> getAllWorkshops() {
        List<WorkshopResponseDTO> list = workshopService.getAllWorkshops()
                .stream()
                .map(workshopMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Workshops fetched successfully", list)
        );
    }

    // GET /api/v1/workshops/{id}  (public)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkshopResponseDTO>> getWorkshopById(@PathVariable Long id) {
        Workshop workshop = workshopService.getWorkshopById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Workshop fetched successfully", workshopMapper.toDTO(workshop))
        );
    }
}