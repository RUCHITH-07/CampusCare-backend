package com.campuscare.dto;

import com.campuscare.entity.Category;
import com.campuscare.entity.Complaint;
import com.campuscare.entity.Priority;
import com.campuscare.entity.Status;
import java.time.LocalDateTime;

public class ComplaintResponse {

    private Long id;
    private String referenceNumber;
    private String title;
    private String description;
    private Category category;
    private Priority priority;
    private Status status;
    private String location;
    private UserResponse createdBy;
    private UserResponse assignedTo;
    private String beforeImagePath;
    private String afterImagePath;
    private String resolutionNote;
    private Integer rating;
    private String feedback;
    private String reopenReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;
    private LocalDateTime closedAt;

    public ComplaintResponse() {}

    public ComplaintResponse(Long id, String referenceNumber, String title, String description, Category category, Priority priority, Status status, String location, UserResponse createdBy, UserResponse assignedTo, String beforeImagePath, String afterImagePath, String resolutionNote, Integer rating, String feedback, String reopenReason, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime resolvedAt, LocalDateTime closedAt) {
        this.id = id;
        this.referenceNumber = referenceNumber;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.location = location;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.beforeImagePath = beforeImagePath;
        this.afterImagePath = afterImagePath;
        this.resolutionNote = resolutionNote;
        this.rating = rating;
        this.feedback = feedback;
        this.reopenReason = reopenReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resolvedAt = resolvedAt;
        this.closedAt = closedAt;
    }

    public static ComplaintResponse fromEntity(Complaint complaint) {
        if (complaint == null) return null;
        return ComplaintResponse.builder()
                .id(complaint.getId())
                .referenceNumber(complaint.getReferenceNumber())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .category(complaint.getCategory())
                .priority(complaint.getPriority())
                .status(complaint.getStatus())
                .location(complaint.getLocation())
                .createdBy(UserResponse.fromEntity(complaint.getCreatedBy()))
                .assignedTo(UserResponse.fromEntity(complaint.getAssignedTo()))
                .beforeImagePath(complaint.getBeforeImagePath())
                .afterImagePath(complaint.getAfterImagePath())
                .resolutionNote(complaint.getResolutionNote())
                .rating(complaint.getRating())
                .feedback(complaint.getFeedback())
                .reopenReason(complaint.getReopenReason())
                .createdAt(complaint.getCreatedAt())
                .updatedAt(complaint.getUpdatedAt())
                .resolvedAt(complaint.getResolvedAt())
                .closedAt(complaint.getClosedAt())
                .build();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public UserResponse getCreatedBy() { return createdBy; }
    public void setCreatedBy(UserResponse createdBy) { this.createdBy = createdBy; }

    public UserResponse getAssignedTo() { return assignedTo; }
    public void setAssignedTo(UserResponse assignedTo) { this.assignedTo = assignedTo; }

    public String getBeforeImagePath() { return beforeImagePath; }
    public void setBeforeImagePath(String beforeImagePath) { this.beforeImagePath = beforeImagePath; }

    public String getAfterImagePath() { return afterImagePath; }
    public void setAfterImagePath(String afterImagePath) { this.afterImagePath = afterImagePath; }

    public String getResolutionNote() { return resolutionNote; }
    public void setResolutionNote(String resolutionNote) { this.resolutionNote = resolutionNote; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public String getReopenReason() { return reopenReason; }
    public void setReopenReason(String reopenReason) { this.reopenReason = reopenReason; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }

    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }

    public static ComplaintResponseBuilder builder() { return new ComplaintResponseBuilder(); }

    public static class ComplaintResponseBuilder {
        private Long id;
        private String referenceNumber;
        private String title;
        private String description;
        private Category category;
        private Priority priority;
        private Status status;
        private String location;
        private UserResponse createdBy;
        private UserResponse assignedTo;
        private String beforeImagePath;
        private String afterImagePath;
        private String resolutionNote;
        private Integer rating;
        private String feedback;
        private String reopenReason;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime resolvedAt;
        private LocalDateTime closedAt;

        public ComplaintResponseBuilder id(Long id) { this.id = id; return this; }
        public ComplaintResponseBuilder referenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; return this; }
        public ComplaintResponseBuilder title(String title) { this.title = title; return this; }
        public ComplaintResponseBuilder description(String description) { this.description = description; return this; }
        public ComplaintResponseBuilder category(Category category) { this.category = category; return this; }
        public ComplaintResponseBuilder priority(Priority priority) { this.priority = priority; return this; }
        public ComplaintResponseBuilder status(Status status) { this.status = status; return this; }
        public ComplaintResponseBuilder location(String location) { this.location = location; return this; }
        public ComplaintResponseBuilder createdBy(UserResponse createdBy) { this.createdBy = createdBy; return this; }
        public ComplaintResponseBuilder assignedTo(UserResponse assignedTo) { this.assignedTo = assignedTo; return this; }
        public ComplaintResponseBuilder beforeImagePath(String beforeImagePath) { this.beforeImagePath = beforeImagePath; return this; }
        public ComplaintResponseBuilder afterImagePath(String afterImagePath) { this.afterImagePath = afterImagePath; return this; }
        public ComplaintResponseBuilder resolutionNote(String resolutionNote) { this.resolutionNote = resolutionNote; return this; }
        public ComplaintResponseBuilder rating(Integer rating) { this.rating = rating; return this; }
        public ComplaintResponseBuilder feedback(String feedback) { this.feedback = feedback; return this; }
        public ComplaintResponseBuilder reopenReason(String reopenReason) { this.reopenReason = reopenReason; return this; }
        public ComplaintResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ComplaintResponseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public ComplaintResponseBuilder resolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; return this; }
        public ComplaintResponseBuilder closedAt(LocalDateTime closedAt) { this.closedAt = closedAt; return this; }

        public ComplaintResponse build() {
            return new ComplaintResponse(id, referenceNumber, title, description, category, priority, status, location, createdBy, assignedTo, beforeImagePath, afterImagePath, resolutionNote, rating, feedback, reopenReason, createdAt, updatedAt, resolvedAt, closedAt);
        }
    }
}
