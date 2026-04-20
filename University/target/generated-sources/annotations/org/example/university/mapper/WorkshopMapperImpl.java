package org.example.university.mapper;

import javax.annotation.processing.Generated;
import org.example.university.dto.request.WorkshopRequestDTO;
import org.example.university.dto.response.WorkshopResponseDTO;
import org.example.university.model.Workshop;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-19T12:57:22-0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class WorkshopMapperImpl implements WorkshopMapper {

    @Override
    public WorkshopResponseDTO toDTO(Workshop workshop) {
        if ( workshop == null ) {
            return null;
        }

        WorkshopResponseDTO workshopResponseDTO = new WorkshopResponseDTO();

        workshopResponseDTO.setId( workshop.getId() );
        workshopResponseDTO.setTitle( workshop.getTitle() );
        workshopResponseDTO.setDescription( workshop.getDescription() );
        workshopResponseDTO.setStartDatetime( workshop.getStartDatetime() );
        workshopResponseDTO.setSeatsRemaining( workshop.getSeatsRemaining() );

        return workshopResponseDTO;
    }

    @Override
    public WorkshopRequestDTO toRequestDTO(Workshop workshop) {
        if ( workshop == null ) {
            return null;
        }

        WorkshopRequestDTO workshopRequestDTO = new WorkshopRequestDTO();

        workshopRequestDTO.setTitle( workshop.getTitle() );
        workshopRequestDTO.setDescription( workshop.getDescription() );
        workshopRequestDTO.setLocation( workshop.getLocation() );
        workshopRequestDTO.setStartDatetime( workshop.getStartDatetime() );
        workshopRequestDTO.setTotalSeats( workshop.getTotalSeats() );

        return workshopRequestDTO;
    }

    @Override
    public Workshop toEntity(WorkshopRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Workshop workshop = new Workshop();

        workshop.setTitle( dto.getTitle() );
        workshop.setDescription( dto.getDescription() );
        workshop.setLocation( dto.getLocation() );
        workshop.setStartDatetime( dto.getStartDatetime() );
        workshop.setTotalSeats( dto.getTotalSeats() );

        return workshop;
    }
}
