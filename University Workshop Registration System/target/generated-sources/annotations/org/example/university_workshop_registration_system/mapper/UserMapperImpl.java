package org.example.university_workshop_registration_system.mapper;

import javax.annotation.processing.Generated;
import org.example.university_workshop_registration_system.dto.request.UserRequestDTO;
import org.example.university_workshop_registration_system.dto.response.UserResponseDTO;
import org.example.university_workshop_registration_system.model.Users;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-20T20:29:52-0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDTO toDTO(Users user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setName( user.getName() );
        userResponseDTO.setEmail( user.getEmail() );

        return userResponseDTO;
    }

    @Override
    public Users toEntity(UserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Users users = new Users();

        users.setName( dto.getName() );
        users.setEmail( dto.getEmail() );

        return users;
    }
}
