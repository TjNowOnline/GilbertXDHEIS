package org.example.gilbertxdheis.presentation;

import org.example.gilbertxdheis.application.OfferService;
import org.example.gilbertxdheis.domain.Offer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OfferRestController {
    private final OfferService offerService;

    public OfferRestController(OfferService offerService){
        this.offerService = offerService;
    }

    @PostMapping("/item/{itemId}/offers")
    public ResponseEntity<String>createOffer(
            @PathVariable int itemId,
            @RequestParam double proposedPrice,
            @RequestParam int userId){
        try {
            offerService.createOffer(userId, itemId, proposedPrice);
            return ResponseEntity.ok("Offer created successfully.");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

}

