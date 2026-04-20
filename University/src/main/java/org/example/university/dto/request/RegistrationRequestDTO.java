package org.example.university.dto.request;

public class RegistrationRequestDTO {
    private Long userId;
    private Long workshopId;

    public RegistrationRequestDTO() {}

    public RegistrationRequestDTO(Long userId, Long workshopId) {
        this.userId = userId;
        this.workshopId = workshopId;
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
