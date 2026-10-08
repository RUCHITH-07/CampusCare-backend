package com.campuscare.service.impl;

import com.campuscare.dto.*;
import com.campuscare.entity.*;
import com.campuscare.exception.BadRequestException;
import com.campuscare.exception.ResourceNotFoundException;
import com.campuscare.repository.ComplaintRepository;
import com.campuscare.repository.ComplaintStatusHistoryRepository;
import com.campuscare.repository.UserRepository;
import com.campuscare.service.AdminComplaintService;
import com.campuscare.service.FileStorageService;
import com.campuscare.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminComplaintServiceImpl implements AdminComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintStatusHistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final NotificationService notificationService;

    public AdminComplaintServiceImpl(ComplaintRepository complaintRepository,
                                     ComplaintStatusHistoryRepository historyRepository,
                                     UserRepository userRepository,
                                     FileStorageService fileStorageService,
                                     NotificationService notificationService) {
        this.complaintRepository = complaintRepository;
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComplaintResponse> getAllComplaints(Status status, Category category, Priority priority, String search, Pageable pageable) {
        return complaintRepository.findFilteredComplaints(null, status, category, priority, search, pageable)
                .map(ComplaintResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ComplaintResponse getComplaintById(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));
        return ComplaintResponse.fromEntity(complaint);
    }

    @Override
    @Transactional
    public ComplaintResponse assignComplaint(Long id, AssignComplaintRequest request, User adminUser) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        User assignedTo = userRepository.findById(request.getAssignedToUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned user not found with id: " + request.getAssignedToUserId()));

        complaint.setAssignedTo(assignedTo);
        Status oldStatus = complaint.getStatus();
        if (oldStatus == Status.SUBMITTED) {
            complaint.setStatus(Status.ASSIGNED);
        }

        Complaint updated = complaintRepository.save(complaint);

        ComplaintStatusHistory history = ComplaintStatusHistory.builder()
                .complaint(updated)
                .oldStatus(oldStatus)
                .newStatus(updated.getStatus())
                .changedBy(adminUser)
                .remarks("Assigned to " + assignedTo.getFullName() + (request.getRemarks() != null ? ": " + request.getRemarks() : ""))
                .build();
        historyRepository.save(history);

        notificationService.createNotification(
                updated.getCreatedBy(),
                updated,
                "Your complaint " + updated.getReferenceNumber() + " has been assigned to " + assignedTo.getFullName() + "."
        );

        return ComplaintResponse.fromEntity(updated);
    }

    @Override
    @Transactional
    public ComplaintResponse updateStatus(Long id, UpdateStatusRequest request, User adminUser) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        Status oldStatus = complaint.getStatus();
        Status newStatus = request.getStatus();

        if (oldStatus == newStatus) {
            return ComplaintResponse.fromEntity(complaint);
        }

        // Validate state transition
        validateStatusTransition(oldStatus, newStatus);

        complaint.setStatus(newStatus);
        Complaint updated = complaintRepository.save(complaint);

        ComplaintStatusHistory history = ComplaintStatusHistory.builder()
                .complaint(updated)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .changedBy(adminUser)
                .remarks(request.getRemarks() != null ? request.getRemarks() : "Status updated to " + newStatus)
                .build();
        historyRepository.save(history);

        notificationService.createNotification(
                updated.getCreatedBy(),
                updated,
                "Status of complaint " + updated.getReferenceNumber() + " updated to " + newStatus + "."
        );

        return ComplaintResponse.fromEntity(updated);
    }

    @Override
    @Transactional
    public ComplaintResponse resolveComplaint(Long id, String resolutionNote, MultipartFile afterImage, User adminUser) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        // MANDATORY REQUIREMENT: An AFTER photo is mandatory to move a complaint to RESOLVED
        if (afterImage == null || afterImage.isEmpty()) {
            throw new BadRequestException("An AFTER (fixed) photo is mandatory to mark a complaint as RESOLVED.");
        }

        if (resolutionNote == null || resolutionNote.isBlank()) {
            throw new BadRequestException("A resolution note is required when marking a complaint as RESOLVED.");
        }

        String afterImagePath = fileStorageService.storeFile(afterImage);
        Status oldStatus = complaint.getStatus();

        complaint.setAfterImagePath(afterImagePath);
        complaint.setResolutionNote(resolutionNote);
        complaint.setStatus(Status.RESOLVED);
        complaint.setResolvedAt(LocalDateTime.now());

        Complaint updated = complaintRepository.save(complaint);

        ComplaintStatusHistory history = ComplaintStatusHistory.builder()
                .complaint(updated)
                .oldStatus(oldStatus)
                .newStatus(Status.RESOLVED)
                .changedBy(adminUser)
                .remarks("Complaint resolved: " + resolutionNote)
                .build();
        historyRepository.save(history);

        notificationService.createNotification(
                updated.getCreatedBy(),
                updated,
                "Your complaint " + updated.getReferenceNumber() + " has been RESOLVED. Please check before/after photos and provide feedback."
        );

        return ComplaintResponse.fromEntity(updated);
    }

    @Override
    @Transactional
    public ComplaintResponse closeComplaint(Long id, String remarks, User adminUser) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        Status oldStatus = complaint.getStatus();
        complaint.setStatus(Status.CLOSED);
        complaint.setClosedAt(LocalDateTime.now());

        Complaint updated = complaintRepository.save(complaint);

        ComplaintStatusHistory history = ComplaintStatusHistory.builder()
                .complaint(updated)
                .oldStatus(oldStatus)
                .newStatus(Status.CLOSED)
                .changedBy(adminUser)
                .remarks(remarks != null ? remarks : "Complaint closed by admin.")
                .build();
        historyRepository.save(history);

        notificationService.createNotification(
                updated.getCreatedBy(),
                updated,
                "Your complaint " + updated.getReferenceNumber() + " has been closed."
        );

        return ComplaintResponse.fromEntity(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintStatusHistoryResponse> getComplaintHistory(Long id) {
        if (!complaintRepository.existsById(id)) {
            throw new ResourceNotFoundException("Complaint not found with id: " + id);
        }
        return historyRepository.findByComplaintIdOrderByChangedAtAsc(id)
                .stream()
                .map(ComplaintStatusHistoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    private void validateStatusTransition(Status oldStatus, Status newStatus) {
        if (oldStatus == Status.CLOSED && newStatus != Status.IN_PROGRESS) {
            throw new BadRequestException("Closed complaints can only be reopened to IN_PROGRESS");
        }
    }
}
