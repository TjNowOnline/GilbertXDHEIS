package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.domain.Profile;
import org.example.gilbertxdheis.application.ProfileService;
import org.example.gilbertxdheis.application.SellerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class SellerProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SellerStatsService sellerStatsService;

    @GetMapping("/seller/{userId}")
    public String getSellerProfile(@PathVariable Long userId, Model model) {
        Optional<Profile> optionalProfile = profileService.getProfileByUserId(userId);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            if ("ROLE_SELLER".equals(profile.getRole())) { // Check the role of the user
                model.addAttribute("profile", profile);
                model.addAttribute("sellerStats", sellerStatsService.getSellerStats(userId));
                return "sellerProfile";
            }
        }
        return "error"; // Redirect to an error page if the user is not a seller
    }
}