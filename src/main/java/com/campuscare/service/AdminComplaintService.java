package com.campuscare.service;

import com.campuscare.dto.*;
import com.campuscare.entity.Category;
import com.campuscare.entity.Priority;
import com.campuscare.entity.Status;
import com.campuscare.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminComplaintService {
    Page<ComplaintResponse> getAllComplaints(Status status, Category category, Priority priority, String search, Pageable pageable);
    ComplaintResponse getComplaintById(Long id);
    ComplaintResponse assignComplaint(Long id, AssignComplaintRequest request, User adminUser);
    ComplaintResponse updateStatus(Long id, UpdateStatusRequest request, User adminUser);
    ComplaintResponse resolveComplaint(Long id, String resolutionNote, MultipartFile afterImage, User adminUser);
    ComplaintResponse closeComplaint(Long id, String remarks, User adminUser);
    List<ComplaintStatusHistoryResponse> getComplaintHistory(Long id);
}
