package org.example.university.Repository;

import org.example.university.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    List<Workshop> findByStatus(String status);

    List<Workshop> findByStartDatetimeAfter(LocalDateTime time);
}
