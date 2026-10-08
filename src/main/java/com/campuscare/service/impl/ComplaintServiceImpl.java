package com.campuscare.service.impl;

import com.campuscare.dto.*;
import com.campuscare.entity.*;
import com.campuscare.exception.BadRequestException;
import com.campuscare.exception.ResourceNotFoundException;
import com.campuscare.exception.UnauthorizedActionException;
import com.campuscare.repository.ComplaintRepository;
import com.campuscare.repository.ComplaintStatusHistoryRepository;
import com.campuscare.service.ComplaintService;
import com.campuscare.service.FileStorageService;
import com.campuscare.service.NotificationService;
import com.campuscare.service.ReferenceNumberGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintStatusHistoryRepository historyRepository;
    private final FileStorageService fileStorageService;
    private final ReferenceNumberGenerator referenceNumberGenerator;
    private final NotificationService notificationService;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository,
                                ComplaintStatusHistoryRepository historyRepository,
                                FileStorageService fileStorageService,
                                ReferenceNumberGenerator referenceNumberGenerator,
                                NotificationService notificationService) {
        this.complaintRepository = complaintRepository;
        this.historyRepository = historyRepository;
        this.fileStorageService = fileStorageService;
        this.referenceNumberGenerator = referenceNumberGenerator;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public ComplaintResponse createComplaint(CreateComplaintRequest request, MultipartFile beforeImage, User currentUser) {
        // Validate BEFORE photo (required for categories other than OTHER)
        if (request.getCategory() != Category.OTHER && (beforeImage == null || beforeImage.isEmpty())) {
            throw new BadRequestException("A BEFORE photo is required for category: " + request.getCategory());
        }

        String imagePath = null;
        if (beforeImage != null && !beforeImage.isEmpty()) {
            imagePath = fileStorageService.storeFile(beforeImage);
        }

        String refNum = referenceNumberGenerator.generateReferenceNumber();

        Complaint complaint = Complaint.builder()
                .referenceNumber(refNum)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .priority(request.getPriority())
                .status(Status.SUBMITTED)
                .location(request.getLocation())
                .createdBy(currentUser)
                .beforeImagePath(imagePath)
                .build();

        Complaint saved = complaintRepository.save(complaint);

        // Record status history
        ComplaintStatusHistory history = ComplaintStatusHistory.builder()
                .complaint(saved)
                .oldStatus(null)
                .newStatus(Status.SUBMITTED)
                .changedBy(currentUser)
                .remarks("Complaint submitted by " + currentUser.getFullName())
                .build();
        historyRepository.save(history);

        // Create notification for user
        notificationService.createNotification(
                currentUser,
                saved,
                "Your complaint " + refNum + " has been submitted successfully."
        );

        return ComplaintResponse.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComplaintResponse> getMyComplaints(User currentUser, Pageable pageable) {
        return complaintRepository.findByCreatedBy(currentUser, pageable)
                .map(ComplaintResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ComplaintResponse getComplaintById(Long id, User currentUser) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        // Security check: USER can only access their own complaint
        if (currentUser.getRole() == Role.USER && !complaint.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new UnauthorizedActionException("You are not authorized to view this complaint");
        }

        return ComplaintResponse.fromEntity(complaint);
    }

    @Override
    @Transactional(readOnly = true)
    public ComplaintResponse trackByReferenceNumber(String referenceNumber) {
        Complaint complaint = complaintRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with reference number: " + referenceNumber));

        return ComplaintResponse.fromEntity(complaint);
    }

    @Override
    @Transactional
    public ComplaintResponse submitFeedback(Long id, FeedbackRequest request, User currentUser) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        if (!complaint.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new UnauthorizedActionException("You can only submit feedback for your own complaints");
        }

        if (complaint.getStatus() != Status.RESOLVED && complaint.getStatus() != Status.CLOSED) {
            throw new BadRequestException("Feedback can only be submitted for RESOLVED or CLOSED complaints");
        }

        complaint.setRating(request.getRating());
        complaint.setFeedback(request.getFeedback());

        Status oldStatus = complaint.getStatus();
        if (oldStatus != Status.CLOSED) {
            complaint.setStatus(Status.CLOSED);
            complaint.setClosedAt(LocalDateTime.now());

            ComplaintStatusHistory history = ComplaintStatusHistory.builder()
                    .complaint(complaint)
                    .oldStatus(oldStatus)
                    .newStatus(Status.CLOSED)
                    .changedBy(currentUser)
                    .remarks("Closed following student feedback rating (" + request.getRating() + "/5)")
                    .build();
            historyRepository.save(history);
        }

        Complaint updated = complaintRepository.save(complaint);
        return ComplaintResponse.fromEntity(updated);
    }

    @Override
    @Transactional
    public ComplaintResponse reopenComplaint(Long id, ReopenComplaintRequest request, User currentUser) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        if (!complaint.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new UnauthorizedActionException("You can only reopen your own complaints");
        }

        if (complaint.getStatus() != Status.RESOLVED && complaint.getStatus() != Status.CLOSED) {
            throw new BadRequestException("Only RESOLVED or CLOSED complaints can be reopened");
        }

        Status oldStatus = complaint.getStatus();
        complaint.setStatus(Status.IN_PROGRESS);
        complaint.setReopenReason(request.getReopenReason());

        ComplaintStatusHistory history = ComplaintStatusHistory.builder()
                .complaint(complaint)
                .oldStatus(oldStatus)
                .newStatus(Status.IN_PROGRESS)
                .changedBy(currentUser)
                .remarks("Reopened by student: " + request.getReopenReason())
                .build();
        historyRepository.save(history);

        Complaint updated = complaintRepository.save(complaint);

        notificationService.createNotification(
                currentUser,
                updated,
                "Complaint " + updated.getReferenceNumber() + " has been reopened."
        );

        return ComplaintResponse.fromEntity(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintStatusHistoryResponse> getComplaintHistory(Long id, User currentUser) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        if (currentUser.getRole() == Role.USER && !complaint.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new UnauthorizedActionException("You are not authorized to view history for this complaint");
        }

        return historyRepository.findByComplaintIdOrderByChangedAtAsc(id)
                .stream()
                .map(ComplaintStatusHistoryResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
