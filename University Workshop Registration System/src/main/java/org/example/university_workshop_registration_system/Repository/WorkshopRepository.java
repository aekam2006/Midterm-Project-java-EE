package org.example.university_workshop_registration_system.Repository;

import org.example.university_workshop_registration_system.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    List<Workshop> findByStatus(String status);

    List<Workshop> findByStartDatetimeAfter(LocalDateTime time);
}
