package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.ProfileService;
import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.domain.Profile;
import org.example.gilbertxdheis.domain.User;
import org.example.gilbertxdheis.infrastructure.JdbcItemRepository;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MyProfileController {

    private final ProfileService profileService;
    private final JdbcItemRepository itemRepository;
    private final JdbcUserRepository userRepository;

    public MyProfileController(ProfileService profileService, JdbcItemRepository itemRepository, JdbcUserRepository userRepository) {
        this.profileService = profileService;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"/myProfile", "/my-profile"})
    public String myProfile(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        // Fetch the user from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Redirect based on role
        if ("ROLE_ADMIN".equals(user.getRole())) {
            return "redirect:/admin";
        } else if ("ROLE_MODERATOR".equals(user.getRole())) {
            return "redirect:/moderator";
        }

        // Get profile
        Profile profile = profileService.getProfileByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        // Fetch items for sale and sold items
        List<Item> itemsForSale = itemRepository.findItemsBySellerIdAndStatus(userId, "FOR_SALE");
        List<Item> soldItems = itemRepository.findItemsBySellerIdAndStatus(userId, "SOLD");

        // Set soldItems in profile
        profile.setSoldItems(soldItems);

        // Add attributes to model
        model.addAttribute("profile", profile);
        model.addAttribute("itemsForSale", itemsForSale);

        return "myProfile";
    }
}