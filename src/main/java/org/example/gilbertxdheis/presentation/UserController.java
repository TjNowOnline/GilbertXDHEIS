package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.UserService;
import org.example.gilbertxdheis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/create")
    public String showCreateUserPage() {
        return "createUser"; // Ensure this matches the Thymeleaf template name
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            if (user.getRole() == null) {
                user.setRole("USER");
            }
            userService.createUser(user); // Save user to the database
            redirectAttributes.addFlashAttribute("success", "User created successfully!");

            return "redirect:/login"; // Redirect to login page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create user.");
            return "createUser"; // Return to the form in case of error
        }
    }
}