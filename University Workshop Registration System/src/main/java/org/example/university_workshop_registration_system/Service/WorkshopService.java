package org.example.university_workshop_registration_system.Service;

import org.example.university_workshop_registration_system.model.Workshop;

import java.util.List;

public interface WorkshopService {
    List<Workshop> getAllWorkshops();

    Workshop getWorkshopById(Long id);

    Workshop createWorkshop(Workshop workshop);

    Workshop updateWorkshop(Long id, Workshop workshop);

    void cancelWorkshop(Long id);
}
