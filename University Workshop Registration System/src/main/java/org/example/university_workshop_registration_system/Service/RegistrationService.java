package org.example.university_workshop_registration_system.Service;

import org.example.university_workshop_registration_system.model.Registration;

import java.util.List;

public interface RegistrationService {
    void register(Long userId, Long workshopId);

    void cancelRegistration(Long registrationId);

    List<Registration> getUserRegistrations(Long userId);
}
