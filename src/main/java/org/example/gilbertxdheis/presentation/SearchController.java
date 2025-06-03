package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.application.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    
    private final ItemService itemService;
    
    @Autowired
    public SearchController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @GetMapping("/search")
    public String searchItems(@RequestParam(required = false) String query, Model model) {
        try {
            List<Item> searchResults = itemService.searchItem(query);
            model.addAttribute("items", searchResults);
            model.addAttribute("searchQuery", query);
            return "index";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while searching. Please try again.");
            return "index";
        }
    }
}