package org.example.university_workshop_registration_system.mapper;

import javax.annotation.processing.Generated;
import org.example.university_workshop_registration_system.dto.response.RegistrationResponseDTO;
import org.example.university_workshop_registration_system.model.Registration;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-20T20:29:52-0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class RegistrationMapperImpl implements RegistrationMapper {

    @Override
    public RegistrationResponseDTO toDTO(Registration registration) {
        if ( registration == null ) {
            return null;
        }

        RegistrationResponseDTO registrationResponseDTO = new RegistrationResponseDTO();

        registrationResponseDTO.setStatus( registration.getStatus() );
        registrationResponseDTO.setCreatedAt( registration.getCreatedAt() );
        registrationResponseDTO.setCancelledAt( registration.getCancelledAt() );

        return registrationResponseDTO;
    }

    @Override
    public Registration toEntity(RegistrationResponseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Registration registration = new Registration();

        registration.setStatus( dto.getStatus() );
        registration.setCreatedAt( dto.getCreatedAt() );
        registration.setCancelledAt( dto.getCancelledAt() );

        return registration;
    }
}
