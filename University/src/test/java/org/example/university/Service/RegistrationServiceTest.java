package org.example.university.Service;

import org.example.university.Repository.RegistrationRepository;
import org.example.university.Repository.UserRepository;
import org.example.university.Repository.WorkshopRepository;
import org.example.university.model.Registration;
import org.example.university.model.User;
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
class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkshopRepository workshopRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    // helper methods
    private User buildUser(Long id, String email) {
        User user = new User();
        user.setId(id);
        user.setName("Test User");
        user.setEmail(email);
        user.setPassword_hash("hashed");
        user.setRole("ATTENDEE");
        return user;
    }

    private Workshop buildWorkshop(Long id, int seats, LocalDateTime start) {
        Workshop workshop = new Workshop();
        workshop.setId(id);
        workshop.setTitle("Test Workshop");
        workshop.setDescription("Description");
        workshop.setLocation("Room A");
        workshop.setStartDatetime(start);
        workshop.setTotalSeats(seats);
        workshop.setSeatsRemaining(seats);
        workshop.setStatus("ACTIVE");
        return workshop;
    }

    // ===============================================================
    // TESTING register()
    // ===============================================================

    @Test
    void register_whenValid_savesRegistration() {

        // Arrange
        User user = buildUser(1L, "test@test.com");
        Workshop workshop = buildWorkshop(1L, 10,
                LocalDateTime.now().plusDays(1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(registrationRepository.existsByUserAndWorkshop(user, workshop))
                .thenReturn(false);

        // Act
        registrationService.register(1L, 1L);

        // Assert
        verify(registrationRepository, times(1)).save(any(Registration.class));
        verify(workshopRepository, times(1)).save(workshop);
        assertEquals(9, workshop.getSeatsRemaining());
    }

    @Test
    void register_whenWorkshopFull_throwsException() {

        // Arrange
        User user = buildUser(1L, "test@test.com");
        Workshop workshop = buildWorkshop(1L, 0,
                LocalDateTime.now().plusDays(1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(workshop));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> registrationService.register(1L, 1L));
        assertTrue(ex.getMessage().contains("full"));
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void register_whenAlreadyRegistered_throwsException() {

        // Arrange
        User user = buildUser(1L, "test@test.com");
        Workshop workshop = buildWorkshop(1L, 10,
                LocalDateTime.now().plusDays(1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(registrationRepository.existsByUserAndWorkshop(user, workshop))
                .thenReturn(true);

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> registrationService.register(1L, 1L));
        assertTrue(ex.getMessage().contains("Already registered"));
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void register_whenWorkshopInPast_throwsException() {

        // Arrange
        User user = buildUser(1L, "test@test.com");
        Workshop workshop = buildWorkshop(1L, 10,
                LocalDateTime.now().minusDays(1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(registrationRepository.existsByUserAndWorkshop(user, workshop))
                .thenReturn(false);

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> registrationService.register(1L, 1L));
        assertTrue(ex.getMessage().contains("started"));
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void register_whenUserNotFound_throwsException() {

        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RuntimeException.class,
                () -> registrationService.register(99L, 1L));
        verify(registrationRepository, never()).save(any());
    }

    // ===============================================================
    // TESTING cancelRegistration()
    // ===============================================================

    @Test
    void cancelRegistration_whenValid_cancelsAndRestoresSeat() {

        // Arrange
        User user = buildUser(1L, "test@test.com");
        Workshop workshop = buildWorkshop(1L, 10,
                LocalDateTime.now().plusDays(1));
        workshop.setSeatsRemaining(9);

        Registration reg = new Registration();
        reg.setId(1L);
        reg.setUser(user);
        reg.setWorkshop(workshop);
        reg.setStatus("ACTIVE");

        when(registrationRepository.findById(1L))
                .thenReturn(Optional.of(reg));

        // Act
        registrationService.cancelRegistration(1L);

        // Assert
        assertEquals("CANCELLED", reg.getStatus());
        assertEquals(10, workshop.getSeatsRemaining());
        verify(registrationRepository, times(1)).save(reg);
        verify(workshopRepository, times(1)).save(workshop);
    }

    @Test
    void cancelRegistration_whenWorkshopStarted_throwsException() {

        // Arrange
        User user = buildUser(1L, "test@test.com");
        Workshop workshop = buildWorkshop(1L, 10,
                LocalDateTime.now().minusDays(1));

        Registration reg = new Registration();
        reg.setId(1L);
        reg.setUser(user);
        reg.setWorkshop(workshop);
        reg.setStatus("ACTIVE");

        when(registrationRepository.findById(1L))
                .thenReturn(Optional.of(reg));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> registrationService.cancelRegistration(1L));
        assertTrue(ex.getMessage().contains("Cannot cancel"));
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void cancelRegistration_whenNotFound_throwsException() {

        // Arrange
        when(registrationRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RuntimeException.class,
                () -> registrationService.cancelRegistration(99L));
    }

    // ===============================================================
    // TESTING getUserRegistrations()
    // ===============================================================

    @Test
    void getUserRegistrations_whenUserExists_returnsList() {

        // Arrange
        User user = buildUser(1L, "test@test.com");
        Workshop workshop = buildWorkshop(1L, 10,
                LocalDateTime.now().plusDays(1));

        Registration reg = new Registration();
        reg.setId(1L);
        reg.setUser(user);
        reg.setWorkshop(workshop);
        reg.setStatus("ACTIVE");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(registrationRepository.findByUser(user))
                .thenReturn(List.of(reg));

        // Act
        List<Registration> result =
                registrationService.getUserRegistrations(1L);

        // Assert
        assertEquals(1, result.size());
        verify(registrationRepository, times(1)).findByUser(user);
    }

    @Test
    void getUserRegistrations_whenUserNotFound_throwsException() {

        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RuntimeException.class,
                () -> registrationService.getUserRegistrations(99L));
    }
}