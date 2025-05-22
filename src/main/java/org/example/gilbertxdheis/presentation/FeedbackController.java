package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.FeedbackService;
import org.example.gilbertxdheis.domain.Feedback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/submit")
    public String submitFeedback(@RequestParam int orderId,
                                 @RequestParam int itemId,
                                 @RequestParam int rating,
                                 @RequestParam String comment) {
        feedbackService.saveFeedback(orderId, itemId, rating, comment);
        return "redirect:/item/" + itemId; // Redirect to the item's page
    }

    @GetMapping("/{itemId}")
    public String getFeedback(@PathVariable int itemId, Model model) {
        List<Feedback> feedbackList = feedbackService.getFeedbackByItem(itemId);
        model.addAttribute("feedbackList", feedbackList);
        return "itemDetails"; // Render the item details page with feedback
    }
}