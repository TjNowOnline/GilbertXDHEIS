package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.BlogPostService;
import org.example.gilbertxdheis.domain.BlogPost;
import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.domain.Profile;
import org.example.gilbertxdheis.application.ProfileService;
import org.example.gilbertxdheis.domain.User;
import org.example.gilbertxdheis.infrastructure.JdbcBlogPostRepository;
import org.example.gilbertxdheis.infrastructure.JdbcItemRepository;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminModeratorController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private JdbcItemRepository itemRepository;

    @Autowired
    private JdbcUserRepository userRepository;

    @Autowired
    private JdbcBlogPostRepository blogRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
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

        // Fetch all admins and moderators
        List<User> admins = userRepository.findByRole("ADMIN");
        List<User> moderators = userRepository.findByRole("MODERATOR");
        model.addAttribute("admins", admins);
        model.addAttribute("moderators", moderators);

        // Fetch all blog posts
        List<BlogPost> blogPosts = blogRepository.findAll();
        System.out.println("BlogPosts found: " + blogPosts.size());
        model.addAttribute("blogPosts", blogPosts);

        return "admin"; // Maps to admin.html
    }

    @PostMapping("/admin/blog/delete/{id}")
    public String deleteBlogPost(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        blogPostService.deleteBlogPost(id);
        redirectAttributes.addFlashAttribute("success", "Blog post deleted successfully!");
        return "redirect:/admin";
    }



    @GetMapping("/moderator")
    public String moderatorPage(Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
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

        return "moderator"; // Maps to moderator.html
    }
}