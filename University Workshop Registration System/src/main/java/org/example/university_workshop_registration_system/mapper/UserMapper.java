package org.example.university_workshop_registration_system.mapper;

import org.example.university_workshop_registration_system.dto.request.UserRequestDTO;
import org.example.university_workshop_registration_system.dto.response.UserResponseDTO;
import org.example.university_workshop_registration_system.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDTO(Users user);
    Users toEntity(UserRequestDTO dto);
}
