// File: src/main/java/org/example/gilbertxdheis/presentation/BlogController.java
package org.example.gilbertxdheis.presentation;

import jakarta.servlet.http.HttpSession;
import org.example.gilbertxdheis.application.BlogPostService;
import org.example.gilbertxdheis.domain.BlogPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Controller
public class BlogController {

    private final BlogPostService blogPostService;

    @Autowired
    public BlogController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping("/create-blog")
    public String createBlogPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        try {
            // Fetch userId from session
            Object userId = session.getAttribute("userId");
            if (userId == null) {
                redirectAttributes.addFlashAttribute("error", "User not logged in.");
                return "redirect:/login"; // Redirect to login page
            }

            // Handle Long to int conversion
            int createdBy = (userId instanceof Long) ? ((Long) userId).intValue() : (int) userId;
            Date timestamp = new Date(); // Current timestamp

            // Save the uploaded image and get its URL
            String imageUrl;
            try {
                imageUrl = saveImage(image);
            } catch (IOException e) {
                imageUrl = "/images/placeholder-blog.jpg"; // Fallback to default if upload fails
            }

            BlogPost blogPost = new BlogPost(0, title, content, createdBy, timestamp, imageUrl);
            blogPostService.createBlogPost(blogPost);
            redirectAttributes.addFlashAttribute("success", "Blog post created successfully!");
            return "redirect:/my-profile"; // Redirect to the profile page
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            redirectAttributes.addFlashAttribute("error", "Failed to create blog post. Please try again.");
            return "redirect:/my-profile"; // Redirect back to the profile page
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/uploads/blog/" + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
            return "/uploads/blog/" + fileName;
        }
        return "/images/placeholder-blog.jpg"; // Fallback image
    }
}