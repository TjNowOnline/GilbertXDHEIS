package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.domain.Profile;
import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.domain.Feedback;
import org.example.gilbertxdheis.application.ProfileService;
import org.example.gilbertxdheis.application.SellerStatsService;
import org.example.gilbertxdheis.application.ItemService;
import org.example.gilbertxdheis.application.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class SellerProfileController {

    private final ProfileService profileService;
    private final SellerStatsService sellerStatsService;
    private final ItemService itemService;
    private final FeedbackService feedbackService;

    @Autowired
    public SellerProfileController(ProfileService profileService, SellerStatsService sellerStatsService,
                                   ItemService itemService, FeedbackService feedbackService) {
        this.profileService = profileService;
        this.sellerStatsService = sellerStatsService;
        this.itemService = itemService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/seller/{userId}")
    public String getSellerProfile(@PathVariable Long userId, Model model) {
        Optional<Profile> optionalProfile = profileService.getProfileByUserId(userId);
        if (optionalProfile.isEmpty()) {
            model.addAttribute("error", "Seller profile not found.");
            return "error";
        }

        Profile profile = optionalProfile.get();
        if (!"ROLE_SELLER".equals(profile.getRole())) {
            model.addAttribute("error", "User is not a seller.");
            return "error";
        }

        model.addAttribute("profile", profile);
        model.addAttribute("sellerStats", sellerStatsService.getSellerStats(userId));
        model.addAttribute("itemsForSale", itemService.getItemsForSaleBySeller(userId));
        model.addAttribute("soldItems", itemService.getSoldItemsBySeller(userId));
        model.addAttribute("feedbackList", feedbackService.getFeedbackBySeller(userId));

        return "sellerProfile";
    }
}