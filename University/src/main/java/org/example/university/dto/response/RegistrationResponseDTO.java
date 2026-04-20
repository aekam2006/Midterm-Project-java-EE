package org.example.university.dto.response;

import java.time.LocalDateTime;

public class RegistrationResponseDTO {
    private Long registrationId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;

    private Long userId;
    private Long workshopId;

    public RegistrationResponseDTO() {
    }

    public RegistrationResponseDTO(Long registrationId, String status, LocalDateTime createdAt, LocalDateTime cancelledAt, Long userId, Long workshopId) {
        this.registrationId = registrationId;
        this.status = status;
        this.createdAt = createdAt;
        this.cancelledAt = cancelledAt;
        this.userId = userId;
        this.workshopId = workshopId;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Long workshopId) {
        this.workshopId = workshopId;
    }
}