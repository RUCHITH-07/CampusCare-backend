package com.campuscare.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaint_status_histories")
public class ComplaintStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Enumerated(EnumType.STRING)
    private Status oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status newStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by_id", nullable = false)
    private User changedBy;

    private String remarks;

    private LocalDateTime changedAt;

    public ComplaintStatusHistory() {}

    public ComplaintStatusHistory(Long id, Complaint complaint, Status oldStatus, Status newStatus, User changedBy, String remarks, LocalDateTime changedAt) {
        this.id = id;
        this.complaint = complaint;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedBy = changedBy;
        this.remarks = remarks;
        this.changedAt = changedAt;
    }

    @PrePersist
    protected void onCreate() {
        if (changedAt == null) {
            changedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Complaint getComplaint() { return complaint; }
    public void setComplaint(Complaint complaint) { this.complaint = complaint; }

    public Status getOldStatus() { return oldStatus; }
    public void setOldStatus(Status oldStatus) { this.oldStatus = oldStatus; }

    public Status getNewStatus() { return newStatus; }
    public void setNewStatus(Status newStatus) { this.newStatus = newStatus; }

    public User getChangedBy() { return changedBy; }
    public void setChangedBy(User changedBy) { this.changedBy = changedBy; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }

    public static ComplaintStatusHistoryBuilder builder() { return new ComplaintStatusHistoryBuilder(); }

    public static class ComplaintStatusHistoryBuilder {
        private Long id;
        private Complaint complaint;
        private Status oldStatus;
        private Status newStatus;
        private User changedBy;
        private String remarks;
        private LocalDateTime changedAt;

        public ComplaintStatusHistoryBuilder id(Long id) { this.id = id; return this; }
        public ComplaintStatusHistoryBuilder complaint(Complaint complaint) { this.complaint = complaint; return this; }
        public ComplaintStatusHistoryBuilder oldStatus(Status oldStatus) { this.oldStatus = oldStatus; return this; }
        public ComplaintStatusHistoryBuilder newStatus(Status newStatus) { this.newStatus = newStatus; return this; }
        public ComplaintStatusHistoryBuilder changedBy(User changedBy) { this.changedBy = changedBy; return this; }
        public ComplaintStatusHistoryBuilder remarks(String remarks) { this.remarks = remarks; return this; }
        public ComplaintStatusHistoryBuilder changedAt(LocalDateTime changedAt) { this.changedAt = changedAt; return this; }

        public ComplaintStatusHistory build() {
            return new ComplaintStatusHistory(id, complaint, oldStatus, newStatus, changedBy, remarks, changedAt);
        }
    }
}
