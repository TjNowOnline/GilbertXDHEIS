package org.example.gilbertxdheis.application;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.infrastructure.JdbcItemRepository;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



import java.util.List;

@Service
public class ItemService {
    private final JdbcItemRepository itemRepository;
    private final LevenshteinDistance levenshtein = new LevenshteinDistance();
    private static final int MAX_DISTANCE = 2; // Reduced from 2 to 1 for stricter matching

    //Levenshtein is our fuzzy search algorithm

    public ItemService(JdbcItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsForSaleBySeller(Long userId) {
        return itemRepository.findItemsBySellerIdAndStatus(userId, "FOR_SALE");
    }

    public List<Item> getSoldItemsBySeller(Long userId) {
        return itemRepository.findItemsBySellerIdAndStatus(userId, "SOLD");
    }

    public List<Item> getAllItems(){
        return (List<Item>) itemRepository.findAll(); }

    public List<Item> searchItem(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllItems();
        }
        
        String trimmedQuery = query.trim();
        return getAllItems().stream()
                .filter(item -> isItemMatch(item, trimmedQuery))
                .collect(Collectors.toList());
    }

    private boolean isItemMatch (Item item, String query){
        return  isSimilar(item.getTitle(),query) ||
                isSimilar(item.getDescription(),query) ||
                isSimilar(item.getCategory(),query);
    }

    private boolean isSimilar(String text, String query) {
        if (text == null || query == null) {
            return false;
        }

        String lowerText = text.toLowerCase();
        String lowerQuery = query.toLowerCase();
        
        // First check for exact substring match
        if (lowerText.contains(lowerQuery)) {
            return true;
        }
        
        // Then check individual words with Levenshtein
        return Arrays.stream(lowerText.split("\\s+"))
                .anyMatch(word -> 
                    word.length() > 2 && // Only check words longer than 2 characters
                    levenshtein.apply(word, lowerQuery) <= MAX_DISTANCE);
    }


    public Item findById(Long id) {
        return (Item) itemRepository.read(id);
    }
}