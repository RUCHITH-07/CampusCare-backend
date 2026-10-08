package com.campuscare.service;

import com.campuscare.dto.UserResponse;

import java.util.List;

public interface AdminUserService {
    List<UserResponse> getAllUsers();
    UserResponse updateUserStatus(Long userId, boolean enabled);
}
