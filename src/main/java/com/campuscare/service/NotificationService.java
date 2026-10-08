package com.campuscare.service;

import com.campuscare.dto.NotificationResponse;
import com.campuscare.entity.Complaint;
import com.campuscare.entity.User;

import java.util.List;

public interface NotificationService {
    void createNotification(User recipient, Complaint complaint, String message);
    List<NotificationResponse> getUserNotifications(Long userId);
    long getUnreadCount(Long userId);
    void markAsRead(Long notificationId, Long userId);
    void markAllAsRead(Long userId);
}
