package org.example.gilbertxdheis.presentation;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Serves the login.html page
    }


    @GetMapping("/profile")
    public String handleProfileRedirect(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            return "redirect:/myProfile";
        }
        return "redirect:/login";
    }
}