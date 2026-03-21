package org.example.university_workshop_registration_system.Service;

import org.example.university_workshop_registration_system.Repository.UsersRepository;
import org.example.university_workshop_registration_system.model.Users;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users registerUser(Users user) {

        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        return usersRepository.save(user);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
