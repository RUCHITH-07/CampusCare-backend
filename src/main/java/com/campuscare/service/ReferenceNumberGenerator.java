package com.campuscare.service;

import com.campuscare.repository.ComplaintRepository;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ReferenceNumberGenerator {

    private final ComplaintRepository complaintRepository;
    private final AtomicLong counter = new AtomicLong(0);

    public ReferenceNumberGenerator(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public synchronized String generateReferenceNumber() {
        if (counter.get() == 0) {
            long count = complaintRepository.count();
            counter.set(count);
        }
        long nextId = counter.incrementAndGet();
        int currentYear = Year.now().getValue();
        return String.format("CC-%d-%06d", currentYear, nextId);
    }
}
