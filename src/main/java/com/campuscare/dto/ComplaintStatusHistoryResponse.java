package com.campuscare.dto;

import com.campuscare.entity.ComplaintStatusHistory;
import com.campuscare.entity.Status;
import java.time.LocalDateTime;

public class ComplaintStatusHistoryResponse {

    private Long id;
    private Status oldStatus;
    private Status newStatus;
    private String changedByName;
    private String remarks;
    private LocalDateTime changedAt;

    public ComplaintStatusHistoryResponse() {}

    public ComplaintStatusHistoryResponse(Long id, Status oldStatus, Status newStatus, String changedByName, String remarks, LocalDateTime changedAt) {
        this.id = id;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedByName = changedByName;
        this.remarks = remarks;
        this.changedAt = changedAt;
    }

    public static ComplaintStatusHistoryResponse fromEntity(ComplaintStatusHistory history) {
        if (history == null) return null;
        return ComplaintStatusHistoryResponse.builder()
                .id(history.getId())
                .oldStatus(history.getOldStatus())
                .newStatus(history.getNewStatus())
                .changedByName(history.getChangedBy() != null ? history.getChangedBy().getFullName() : "System")
                .remarks(history.getRemarks())
                .changedAt(history.getChangedAt())
                .build();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Status getOldStatus() { return oldStatus; }
    public void setOldStatus(Status oldStatus) { this.oldStatus = oldStatus; }

    public Status getNewStatus() { return newStatus; }
    public void setNewStatus(Status newStatus) { this.newStatus = newStatus; }

    public String getChangedByName() { return changedByName; }
    public void setChangedByName(String changedByName) { this.changedByName = changedByName; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }

    public static ComplaintStatusHistoryResponseBuilder builder() { return new ComplaintStatusHistoryResponseBuilder(); }

    public static class ComplaintStatusHistoryResponseBuilder {
        private Long id;
        private Status oldStatus;
        private Status newStatus;
        private String changedByName;
        private String remarks;
        private LocalDateTime changedAt;

        public ComplaintStatusHistoryResponseBuilder id(Long id) { this.id = id; return this; }
        public ComplaintStatusHistoryResponseBuilder oldStatus(Status oldStatus) { this.oldStatus = oldStatus; return this; }
        public ComplaintStatusHistoryResponseBuilder newStatus(Status newStatus) { this.newStatus = newStatus; return this; }
        public ComplaintStatusHistoryResponseBuilder changedByName(String changedByName) { this.changedByName = changedByName; return this; }
        public ComplaintStatusHistoryResponseBuilder remarks(String remarks) { this.remarks = remarks; return this; }
        public ComplaintStatusHistoryResponseBuilder changedAt(LocalDateTime changedAt) { this.changedAt = changedAt; return this; }

        public ComplaintStatusHistoryResponse build() {
            return new ComplaintStatusHistoryResponse(id, oldStatus, newStatus, changedByName, remarks, changedAt);
        }
    }
}
