package org.example.university_workshop_registration_system.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String email;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
