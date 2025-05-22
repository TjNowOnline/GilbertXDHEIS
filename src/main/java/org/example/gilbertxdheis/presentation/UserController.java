package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.UserService;
import org.example.gilbertxdheis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/create")
    public String showCreateUserPage() {
        return "createUser"; // Ensure this matches the Thymeleaf template name
    }

    @PostMapping("/users/create")
    public String createUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "createUser"; // Return to the form if validation fails
        }

        try {
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("USER"); // Default role
            }
            userService.createUser(user); // Save user to the database
            redirectAttributes.addFlashAttribute("success", "User created successfully!");
            return "redirect:/login"; // Redirect to login page
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid input: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create user. Please try again.");
        }
        return "createUser"; // Return to the form in case of error
    }
}