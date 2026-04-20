package org.example.university.mapper;

import org.example.university.dto.request.UserRequestDTO;
import org.example.university.dto.response.UserResponseDTO;
import org.example.university.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDTO(User user);
    User toEntity(UserRequestDTO dto);
}
