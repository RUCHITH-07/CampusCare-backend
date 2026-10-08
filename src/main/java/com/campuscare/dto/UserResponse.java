package com.campuscare.dto;

import com.campuscare.entity.Role;
import com.campuscare.entity.User;
import java.time.LocalDateTime;

public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String rollNumberOrEmployeeId;
    private String department;
    private Role role;
    private boolean enabled;
    private LocalDateTime createdAt;

    public UserResponse() {}

    public UserResponse(Long id, String fullName, String email, String phone, String rollNumberOrEmployeeId, String department, Role role, boolean enabled, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.rollNumberOrEmployeeId = rollNumberOrEmployeeId;
        this.department = department;
        this.role = role;
        this.enabled = enabled;
        this.createdAt = createdAt;
    }

    public static UserResponse fromEntity(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .rollNumberOrEmployeeId(user.getRollNumberOrEmployeeId())
                .department(user.getDepartment())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRollNumberOrEmployeeId() { return rollNumberOrEmployeeId; }
    public void setRollNumberOrEmployeeId(String rollNumberOrEmployeeId) { this.rollNumberOrEmployeeId = rollNumberOrEmployeeId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static UserResponseBuilder builder() { return new UserResponseBuilder(); }

    public static class UserResponseBuilder {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
        private String rollNumberOrEmployeeId;
        private String department;
        private Role role;
        private boolean enabled;
        private LocalDateTime createdAt;

        public UserResponseBuilder id(Long id) { this.id = id; return this; }
        public UserResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public UserResponseBuilder email(String email) { this.email = email; return this; }
        public UserResponseBuilder phone(String phone) { this.phone = phone; return this; }
        public UserResponseBuilder rollNumberOrEmployeeId(String rollNumberOrEmployeeId) { this.rollNumberOrEmployeeId = rollNumberOrEmployeeId; return this; }
        public UserResponseBuilder department(String department) { this.department = department; return this; }
        public UserResponseBuilder role(Role role) { this.role = role; return this; }
        public UserResponseBuilder enabled(boolean enabled) { this.enabled = enabled; return this; }
        public UserResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserResponse build() {
            return new UserResponse(id, fullName, email, phone, rollNumberOrEmployeeId, department, role, enabled, createdAt);
        }
    }
}
