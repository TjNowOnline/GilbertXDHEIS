
package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.BlogPostService;
import org.example.gilbertxdheis.domain.BlogPost;
import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.infrastructure.JdbcItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private JdbcItemRepository itemRepository;

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping("/sell")
    public String showSellForm(HttpSession session, RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("Accessing /sell, userId in session: " + userId);
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to sell an item.");
            return "redirect:/login";
        }
        return "sell"; // Maps to sell.html in templates folder
    }

    @PostMapping("/api/items")
    public String createItem(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("condition") String condition,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image,
            @RequestParam("isActive") boolean isActive,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("Submitting /api/items, userId in session: " + userId);
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to sell an item.");
            return "redirect:/login";
        }

        try {
            // Save image to server
            String imageUrl = saveImage(image);

            // Create new Item with sellerId from session
            Item item = new Item(0, title, description, price, condition, imageUrl, category, userId.intValue(), isActive);
            itemRepository.create(item);

            redirectAttributes.addFlashAttribute("message", "Item listed successfully!");
            return "redirect:/";
        } catch (IOException e) {
            System.out.println("Image upload failed: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Failed to upload image.");
            return "redirect:/sell";
        }
    }

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        List<BlogPost> blogPosts = blogPostService.getRecentBlogPosts(5);
        if (blogPosts == null) {
            blogPosts = new ArrayList<>(); // Initialize to avoid null
        }
        model.addAttribute("recentBlogPosts", blogPosts); // Correct attribute name
        model.addAttribute("items", items);
        return "index"; // Maps to index.html
    }

    private String saveImage(MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
            return "/uploads/" + fileName;
        }
        return "/images/default.jpg"; // Fallback image
    }
}