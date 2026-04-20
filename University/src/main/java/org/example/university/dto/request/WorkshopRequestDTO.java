package org.example.university.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class WorkshopRequestDTO {
    @NotBlank
    @Size(min = 5, max = 80)
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotNull
    @Future
    private LocalDateTime startDatetime;

    @Min(1)
    private int totalSeats;

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
