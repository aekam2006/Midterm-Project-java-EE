package org.example.university.Service;

import org.example.university.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);

    Optional<User> findByEmail(String email);

    List<User> getAllUsers();
    User getUserById(Long id);
}
