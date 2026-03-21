package org.example.university_workshop_registration_system.Service;

import org.example.university_workshop_registration_system.model.Users;

import java.util.Optional;

public interface UserService {
    Users registerUser(Users user);

    Optional<Users> findByEmail(String email);
}
