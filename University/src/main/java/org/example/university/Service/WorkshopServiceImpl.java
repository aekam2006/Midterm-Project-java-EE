package org.example.university.Service;

import org.example.university.Repository.WorkshopRepository;
import org.example.university.model.Workshop;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopServiceImpl implements WorkshopService{
    private final WorkshopRepository workshopRepository;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public List<Workshop> getAllWorkshops() {
        return workshopRepository.findAll();
    }

    @Override
    public Workshop getWorkshopById(Long id) {
        return workshopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workshop not found"));
    }

    @Override
    public Workshop createWorkshop(Workshop workshop) {
        workshop.setSeatsRemaining(workshop.getTotalSeats());
        workshop.setStatus("ACTIVE");
        return workshopRepository.save(workshop);
    }

    @Override
    public Workshop updateWorkshop(Long id, Workshop workshop) {
        Workshop existing = getWorkshopById(id);

        existing.setTitle(workshop.getTitle());
        existing.setDescription(workshop.getDescription());
        existing.setLocation(workshop.getLocation());
        existing.setStartDatetime(workshop.getStartDatetime());
        existing.setTotalSeats(workshop.getTotalSeats());

        return workshopRepository.save(existing);
    }

    @Override
    public void cancelWorkshop(Long id) {
        Workshop workshop = getWorkshopById(id);
        workshop.setStatus("CANCELLED");
        workshopRepository.save(workshop);
    }
}
