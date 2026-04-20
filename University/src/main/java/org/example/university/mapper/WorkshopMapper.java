package org.example.university.mapper;

import org.example.university.dto.request.WorkshopRequestDTO;
import org.example.university.dto.response.WorkshopResponseDTO;
import org.example.university.model.Workshop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkshopMapper {
    WorkshopResponseDTO toDTO(Workshop workshop);

    WorkshopRequestDTO toRequestDTO(Workshop workshop);

    Workshop toEntity(WorkshopRequestDTO dto);
}
