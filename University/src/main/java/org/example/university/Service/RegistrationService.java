package org.example.university.Service;

import org.example.university.model.Registration;

import java.util.List;

public interface RegistrationService {
    void register(Long userId, Long workshopId);

    void cancelRegistration(Long registrationId);

    List<Registration> getUserRegistrations(Long userId);

    List<Registration> getWorkshopRegistrations(Long workshopId);
}