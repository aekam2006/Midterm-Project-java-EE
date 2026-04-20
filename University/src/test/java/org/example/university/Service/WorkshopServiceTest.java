package org.example.university.Service;

import org.example.university.Repository.WorkshopRepository;
import org.example.university.model.Workshop;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkshopServiceTest {

    @Mock
    private WorkshopRepository workshopRepository;

    @InjectMocks
    private WorkshopServiceImpl workshopService;

    // helper method
    private Workshop buildWorkshop(Long id, String title) {
        Workshop workshop = new Workshop();
        workshop.setId(id);
        workshop.setTitle(title);
        workshop.setDescription("Description");
        workshop.setLocation("Room A");
        workshop.setStartDatetime(LocalDateTime.now().plusDays(1));
        workshop.setTotalSeats(10);
        workshop.setSeatsRemaining(10);
        workshop.setStatus("ACTIVE");
        return workshop;
    }

    // ===============================================================
    // TESTING getAllWorkshops()
    // ===============================================================

    @Test
    void getAllWorkshops_returnsAllWorkshops() {

        // Arrange
        List<Workshop> fakeList = List.of(
                buildWorkshop(1L, "Spring Boot Basics"),
                buildWorkshop(2L, "Career Fair Prep")
        );
        when(workshopRepository.findAll()).thenReturn(fakeList);

        // Act
        List<Workshop> result = workshopService.getAllWorkshops();

        // Assert
        assertEquals(2, result.size());
        verify(workshopRepository, times(1)).findAll();
    }

    @Test
    void getAllWorkshops_whenEmpty_returnsEmptyList() {

        // Arrange
        when(workshopRepository.findAll()).thenReturn(List.of());

        // Act
        List<Workshop> result = workshopService.getAllWorkshops();

        // Assert
        assertEquals(0, result.size());
        verify(workshopRepository).findAll();
    }

    // ===============================================================
    // TESTING getWorkshopById()
    // ===============================================================

    @Test
    void getWorkshopById_whenExists_returnsWorkshop() {

        // Arrange
        Workshop workshop = buildWorkshop(1L, "Spring Boot Basics");
        when(workshopRepository.findById(1L))
                .thenReturn(Optional.of(workshop));

        // Act
        Workshop result = workshopService.getWorkshopById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Spring Boot Basics", result.getTitle());
        verify(workshopRepository).findById(1L);
    }

    @Test
    void getWorkshopById_whenNotFound_throwsException() {

        // Arrange
        when(workshopRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> workshopService.getWorkshopById(99L));
        assertTrue(ex.getMessage().contains("Workshop not found"));
        verify(workshopRepository).findById(99L);
    }

    // ===============================================================
    // TESTING createWorkshop()
    // ===============================================================

    @Test
    void createWorkshop_setsStatusActiveAndSaves() {

        // Arrange
        Workshop workshop = buildWorkshop(null, "New Workshop");
        workshop.setStatus(null);
        Workshop saved = buildWorkshop(1L, "New Workshop");

        when(workshopRepository.save(workshop)).thenReturn(saved);

        // Act
        Workshop result = workshopService.createWorkshop(workshop);

        // Assert
        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
        verify(workshopRepository, times(1)).save(workshop);
    }

    @Test
    void createWorkshop_setsSeatRemainingEqualToTotalSeats() {

        // Arrange
        Workshop workshop = buildWorkshop(null, "New Workshop");
        workshop.setTotalSeats(20);
        Workshop saved = buildWorkshop(1L, "New Workshop");
        saved.setSeatsRemaining(20);

        when(workshopRepository.save(workshop)).thenReturn(saved);

        // Act
        Workshop result = workshopService.createWorkshop(workshop);

        // Assert
        assertEquals(20, result.getSeatsRemaining());
        verify(workshopRepository).save(workshop);
    }

    // ===============================================================
    // TESTING updateWorkshop()
    // ===============================================================

    @Test
    void updateWorkshop_whenExists_updatesAndReturns() {

        // Arrange
        Workshop existing = buildWorkshop(1L, "Old Title");
        Workshop updated = buildWorkshop(1L, "New Title");
        updated.setLocation("New Location");

        when(workshopRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(workshopRepository.save(existing)).thenReturn(existing);

        // Act
        Workshop result = workshopService.updateWorkshop(1L, updated);

        // Assert
        assertEquals("New Title", result.getTitle());
        assertEquals("New Location", result.getLocation());
        verify(workshopRepository).save(existing);
    }

    @Test
    void updateWorkshop_whenNotFound_throwsException() {

        // Arrange
        Workshop updated = buildWorkshop(99L, "New Title");
        when(workshopRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RuntimeException.class,
                () -> workshopService.updateWorkshop(99L, updated));
        verify(workshopRepository, never()).save(any());
    }

    // ===============================================================
    // TESTING cancelWorkshop()
    // ===============================================================

    @Test
    void cancelWorkshop_setsStatusCancelled() {

        // Arrange
        Workshop workshop = buildWorkshop(1L, "Spring Boot Basics");
        when(workshopRepository.findById(1L))
                .thenReturn(Optional.of(workshop));

        // Act
        workshopService.cancelWorkshop(1L);

        // Assert
        assertEquals("CANCELLED", workshop.getStatus());
        verify(workshopRepository).save(workshop);
    }

    @Test
    void cancelWorkshop_whenNotFound_throwsException() {

        // Arrange
        when(workshopRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RuntimeException.class,
                () -> workshopService.cancelWorkshop(99L));
        verify(workshopRepository, never()).save(any());
    }
}