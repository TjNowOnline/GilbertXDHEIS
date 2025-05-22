package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.ProfileService;
import org.example.gilbertxdheis.domain.Profile;
import org.example.gilbertxdheis.domain.SellerStats;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
public class MyProfileController {

    private final ProfileService profileService;

    public MyProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/myProfile")
    public String myProfile(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            Profile profile = profileService.getProfileByUserId(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

            // Ensure soldItems is not null
            if (profile.getSoldItems() == null) {
                profile.setSoldItems(Collections.emptyList());
            }

            model.addAttribute("profile", profile);
            return "myProfile";
        }
        return "redirect:/login";
    }
}