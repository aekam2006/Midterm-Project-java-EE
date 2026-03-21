package org.example.university_workshop_registration_system.Service;

import jakarta.transaction.Transactional;
import org.example.university_workshop_registration_system.Repository.RegistrationRepository;
import org.example.university_workshop_registration_system.Repository.UsersRepository;
import org.example.university_workshop_registration_system.Repository.WorkshopRepository;
import org.example.university_workshop_registration_system.model.Registration;
import org.example.university_workshop_registration_system.model.Users;
import org.example.university_workshop_registration_system.model.Workshop;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService{
    private final RegistrationRepository registrationRepository;
    private final UsersRepository usersRepository;
    private final WorkshopRepository workshopRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository,
                                   UsersRepository usersRepository,
                                   WorkshopRepository workshopRepository) {
        this.registrationRepository = registrationRepository;
        this.usersRepository = usersRepository;
        this.workshopRepository = workshopRepository;
    }

    @Override
    @Transactional
    public void register(Long userId, Long workshopId) {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Workshop workshop = workshopRepository.findById(workshopId)
                .orElseThrow(() -> new RuntimeException("Workshop not found"));

        // Rule A: Seat limit
        if (workshop.getSeatsRemaining() == 0) {
            throw new RuntimeException("Workshop is full");
        }

        // Rule B: Duplicate
        if (registrationRepository.existsByUserAndWorkshop(user, workshop)) {
            throw new RuntimeException("Already registered");
        }

        // Rule C: Past workshop
        if (workshop.getStartDatetime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Workshop already started");
        }

        Registration reg = new Registration();
        reg.setUser(user);
        reg.setWorkshop(workshop);
        reg.setStatus("ACTIVE");
        reg.setCreatedAt(LocalDateTime.now());

        workshop.setSeatsRemaining(workshop.getSeatsRemaining() - 1);

        registrationRepository.save(reg);
        workshopRepository.save(workshop);
    }

    @Override
    @Transactional
    public void cancelRegistration(Long registrationId) {

        Registration reg = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        Workshop workshop = reg.getWorkshop();

        // Rule D: Cannot cancel after start
        if (workshop.getStartDatetime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Cannot cancel after workshop starts");
        }

        reg.setStatus("CANCELLED");
        reg.setCancelledAt(LocalDateTime.now());

        workshop.setSeatsRemaining(workshop.getSeatsRemaining() + 1);

        registrationRepository.save(reg);
        workshopRepository.save(workshop);
    }

    @Override
    public List<Registration> getUserRegistrations(Long userId) {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return registrationRepository.findByUser(user);
    }
}
