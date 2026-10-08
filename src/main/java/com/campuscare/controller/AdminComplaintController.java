package com.campuscare.controller;

import com.campuscare.dto.*;
import com.campuscare.entity.Category;
import com.campuscare.entity.Priority;
import com.campuscare.entity.Status;
import com.campuscare.entity.User;
import com.campuscare.security.CustomUserDetailsService;
import com.campuscare.service.AdminComplaintService;
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
@RequestMapping("/api/admin/complaints")
public class AdminComplaintController {

    private final AdminComplaintService adminComplaintService;
    private final CustomUserDetailsService userDetailsService;

    public AdminComplaintController(AdminComplaintService adminComplaintService, CustomUserDetailsService userDetailsService) {
        this.adminComplaintService = adminComplaintService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ComplaintResponse>>> getAllComplaints(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ComplaintResponse> complaints = adminComplaintService.getAllComplaints(status, category, priority, search, pageable);
        return ResponseEntity.ok(ApiResponse.success("Complaints retrieved successfully", complaints));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ComplaintResponse>> getComplaintById(@PathVariable Long id) {
        ComplaintResponse complaint = adminComplaintService.getComplaintById(id);
        return ResponseEntity.ok(ApiResponse.success("Complaint details retrieved", complaint));
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<ApiResponse<ComplaintResponse>> assignComplaint(
            @PathVariable Long id,
            @Valid @RequestBody AssignComplaintRequest request,
            Authentication authentication) {

        User adminUser = userDetailsService.getDomainUser(authentication.getName());
        ComplaintResponse updated = adminComplaintService.assignComplaint(id, request, adminUser);
        return ResponseEntity.ok(ApiResponse.success("Complaint assigned successfully", updated));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ComplaintResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request,
            Authentication authentication) {

        User adminUser = userDetailsService.getDomainUser(authentication.getName());
        ComplaintResponse updated = adminComplaintService.updateStatus(id, request, adminUser);
        return ResponseEntity.ok(ApiResponse.success("Status updated successfully", updated));
    }

    @PostMapping(value = "/{id}/resolve", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<ComplaintResponse>> resolveComplaint(
            @PathVariable Long id,
            @RequestParam("resolutionNote") String resolutionNote,
            @RequestPart("afterImage") MultipartFile afterImage,
            Authentication authentication) {

        User adminUser = userDetailsService.getDomainUser(authentication.getName());
        ComplaintResponse updated = adminComplaintService.resolveComplaint(id, resolutionNote, afterImage, adminUser);
        return ResponseEntity.ok(ApiResponse.success("Complaint marked as RESOLVED successfully", updated));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<ApiResponse<ComplaintResponse>> closeComplaint(
            @PathVariable Long id,
            @RequestParam(required = false) String remarks,
            Authentication authentication) {

        User adminUser = userDetailsService.getDomainUser(authentication.getName());
        ComplaintResponse updated = adminComplaintService.closeComplaint(id, remarks, adminUser);
        return ResponseEntity.ok(ApiResponse.success("Complaint closed successfully", updated));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<ApiResponse<List<ComplaintStatusHistoryResponse>>> getHistory(@PathVariable Long id) {
        List<ComplaintStatusHistoryResponse> history = adminComplaintService.getComplaintHistory(id);
        return ResponseEntity.ok(ApiResponse.success("Complaint status history retrieved", history));
    }
}
