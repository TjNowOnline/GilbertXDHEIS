package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.infrastructure.JdbcItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final JdbcItemRepository itemRepository;

    public ItemService(JdbcItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsForSaleBySeller(Long userId) {
        return itemRepository.findItemsBySellerIdAndStatus(userId, "FOR_SALE");
    }

    public List<Item> getSoldItemsBySeller(Long userId) {
        return itemRepository.findItemsBySellerIdAndStatus(userId, "SOLD");
    }
}
