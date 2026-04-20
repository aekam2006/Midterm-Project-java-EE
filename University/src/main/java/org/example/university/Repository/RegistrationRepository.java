package org.example.university.Repository;

import org.example.university.model.Registration;
import org.example.university.model.User;
import org.example.university.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByUserAndWorkshop(User user, Workshop workshop);

    List<Registration> findByUser(User user);

    List<Registration> findByWorkshop(Workshop workshop);

    Optional<Registration> findByUserAndWorkshop(User user, Workshop workshop);
}