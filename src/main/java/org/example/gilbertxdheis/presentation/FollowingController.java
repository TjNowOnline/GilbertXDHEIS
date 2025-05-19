package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class FollowingController {

    private final JdbcUserRepository userRepository;

    public FollowingController(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/following")
    public String showFollowingPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<Item> items = userRepository.getItemsFromFollowedSellers(userId);
        model.addAttribute("items", items);
        return "following";
    }
}