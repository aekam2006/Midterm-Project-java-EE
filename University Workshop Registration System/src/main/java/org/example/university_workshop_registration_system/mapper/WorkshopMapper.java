package org.example.university_workshop_registration_system.mapper;

import org.example.university_workshop_registration_system.dto.request.WorkshopRequestDTO;
import org.example.university_workshop_registration_system.dto.response.WorkshopResponseDTO;
import org.example.university_workshop_registration_system.model.Workshop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface WorkshopMapper {
    WorkshopResponseDTO toDTO(Workshop workshop);

    Workshop toEntity(WorkshopRequestDTO dto);
}
