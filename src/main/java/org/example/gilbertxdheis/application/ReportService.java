package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.domain.Report;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final JdbcUserRepository userRepository;

    public ReportService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void reportSeller(Long reporterId, Long sellerId, String reason) {
        // Validate inputs
        if (reporterId == null || sellerId == null) {
            throw new IllegalArgumentException("Reporter ID and Seller ID must not be null");
        }
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason must not be empty");
        }

        // Check if reporter exists
        if (!userRepository.existsById(reporterId)) {
            throw new IllegalArgumentException("Reporter with ID " + reporterId + " does not exist");
        }

        // Check if seller exists
        if (!userRepository.existsById(sellerId)) {
            throw new IllegalArgumentException("Seller with ID " + sellerId + " does not exist");
        }

        // Prevent self-reporting
        if (reporterId.equals(sellerId)) {
            throw new IllegalArgumentException("You cannot report yourself");
        }

        // Create and save the report
        Report report = new Report();
        report.setReporterId(reporterId.intValue());
        report.setSellerId(sellerId.intValue());
        report.setReason(reason.trim());
        report.setStatus("Pending");

        userRepository.saveReport(report);
    }
}