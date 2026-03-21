package org.example.university_workshop_registration_system.mapper;

import org.example.university_workshop_registration_system.dto.response.RegistrationResponseDTO;
import org.example.university_workshop_registration_system.model.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    RegistrationResponseDTO toDTO(Registration registration);

    Registration toEntity(RegistrationResponseDTO dto);
}
