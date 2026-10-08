package com.campuscare.controller;

import com.campuscare.dto.*;
import com.campuscare.entity.Category;
import com.campuscare.entity.Priority;
import com.campuscare.entity.User;
import com.campuscare.security.CustomUserDetailsService;
import com.campuscare.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final CustomUserDetailsService userDetailsService;

    public ComplaintController(ComplaintService complaintService, CustomUserDetailsService userDetailsService) {
        this.complaintService = complaintService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<ComplaintResponse>> createComplaint(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") Category category,
            @RequestParam("priority") Priority priority,
            @RequestParam("location") String location,
            @RequestPart(value = "beforeImage", required = false) MultipartFile beforeImage,
            Authentication authentication) {

        User currentUser = userDetailsService.getDomainUser(authentication.getName());

        CreateComplaintRequest request = new CreateComplaintRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setCategory(category);
        request.setPriority(priority);
        request.setLocation(location);

        ComplaintResponse response = complaintService.createComplaint(request, beforeImage, currentUser);
        return ResponseEntity.ok(ApiResponse.success("Complaint submitted successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ComplaintResponse>>> getMyComplaints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            Authentication authentication) {

        User currentUser = userDetailsService.getDomainUser(authentication.getName());
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ComplaintResponse> response = complaintService.getMyComplaints(currentUser, pageable);
        return ResponseEntity.ok(ApiResponse.success("Complaints retrieved successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ComplaintResponse>> getComplaintById(
            @PathVariable Long id,
            Authentication authentication) {

        User currentUser = userDetailsService.getDomainUser(authentication.getName());
        ComplaintResponse response = complaintService.getComplaintById(id, currentUser);
        return ResponseEntity.ok(ApiResponse.success("Complaint details retrieved", response));
    }

    @GetMapping("/track/{referenceNumber}")
    public ResponseEntity<ApiResponse<ComplaintResponse>> trackComplaint(@PathVariable String referenceNumber) {
        ComplaintResponse response = complaintService.trackByReferenceNumber(referenceNumber);
        return ResponseEntity.ok(ApiResponse.success("Complaint details retrieved", response));
    }

    @PostMapping("/{id}/feedback")
    public ResponseEntity<ApiResponse<ComplaintResponse>> submitFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackRequest request,
            Authentication authentication) {

        User currentUser = userDetailsService.getDomainUser(authentication.getName());
        ComplaintResponse response = complaintService.submitFeedback(id, request, currentUser);
        return ResponseEntity.ok(ApiResponse.success("Feedback submitted successfully", response));
    }

    @PostMapping("/{id}/reopen")
    public ResponseEntity<ApiResponse<ComplaintResponse>> reopenComplaint(
            @PathVariable Long id,
            @Valid @RequestBody ReopenComplaintRequest request,
            Authentication authentication) {

        User currentUser = userDetailsService.getDomainUser(authentication.getName());
        ComplaintResponse response = complaintService.reopenComplaint(id, request, currentUser);
        return ResponseEntity.ok(ApiResponse.success("Complaint reopened successfully", response));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<ApiResponse<List<ComplaintStatusHistoryResponse>>> getHistory(
            @PathVariable Long id,
            Authentication authentication) {

        User currentUser = userDetailsService.getDomainUser(authentication.getName());
        List<ComplaintStatusHistoryResponse> history = complaintService.getComplaintHistory(id, currentUser);
        return ResponseEntity.ok(ApiResponse.success("History retrieved successfully", history));
    }
}
