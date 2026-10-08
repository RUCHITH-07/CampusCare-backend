package com.campuscare.service;

import com.campuscare.dto.*;
import com.campuscare.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ComplaintService {
    ComplaintResponse createComplaint(CreateComplaintRequest request, MultipartFile beforeImage, User currentUser);
    Page<ComplaintResponse> getMyComplaints(User currentUser, Pageable pageable);
    ComplaintResponse getComplaintById(Long id, User currentUser);
    ComplaintResponse trackByReferenceNumber(String referenceNumber);
    ComplaintResponse submitFeedback(Long id, FeedbackRequest request, User currentUser);
    ComplaintResponse reopenComplaint(Long id, ReopenComplaintRequest request, User currentUser);
    List<ComplaintStatusHistoryResponse> getComplaintHistory(Long id, User currentUser);
}
