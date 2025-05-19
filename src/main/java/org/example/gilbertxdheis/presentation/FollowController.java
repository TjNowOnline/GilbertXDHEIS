package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.FollowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow-seller")
    public String followSeller(
            @RequestParam("followerId") Long followerId,
            @RequestParam("sellerId") Long sellerId,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");
        if (loggedInUserId == null || !loggedInUserId.equals(followerId)) {
            redirectAttributes.addFlashAttribute("error", "Uautoriseret handling");
            return "redirect:/login";
        }

        try {
            followService.followSeller(followerId, sellerId);
            redirectAttributes.addFlashAttribute("message", "Du følger nu sælgeren");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/seller-profile?sellerId=" + sellerId;
    }

    @PostMapping("/unfollow-seller")
    public String unfollowSeller(
            @RequestParam("followerId") Long followerId,
            @RequestParam("sellerId") Long sellerId,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");
        if (loggedInUserId == null || !loggedInUserId.equals(followerId)) {
            redirectAttributes.addFlashAttribute("error", "Uautoriseret handling");
            return "redirect:/login";
        }

        try {
            followService.unfollowSeller(followerId, sellerId);
            redirectAttributes.addFlashAttribute("message", "Du følger ikke længere sælgeren");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/seller-profile?sellerId=" + sellerId;
    }
}