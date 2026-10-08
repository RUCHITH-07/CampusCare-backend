package com.campuscare.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String referenceNumber;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    private String beforeImagePath;

    private String afterImagePath;

    @Column(columnDefinition = "TEXT")
    private String resolutionNote;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(columnDefinition = "TEXT")
    private String reopenReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime resolvedAt;

    private LocalDateTime closedAt;

    public Complaint() {}

    public Complaint(Long id, String referenceNumber, String title, String description, Category category, Priority priority, Status status, String location, User createdBy, User assignedTo, String beforeImagePath, String afterImagePath, String resolutionNote, Integer rating, String feedback, String reopenReason, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime resolvedAt, LocalDateTime closedAt) {
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

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
        if (status == null) {
            status = Status.SUBMITTED;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
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

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public User getAssignedTo() { return assignedTo; }
    public void setAssignedTo(User assignedTo) { this.assignedTo = assignedTo; }

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

    // Builder pattern
    public static ComplaintBuilder builder() { return new ComplaintBuilder(); }

    public static class ComplaintBuilder {
        private Long id;
        private String referenceNumber;
        private String title;
        private String description;
        private Category category;
        private Priority priority;
        private Status status;
        private String location;
        private User createdBy;
        private User assignedTo;
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

        public ComplaintBuilder id(Long id) { this.id = id; return this; }
        public ComplaintBuilder referenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; return this; }
        public ComplaintBuilder title(String title) { this.title = title; return this; }
        public ComplaintBuilder description(String description) { this.description = description; return this; }
        public ComplaintBuilder category(Category category) { this.category = category; return this; }
        public ComplaintBuilder priority(Priority priority) { this.priority = priority; return this; }
        public ComplaintBuilder status(Status status) { this.status = status; return this; }
        public ComplaintBuilder location(String location) { this.location = location; return this; }
        public ComplaintBuilder createdBy(User createdBy) { this.createdBy = createdBy; return this; }
        public ComplaintBuilder assignedTo(User assignedTo) { this.assignedTo = assignedTo; return this; }
        public ComplaintBuilder beforeImagePath(String beforeImagePath) { this.beforeImagePath = beforeImagePath; return this; }
        public ComplaintBuilder afterImagePath(String afterImagePath) { this.afterImagePath = afterImagePath; return this; }
        public ComplaintBuilder resolutionNote(String resolutionNote) { this.resolutionNote = resolutionNote; return this; }
        public ComplaintBuilder rating(Integer rating) { this.rating = rating; return this; }
        public ComplaintBuilder feedback(String feedback) { this.feedback = feedback; return this; }
        public ComplaintBuilder reopenReason(String reopenReason) { this.reopenReason = reopenReason; return this; }
        public ComplaintBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ComplaintBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public ComplaintBuilder resolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; return this; }
        public ComplaintBuilder closedAt(LocalDateTime closedAt) { this.closedAt = closedAt; return this; }

        public Complaint build() {
            return new Complaint(id, referenceNumber, title, description, category, priority, status, location, createdBy, assignedTo, beforeImagePath, afterImagePath, resolutionNote, rating, feedback, reopenReason, createdAt, updatedAt, resolvedAt, closedAt);
        }
    }
}
