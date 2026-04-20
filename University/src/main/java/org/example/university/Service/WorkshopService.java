package org.example.university.Service;

import org.example.university.model.Workshop;

import java.util.List;

public interface WorkshopService {
    List<Workshop> getAllWorkshops();

    Workshop getWorkshopById(Long id);

    Workshop createWorkshop(Workshop workshop);

    Workshop updateWorkshop(Long id, Workshop workshop);

    void cancelWorkshop(Long id);
}