package com.campuscare.controller;

import com.campuscare.dto.ApiResponse;
import com.campuscare.dto.NotificationResponse;
import com.campuscare.entity.User;
import com.campuscare.security.CustomUserDetailsService;
import com.campuscare.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final CustomUserDetailsService userDetailsService;

    public NotificationController(NotificationService notificationService, CustomUserDetailsService userDetailsService) {
        this.notificationService = notificationService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getMyNotifications(Authentication authentication) {
        User currentUser = userDetailsService.getDomainUser(authentication.getName());
        List<NotificationResponse> list = notificationService.getUserNotifications(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved", list));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(Authentication authentication) {
        User currentUser = userDetailsService.getDomainUser(authentication.getName());
        long count = notificationService.getUnreadCount(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Unread count retrieved", count));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @PathVariable Long id,
            Authentication authentication) {

        User currentUser = userDetailsService.getDomainUser(authentication.getName());
        notificationService.markAsRead(id, currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read"));
    }

    @PutMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(Authentication authentication) {
        User currentUser = userDetailsService.getDomainUser(authentication.getName());
        notificationService.markAllAsRead(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read"));
    }
}
