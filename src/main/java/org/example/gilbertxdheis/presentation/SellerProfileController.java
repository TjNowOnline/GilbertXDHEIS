package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.domain.Profile;
import org.example.gilbertxdheis.application.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SellerProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/seller/{userId}")
    public String getSellerProfile(@PathVariable Long userId, Model model) {
        Profile profile = profileService.getProfileByUserId(userId);
        if (profile != null && "ROLE_SELLER".equals(profile.getRole())) {
            model.addAttribute("profile", profile);
            return "sellerProfile";
        }
        return "error"; // Redirect to an error page if the user is not a seller
    }
}