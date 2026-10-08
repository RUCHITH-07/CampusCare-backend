package com.campuscare.service.impl;

import com.campuscare.dto.DashboardStatsResponse;
import com.campuscare.entity.Category;
import com.campuscare.entity.Status;
import com.campuscare.repository.ComplaintRepository;
import com.campuscare.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ComplaintRepository complaintRepository;

    public DashboardServiceImpl(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsResponse getDashboardStats() {
        long total = complaintRepository.count();
        long submitted = complaintRepository.countByStatus(Status.SUBMITTED);
        long assigned = complaintRepository.countByStatus(Status.ASSIGNED);
        long inProgress = complaintRepository.countByStatus(Status.IN_PROGRESS);
        long resolved = complaintRepository.countByStatus(Status.RESOLVED);
        long closed = complaintRepository.countByStatus(Status.CLOSED);

        // Status counts map
        Map<String, Long> statusCounts = new HashMap<>();
        for (Status s : Status.values()) {
            statusCounts.put(s.name(), complaintRepository.countByStatus(s));
        }

        // Category counts map
        Map<String, Long> categoryCounts = new HashMap<>();
        for (Category c : Category.values()) {
            categoryCounts.put(c.name(), complaintRepository.countByCategory(c));
        }

        // Monthly trends
        List<DashboardStatsResponse.MonthlyTrendDto> monthlyTrends = new ArrayList<>();
        List<Object[]> rawTrends = complaintRepository.countMonthlyTrend();
        for (Object[] row : rawTrends) {
            if (row.length >= 2 && row[0] != null && row[1] != null) {
                String month = row[0].toString();
                long count = ((Number) row[1]).longValue();
                monthlyTrends.add(new DashboardStatsResponse.MonthlyTrendDto(month, count));
            }
        }

        return DashboardStatsResponse.builder()
                .totalComplaints(total)
                .submittedCount(submitted)
                .assignedCount(assigned)
                .inProgressCount(inProgress)
                .resolvedCount(resolved)
                .closedCount(closed)
                .statusCounts(statusCounts)
                .categoryCounts(categoryCounts)
                .monthlyTrends(monthlyTrends)
                .build();
    }
}
