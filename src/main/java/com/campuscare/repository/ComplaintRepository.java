package com.campuscare.repository;

import com.campuscare.entity.Category;
import com.campuscare.entity.Complaint;
import com.campuscare.entity.Priority;
import com.campuscare.entity.Status;
import com.campuscare.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findByReferenceNumber(String referenceNumber);

    Page<Complaint> findByCreatedBy(User createdBy, Pageable pageable);

    @Query("SELECT c FROM Complaint c WHERE " +
           "(:userId IS NULL OR c.createdBy.id = :userId) AND " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:category IS NULL OR c.category = :category) AND " +
           "(:priority IS NULL OR c.priority = :priority) AND " +
           "(:search IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(c.referenceNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(c.location) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Complaint> findFilteredComplaints(@Param("userId") Long userId,
                                           @Param("status") Status status,
                                           @Param("category") Category category,
                                           @Param("priority") Priority priority,
                                           @Param("search") String search,
                                           Pageable pageable);

    long countByStatus(Status status);

    long countByCategory(Category category);

    @Query("SELECT c.status, COUNT(c) FROM Complaint c GROUP BY c.status")
    List<Object[]> countByStatusGroup();

    @Query("SELECT c.category, COUNT(c) FROM Complaint c GROUP BY c.category")
    List<Object[]> countByCategoryGroup();

    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-%m') AS month, COUNT(*) AS count " +
                   "FROM complaints GROUP BY month ORDER BY month DESC LIMIT 12",
           nativeQuery = true)
    List<Object[]> countMonthlyTrend();
}
