package com.campuscare.dto;

import com.campuscare.entity.Notification;
import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
    private Long complaintId;
    private String complaintRefNumber;

    public NotificationResponse() {}

    public NotificationResponse(Long id, String message, boolean isRead, LocalDateTime createdAt, Long complaintId, String complaintRefNumber) {
        this.id = id;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.complaintId = complaintId;
        this.complaintRefNumber = complaintRefNumber;
    }

    public static NotificationResponse fromEntity(Notification notification) {
        if (notification == null) return null;
        return NotificationResponse.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .complaintId(notification.getComplaint() != null ? notification.getComplaint().getId() : null)
                .complaintRefNumber(notification.getComplaint() != null ? notification.getComplaint().getReferenceNumber() : null)
                .build();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getComplaintId() { return complaintId; }
    public void setComplaintId(Long complaintId) { this.complaintId = complaintId; }

    public String getComplaintRefNumber() { return complaintRefNumber; }
    public void setComplaintRefNumber(String complaintRefNumber) { this.complaintRefNumber = complaintRefNumber; }

    public static NotificationResponseBuilder builder() { return new NotificationResponseBuilder(); }

    public static class NotificationResponseBuilder {
        private Long id;
        private String message;
        private boolean isRead;
        private LocalDateTime createdAt;
        private Long complaintId;
        private String complaintRefNumber;

        public NotificationResponseBuilder id(Long id) { this.id = id; return this; }
        public NotificationResponseBuilder message(String message) { this.message = message; return this; }
        public NotificationResponseBuilder isRead(boolean isRead) { this.isRead = isRead; return this; }
        public NotificationResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public NotificationResponseBuilder complaintId(Long complaintId) { this.complaintId = complaintId; return this; }
        public NotificationResponseBuilder complaintRefNumber(String complaintRefNumber) { this.complaintRefNumber = complaintRefNumber; return this; }

        public NotificationResponse build() {
            return new NotificationResponse(id, message, isRead, createdAt, complaintId, complaintRefNumber);
        }
    }
}
