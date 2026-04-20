package org.example.university.mapper;

import org.example.university.dto.response.RegistrationResponseDTO;
import org.example.university.model.Registration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    RegistrationResponseDTO toDTO(Registration registration);

    Registration toEntity(RegistrationResponseDTO dto);
}
