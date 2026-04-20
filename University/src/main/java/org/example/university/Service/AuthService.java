package org.example.university.Service;

import org.example.university.dto.request.LoginRequestDTO;
import org.example.university.dto.request.UserRequestDTO;
import org.example.university.dto.response.AuthResponseDTO;

public interface AuthService{
    AuthResponseDTO register(UserRequestDTO dto);

    AuthResponseDTO login(LoginRequestDTO dto);
}
