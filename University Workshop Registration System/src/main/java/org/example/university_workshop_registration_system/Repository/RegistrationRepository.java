package org.example.university_workshop_registration_system.Repository;

import org.example.university_workshop_registration_system.model.Registration;
import org.example.university_workshop_registration_system.model.Users;
import org.example.university_workshop_registration_system.model.Users;
import org.example.university_workshop_registration_system.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByUserAndWorkshop(Users user, Workshop workshop);

    List<Registration> findByUser(Users user);

    List<Registration> findByWorkshop(Workshop workshop);

    Optional<Registration> findByUserAndWorkshop(Users user, Workshop workshop);
}
