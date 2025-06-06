package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.OfferService;
import org.example.gilbertxdheis.domain.Offer;
import org.example.gilbertxdheis.infrastructure.JdbcOfferRepository;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.example.gilbertxdheis.application.ItemService;
import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.infrastructure.JdbcItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OfferController {
    private final JdbcItemRepository itemRepository;
    private final ItemService itemService;
    private final JdbcOfferRepository offerRepository;
    private final OfferService offerService;

    public OfferController(JdbcItemRepository itemRepository, ItemService itemService, JdbcOfferRepository offerRepository, OfferService offerService) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
        this.offerRepository = offerRepository;
        this.offerService = offerService;
    }

    @GetMapping("/item/{itemId}/offer")
    public String showOffer(@PathVariable int itemId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
            // if user is not logged in, redirect to login page
        }

        try {
            Item item = itemService.findById((long) itemId);
            if (item == null) {
                return "redirect:/index";
            }
            Offer offer = new Offer(0, 0, 0, 0.0, "PENDING");
            model.addAttribute("offer", offer);
            model.addAttribute("item", item);

            return "offer";

        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());
            return "redirect:/index";
        }
    }
    //
    @PostMapping("/api/item/{itemId}/offers")
    public String createOffer(@PathVariable int itemId, @RequestParam double proposedPrice,
                              Model model, HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            Item item = itemService.findById((long) itemId);
            if (item == null) {
                return "redirect:/index";
            }
            Offer offer = new Offer(0, 0, 0, 0.0, "PENDING");
            offer.setUserId(userId.intValue());
            offer.setItemId(itemId);
            offer.setProposedPrice(proposedPrice);
            offerRepository.save(offer);

            redirectAttributes.addFlashAttribute("success", "Offer submitted successfully.");
            return "redirect:/item/" + itemId;

        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/item/" + itemId + "/offer";
        }

    }
    @PostMapping("/offer/{offerId}/accept")
    public String acceptOffer(@PathVariable int offerID, RedirectAttributes redirectAttributes) {
        try {
            offerService.acceptOffer(offerID);
            redirectAttributes.addFlashAttribute("success", "offer accepted");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not accept offer.");
        }return"my-profile";
        }
    }

