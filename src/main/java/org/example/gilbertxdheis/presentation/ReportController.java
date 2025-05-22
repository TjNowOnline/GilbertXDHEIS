package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report-seller")
    public String showReportForm(@RequestParam("sellerId") Long sellerId, Model model, HttpSession session) {
        Long reporterId = (Long) session.getAttribute("userId");
        if (reporterId == null) {
            return "redirect:/login";
        }

        model.addAttribute("reporterId", reporterId);
        model.addAttribute("sellerId", sellerId);
        return "report-seller";
    }

    @PostMapping("/reports/seller")
    public String submitReport(
            @RequestParam("reporterId") Long reporterId,
            @RequestParam("sellerId") Long sellerId,
            @RequestParam("reason") String reason,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");
        if (loggedInUserId == null || !loggedInUserId.equals(reporterId)) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized action.");
            return "redirect:/login";
        }

        try {
            reportService.reportSeller(reporterId, sellerId, reason);
            redirectAttributes.addFlashAttribute("success", "Report submitted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid input: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to submit report. Please try again.");
        }

        return "redirect:/seller-profile?sellerId=" + sellerId;
    }
}