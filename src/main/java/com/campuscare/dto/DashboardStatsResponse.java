package com.campuscare.dto;

import java.util.List;
import java.util.Map;

public class DashboardStatsResponse {

    private long totalComplaints;
    private long submittedCount;
    private long assignedCount;
    private long inProgressCount;
    private long resolvedCount;
    private long closedCount;

    private Map<String, Long> categoryCounts;
    private Map<String, Long> statusCounts;
    private List<MonthlyTrendDto> monthlyTrends;

    public DashboardStatsResponse() {}

    public DashboardStatsResponse(long totalComplaints, long submittedCount, long assignedCount, long inProgressCount, long resolvedCount, long closedCount, Map<String, Long> categoryCounts, Map<String, Long> statusCounts, List<MonthlyTrendDto> monthlyTrends) {
        this.totalComplaints = totalComplaints;
        this.submittedCount = submittedCount;
        this.assignedCount = assignedCount;
        this.inProgressCount = inProgressCount;
        this.resolvedCount = resolvedCount;
        this.closedCount = closedCount;
        this.categoryCounts = categoryCounts;
        this.statusCounts = statusCounts;
        this.monthlyTrends = monthlyTrends;
    }

    public long getTotalComplaints() { return totalComplaints; }
    public void setTotalComplaints(long totalComplaints) { this.totalComplaints = totalComplaints; }

    public long getSubmittedCount() { return submittedCount; }
    public void setSubmittedCount(long submittedCount) { this.submittedCount = submittedCount; }

    public long getAssignedCount() { return assignedCount; }
    public void setAssignedCount(long assignedCount) { this.assignedCount = assignedCount; }

    public long getInProgressCount() { return inProgressCount; }
    public void setInProgressCount(long inProgressCount) { this.inProgressCount = inProgressCount; }

    public long getResolvedCount() { return resolvedCount; }
    public void setResolvedCount(long resolvedCount) { this.resolvedCount = resolvedCount; }

    public long getClosedCount() { return closedCount; }
    public void setClosedCount(long closedCount) { this.closedCount = closedCount; }

    public Map<String, Long> getCategoryCounts() { return categoryCounts; }
    public void setCategoryCounts(Map<String, Long> categoryCounts) { this.categoryCounts = categoryCounts; }

    public Map<String, Long> getStatusCounts() { return statusCounts; }
    public void setStatusCounts(Map<String, Long> statusCounts) { this.statusCounts = statusCounts; }

    public List<MonthlyTrendDto> getMonthlyTrends() { return monthlyTrends; }
    public void setMonthlyTrends(List<MonthlyTrendDto> monthlyTrends) { this.monthlyTrends = monthlyTrends; }

    public static DashboardStatsResponseBuilder builder() { return new DashboardStatsResponseBuilder(); }

    public static class DashboardStatsResponseBuilder {
        private long totalComplaints;
        private long submittedCount;
        private long assignedCount;
        private long inProgressCount;
        private long resolvedCount;
        private long closedCount;
        private Map<String, Long> categoryCounts;
        private Map<String, Long> statusCounts;
        private List<MonthlyTrendDto> monthlyTrends;

        public DashboardStatsResponseBuilder totalComplaints(long totalComplaints) { this.totalComplaints = totalComplaints; return this; }
        public DashboardStatsResponseBuilder submittedCount(long submittedCount) { this.submittedCount = submittedCount; return this; }
        public DashboardStatsResponseBuilder assignedCount(long assignedCount) { this.assignedCount = assignedCount; return this; }
        public DashboardStatsResponseBuilder inProgressCount(long inProgressCount) { this.inProgressCount = inProgressCount; return this; }
        public DashboardStatsResponseBuilder resolvedCount(long resolvedCount) { this.resolvedCount = resolvedCount; return this; }
        public DashboardStatsResponseBuilder closedCount(long closedCount) { this.closedCount = closedCount; return this; }
        public DashboardStatsResponseBuilder categoryCounts(Map<String, Long> categoryCounts) { this.categoryCounts = categoryCounts; return this; }
        public DashboardStatsResponseBuilder statusCounts(Map<String, Long> statusCounts) { this.statusCounts = statusCounts; return this; }
        public DashboardStatsResponseBuilder monthlyTrends(List<MonthlyTrendDto> monthlyTrends) { this.monthlyTrends = monthlyTrends; return this; }

        public DashboardStatsResponse build() {
            return new DashboardStatsResponse(totalComplaints, submittedCount, assignedCount, inProgressCount, resolvedCount, closedCount, categoryCounts, statusCounts, monthlyTrends);
        }
    }

    public static class MonthlyTrendDto {
        private String month;
        private long count;

        public MonthlyTrendDto() {}
        public MonthlyTrendDto(String month, long count) {
            this.month = month;
            this.count = count;
        }

        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public long getCount() { return count; }
        public void setCount(long count) { this.count = count; }
    }
}
