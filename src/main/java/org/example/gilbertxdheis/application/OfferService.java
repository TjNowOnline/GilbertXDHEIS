package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.domain.Offer;
import org.example.gilbertxdheis.infrastructure.JdbcItemRepository;
import org.example.gilbertxdheis.infrastructure.JdbcOfferRepository;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    private final JdbcOfferRepository offerRepository;
    private final JdbcItemRepository itemRepository;

    public OfferService(JdbcOfferRepository offerRepository, JdbcItemRepository itemRepository) {
        this.offerRepository = offerRepository;
        this.itemRepository = itemRepository;

    }

    public void createOffer(int userId, int itemId, double proposedPrice) {
        Item item = (Item) itemRepository.read(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item does not exist");
        }
        double currentPrice = item.getPrice(); // kalder på vores orginale pris
        double minimumAllowedPrice = currentPrice * 0.7; // Kan ikke bydes under 70% af varens pris.

        if (proposedPrice >= currentPrice) {
            throw new IllegalArgumentException("Proposed price is greater than current price");
        }

        if (proposedPrice < minimumAllowedPrice) {
            throw new IllegalArgumentException(
                    String.format("Proposed price is less than minimum allowed price of %f", minimumAllowedPrice));
        }


        //Offer objekt
        Offer newOffer = new Offer(0, userId, itemId, proposedPrice, "PENDING");
        offerRepository.save(newOffer);
    }

    public void acceptOffer(int offerId) {
        offerRepository.updateStatus(offerId, "ACCEPTED");
    }
    public void rejectOffer(int offerId) {
        offerRepository.updateStatus(offerId, "REJECTED");
    }
}



