package com.campuscare.dto;

import jakarta.validation.constraints.NotBlank;

public class ReopenComplaintRequest {

    @NotBlank(message = "Reopen reason is required")
    private String reopenReason;

    public ReopenComplaintRequest() {}

    public String getReopenReason() { return reopenReason; }
    public void setReopenReason(String reopenReason) { this.reopenReason = reopenReason; }
}
